package com.liucan.boot.service.kafka;

/**
 * 日志类消费者
 *
 * @author liucan
 * @version 19-5-15
 */
public abstract class AbstractKafkaConsumerLogger implements IKafkaConsumer {
    @Override
    public String topic() {
        return "topic-logger";
    }
}
