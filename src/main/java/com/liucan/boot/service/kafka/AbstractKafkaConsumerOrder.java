package com.liucan.boot.service.kafka;

/**
 * 订单类消费者
 *
 * @author liucan
 * @version 19-5-15
 */
public abstract class AbstractKafkaConsumerOrder implements IKafkaConsumer {
    @Override
    public String topic() {
        return "topic-order";
    }
}
