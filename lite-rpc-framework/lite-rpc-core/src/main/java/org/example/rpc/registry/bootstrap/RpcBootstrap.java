package org.example.rpc.registry.bootstrap;


import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rpc.entity.config.ProtocolConfig;
import org.example.rpc.entity.config.SerializationConfig;
import org.example.rpc.entity.config.ServiceConfig;
import org.example.rpc.registry.RegistryCenter;
import org.example.rpc.remoting.utils.PortConst;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.ObjectUtils.anyNull;


/**
 * provider bootstrap, which is used to initialize the provider service
 * 启动一个客户端，作为provider上报服务消息或者作为consumer查询服务provider信息。
 *
 * @author yelihu
 */
@Getter
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class RpcBootstrap {

    /**
     * singleton instance
     */
    private static final RpcBootstrap singletonInstance = new RpcBootstrap();

    /**
     * application name, identify this application
     */
    private String appName;

    /**
     * serialization configuration
     */
    private SerializationConfig serialize;

    /**
     * protocol configuration
     */
    private ProtocolConfig protocol;

    /**
     * registry center configuration, configure the registry center address and port
     */
    private RegistryCenter registryCenter;

    //service map, store the service reference which is published, key is service class name.
    private static final Map<String, ServiceConfig> publishedServiceMap = Maps.newConcurrentMap();


    /**
     * cache netty channel, avoid create new channel every time before consume
     */
    public static final Map<InetSocketAddress, Channel> CHANNEL_CACHE = Maps.newConcurrentMap();


    public static RpcBootstrap getInstance() {
        return singletonInstance;
    }

    public RpcBootstrap appName(String appName) {
        this.appName = appName;
        return this;
    }

    public RpcBootstrap registry(RegistryCenter registryCenter) {
        if (log.isDebugEnabled()) {
            log.debug("registry center config, use registry center {}", registryCenter.getClass().getSimpleName());
        }
        this.registryCenter = registryCenter;
        return this;
    }

    public RpcBootstrap serialize(SerializationConfig serialize) {
        this.serialize = serialize;
        return this;
    }

    public RpcBootstrap protocol(ProtocolConfig protocol) {
        if (log.isDebugEnabled()) {
            log.debug("protocol config, use protocol {}", protocol);
        }
        this.protocol = protocol;
        return this;
    }

    /**
     * configure the service reference, create proxy object when {@link ProxyReference#get()} is called
     *
     * @param reference service reference
     * @return {@link RpcBootstrap}
     */
    public RpcBootstrap reference(ProxyReference<?> reference) {
        if (Objects.isNull(this.registryCenter)) {
            throw new IllegalArgumentException("RpcBootstrap's registryCenter can not be null,check configuration before reference");
        }
        // setting registry center to proxy reference
        reference.setRegistryCenter(this.registryCenter);
        return this;
    }

    /**
     * publish the service to the registry center
     *
     * @param serviceConfig Service configuration
     * @return {@link RpcBootstrap}
     */
    public RpcBootstrap publish(ServiceConfig serviceConfig) {
        //check RpcBootstrap's attributes null
        if (anyNull(this.appName, this.registryCenter, this.serialize, this.protocol, serviceConfig)) {
            throw new IllegalArgumentException("RpcBootstrap's attributes can not be null,check configuration before publish");
        }

        publishedServiceMap.put(serviceConfig.getRegistryKey(), serviceConfig);

        registryCenter.register(serviceConfig);
        return this;
    }


    public RpcBootstrap publish(List<ServiceConfig> serviceConfig) {
        serviceConfig.forEach(this::publish);
        return this;
    }


    /**
     * start this bootstrap
     */
    public void start() {
        //bootstrap a netty server
        this.serverStart();
    }


    public void serverStart() {
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(10);
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // TODO: 2023/10/7 define channel handlers for netty server, process inbound message and outbound message
                            ch.pipeline().addLast(null);
                        }
                    });

            log.info("netty server started!");
            //bind server port 8080
            ChannelFuture channelFuture = serverBootstrap.bind(PortConst._8080).sync();

            //wait for server channel close
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty server start error", e);
            //e.printStackTrace();
        } finally {
            //优雅退出
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
            log.info("netty server terminated!");
        }

    }


}
