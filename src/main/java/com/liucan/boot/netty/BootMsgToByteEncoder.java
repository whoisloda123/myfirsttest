package com.liucan.boot.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * bootMessage编码器
 * test
 * @author liucan
 * @version 20-2-7
 */
public class BootMsgToByteEncoder extends MessageToByteEncoder<BootMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BootMsg bootMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(bootMsg.getType().getType())
                .writeInt(bootMsg.getLength())
                .writeBytes(bootMsg.getContent().getBytes(StandardCharsets.UTF_8));
    }
}
