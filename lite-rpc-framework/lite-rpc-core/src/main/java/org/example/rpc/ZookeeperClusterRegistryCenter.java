package org.example.rpc;

import lombok.Value;

/**
 * Zookeeper cluster registry center
 *
 * @author yelihu
 */
@Value
public class ZookeeperClusterRegistryCenter {

    /**
     * cluster connection string, split by comma, example:
     * 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185
     */
    String connection;
}
