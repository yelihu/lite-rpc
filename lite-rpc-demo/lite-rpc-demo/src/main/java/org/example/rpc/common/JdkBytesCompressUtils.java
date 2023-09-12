package org.example.rpc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author yelihu
 */
public class JdkBytesCompressUtils {

    /**
     * compress byte array
     *
     * @param uncompressed uncompressed byte array
     * @return {@link byte[]}
     */
    public static byte[] compress(byte[] uncompressed) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             //装饰一个zip输出流
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);) {

            gzipOutputStream.write(uncompressed);
            gzipOutputStream.finish();

            return outputStream.toByteArray();
        }
    }

    /**
     * decompress byte array
     *
     * @param compressed compressed byte array
     * @return {@link byte[]}
     */
    public static byte[] decompressed(byte[] compressed) throws IOException {
        byte[] byteArray;
        try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(compressed);
             GZIPInputStream inputStream = new GZIPInputStream(arrayInputStream);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                byteOut.write(buffer, 0, len);
            }
            byteArray = byteOut.toByteArray();
        }
        return byteArray;
    }
}
