package org.example.rpc.remoting.zookeeper.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.example.rpc.dev.ForTestingOnly;
import org.example.rpc.remoting.exceptions.ZookeeperException;
import org.example.rpc.remoting.zookeeper.service.ZookeeperClient;
import org.example.rpc.remoting.zookeeper.service.entity.ZNode;

import java.io.IOException;
import java.util.Objects;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * zookeeper cluster service
 *
 * @author yelihu
 */
@Slf4j
@Getter
public class ZookeeperClusterClient implements ZookeeperClient {
    @ForTestingOnly
    public static final String TEST_LOCAL_ZOOKEEPER_CLUSTER_CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static final int SESSION_TIMEOUT = 10000;

    private final String connectUrl;

    private final int timeout;

    private final Watcher watcher;

    private final ZooKeeper zooKeeper;

    public ZookeeperClusterClient(String url, int timeout, Watcher watcher) {
        ZooKeeper zookeeper = this.getClient(url, timeout, watcher);
        this.connectUrl = url;
        this.timeout = timeout;
        this.watcher = watcher;
        this.zooKeeper = zookeeper;
    }

    public ZookeeperClusterClient(Watcher watcher) {
        ZooKeeper zookeeper = this.getClient(TEST_LOCAL_ZOOKEEPER_CLUSTER_CONNECT_URL, SESSION_TIMEOUT, watcher);
        if (Objects.isNull(zookeeper)) {
            throw new ZookeeperException("zookeeper client init error");
        }
        this.connectUrl = TEST_LOCAL_ZOOKEEPER_CLUSTER_CONNECT_URL;
        this.timeout = SESSION_TIMEOUT;
        this.watcher = watcher;
        this.zooKeeper = zookeeper;
    }

    public ZooKeeper getClient(String connectUrl, int timeout, Watcher watcher) {
        ZooKeeper zooKeeper;
        try {
            zooKeeper = new ZooKeeper(connectUrl, timeout, watcher);
        } catch (IOException e) {
            log.error("connection with zookeeper occurs IOException, message={}", e.getMessage());
            throw new ZookeeperException(e.getMessage(), e);
        }
        return zooKeeper;
    }

    @Override
    public void createPersistentNodeNx(ZNode node) throws ZookeeperException {
        createPersistentNodeNx(node, null);
    }

    @Override
    public void createPersistentNodeNx(ZNode node, Watcher watcher) {
        CreateMode createMode = CreateMode.PERSISTENT;
        createNodeNx(node, watcher, createMode);
    }

    @Override
    public void createEphemeralNodeNx(ZNode node) throws ZookeeperException {
        createEphemeralNodeNx(node, null);
    }

    @Override
    public void createEphemeralNodeNx(ZNode node, Watcher watcher) {
        CreateMode createMode = CreateMode.EPHEMERAL;
        createNodeNx(node, watcher, createMode);
    }

    @Override
    public Boolean checkNodeExists(ZNode node) throws ZookeeperException {
        return checkNodeExists(node, null);
    }

    private void createNodeNx(ZNode node, Watcher watcher, CreateMode persistent) {
        try {
            String createResult;
            //create node if not exists
            if (!this.checkNodeExists(node, watcher)) {
                createResult = zooKeeper.create(node.getPath(), node.getData(), OPEN_ACL_UNSAFE, persistent);
                log.info("zookeeper znode created, path={}, result={}", node.getPath(), createResult);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("zookeeper znode={} already exists", node.getPath());
                }
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("check znode exists or create node error, message={}", e.getMessage());
            throw new ZookeeperException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean checkNodeExists(ZNode node, Watcher watcher) {
        try {
            Stat exists = zooKeeper.exists(node.getPath(), watcher);
            return exists != null;
        } catch (KeeperException | InterruptedException e) {
            log.error("check znode exists error, message={}", e.getMessage());
            throw new ZookeeperException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("close zookeeper client error, message={}", e.getMessage());
            throw new ZookeeperException(e.getMessage(), e);
        }
    }

}
