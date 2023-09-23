package org.example.rpc.demo.service.impl;

import org.example.rpc.demo.IHelloLiteRpc;

/**
 * @author yelihu
 */
public class HelloLiteRpcServiceImpl implements IHelloLiteRpc {
    @Override
    public String sayHi(String msg) {
        return "Server received message=" + msg + ". Hello client! ";
    }
}
