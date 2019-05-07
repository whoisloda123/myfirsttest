package com.liucan.boot.service.redis;

import com.alibaba.fastjson.JSONObject;
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
public class RedisPubSub {
    protected final String channel = "pubsub:chat-message";
    @Autowired
    protected LedisCluster ledisCluster;

    /**
     * 发布消息
     */
    public void publish(Object object) {
        String msg = JSONObject.toJSONString(object);
        log.info("[redis订阅者/发布者]发布者消息,channel:{}, msg:{}", channel, msg);
        ledisCluster.convertAndSend(channel, msg);
    }
}
