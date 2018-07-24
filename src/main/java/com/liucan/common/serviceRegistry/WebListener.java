package com.liucan.common.serviceRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author liucan
 * @date 2018/7/24
 * @brief 注册服务
 */
@Component
public class WebListener implements ServletContextListener {
    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private int serverPort;
    @Value("${server.name}")
    private String serverName;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        serviceRegistry.register("java-learn", String.format("%s:%d/%s", serverAddress, serverPort, serverName));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        serviceRegistry.close();
    }
}
