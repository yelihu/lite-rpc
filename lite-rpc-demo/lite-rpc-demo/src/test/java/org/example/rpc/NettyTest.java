package org.example.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;

/**
 * Unit test for ByteBuf
 */
public class NettyTest {

    @Test
    public void testByteBuf() {
        ByteBuf header = Unpooled.buffer();
        ByteBuf body = Unpooled.buffer();

        //逻辑上组装CompositeByteBuf，并非物理拷贝
        //实现JVM内的“0拷贝”
        CompositeByteBuf compositedBuffer = Unpooled.compositeBuffer();
        compositedBuffer.addComponents(header, body);


    }

    @Test
    public void testWrappedBuf() {
        byte[] bytes1 = new byte[1024];
        byte[] bytes2 = new byte[1024];
        //共享数组的内容
        //实现JVM内的“0拷贝”
        ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes1, bytes2);

    }
}
