package org.example.rpc;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.registry.RegistryCenter;

import java.lang.reflect.Proxy;

/**
 * service reference
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
            log.info("method = {}", method.getName());
            log.info("args = {}", args);

            //look up service by interface name
            //registryCenter.lookUp(interfaceClass.getName());

            return null;
        });

        //noinspection unchecked
        return (T) proxyInstance;
    }

}
