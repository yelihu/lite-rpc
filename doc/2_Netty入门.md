## Netty入门

## 实现客户端和服务端交互

### 客户端实现的关键组组件

客户端代码如👉🏻：[AppClient.java](..%2Flite-rpc-demo%2Flite-rpc-demo%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Frpc%2Fnetty%2FAppClient.java)

| Netty组件            | 作用                                  |
|--------------------|-------------------------------------|
| NioEventLoopGroup  | 线程池，负责处理网络IO事件，包括接收和发送数据。           |
| Bootstrap          | 配置和启动客户端                            |
| NioSocketChannel   | 是建立客户端连接的通道类型                       |
| ChannelInitializer | 初始化通道的处理器，内部实现由开发手动配置和添处理器          |
| ChannelFuture      | bootstrap.connect()可以获得和服务器建立连接的结果。 |

ChannelFuture可以获取到Channel对象，writeAndFlush可以往Channel里面写数据，AppClient.java里面往Channel写了一个ByteBuf对象，ByteBuf包了一个字节数组对象



> *这些组件的详细介绍，背后的实现原理和源码解读，不是实现RPC框架的重点。*

### 服务端实现关键组件

服务端代码如👉🏻：[AppServer.java](..%2Flite-rpc-demo%2Flite-rpc-demo%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Frpc%2Fnetty%2FAppServer.java)

| Netty组件                              | 作用                                                  |
|--------------------------------------|-----------------------------------------------------|
| NioEventLoopGroup                    | 用于处理I/O操作的多线程事件循环器，由boss和worker两个组构成，分别用于接收连接和处理请求。 |
| ServerBootstrap                      | 用于启动Netty服务器的引导类，可以设置并配置服务器的相关参数。                   |
| NioServerSocketChannel               | 用于接收客户端连接的通道类，通过该通道可以监听和接受客户端的连接请求。                 |
| childHandler                         | 定义了处理请求的处理器，包括业务逻辑的处理、编解码器的设置等。                     |
| ChannelFuture                        | 用于异步操作的结果通知，可以用于等待绑定端口的操作完成。                        |
| NioEventLoopGroup#shutdownGracefully | 优雅地关闭相关资源，包括释放线程、关闭通道等。                             |

### 区别和使用注意要点

**为什么netty实现的服务端的NioEventLoopGroup需要两个group（2个boss和10个worker），而客户端却不需要？**

客户端通常只需要一个NioEventLoopGroup，用于处理连接请求和请求处理任务，因为客户端一般只需要与一个服务器进行通信。

服务端需要两个NioEventLoopGroup，一个用于接收客户端的连接（boss group），另一个用于处理客户端的请求（worker group）。这样好处：

请求链接和处理职责分离，请求处理能力不受到链接数量的影响。可升缩性和并发处理性能更好。

**为什么服务端和客户端使用的channel一个是NioServerSocketChannel另外一个是NioSocketChannel？区别在哪里？**

|                        |                                                                                                                       |
|------------------------|-----------------------------------------------------------------------------------------------------------------------|
| NioServerSocketChannel | 服务端专用的通道类型，用于监听和接受客户端的连接请求。当有客户端请求进来之后，再创建下面的NioSocketChannel建立链接处理数据，**注意：服务端只需要一个NioServerSocketChannel来监听连接请求即可。** |
| NioSocketChannel       | 客户端专用的通道类型，用于与服务端建立连接和进行**数据的读写操作**。使用NioSocketChannel，客户端可以**主动发起连接请求**，并与服务端建立连接。                                   |

总结：NioServerSocketChannel职责是**被动接收请求**，实际处理客户端的数据传输还是需要NioSocketChannel，NioSocketChannel用来数据读写，也可以主动发起连接请求。

**服务端和客户端的ServerBootstrap的childHandler和Bootstrap的handler的区别是什么？**

区别主要在于用途和配置，ServerBootstrap用于创建服务器端：

1. childHandler用于配置服务端的ChannelPipeline，即定义处理客户端连接的Channel的处理器链。
2. 与服务端连接成功之后NioSocketChannel被创建之后才会执行，childHandler用于设置这个Channel的处理器，也就是**处理从客户端发来的消息的逻辑
   **。
3. 通常会在childHandler中添加**多个ChannelHandler**，用于处理不同的业务逻辑，如解码器、编码器、业务处理器等。

Bootstrap的handler用于配置客户端的ChannelPipeline：

处理与服务端建立连接的Channel的处理器链，NioSocketChannel被创建之后才会执行，用于设置这个NioSocketChannel的处理器，也就是处理从服务端发来的消息的逻辑。







