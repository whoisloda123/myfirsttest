package com.liucan.boot.framework.log;

import com.liucan.boot.persist.mybatis.mapper.SystemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author liucan
 * @version 2020/4/19
 */
@Slf4j
@Service
public class LogService {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private SystemLogMapper systemLogMapper;

    /**
     * 消息出队,队列为空阻塞等待
     */
    @PostConstruct
    public void takeSystemLog() {
        threadPoolTaskExecutor.submit(() -> {
            while (true) {
                try {
                    systemLogMapper.insert(LoggerQueue.getInstance().take());
                } catch (Exception e) {
                    log.error("保存到保存到数据库异常", e);
                }
            }
        });
    }
}
