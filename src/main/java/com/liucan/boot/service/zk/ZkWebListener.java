package com.liucan.boot.service.zk;

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
public class ZkWebListener implements ServletContextListener {
    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private int serverPort;
    @Value("${spring.application.name}")
    private String serverName;

    @Autowired
    private ZkServiceRegistry zkServiceRegistry;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        zkServiceRegistry.register("java-learn", String.format("%s:%d/%s", serverAddress, serverPort, serverName));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        zkServiceRegistry.close();
    }
}
