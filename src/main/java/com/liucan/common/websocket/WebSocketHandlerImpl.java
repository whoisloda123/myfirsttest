package com.liucan.common.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liucan
 * @date 2018/8/12
 * @brief websocket处理器
 */
@Slf4j
public class WebSocketHandlerImpl extends TextWebSocketHandler {
    //用户列表
    private ConcurrentHashMap<String, WebSocketSession> userMap = new ConcurrentHashMap<>();

    /**
     * 建立连接后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String ID = session.getUri().toString().split("ID=")[1];
        if (ID != null) {
            userMap.put(ID, session);
            log.info("[websocekt]建立连接成功，ID：{}", ID);
            session.sendMessage(new TextMessage("成功建立websocket连接"));
        }
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("[websocekt]连接已关闭，closeStatus：{}", closeStatus);
        String userId = getClientUserId(session);
        if (userId != null) {
            userMap.remove(userId);
        }
    }

    /**
     * 出现错误
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.error("[websocekt]连接出错，session：{}", session);
        if (session.isOpen()) {
            session.close();
        }

        String userId = getClientUserId(session);
        if (userId != null) {
            userMap.remove(userId);
        }
    }

    /**
     * 收到socket消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            log.info("[websocket]收到socket消息，message:{}", session, message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(message.getPayload());
            String userId = (String) session.getAttributes().get("websocket-userid");
            sendMessageToUser(userId, new TextMessage("服务器收到消息了"));
        } catch (Exception e) {
            log.error("[websocket]收到socket消息，解析出现异常，session：{}，message:{}", session, message);
        }
    }

    /**
     * 发送单个socket消息
     */
    public boolean sendMessageToUser(String userId, TextMessage message) {
        WebSocketSession session = userMap.get(userId);
        if (session == null) {
            return false;
        }
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (Exception e) {
            log.error("[websocket]发送socket消息异常，userId：{}，message:{}", userId, message, e);
            return false;
        }
        return true;
    }

    /**
     * 发送多个socket消息
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> userIds = userMap.keySet();
        WebSocketSession session = null;
        for (String userId : userIds) {
            try {
                session = userMap.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (Exception e) {
                log.error("[websocket]发送socket消息异常，userId：{}，message:{}", userId, message, e);
                allSendSuccess = false;
            }
        }
        return allSendSuccess;
    }

    private String getClientUserId(WebSocketSession session) {
        try {
            return (String) session.getAttributes().get("WEBSOCKET_USERID");
        } catch (Exception e) {
            return null;
        }
    }
}
