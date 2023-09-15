package org.example.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yelihu
 */
@Slf4j
public class ZooKeeperClusterTest {
    private static final String CONNECT_CLUSTER_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final int SESSION_TIMEOUT_MILL_SEC = 3000;
    private static final byte[] DATA_BYTES = "hello".getBytes();

    CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void testNode() {
        String result = null;
        log.info("start to connect zk cluster");
        Watcher watcher = event -> {
            if (event.getState().equals(Watcher.Event.KeeperState.SyncConnected)) {
                log.info("zk connected!");
            }
        };

        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_CLUSTER_STRING, SESSION_TIMEOUT_MILL_SEC, watcher)) {
            result = zooKeeper.create("/lite-rpc/test", DATA_BYTES, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            System.out.println(result);
        } catch (InterruptedException | IOException | KeeperException e) {
            throw new RuntimeException(e);
        }
    }

}
