package com.liucan.boot.service.redis;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * 2018/8/16
 * redis pub/sub模式的publish发布者
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisPubSub {
    private final static String channel = "pubsub:chat-message";
    private final StringRedisTemplate redisTemplate;

    /**
     * 发布消息
     */
    public void publish(Object object) {
        String msg = JSONObject.toJSONString(object);
        log.info("[redis订阅者/发布者]发布者消息,channel:{}, msg:{}", channel, msg);
        redisTemplate.convertAndSend(channel, msg);
    }
}
