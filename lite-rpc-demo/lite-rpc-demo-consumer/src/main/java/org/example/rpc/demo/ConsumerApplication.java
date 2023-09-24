package org.example.rpc.demo;

import org.example.rpc.ProxyReference;
import org.example.rpc.RpcBootstrap;
import org.example.rpc.registry.impl.ZookeeperRegistryCenter;

/**
 * @author yelihu
 */
public class ConsumerApplication {
    public static void main(String[] args) {
        ProxyReference<IHelloLiteRpc> reference = new ProxyReference<>();
        reference.setInterfaceClass(IHelloLiteRpc.class);

        RpcBootstrap.getInstance()
                .appName("demo-consumer")
                .registry(new ZookeeperRegistryCenter("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 10000))
                .reference(reference);

        IHelloLiteRpc helloLiteRpc = reference.get();
        helloLiteRpc.sayHi("\"This is Consumer\"");
    }
}
