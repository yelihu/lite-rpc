package org.example.rpc.registry.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Op;
import org.example.rpc.entity.IPAndPort;
import org.example.rpc.exception.ConsumerException;
import org.example.rpc.registry.RegistryCenter;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * service reference，代理服务创建，通过此类实现“RPC调用类似本地调用”
 *
 * @author yelihu
 */
@Slf4j
@Getter
public class ProxyReference<T> {

    @Setter
    private Class<T> interfaceClass;

    @Setter
    private RegistryCenter registryCenter;

    /**
     * generate a proxy instance
     *
     * @return {@link T}
     */
    public T get() {
        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> {
            if (log.isDebugEnabled()) {
                log.info("creating proxy instance, method = {} , args = {}", method.getName(), args);
            }

            IPAndPort availableProviderAddress = registryCenter.lookup(interfaceClass.getName());

            Channel channel = getSocketChannel(availableProviderAddress);

            ChannelFuture channelFuture = channel.writeAndFlush(new Object());
            return null;
        });

        //noinspection unchecked
        return (T) proxyInstance;
    }

    /**
     * get socket channel from cache or create a new one
     *
     * @param availableProviderAddress provider node ip:port
     * @return {@link Channel}
     */
    private static Channel getSocketChannel(IPAndPort availableProviderAddress) {
        Channel channel = RpcBootstrap.CHANNEL_CACHE.get(new InetSocketAddress(availableProviderAddress.getIp(), availableProviderAddress.getPort()));
        if (Objects.nonNull(channel)) {
            return channel;
        }
        return createClientSockChannel(availableProviderAddress);
    }

    private static Channel createClientSockChannel(IPAndPort availableProviderAddress) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Channel channel = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .remoteAddress(new InetSocketAddress(availableProviderAddress.getIp(), availableProviderAddress.getPort()))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(null);
                        }
                    });
            channel = bootstrap.connect().sync().channel();
            //update channel cache
            RpcBootstrap.CHANNEL_CACHE.put(new InetSocketAddress(availableProviderAddress.getIp(), availableProviderAddress.getPort()), channel);
        } catch (Exception e) {
            log.error("start client error", e);
        }
        if (Objects.isNull(channel)) {
            log.error("channel is null, cannot get channel for {}:{}", availableProviderAddress.getIp(), availableProviderAddress.getPort());
            throw ConsumerException.create("channel is null, cannot get channel for " + availableProviderAddress.getIp() + ":" + availableProviderAddress.getPort());
        }
        return channel;
    }

}
