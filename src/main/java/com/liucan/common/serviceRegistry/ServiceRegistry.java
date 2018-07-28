package com.liucan.common.serviceRegistry;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author liucan
 * @date 2018/7/23
 * @brief zookeeper服务注册
 *        zooKeeper用处
 *        1.配置管理
 *        2.分布式服务集群管理，服务注册，监控，master选举等等
 *        3.分布式锁，分布式队列等等
 */
@Slf4j
public class ServiceRegistry implements Watcher {
    private static final int SESSION_TIMEOUT = 5000;
    private static final String REGISTRY_PATH = "/registry";
    private static CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zk;

    public ServiceRegistry(String zkServers) {
        try {
            log.info("[服务注册]开始连接zookeeper");
            //禁用sasl认证，否则会报错,不禁用不影响运行
            System.setProperty("zookeeper.sasl.client", "false");
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await(); //等待创建成功
        } catch (Exception e) {
            log.error("[服务注册]创建zk失败", e);
        }
    }

    public void register(String serviceName, String serviceAddress) {
        try {
            //创建根节点：持久节点
            if (zk.exists(REGISTRY_PATH, false) == null) {
                zk.create(REGISTRY_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                log.info("[服务注册]创建rootNode:{}", REGISTRY_PATH);
            }
            //创建服务节点：持久节点
            String servicePath = REGISTRY_PATH + "/" + serviceName;
            if (zk.exists(servicePath, false) == null) {
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                log.info("[服务注册]创建serviceNode:{}", servicePath);
            }
            //创建地址节点：临时顺序节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("[服务注册]创建addressNode:{}", addressNode);
        } catch (Exception e) {
            log.error("[服务注册]注册失败,serviceName:{},serviceAddress:{}", serviceName, serviceAddress, e);
        }
    }

    public void close() {
        if (zk != null) {
            try {
                zk.close();
                log.info("[服务注册]关闭zookeeper成功");
            } catch (Exception e) {
                log.info("[服务注册]关闭zookeeper失败", e);
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
            log.info("[服务注册]连接zookeeper成功");
        }
    }
}
