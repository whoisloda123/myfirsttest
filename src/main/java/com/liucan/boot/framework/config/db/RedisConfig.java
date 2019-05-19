package com.liucan.boot.framework.config.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liucan.boot.service.redis.RedisMessageListener;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * 一.redis集群哨兵
 *  参考：http://youzhixueyuan.com/redis-high-availability.html
 *  1.每个key会对应一个slot，总共16384个slot均分到不同的节点上面
 *  2.每个节点master，有slave
 *
 * 二.群投票机制
 *  1.所有master参与，每个master都和其他master连接上了的，如果半数以上的master认为某个节点挂掉，就真的挂掉了
 *
 * 三.单线程为何还快
 *  1.完全基于内存
 *  2.使用epoll多路 I/O 复用模型来处理多并发客户端
 *  3.不存在多进程或者多线程导致的切换而消耗CPU
 *
 * 四.持久化
 *  1.RDB快照:过一段时间所有数据所生成的数据快照保存到dump.rdb文件,会单独创建(fork)一个子进程来进行持久化
 *  2.AOF:每隔一段时间（默认每一秒）将执行写命令append写入文件，在恢复的时候可重新执行命令
 *
 * 五.分布式锁
 *
 * 六.缓存问题
 *  1.缓存雪崩：缓存同一时间大面积的失效，这个时候又来了一波请求，结果请求都怼到数据库上，从而导致数据库连接异常
 *      解决方法：给缓存的失效时间，加上一个随机值，避免集体失效。
 *  2.缓存穿透：黑客故意去请求缓存中不存在的数据，导致所有的请求都怼到数据库上，从而数据库连接异常
 *      解决方法：为空也保存到内存里面
 *  3.缓存击穿：某个热销商品过期瞬间，很多请求怼到数据库上
 *  等
 *
 * 七.事务
 *   1.集群不支持事务
 *      a.事务里面操作可以在不同节点,对于多个key的操作,保证不了不会被其他命令插进来,无法保证其原子性,但集群里边的每个节点支持事务，
 *      b.事务不支持,因为redis不是关系型数据库，是key-value不关心业务，没有必要
 *   2.集群不支持pipeline管道
 *      a.批量操作位于不同的slot的时候会失败,因为slot在不同的node,但也支持单个节点
 *      b.单个节点操作：通过key计算出slot，然后通过slot找到对应的节点，然后在节点上面操作
 *   3.集群不支持对所有键进行scan操作
 *
 * 二.spring-redis
 *  参考：https://www.cnblogs.com/EasonJim/p/7803067.html（spring-redis）
 *      http://blog.51cto.com/aiilive/1627455（spring-redis）
 *
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
     * 使用jackson序列化redis缓存对象
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

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
    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * redis,subscribe监听器
     */
    @Bean
    MessageListenerAdapter messageListener(RedisMessageListener redisMessageListener) {
        return new MessageListenerAdapter(redisMessageListener);
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                RedisMessageListener redisMessageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisMessageListener, new ChannelTopic("pubsub:chat-message"));
        return container;
    }

}
