package com.liucan.boot.framework.config;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author liucan
 * @date 2018/7/29
 */
@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;
    @Value("${spring.kafka.template.default-topic}")
    private String topic;
    @Value("${spring.kafka.consumer.group-id}")
    private String group;
    @Value("${registry.servers}")
    private String zkConnect;

    @Bean
    public ProducerConfig producerConfig() {
        Properties kProProperties = new Properties();
        kProProperties.setProperty("metadata.broker.list", brokers);
        kProProperties.setProperty("producer.type", "async");
        kProProperties.setProperty("compression.codec", "none");
        kProProperties.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        kProProperties.setProperty("request.required.acks", "1");
        kProProperties.setProperty("message.send.max.retries", "5");
        kProProperties.setProperty("retry.backoff.ms", "100");
        kProProperties.setProperty("batch.num.messages", "100");
        return new ProducerConfig(kProProperties);
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        Properties kConProperties = new Properties();
        kConProperties.setProperty("zookeeper.connect", zkConnect);
        kConProperties.setProperty("zookeeper.connection.timeout.ms", "100000");
        kConProperties.setProperty("group.id", group);
        kConProperties.setProperty("auto.commit.enable", "true");
        kConProperties.setProperty("auto.commit.interval.ms", "30000");
        //设置ConsumerIterator的hasNext的超时时间,不设置则永远阻塞直到有新消息来
        //kConProperties.setProperty("consumer.timeout.ms", "5000");
        kConProperties.setProperty("auto.offset.reset", "largest");
        return new ConsumerConfig(kConProperties);
    }

    @Bean
    public Producer<String, String> kafkaProducer(ProducerConfig producerConfig) {
        return new Producer<>(producerConfig);
    }
}
