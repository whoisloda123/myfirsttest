package com.liucan.boot.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * bootMessage解码器
 *
 * @author liucan
 * @version 20-2-7
 */
public class ByteToBootMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(MsgType.msgType(in.readByte()));
        out.add(in.readInt());
        if (in.readableBytes() > 5) {
            byte[] bytes = new byte[1024];
            in.readBytes(bytes);
            out.add(new String(bytes, StandardCharsets.UTF_8));
        }
    }
}
