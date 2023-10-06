package org.example.rpc.remoting.zookeeper;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author yelihu
 */
public abstract class IPAndPortUtils {
    public static Pair<String, Integer> split(String ipAndPort) {
        String[] ipAndPortArray = ipAndPort.split(":");

        return Pair.of(ipAndPortArray[0], Integer.valueOf(ipAndPortArray[1]));
    }
}
