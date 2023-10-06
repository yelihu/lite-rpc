package org.example.rpc;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * configuration of service, which is used to register service to RPC server
 *
 * @author yelihu
 */
@Builder
@Getter
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

    private String getInterfaceName() {
        return serviceInterface.getName();
    }

    public String getRegistryKey() {
        return this.getInterfaceName();
    }
}
