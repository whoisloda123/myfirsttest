package com.liucan.boot.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * 原理:https://www.cnblogs.com/xifenglou/p/7251112.html
 *  https://blog.csdn.net/qq_29186199/article/details/80827085
 * spring:
 *        1.参考资料：https://blog.csdn.net/imgxr/article/details/80130878
 *        2.参考资料：https://blog.csdn.net/lifuxiangcaohui/article/details/51374862
 */
@Slf4j
@Component
public class KafkaListeners {

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void processMessage(ConsumerRecord<?, ?> record) {
        log.info("[kafka]接受到kafka消息topic:{}, record:{}", record.topic(), record);
    }
}
