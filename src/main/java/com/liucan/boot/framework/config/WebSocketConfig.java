package com.liucan.boot.framework.config;

import com.liucan.boot.framework.interceptor.WebSocketInterceptor;
import com.liucan.boot.framework.websocket.WebSocketHandlerImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author liucan
 * @date 2018/8/12
 * @brief websocket配置
 *        1.registerWebSocketHandlers核心实现方法
 *        2.配置websocketr入口，运行访问的域、注册Handler、定义拦截器
 *        3.客户端通过/boot/websocket直接访问handler核心类进行socket连接，接收，发送等操作
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandlerImpl(), "/boot/websocket/*")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }

}
