## Netty入门

## 实现客户端和服务端交互

*
服务端：[AppServer.java](..%2Flite-rpc-demo%2Flite-rpc-demo%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Frpc%2Fnetty%2FAppServer.java)
*
客户端：[AppClient.java](..%2Flite-rpc-demo%2Flite-rpc-demo%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Frpc%2Fnetty%2FAppClient.java)

## 客户端实现的关键组组件

|                    | 作用                                  | 备注 |
|--------------------|-------------------------------------|----|
| NioEventLoopGroup  | 线程池，负责处理网络IO事件，包括接收和发送数据。           |    |
| Bootstrap          | 配置和启动客户端                            |    |
| NioSocketChannel   | 是建立客户端连接的通道类型                       |    |
| ChannelInitializer | 初始化通道的处理器，内部实现由开发手动配置和添处理器          |    |
| ChannelFuture      | bootstrap.connect()可以获得和服务器建立连接的结果。 |    |

ChannelFuture可以获取到Channel对象，writeAndFlush可以往Channel里面写数据，AppClient.java里面往Channel写了一个ByteBuf对象，ByteBuf包了一个字节数组对象



> *这些组件的详细介绍，背后的实现原理和源码解读，不是实现RPC框架的重点。*



