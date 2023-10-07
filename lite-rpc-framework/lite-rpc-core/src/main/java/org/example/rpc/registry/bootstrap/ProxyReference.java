package org.example.rpc.registry.bootstrap;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.entity.IPAndPort;
import org.example.rpc.registry.RegistryCenter;
import org.example.rpc.registry.RegistryCenterFactory;

import java.lang.reflect.Proxy;

/**
 * service reference，代理服务创建，通过此类实现“RPC调用类似本地调用”
 *
 * @author yelihu
 */
@Slf4j
@Getter
public class ProxyReference<T> {

    @Setter
    private Class<T> interfaceClass;

    @Setter
    private RegistryCenter registryCenter;

    /**
     * generate a proxy instance
     *
     * @return {@link T}
     */
    public T get() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //create proxy instance
        Object proxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, (proxy, method, args) -> {
            if (log.isDebugEnabled()) {
                log.info("creating proxy instance, method = {} , args = {}", method.getName(), args);
            }

            IPAndPort availableProviderAddress = registryCenter.lookup(interfaceClass.getName());
            if (log.isDebugEnabled()) {
                log.debug("serviceInterfaceName = {}, availableProviderAddress = {}", interfaceClass.getName(), availableProviderAddress);
            }
            return null;
        });

        //noinspection unchecked
        return (T) proxyInstance;
    }

}
