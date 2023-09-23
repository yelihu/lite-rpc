package org.example.rpc;

import lombok.Builder;
import lombok.Value;

/**
 * configuration of service, which is used to register service to RPC server
 *
 * @author yelihu
 */
@Builder
public class ServiceConfig {
    /**
     * Service interface, implemented by {@link ServiceConfig#serviceInstance}
     */
    Class<?> serviceInterface;

    Object serviceInstance;

    @Override
    public String toString() {
        return "ServiceConfig{" +
                "interface=" + serviceInterface.getName() + '}';
    }
}
