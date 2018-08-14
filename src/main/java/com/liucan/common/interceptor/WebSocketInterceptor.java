package com.liucan.common.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author liucan
 * @date 2018/8/12
 * @brief 第一次连接websocket进入hander之前的拦截器
 *        1.拦截建立连接url地址，拦截用户id等待，将它以键值对应放session里
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> map) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();

            String query = request.getURI().getQuery();
            if (!query.isEmpty()) {
                String[] querys = query.split("=");
                map.put("userId", Integer.valueOf(querys[1]));
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception e) {
    }
}
