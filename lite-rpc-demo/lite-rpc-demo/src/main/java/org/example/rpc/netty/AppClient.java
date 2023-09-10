package org.example.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 测试DEMO - 客户端代码
 *
 * @author yelihu
 */
@SuppressWarnings("CallToPrintStackTrace")
public class AppClient {

    public static void main(String[] args) {
        new AppClient().run();
    }

    public void run() {
        //定义netty的线程池-eventGroup
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //启动一个客户端需要辅助类
            Bootstrap bootstrap = new Bootstrap();

            //使用什么线程池来处理
            bootstrap.group(eventLoopGroup)
                    .remoteAddress(new InetSocketAddress(8080))
                    //选择初始化一个什么样的channel
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        }
                    });

            //链接服务器
            ChannelFuture channelFuture = bootstrap.connect().sync();

            //获取channel
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("hello".getBytes(UTF_8)));

            //等待未来的关闭channel，这里会阻塞这个程序不会关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅的退出
            try {
                eventLoopGroup.shutdownGracefully();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
