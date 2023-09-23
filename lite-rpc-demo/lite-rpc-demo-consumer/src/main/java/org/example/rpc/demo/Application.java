package org.example.rpc.demo;

import org.example.rpc.ProxyReference;
import org.example.rpc.RpcBootstrap;
import org.example.rpc.ZookeeperClusterRegistryCenter;

/**
 * @author yelihu
 */
public class Application {
    public static void main(String[] args) {
        //LiteRpcConsumerBootstrap.getInstance();
        ProxyReference<IHelloLiteRpc> reference = new ProxyReference<>();

        reference.setInterfaceClass(IHelloLiteRpc.class);

        RpcBootstrap.getInstance()
                .appName("demo-consumer")
                .registry(new ZookeeperClusterRegistryCenter("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183"))
                .reference(reference);



        // 思考：代理对象需要做什么事情？
        IHelloLiteRpc helloLiteRpc = reference.get();
        helloLiteRpc.sayHi("\"This is Consumer\"");
    }
}
