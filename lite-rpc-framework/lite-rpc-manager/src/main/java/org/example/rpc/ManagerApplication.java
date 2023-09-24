package org.example.rpc;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.example.rpc.zookeeper.service.entity.ZNode;
import org.example.rpc.zookeeper.service.impl.ZookeeperClusterClient;
import org.example.rpc.zookeeper.service.ZookeeperClient;

import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;
import static org.example.rpc.zookeeper.ZNodePathUtils.*;

/**
 * manage registry center
 * connect with zookeeper and create initial node before start
 *
 * @author yelihu
 */
@Slf4j
public class ManagerApplication {
    private static final ZookeeperClient ZOOKEEPER_CLIENT;

    static {
        CountDownLatch latch = new CountDownLatch(1);
        ZOOKEEPER_CLIENT = new ZookeeperClusterClient(getConnectSuccessWatcher(latch));
    }

    /**
     * create basic node of zookeeper
     */
    public static void main(String[] args) {

        Lists.newArrayList(ZNode.createEmpty(ROOT), ZNode.createEmpty(PROVIDERS), ZNode.createEmpty(CONSUMERS))
                .forEach(node -> ZOOKEEPER_CLIENT.createPersistentNodeNx(node, null));

        ZOOKEEPER_CLIENT.close();
    }

    private static Watcher getConnectSuccessWatcher(CountDownLatch latch) {
        return event -> {
            if (event.getState() == SyncConnected) {
                log.info("client connected with zookeeper successfully");
                latch.countDown();
            }
        };
    }

}
