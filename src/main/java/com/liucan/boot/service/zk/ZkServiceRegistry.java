package com.liucan.boot.service.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * zookeeper:分布式系统中的协调系统，提供配置管理、分布式服务的集群管理，服务注册、监控、分布式锁和分布式队列
 *  本身就是集群模式
 * 参考：https://www.cnblogs.com/felixzh/p/5869212.html
 *  1.文件系统：
 *      核心是类似于linux文件系统，节点类型：持久化目录节点 、持久化顺序目录节点 、临时目录节点、临时顺序目录节点
 *  2.通知机制
 *      只关心监听他的目录节点，当目录节点的发生改变（数据改变、被删除、子目录节点增加删除）会通知
 *  3.配置管理
 *      服务节点的配置（mysql的ip，port，redis的地址等）放zookeeper，且发生改变时会通知所有服务
 *  4.集群管理(是否有机器退出和加入、选举master)
 *      a.是否有机器退出和加入:所有机器在父目录下建立临时顺序子节点，监听父目录下子节点变化，所有临时节点加入和删除都会通知到其他节点
 *      b.选举maser每次选最小节点即可
 *  5.分布式锁
 *      a.独占锁：创建 /distribute_lock节点成功的则获得锁，其他则监控该节点，释放锁则删除该节点，其他节点收到删除通知后进行创建 /distribute_lock节点
 *      b.公平锁： /distribute_lock已经存在，创建该节点临时顺序子节点，和选举master一样的，最小节点获得锁，同时子节点监听比自己小一点的子节点
 *  6.分布式队列
 *      a.同步队列，当一个队列的成员都聚齐时，这个队列才可用，否则一直等待所有成员到达（在约定目录下创建临时目录节点，监听子节节点数目是否是我们要求的数目）
 *      b.队列按照 FIFO 方式进行入队和出队操作(和分布式锁一样的，最小的先出队列)
 */
@Slf4j
public class ZkServiceRegistry implements Watcher {
    private static final int SESSION_TIMEOUT = 30000;
    private static final String REGISTRY_PATH = "/registry";
    private static CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zk;

    public ZkServiceRegistry(String zkServers) {
        try {
            log.info("[服务注册]开始连接zookeeper");
            //禁用sasl认证，否则会报错,不禁用不影响运行
            System.setProperty("zookeeper.sasl.client", "false");
            //异步创建的
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
