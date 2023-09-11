package org.example.rpc.common;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yelihu
 */
public abstract class ProtocolConst {

    public static final String PROTOCOL_VERSION = "1.0";

    public static final String PROTOCOL_MAGIC = "LITE";

    public static final byte[] PROTOCOL_MAGIC_BYTES = "LITE".getBytes(UTF_8);

}
