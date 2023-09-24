package org.example.rpc.registry;

import org.example.rpc.ServiceConfig;

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
}
