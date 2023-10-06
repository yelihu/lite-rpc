package org.example.rpc.remoting.zookeeper.service.entity;

import lombok.Value;

/**
 * @author yelihu
 */
@Value
public class IPAndPort {
    String ip;
    int port;
}
