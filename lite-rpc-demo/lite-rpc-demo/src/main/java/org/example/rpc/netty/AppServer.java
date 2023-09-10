package org.example.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试DEMO - 客户端代码
 *
 * @author yelihu
 */
@Slf4j
@RequiredArgsConstructor
public class AppServer {

    /**
     * server绑定的端口
     */
    private final int port;

    public static void main(String[] args) {
        new AppServer(8080).start();
    }

    public void start() {
        //接受请求并dispatch到worker
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(10);
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    //childHandler定义了处理请求应该如何做
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //receive data and print log
                            ch.pipeline().addLast(new MyChannelHandler());
                        }
                    });
            log.info("server started!");
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();


            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
            log.info("server terminated!");

        }

    }

}
