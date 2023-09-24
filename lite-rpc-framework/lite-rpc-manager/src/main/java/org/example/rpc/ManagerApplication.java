package org.example.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

/**
 * manage registry center
 *
 * @author yelihu
 */
@Slf4j
public class ManagerApplication {
    private static final String CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final int SESSION_TIMEOUT = 10000;
    /**
     * root znode name
     */
    private static final String LITE_RPC_METADATA = "/lite-rpc-metadata";

    /**
     * create basic node of zookeeper
     */
    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;

        CountDownLatch latch = new CountDownLatch(1);
        try {
            zooKeeper = new ZooKeeper(CONNECT_URL, SESSION_TIMEOUT, getConnectSuccessWatcher(latch));

            zooKeeper.setData(LITE_RPC_METADATA, "".getBytes(UTF_8), -1);


        } catch (IOException | KeeperException | InterruptedException e) {
            log.error("connection with zookeeper occurs IOException, message={}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(zooKeeper)) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    log.error("connection with zookeeper error, message={}", e.getMessage());
                }
            }
        }

    }

    public void test(String arg2, String arg1, Object arg3) {

    }

    private static Watcher getConnectSuccessWatcher(CountDownLatch latch) {
        return event -> {
            if (event.getState() == SyncConnected) {
                log.info("client connected with zookeeper, connect_string={}, session_timeout={}", CONNECT_URL, SESSION_TIMEOUT);
                latch.countDown();
            }
        };
    }

}
