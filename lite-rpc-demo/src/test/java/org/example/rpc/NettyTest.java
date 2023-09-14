package org.example.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.rpc.common.ObjectSerializer;
import org.example.rpc.common.compress.JdkBytesCompress;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import static org.example.rpc.common.ProtocolConst.PROTOCOL_MAGIC_BYTES;

/**
 * Unit test for ByteBuf
 */
public class NettyTest {

    private static final byte[] COMPRESSED = new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, 0, -13, -15, 12, 113, 101, 100, -88, 101, 96, 96, 100, 96, 100, 4, -110, 93, -99, -121, 31, 93, 93, -13, -106, -127, -75, -72, -120, 65, 46, -65, 40, 93, 47, -75, 34, 49, -73, 32, 39, 85, -81, -88, 32, 89, -49, 47, -75, -92, -92, 50, 36, -75, -72, 68, 37, -76, 56, -75, 72, -10, -65, 57, -13, -119, 46, -26, -23, 76, 12, -52, 62, 12, -52, -119, -23, -87, 37, 12, -62, 62, 89, -119, 101, -119, -6, 57, -119, 121, -23, -6, -98, 121, 37, -87, -23, -87, 69, -42, 62, 12, 108, -91, 64, -43, -98, 41, 37, 12, 2, 72, -46, 62, -7, 121, -23, 64, 57, 14, -112, 92, 94, 98, 46, 80, -77, 16, -110, 108, 112, 73, 81, 38, 80, -66, -94, 0, -24, 12, 65, -112, -80, 30, 72, 88, 15, 106, -90, -48, -93, 5, 75, -66, 55, -74, 91, 48, 49, 48, 122, 50, -80, -106, 37, -26, -108, -90, 86, 20, 49, 8, 32, -44, -7, -107, -26, 38, -91, 22, -75, -83, -103, 42, -53, 61, -27, 65, 55, 19, 3, 67, 69, 1, 3, 3, -125, 56, -48, 48, 62, -124, 34, -80, 11, -70, -97, 76, 56, -45, -81, 124, 31, 104, -110, 23, -52, -92, 66, -122, 58, 6, 54, 6, 16, -104, 49, -83, -79, -124, -127, 53, 43, 49, 57, -69, -110, 97, -104, 2, 0, -93, -116, 34, -4, 0, 2, 0, 0};

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


    public static ByteBuf getByteBuf() throws IOException {
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

        byteBuf.writeBytes(ObjectSerializer.toByteArray(jacky));
        return byteBuf;
    }

    @SneakyThrows
    @Test
    public void testMessage() {
        ByteBuf byteBuf = getByteBuf();

        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));
    }


    @AllArgsConstructor
    public static class User implements Serializable {
        private static final long serialVersionUID = 2161506835550045079L;

        private Long userId;
        private String username;
        private Integer age;

    }

    @Test
    public void testCompressByGzip() throws IOException {
        //压缩二进制字节
        ByteBuf byteBuf = getByteBuf();
        JdkBytesCompress compress = new JdkBytesCompress();
        byte[] streamByteArray = compress.compress(byteBuf.array());

        System.out.println(Arrays.toString(byteBuf.array()));
        System.out.println(Arrays.toString(streamByteArray));
        System.out.println("bytes compressed size =" + streamByteArray.length);
        System.out.println("bytes uncompressed size =" + byteBuf.array().length);
    }

    @Test
    public void testDecompress() throws IOException {
        JdkBytesCompress compress = new JdkBytesCompress();
        byte[] byteArray = compress.decompressed(COMPRESSED);
        System.out.println(Arrays.toString(byteArray));
        System.out.println(byteArray.length);
    }


}
