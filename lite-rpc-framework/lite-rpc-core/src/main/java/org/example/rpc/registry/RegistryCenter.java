package org.example.rpc.registry;

import org.example.rpc.ServiceConfig;
import org.example.rpc.entity.IPAndPort;

/**
 * @author yelihu
 */
public interface RegistryCenter extends AutoCloseable{


    /**
     * register a service to registry center
     * register a service to registry center
     *
     * @param serviceConfig Service Configuration
     */
    void register(ServiceConfig serviceConfig);

    /**
     * get a available service
     *
     * @param name 名字
     * @return {@link IPAndPort}
     */
    IPAndPort lookUp(String name);

}
