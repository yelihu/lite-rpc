package org.example.rpc.registry;

import org.example.rpc.entity.IPAndPort;
import org.example.rpc.entity.config.ServiceConfig;

/**
 * @author yelihu
 */
public interface RegistryCenter extends AutoCloseable {


    /**
     * register a service to registry center
     *
     * @param serviceConfig Service Configuration
     */
    void register(ServiceConfig serviceConfig);

    /**
     * get a available provider service node from registry center
     *
     * @param name provider service interface class name
     * @return {@link IPAndPort}
     */
    IPAndPort lookup(String name);

}
