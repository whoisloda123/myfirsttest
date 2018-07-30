package com.liucan.common.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * @date 2018/7/29
 * @brief
 */
@Component
public class KafkaListeners {

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void processMessage(String content) {
        System.out.println(content);
    }
}
