package com.liucan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    private String timeout; //超时时间
    private String maxActive; //最大连接数
    private String maxIdle; //最大空闲数
    private String minIdle; //最小空闲数
    private String maxWait; //最大等待时间
    private String testOnBorrow; //使用连接时，检测连接是否成功
    private String testOnReturn; //返回连接时，检测连接是否成功
}
