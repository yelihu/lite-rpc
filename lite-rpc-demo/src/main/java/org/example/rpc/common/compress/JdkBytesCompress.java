package org.example.rpc.common.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * compress and decompress byte array, using {@link java.util.zip.GZIPInputStream} and {@link java.util.zip.GZIPOutputStream}
 *
 * @author yelihu
 */
public class JdkBytesCompress implements BytesCompress {

    private static void checkByteArraySize(byte[] compressed) {
        if (null == compressed || compressed.length <= 0) {
            throw new IllegalArgumentException("compressed bytes can't be empty");
        }
    }

    @Override
    public byte[] compress(byte[] uncompressed) throws IOException {
        checkByteArraySize(uncompressed);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             //decorate the stream with a GZIPOutputStream
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);) {

            gzipOutputStream.write(uncompressed);
            gzipOutputStream.finish();

            return outputStream.toByteArray();
        }
    }

    @Override
    public byte[] decompressed(byte[] compressed) throws IOException {
        return decompressed(compressed, 1024);
    }

    /**
     * decompress byte array using specified buffer size
     *
     * @param compressed compressed byte array
     * @param bufSize    specified buffer size
     * @return {@link byte[]}
     */
    @Override
    public byte[] decompressed(byte[] compressed, int bufSize) throws IOException {
        checkByteArraySize(compressed);
        byte[] byteArray;
        try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(compressed);
             GZIPInputStream inputStream = new GZIPInputStream(arrayInputStream);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[bufSize];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                byteOut.write(buffer, 0, len);
            }
            byteArray = byteOut.toByteArray();
        }
        return byteArray;
    }

}
