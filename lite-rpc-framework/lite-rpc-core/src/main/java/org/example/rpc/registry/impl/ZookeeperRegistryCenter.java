package org.example.rpc.registry.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.rpc.ServiceConfig;
import org.example.rpc.remoting.io.NetworkUtils;
import org.example.rpc.registry.AbstractRegistryCenter;
import org.example.rpc.remoting.zookeeper.service.ZookeeperClient;
import org.example.rpc.remoting.zookeeper.service.entity.ZNode;
import org.example.rpc.remoting.zookeeper.service.impl.ZookeeperClusterClient;

import static org.example.rpc.remoting.zookeeper.ZNodePathUtils.getProviderServiceNodePath;
import static org.example.rpc.remoting.zookeeper.ZNodePathUtils.getProviderServicePath;

/**
 * @author yelihu
 */
@Slf4j
public class ZookeeperRegistryCenter extends AbstractRegistryCenter {
    private static final int PORT_8088 = 8088;
    private final ZookeeperClient zookeeperClient;
    public ZookeeperRegistryCenter(String connectionString, int sessionTimeout) {
        this.zookeeperClient = new ZookeeperClusterClient(connectionString, sessionTimeout, null);
    }
    @Override
    public void register(ServiceConfig serviceConfig) {
        if (log.isDebugEnabled()) {
            log.debug("publish service config, use service config {}", serviceConfig);
        }

        String serviceNodePath = getProviderServicePath(serviceConfig.getServiceInterface().getName());
        ZNode serviceNode = ZNode.createEmpty(serviceNodePath);
        //check service node exists before create
        zookeeperClient.createPersistentNodeNx(serviceNode);


        //create Ephemeral node, ip ( The ip address of the LAN ) :port
        String thisProviderNodePath = getProviderServiceNodePath(serviceConfig.getServiceInterface().getName(), NetworkUtils.getIpAddress(), PORT_8088);
        ZNode serviceProviderNode = ZNode.createEmpty(thisProviderNodePath);
        zookeeperClient.createEphemeralNodeNx(serviceProviderNode);
    }
    @Override
    public void close() {
        zookeeperClient.close();
    }
}
