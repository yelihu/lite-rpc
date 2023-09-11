package org.example.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.rpc.common.ObjectSerializeUtils;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.example.rpc.common.ProtocolConst.PROTOCOL_MAGIC_BYTES;

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


    @SneakyThrows
    @Test
    public void testMessage() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(PROTOCOL_MAGIC_BYTES);
        //version
        byteBuf.writeByte(1);
        //header length (bytes)
        byteBuf.writeShort(125);
        //request length (bytes)
        byteBuf.writeInt(256);
        //compress type[1、2、3...]
        byteBuf.writeByte(1);
        //serialize type[1、2、3...]
        byteBuf.writeByte(1);
        //request id
        long requestId = System.currentTimeMillis();
        byteBuf.writeLong(requestId);

        User jacky = new User(10000001L, "jacky", 23);

        byteBuf.writeBytes(ObjectSerializeUtils.toByteArray(jacky));

        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
    }


    @AllArgsConstructor
    public static class User implements Serializable {
        private static final long serialVersionUID = 2161506835550045079L;

        private Long userId;
        private String username;
        private Integer age;

    }
}
