package org.example.rpc;

import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * service reference
 *
 * @author yelihu
 */
public class ProxyReference<T> {

    @Setter
    private Class<T> interfaceClass;

    /**
     * @return {@link T}
     */
    public T get() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //TODO use dynamic proxy create service instance
        Object proxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new ServiceInvocationHandler());
        //noinspection unchecked
        return (T) proxyInstance;
    }

    private class ServiceInvocationHandler implements InvocationHandler {
        /**
         * handle rpc detail and send request to provider
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoke method, this method is not implemented");
            method.invoke(args);
            return null;
        }
    }
}
