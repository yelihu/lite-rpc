package org.example.rpc.demo;

import org.example.rpc.ProxyReference;
import org.example.rpc.RpcBootstrap;
import org.example.rpc.registry.RegistryCenterFactory;

/**
 * @author yelihu
 */
public class ConsumerApplication {
    public static void main(String[] args) {
        ProxyReference<IHelloLiteRpc> reference = new ProxyReference<>();
        reference.setInterfaceClass(IHelloLiteRpc.class);

        RpcBootstrap.getInstance()
                .appName("demo-consumer")
                .registry(new RegistryCenterFactory()
                        .setConnectString("zookeeper://127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183")
                        .setSessionTimeout(10000)
                        .getRegistryCenter())
                //
                .reference(reference);

        IHelloLiteRpc helloLiteRpc = reference.get();
        helloLiteRpc.sayHi("\"This is Consumer\"");
    }
}
