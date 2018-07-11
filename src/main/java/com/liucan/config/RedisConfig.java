package com.liucan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

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
    private String ip;
    private String port;
    private String password;
    private String expire;
    private String timeout;

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
    private String clusterNodes; //集群
    private Integer clusterMaxRedirects; //集群

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

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        return jedisConnectionFactory;
    }
}
