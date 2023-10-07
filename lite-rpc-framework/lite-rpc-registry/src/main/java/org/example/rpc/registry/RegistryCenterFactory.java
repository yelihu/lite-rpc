package org.example.rpc.registry;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.exception.RegistryCenterNotFoundException;
import org.example.rpc.registry.impl.ZookeeperRegistryCenter;

/**
 * @author yelihu
 */
@Data
@Slf4j
@Accessors(chain = true)
public class RegistryCenterFactory {

    @Getter
    private String connectString;

    private int sessionTimeout;

    public RegistryCenter getRegistryCenter() {
        String[] split = getConnectString().split("://");
        if (split.length != 2) {
            throw new IllegalArgumentException("connectString must be like: xxx://xxx");
        }
        String typePrefix = split[0];
        String realConnectString = split[1];
        if (log.isDebugEnabled()) {
            log.debug("RegistryCenterFactory getRegistryCenter, typePrefix:{}, realConnectString:{}", typePrefix, realConnectString);
        }
        switch (typePrefix) {
            case "zookeeper":
                return new ZookeeperRegistryCenter(realConnectString, getSessionTimeout());
            case "etcd":
            default:
                throw new RegistryCenterNotFoundException("not found registry center type:" + typePrefix);
        }
    }
}
