package com.liucan.boot.service.kafka.common;

/**
 * 想消费kafka消息，实现该接口即可
 *
 * @author liucan
 * @version 19-5-15
 */
public interface IKafkaConsumer {
    String topic();

    void process(String message);
}
