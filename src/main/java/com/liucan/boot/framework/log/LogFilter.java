package com.liucan.boot.framework.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.liucan.boot.persist.mybatis.mode.SystemLogWithBLOBs;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 日志收集
 *
 * @author liucan
 * @version 2020/4/19
 */
@Slf4j
public class LogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        StringBuilder exception = new StringBuilder();
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null) {
            exception = new StringBuilder("<span class='excehtext'>" +
                    iThrowableProxy.getClassName() + " " + iThrowableProxy.getMessage() + "</span></br>");
            for (int i = 0; i < iThrowableProxy.getStackTraceElementProxyArray().length; i++) {
                exception.append("<span class='excetext'>").
                        append(iThrowableProxy.getStackTraceElementProxyArray()[i].toString()).
                        append("</span></br>");
            }
        }

        SystemLogWithBLOBs log = new SystemLogWithBLOBs();
        log.setBody(event.getMessage());
        log.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()), ZoneId.systemDefault()));
        log.setException(exception.toString());
        log.setType(event.getLevel().toInteger());
        log.setThreadName(event.getThreadName());
        log.setClassName(event.getLoggerName());
        LoggerQueue.getInstance().offer(log);
        return FilterReply.ACCEPT;
    }
}
