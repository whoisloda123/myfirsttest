package com.liucan.boot.netty;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * 消息类型
 *
 * @author liucan
 * @version 20-2-7
 */
@Getter
public enum MsgType {
    NORMAL((byte) 1, "正常消息"),
    HEART((byte) 2, "心跳消息"),
    ILLEGAL((byte) 3, "非法消息");

    private Byte type;
    private String msg;

    MsgType(Byte type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static MsgType msgType(Byte type) throws IllegalArgumentException {
        return Stream.of(MsgType.values())
                .filter(msgType -> msgType.getType().equals(type))
                .findFirst()
                .orElse(MsgType.ILLEGAL);
    }
}
