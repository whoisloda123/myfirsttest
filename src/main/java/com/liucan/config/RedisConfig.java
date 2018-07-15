package com.liucan.config;

import com.liucan.common.redis.JedisCluster;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief redis配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
@PropertySource(value = "classpath:properties/redis.properties")
public class RedisConfig {
    //mater
    private String hostName;
    private Integer port;
    private String password;
    private String expire;
    private Long timeout;

    //连接池配置
    private Integer maxIdle;
    private Integer maxTotal;
    private Integer maxWaitMillis;
    private Integer minEvictableIdleTimeMillis;
    private Integer numTestsPerEvictionRun;
    private Long timeBetweenEvictionRunsMillis;
    private Boolean testOnBorrow;
    private Boolean testWhileIdle;

    //哨兵，集群
    private String clusterNodes;
    private Integer clusterMaxRedirects;
    private String sentinelNodes;
    private String sentinelMaster;

    /**
     * JedisPoolConfig 连接池
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    /**
     * 1.集群配置:自带类似于哨兵功能,分布式节点
     * 2.至少需要6个redis服务，集群会自动分配3主3从，且主挂了，会自动将slave变为mater
     * 若都挂了，则整个集群down
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        String[] servers = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for (String server : servers) {
            String[] ipAndPort = server.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(clusterMaxRedirects);
        //redisClusterConfiguration.setPassword(RedisPassword.of(password));
        return redisClusterConfiguration;
    }

    /**
     * 哨兵配置,缺点是每个节点都是全量配置，浪费资源
     */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置master的名称
        RedisNode redisNode = new RedisNode(hostName, port);
        redisNode.setName(sentinelMaster);
        redisSentinelConfiguration.setMaster(redisNode);
        //redisSentinelConfiguration.setPassword(RedisPassword.of(password));

        //配置redis的哨兵
        Set<RedisNode> nodes = new HashSet<>();
        String[] servers = sentinelNodes.split(",");
        for (String server : servers) {
            String[] ipAndPort = server.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        }
        redisSentinelConfiguration.setSentinels(nodes);
        return redisSentinelConfiguration;
    }

    /**
     * 单机版connectionFactory
     */
    //@Bean
    public JedisConnectionFactory jedisStandaloneConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(hostName);
        standaloneConfiguration.setPort(port);
        //standaloneConfiguration.setPassword(RedisPassword.of(password));

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        return new JedisConnectionFactory(standaloneConfiguration, jedisClientConfiguration.build());
    }

    /**
     * 集群connectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisClusterConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
                                                                JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    /**
     * 哨兵connectionFactory
     */
    //@Bean
    public JedisConnectionFactory jedisSentinelConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration,
                                                                 JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(redisSentinelConfiguration, jedisPoolConfig);
    }

    /**
     * 实例化RedisTemplate对象
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 实例化jedisCluster对象
     */
    @Bean
    public JedisCluster jedisCluster(RedisTemplate<String, Object> redisTemplate) {
        JedisCluster jedisCluster = new JedisCluster();
        jedisCluster.setRedisTemplate(redisTemplate);
        return jedisCluster;
    }
}
