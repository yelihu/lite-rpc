package org.example.rpc.zookeeper.service;

import org.apache.zookeeper.Watcher;
import org.example.rpc.exceptions.ZookeeperException;
import org.example.rpc.zookeeper.service.entity.ZNode;

/**
 * @author yelihu
 */
public interface ZookeeperClient {

    void createPersistentNodeNx(ZNode node) throws ZookeeperException;

    /**
     * create a persistent node if not exist, else print a message
     *
     * @param node znode
     * @throws ZookeeperException zookeeper exception
     */
    void createPersistentNodeNx(ZNode node, Watcher watcher) throws ZookeeperException;


    void createEphemeralNodeNx(ZNode node) throws ZookeeperException;

    /**
     * create an ephemeral node if not exist, else print a message
     */
    void createEphemeralNodeNx(ZNode node, Watcher watcher) throws ZookeeperException;


    Boolean checkNodeExists(ZNode node) throws ZookeeperException;

    /**
     * check if node exists
     */
    Boolean checkNodeExists(ZNode node, Watcher watcher) throws ZookeeperException;

    /**
     * close zookeeper client
     */
    void close();
}
