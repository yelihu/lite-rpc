package org.example.rpc.utils.network;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author yelihu
 */
public abstract class IPAndPortUtils {

    /**
     * split a string like "127.0.0.1:8080" to "127.0.0.1" and "8080"
     *
     * @param ipAndPort IP and port
     * @return {@link Pair}<{@link String}, {@link Integer}>
     */
    public static Pair<String, Integer> split(String ipAndPort) {
        String[] ipAndPortArray = ipAndPort.split(":");

        return Pair.of(ipAndPortArray[0], Integer.valueOf(ipAndPortArray[1]));
    }
}
