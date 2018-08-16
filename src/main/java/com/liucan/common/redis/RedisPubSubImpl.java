package com.liucan.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * @date 2018/8/16
 * @brief redis pub/sub模式的publish发布者
 */
@Slf4j
@Component
public class RedisPubSubImpl {
    public final String channel = "pubsub:chat-message";
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 发布消息
     */
    public void publish(String message) {
        log.info("[redis订阅者/发布者]发布者消息,channel:{}, msg:{}", channel, message);
        jedisCluster.convertAndSend(channel, message);
    }
}
