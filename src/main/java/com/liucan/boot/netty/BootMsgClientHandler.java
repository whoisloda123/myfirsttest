package com.liucan.boot.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liucan
 * @version 20-2-7
 */
public class BootMsgClientHandler extends SimpleChannelInboundHandler<BootMsg> {
    /**
     * 收到消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BootMsg msg) throws Exception {
        System.out.println("收到服务器消息:" + JSONObject.toJSONString(msg));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
        System.out.println("连接服务器成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
        System.out.println("连接服务器断开");
    }
}
