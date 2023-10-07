package org.example.rpc.registry.bootstrap;


import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.entity.config.ProtocolConfig;
import org.example.rpc.entity.config.SerializationConfig;
import org.example.rpc.entity.config.ServiceConfig;
import org.example.rpc.registry.RegistryCenter;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.ObjectUtils.anyNull;


/**
 * provider bootstrap, which is used to initialize the provider service
 * 启动一个客户端，作为provider上报服务消息或者作为consumer查询服务provider信息。
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
     * serialization configuration
     */
    private SerializationConfig serialize;

    /**
     * protocol configuration
     */
    private ProtocolConfig protocol;

    /**
     * registry center configuration, configure the registry center address and port
     */
    private RegistryCenter registryCenter;

    //service map, store the service reference which is published, key is service class name.
    private static final Map<String, ServiceConfig> publishedServiceMap = Maps.newConcurrentMap();


    public static RpcBootstrap getInstance() {
        return singletonInstance;
    }

    public RpcBootstrap appName(String appName) {
        this.appName = appName;
        return this;
    }

    public RpcBootstrap registry(RegistryCenter registryCenter) {
        if (log.isDebugEnabled()) {
            log.debug("registry center config, use registry center {}", registryCenter.getClass().getSimpleName());
        }
        this.registryCenter = registryCenter;
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
        // setting registry center to proxy reference
        reference.setRegistryCenter(this.registryCenter);
        return this;
    }

    /**
     * publish the service to the registry center
     *
     * @param serviceConfig Service configuration
     * @return {@link RpcBootstrap}
     */
    public RpcBootstrap publish(ServiceConfig serviceConfig) {
        //check RpcBootstrap's attributes null
        if (anyNull(this.appName, this.registryCenter, this.serialize, this.protocol, serviceConfig)) {
            throw new IllegalArgumentException("RpcBootstrap's attributes can not be null,check configuration before publish");
        }
        //
        publishedServiceMap.put(serviceConfig.getRegistryKey(), serviceConfig);

        registryCenter.register(serviceConfig);
        return this;
    }


    public RpcBootstrap publish(List<ServiceConfig> serviceConfig) {
        serviceConfig.forEach(this::publish);
        return this;
    }


    /**
     * start this bootstrap
     */
    public void start() {
        try {
            Thread.sleep(3000 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                registryCenter.close();
            } catch (Exception e) {
                log.error("registry center close error", e);
            }
        }
    }


}
