package com.liucan.boot.web.controller;

import com.liucan.boot.framework.annotation.LoginCheck;
import com.liucan.boot.framework.annotation.UserId;
import com.liucan.boot.framework.config.CachingConfig;
import com.liucan.boot.service.db.JooqService;
import com.liucan.boot.service.db.UserInfoJdbcTemplateService;
import com.liucan.boot.service.db.UserInfoMybatisService;
import com.liucan.boot.service.dubbo.BootDubboServiceImpl;
import com.liucan.boot.service.kafka.common.KafkaService;
import com.liucan.boot.service.redis.RedisPubSub;
import com.liucan.boot.service.redis.RedisTemplateService;
import com.liucan.boot.web.common.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 */
@Slf4j
@RestController
@RequestMapping("bootlearn")
@AllArgsConstructor
public class MyRestController {
    private final UserInfoJdbcTemplateService userInfoJdbcTemplateService;
    private final UserInfoMybatisService userInfoMybatisService;
    private final StringRedisTemplate redisTemplate;
    private final BootDubboServiceImpl bootDubboService;
    private final RedisPubSub redisPubSub;
    private final RedisTemplateService redisTemplateService;
    private final KafkaService kafkaService;
    private final JooqService jooqService;

    @Cacheable(cacheNames = CachingConfig.ENTRY_TTL_1M, cacheManager = "redisCacheManager", keyGenerator = "keyGenerator")
    @GetMapping("find_name")
    public String findName(@RequestParam("user_id") Integer userId) {
        return userInfoJdbcTemplateService.queryUser(userId);
    }

    @GetMapping("find_name1")
    public CommonResponse findName1(@RequestParam("user_id") Integer userId) {
        return userInfoMybatisService.getName(userId);
    }

    @Cacheable("userInfo")
    @GetMapping("find_phone")
    public CommonResponse findPhone(@RequestParam("user_id") Integer userId) {
        return userInfoMybatisService.getUserPhone(userId);
    }

    @PostMapping("redis_set")
    public CommonResponse redisSet(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return CommonResponse.ok();
    }

    @GetMapping("redis_set")
    public Object redisSet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("kafka")
    public CommonResponse kafka() {
        for (int i = 0; i < 50; i++) {
            kafkaService.send("topic-logger", String.valueOf(i), "kafka消息" + i);
            kafkaService.send("topic-order", String.valueOf(i), "kafka消息" + i);
        }
        return CommonResponse.ok();
    }

    @GetMapping("dubbo")
    public CommonResponse dubbo(@RequestParam("user_id") Integer userId) {
        return CommonResponse.ok(bootDubboService.getUserName(userId));
    }

    @GetMapping("redisPubSub")
    public CommonResponse redisPubSub(String message) {
        redisPubSub.publish(CommonResponse.ok(message));
        return CommonResponse.ok();
    }

    @GetMapping("spring-redis")
    public CommonResponse springRedis() {
        redisTemplateService.SpringRedis();
        return CommonResponse.ok();
    }

    @GetMapping("annotaiton")
    @LoginCheck
    public CommonResponse annotaiton(@UserId Integer userId) {
        return CommonResponse.ok(userId);
    }

    @GetMapping("jooq")
    public CommonResponse jooq(Integer userId) {
        return CommonResponse.ok(jooqService.getUserName(userId));
    }
}

