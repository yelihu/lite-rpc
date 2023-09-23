package org.example.rpc.demo;

/**
 * @author yelihu
 */
public class Application {
    public static void main(String[] args) {
        //LiteRpcConsumerBootstrap.getInstance();
        ProxyReference<IHelloLiteRpc> reference = new ProxyReference<>();

        reference.setInterface(IHelloLiteRpc.class);
        // 思考：代理对象需要做什么事情？
        IHelloLiteRpc helloLiteRpc = reference.get();
        helloLiteRpc.sayHi("\"This is Consumer\"");
    }
}
