package com.liucan.boot.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * 原理:https://www.cnblogs.com/xifenglou/p/7251112.html
 *  https://blog.csdn.net/qq_29186199/article/details/80827085
 *
 * 一.概念:
 *  a.producer生产者,broker:kafka集群,consumer消费者,topic消息类型,cunsumer-group消费组
 *  b.partition:分片,一个topic包含多个partition
 *  c.leader:多个partiton里面的一个角色,consumer和producer只和leader交互
 *  d.follower:多个partiton里面的多个角色,只负责同步leader数据
 *  e.zookeeper:存储集群的信息
 *
 * 二.producer生产消息
 *  a.写入方式:push 模式将消息发布到 broker,每条消息append到partition中,顺序写磁盘
 *  b.消息路由:发送消息时,根据分区算法找到一个paration,涉及到topic,paration,key,msg
 *      1.指定paration则直接使用
 *      2.未指定 patition 但指定 key，通过对 key 的 value 进行hash 选出一个 patition
 *      3.patition 和 key 都未指定，使用轮询选出一个 patition
 *  c.写入流程:
 *      1.producer 从zookeeper中找到该 partition 的 leader
 *      2.消息发送给leader,leader写入本地log
 *      3.followers从leader中pull消息,发送ack
 *      4.leader收到所有followers的ack后,提交commmin,然后返回producer,相当于要所有的followers都有消息才会commit
 *
 *  三.broker保存消息
 *   a..存储方式:物理上把 topic 分成一个或多个 patition（对应 server.properties 中的 num.partitions=3 配置）
 *      ，每个 patition 物理上对应一个文件夹（该文件夹存储该 patition 的所有消息和索引文件）
 *   b.存储策略:无论消息是否被消费，kafka 都会保留所有消息
 *   c.topic创建
 *      1.controller 在 ZooKeeper 的 /brokers/topics 节点上注册 watcher，当 topic 被创建，则 controller
 *          会通过 watch 得到该 topic 的 partition/replica 分配
 *      2.controller从 /brokers/ids 读取当前所有可用的 broker 列表,然后选leader等等
 *   d.topic删除
 *      1.controller 在 zooKeeper 的 /brokers/topics 节点上注册 watcher，当 topic 被删除，
 *          则 controller 会通过 watch 得到该 topic 的 partition/replica 分配。
 *      2.后续操作
 *   e.replication
 *      当 partition 对应的 leader 宕机时，需要从 follower 中选举出新 leader。
 *      在选举新leader时，一个基本的原则是，新的 leader 必须拥有旧 leader commit 过的所有消息
 *  四.consumer 消费消息
 *      a.consumer group
 *          一个消息只能被 group 内的一个 consumer 所消费，且 consumer 消费消息时不关注 offset，最后一个 offset 由 zookeeper 保存。
 *          但是多个 group 可以同时消费这个 partition
 *      b.消费方式:consumer 采用 pull 模式从 broker 中读取数据
 *          1.push模式容易造成消费者压力大
 *          2.pull可让消费者自己处理,简化kafka-borker设计
 *      c.消息处理方式:
 *          1.At most once最多一次,读完消息马上commit,然后在处理消息,如果处理消息异常,则下次不会在读到上一次消息了
 *          2.At least once(默认方式)最少一次,读完消息,然后处理消息,如果因异常导致没有commit,下次会重新读取到
 *          3.Exactly once刚好一次,比较难
 *
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
        Producer objectObjectProducer = new KafkaProducer<String, String>();
        ProducerRecord producerRecord = new ProducerRecord("11", "key", "msg");
        objectObjectProducer.send(producerRecord);
    }
}
