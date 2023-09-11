package org.example.rpc.common;

import org.apache.commons.lang3.StringUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yelihu
 */
public abstract class ByteConverter {

    public static byte[] toUTF8(String str) {
        if (StringUtils.isEmpty(str)) {
            return new byte[0];
        }
        return str.getBytes(UTF_8);
    }
}
