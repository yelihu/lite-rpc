package org.example.rpc.entity;

import lombok.Value;

/**
 * @author yelihu
 */
@Value
public class IPAndPort {
    String ip;
    int port;

    @Override
    public String toString() {
        return "IPAndPort{" +
                "'" + ip + '\'' +
                ":" + port +
                '}';
    }
}
