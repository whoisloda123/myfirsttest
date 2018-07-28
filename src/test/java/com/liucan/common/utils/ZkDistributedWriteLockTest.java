package com.liucan.common.utils;

import com.liucan.BootApplicationTests;
import org.junit.Test;

import java.time.Duration;

/**
 * @author liucan
 * @date 2018/7/28
 * @brief
 */
public class ZkDistributedWriteLockTest extends BootApplicationTests {
    @Test
    public void test() {
        try {
            //创建lock对象
            ZkDistributedWriteLock writeLock = new ZkDistributedWriteLock("192.168.32.128:2183", "distributed-test");
            //线程1
            new Thread(
                    () -> {
                        try {
                            writeLock.lock();
                            //dosomething
                            System.out.println("线程1开始工作");
                            Thread.sleep(Duration.ofSeconds(4).toMillis());
                            writeLock.unlock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            ).start();


            //睡眠2秒
            Thread.sleep(Duration.ofSeconds(2).toMillis());

            //线程2
            new Thread(
                    () -> {
                        try {
                            writeLock.lock();
                            //dosomething
                            System.out.println("线程2开始工作");
                            Thread.sleep(Duration.ofSeconds(5).toMillis());
                            writeLock.unlock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            ).start();

            //睡眠20秒等所有执行完成
            Thread.sleep(Duration.ofSeconds(20).toMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}