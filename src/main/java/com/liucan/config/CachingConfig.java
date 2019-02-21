package com.liucan.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liucan
 * @date 2018/7/15
 * @brief 1.EnableCaching，spring会默认创建一个CacheManager的bean，也可以使用自己的缓存管理
 *        2.此处用redis的缓存管理RedisCacheManager,
 *        3.好处：spring cache只适合单机环境，集群情况下是不同步的，而redis是分布式的服务
 *        4.用RedisCacheManager管理的，放入缓存的对象必须可序列化，因为其实是放入redis
 *        5.Cacheable:一般用于查询操作，根据key查询缓存,没有查询更新缓存
 *          CachePut: 一般用于更新和插入操作，每次都会请求缓存，通过key操作redis，没有则插入，有则更新
 *          CacheEvict：根据key删除缓存中的数据。allEntries=true表示删除缓存中的所有数据
 */
@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
    public static final String ENTRY_TTL_1M = "entry_ttl_1m"; //key的过期时间
    public static final String ENTRY_TTL_2M = "entry_ttl_2m"; //key的过期时间
    /**
     * 自定义key生成策略，如果在Cache注解上没有指定key的话会调用keyGenerator自动生成
     * 类名+方法名+参数(适用于分布式缓存)，默认key生成策略分布式下有可能重复被覆盖
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("(");
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            sb.append(")");
            return sb.toString();
        };
    }

    /**
     * cacheManager:用RedisCacheManager
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                          Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        Map<String, RedisCacheConfiguration> map = new TreeMap<>();
        map.put(ENTRY_TTL_1M, redisCacheConfiguration(jackson2JsonRedisSerializer, 1));
        map.put(ENTRY_TTL_2M, redisCacheConfiguration(jackson2JsonRedisSerializer, 2));
        return RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(map)
                .build();
    }

    private RedisCacheConfiguration redisCacheConfiguration(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer,
                                                            int minutes) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        redisCacheConfiguration.entryTtl(Duration.ofMinutes(minutes));
        return redisCacheConfiguration;
    }
}
