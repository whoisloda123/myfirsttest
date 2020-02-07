package com.liucan.boot.netty;

import lombok.Builder;
import lombok.Data;

/**
 * 传输的bootMessage内容
 *
 * @author liucan
 * @version 20-2-7
 */
@Data
@Builder
public class BootMsg {
    private MsgType type = MsgType.NORMAL; //消息的类型，有心跳类型和内容类型
    private Integer length = 0; //消息长度
    private String content = ""; //表示消息的内容（心跳包在这里没有内容）
}
