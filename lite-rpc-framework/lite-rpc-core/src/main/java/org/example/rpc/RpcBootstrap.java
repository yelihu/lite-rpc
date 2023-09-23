package org.example.rpc;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;


/**
 * provider bootstrap, which is used to initialize the provider service
 *
 * @author yelihu
 */
@Getter
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class RpcBootstrap {

    /**
     * singleton instance
     */
    private static final RpcBootstrap singletonInstance = new RpcBootstrap();

    /**
     * application name, identify this application
     */
    private String appName;


    /**
     * registry center configuration, configure the registry center address and port
     */
    private ZookeeperClusterRegistryCenter registry;

    /**
     * serialization configuration
     */
    private SerializationConfig serialize;

    /**
     * protocol configuration
     */
    private ProtocolConfig protocol;

    private ServiceConfig service;

    public static RpcBootstrap getInstance() {
        return singletonInstance;
    }

    public RpcBootstrap appName(String appName) {
        this.appName = appName;
        return this;
    }

    public RpcBootstrap registry(ZookeeperClusterRegistryCenter registry) {
        if (log.isDebugEnabled()) {
            log.debug("registry center config, use registry center {}", registry);
        }
        this.registry = registry;
        return this;
    }

    public RpcBootstrap serialize(SerializationConfig serialize) {
        this.serialize = serialize;
        return this;
    }

    public RpcBootstrap protocol(ProtocolConfig protocol) {
        if (log.isDebugEnabled()) {
            log.debug("protocol config, use protocol {}", protocol);
        }
        this.protocol = protocol;
        return this;
    }

    /**
     * configure the service reference, create proxy object when {@link ProxyReference#get()} is called
     *
     * @param reference service reference
     * @return {@link RpcBootstrap}
     */
    public RpcBootstrap reference(ProxyReference<?> reference) {

        return this;
    }

    /**
     * publish the service to the registry center
     *
     * @param serviceConfig Service configuration
     * @return {@link RpcBootstrap}
     */
    public RpcBootstrap publish(ServiceConfig serviceConfig) {
        if (log.isDebugEnabled()) {
            log.debug("publish service config, use service config {}", serviceConfig);
        }
        this.service = serviceConfig;
        return this;
    }

    /**
     * start this bootstrap
     */
    public void start() {


    }


}
