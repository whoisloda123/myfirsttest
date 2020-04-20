package com.liucan.boot.framework.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.ConsoleAppender;
import com.liucan.boot.persist.mybatis.mode.SystemLogWithBLOBs;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 继承ConsoleAppender 重写append方法,收集系统中所有的 log.info error warn debug 等打印语句
 *
 * @author liucan
 * @version 2020/4/20
 */
public class LogAppender extends ConsoleAppender<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent event) {
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
        super.append(event);
    }

}
