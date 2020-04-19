package com.liucan.boot.framework.log;

import com.liucan.boot.persist.mybatis.mapper.SystemLogMapper;
import com.liucan.boot.persist.mybatis.mode.SystemLogWithBLOBs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author liucan
 * @version 2020/4/19
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogService {
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final SystemLogMapper systemLogMapper;

    /**
     * 消息出队,队列为空阻塞等待
     */
    @PostConstruct
    public void pollSystemLog() {
        threadPoolTaskExecutor.submit(() -> {
            while (true) {
                try {
                    LoggerQueue instance = LoggerQueue.getInstance();
                    SystemLogWithBLOBs systemLog = LoggerQueue.getInstance().poll();
                    if (systemLog != null) {
                        systemLogMapper.insert(systemLog);
                    }
                } catch (Exception e) {
                    log.error("保存到保存到数据库异常", e);
                }
            }
        });
    }
}
