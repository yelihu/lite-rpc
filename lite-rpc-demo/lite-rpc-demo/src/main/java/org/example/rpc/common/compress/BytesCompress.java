package org.example.rpc.common.compress;

import java.io.IOException;

/**
 * compress and decompress byte array
 *
 * @author yelihu
 */
public interface BytesCompress {
    /**
     * compress byte array
     *
     * @param uncompressed uncompressed byte array
     * @return {@link byte[]}
     */
    byte[] compress(byte[] uncompressed) throws IOException;

    /**
     * decompress byte array, using default buffer size of 1024
     *
     * @param compressed compressed byte array
     * @return {@link byte[]}
     */
    byte[] decompressed(byte[] compressed) throws IOException;


    byte[] decompressed(byte[] compressed, int bufSize) throws IOException;

}
