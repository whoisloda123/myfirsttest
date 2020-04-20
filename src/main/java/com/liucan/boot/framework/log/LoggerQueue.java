package com.liucan.boot.framework.log;

import com.liucan.boot.persist.mybatis.mode.SystemLogWithBLOBs;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liucan
 * @version 2020/4/19
 */
@Slf4j
public class LoggerQueue {

    /*
     * 阻塞队列
     */
    private final BlockingQueue<SystemLogWithBLOBs> blockingQueue = new LinkedBlockingQueue<>(10000);

    private final static LoggerQueue loggerQueue = new LoggerQueue();

    private LoggerQueue() {

    }

    public static LoggerQueue getInstance() {
        return loggerQueue;
    }

    /**
     * 消息入队,队列满了直接返回，不阻塞
     *
     * @param log 日志消息体
     */
    public void offer(SystemLogWithBLOBs log) {
        this.blockingQueue.offer(log);
    }


    /**
     * 消息出队
     *
     * @return 日志消息体
     */
    public SystemLogWithBLOBs take() throws InterruptedException {
        return this.blockingQueue.take();
    }
}
