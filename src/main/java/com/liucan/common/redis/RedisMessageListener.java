package com.liucan.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * @date 2018/8/16
 * @brief redis接收到pub消息监听器
 * 参考资料：https://www.cnblogs.com/qianlizeguo/articles/6856813.html
 * 1.Pub/Sub功能
 * 2.消息订阅者，即subscribe客户端，需要独占链接，即进行subscribe期间，redis-client无法穿插其他操作，
 * 此时client以阻塞的方式等待“publish端”的消息；甚至需要在额外的线程中使用。
 * 3.消息发布者，即publish客户端，无需独占链接
 * 4.spring封装的pub/sub里面消息订阅者其实就是在额外线程里面执行的，可打断点调试
 */
@Slf4j
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 订阅消息
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = (String) jedisCluster.getKeySerializer().deserialize(message.getChannel());
        String channel = (String) jedisCluster.getValueSerializer().deserialize(message.getBody());
        log.info("[redis订阅者/发布者]订阅者收到消息,channel:{}, msg:{}", channel, msg);
    }
}
