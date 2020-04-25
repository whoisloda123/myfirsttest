package com.liucan.boot.service;

import com.liucan.boot.service.db.LoggingService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时处理任务配置
 *
 * @author liucan
 * @version 2020/4/25
 */
@EnableScheduling
@Configuration
@AllArgsConstructor
public class ScheduledService {
    private final LoggingService loggingService;

    /**
     * 处理记录日志信息记录,每天0点执行
     */
    @Scheduled(cron = "0 0 0 * ? *")
    public void cleanLogData() {
        loggingService.cleanLogData();
    }
}
