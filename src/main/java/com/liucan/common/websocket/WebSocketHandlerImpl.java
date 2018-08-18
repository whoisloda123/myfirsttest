package com.liucan.common.websocket;

import com.alibaba.fastjson.JSONObject;
import com.liucan.common.redis.RedisPubSub;
import com.liucan.domain.PalyloadMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
 *        1.用redis的pub/sub来处理2个用户分布式通信
 */
@Slf4j
@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {
    //用户列表
    private ConcurrentHashMap<Integer, WebSocketSession> userMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisPubSub redisPubSub;

    /**
     * 建立连接后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        if (userId != null) {
            userMap.put(userId, session);
            log.info("[websocekt]建立连接成功，userId：{}", userId);

            PalyloadMsg palyloadMsg = new PalyloadMsg();
            palyloadMsg.setUserId(userId);
            palyloadMsg.setMsg("成功建立websocket连接");
            sendMessageToUser(palyloadMsg, false);
        }
    }

    /**
     * 收到socket消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            log.info("[websocket]收到socket消息，message:{}", session, message.getPayload());

            PalyloadMsg palyloadMsg = JSONObject.parseObject(message.getPayload(), PalyloadMsg.class);
            if (palyloadMsg != null) {
                sendMessageToUser(palyloadMsg, true);
            }
        } catch (Exception e) {
            log.error("[websocket]收到socket消息，解析出现异常，session：{}，message:{}", session, message);
        }
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        Integer userId = getClientUserId(session);
        if (userId != null) {
            log.info("[websocekt]连接已关闭，closeStatus：{}, userId:{}", closeStatus, userId);
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

        Integer userId = getClientUserId(session);
        if (userId != null) {
            userMap.remove(userId);
        }
    }

    /**
     * 发送单个socket消息
     * @param usePubSub 是否用redis-pub/sub
     */
    public boolean sendMessageToUser(PalyloadMsg palyloadMsg, boolean usePubSub) {
        if (palyloadMsg == null) {
            return false;
        }
        Integer userId = palyloadMsg.getUserId();
        String msg = palyloadMsg.getMsg();
        if (userId == null || msg == null) {
            return false;
        }
        WebSocketSession session = userMap.get(userId);
        if (session != null) {
            if (!session.isOpen()) {
                log.error("[websocket]发送socket消息失败，用户不在线，userId：{}", userId);
                return false;
            }
            try {
                session.sendMessage(new TextMessage(msg));
                log.info("[websocket]发送socket消息成功，userId：{}，message:{}", userId, msg);
            } catch (Exception e) {
                log.error("[websocket]发送socket消息异常，userId：{}，message:{}", userId, msg, e);
                return false;
            }
        } else {
            if (usePubSub) {
                log.info("[websocket]发送socket消息失败，找不到用户，尝试发送redis-pub/sub, userId：{}", userId);
                redisPubSub.publish(palyloadMsg);
            } else {
                log.error("[websocket]发送socket消息失败，找不到用户，userId：{}", userId);
            }
        }

        return true;
    }

    /**
     * 发送多个socket消息
     */
    public boolean sendMessageToAllUsers(PalyloadMsg palyloadMsg) {
        boolean allSendSuccess = true;
        Set<Integer> userIds = userMap.keySet();
        WebSocketSession session;
        for (Integer userId : userIds) {
            try {
                session = userMap.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(palyloadMsg.getMsg()));
                }
            } catch (Exception e) {
                log.error("[websocket]发送socket消息异常，userId：{}，message:{}", userId, palyloadMsg.getMsg(), e);
                allSendSuccess = false;
            }
        }
        return allSendSuccess;
    }

    private Integer getClientUserId(WebSocketSession session) {
        try {
            return (Integer) session.getAttributes().get("userId");
        } catch (Exception e) {
            return null;
        }
    }
}
