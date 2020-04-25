package com.liucan.boot.service.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liucan.boot.persist.mybatis.mapper.LogginEventProperyMapper;
import com.liucan.boot.persist.mybatis.mapper.LoggingEventExceptionMapper;
import com.liucan.boot.persist.mybatis.mapper.LoggingEventMapper;
import com.liucan.boot.persist.mybatis.mode.LoggingEvent;
import com.liucan.boot.persist.mybatis.mode.LoggingEventException;
import com.liucan.boot.persist.mybatis.mode.LoggingEventProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liucan
 * @version 2020/4/25
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoggingService {
    private final LoggingEventMapper loggingEventMapper;
    private final LoggingEventExceptionMapper loggingEventExceptionMapper;
    private final LogginEventProperyMapper logginEventProperyMapper;

    /**
     * 处理记录日志信息记录,每天0点执行，删除头一天的数据
     */
    public void cleanLogData() {
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        //可能需要修改
        long endTime = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long startTime = localDateTime.minusDays(1).toInstant(ZoneOffset.of("+8")).toEpochMilli();

        log.info("[日志清除]开始清除日志，startTime:{},endTime:{}", startTime, endTime);
        Page<LoggingEvent> page;
        do {
            page = (Page<LoggingEvent>) loggingEventMapper.selectPage(new Page<>(1, 500),
                    Wrappers.<LoggingEvent>lambdaQuery().between(LoggingEvent::getTimestmp, startTime, endTime));

            List<LoggingEvent> records = page.getRecords();
            if (CollectionUtils.isNotEmpty(records)) {
                List<Long> eventIds = records.stream().map(LoggingEvent::getEventId).collect(Collectors.toList());
                loggingEventMapper.deleteBatchIds(eventIds);
                loggingEventExceptionMapper.delete(Wrappers.<LoggingEventException>lambdaUpdate().in(LoggingEventException::getEventId, eventIds));
                logginEventProperyMapper.delete(Wrappers.<LoggingEventProperty>lambdaUpdate().in(LoggingEventProperty::getEventId, eventIds));
            }
        } while (page.hasNext());
        log.info("[日志清除]结束清除日志，startTime:{},endTime:{}", startTime, endTime);
    }
}
