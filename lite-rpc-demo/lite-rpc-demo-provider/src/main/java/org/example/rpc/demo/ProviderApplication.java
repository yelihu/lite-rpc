package org.example.rpc.demo;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.demo.service.impl.HelloLiteRpcServiceImpl;
import org.example.rpc.entity.config.ProtocolConfig;
import org.example.rpc.registry.RegistryCenterFactory;
import org.example.rpc.registry.bootstrap.RpcBootstrap;
import org.example.rpc.entity.config.SerializationConfig;
import org.example.rpc.entity.config.ServiceConfig;

/**
 * @author yelihu
 */
@Slf4j
public class ProviderApplication {
    public static void main(String[] args) {
        ServiceConfig serviceConfig = ServiceConfig.builder()
                .serviceInterface(IHelloLiteRpc.class)
                .serviceInstance(new HelloLiteRpcServiceImpl())
                .build();

        RpcBootstrap.getInstance()
                .appName("demo-provider")
                .registry(new RegistryCenterFactory()
                        .setConnectString("zookeeper://127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183")
                        .setSessionTimeout(10000)
                        .getRegistryCenter())
                .protocol(new ProtocolConfig("jdk"))
                .serialize(new SerializationConfig("jdk"))
                .publish(serviceConfig)
                .start();

    }
}
