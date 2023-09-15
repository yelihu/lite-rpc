package org.example.rpc;

import lombok.SneakyThrows;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.example.rpc.zk.MyWatcher;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yelihu
 */
public class ZooKeeperTest {
    private static final String CONNECT_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT_MILL_SEC = 3000;
    private static final byte[] DATA_BYTES = "hello".getBytes();


    /**
     * 测试创建节点
     * ZooDefs.Ids.OPEN_ACL_UNSAFE 表示完全开放，任何client都可以操作节点
     */
    @Test
    public void testNode() {
        String result = null;
        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, null)) {
            result = zooKeeper.create("/lite-rpc/yelihu", DATA_BYTES, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(result);
        } catch (KeeperException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试删除节点
     */
    @Test
    @SneakyThrows
    public void testDeleteNode() {
        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, null)) {
            //version -1表示强制删除
            zooKeeper.delete("/lite-rpc/yelihu", -1);
        }
    }

    /**
     * 测试节点是否存在
     */
    @Test
    @SneakyThrows
    public void tesNodeExist() {
        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, null)) {
            Stat exists = zooKeeper.exists("/lite-rpc", null);
            System.out.println(exists);
            //0
            //2
            //0
            System.out.println(exists.getVersion());
            System.out.println(exists.getCversion());
            System.out.println(exists.getAversion());
        }
    }

    @Test
    @SneakyThrows
    public void tesNodeExistUsingDefaultWatcher() {
        //set MyWatcher to watch, print log when specific node ("/lite-rpc") deleted
        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, new MyWatcher())) {
            // if watch is true, watch this node using default watcher (MyWatcher)
            Stat exists = zooKeeper.exists("/lite-rpc", true);
            System.out.println(exists);

            System.out.println(exists.getVersion());
            System.out.println(exists.getCversion());
            System.out.println(exists.getAversion());

            while (true) {
                Thread.sleep(10000);
            }

        }
    }


    /**
     * 测试修改节点数据
     */
    @Test
    @SneakyThrows
    public void testSetData() {
        try (ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, null)) {
            Stat exists = zooKeeper.exists("/lite-rpc", null);

            Stat newExists = zooKeeper.setData("/lite-rpc", "hello world".getBytes(StandardCharsets.UTF_8), -1);
            //0|1
            //2|2
            //0|0
            System.out.println(exists);
            System.out.println(exists.getVersion() + "|" + newExists.getVersion());
            System.out.println(exists.getCversion() + "|" + newExists.getCversion());
            System.out.println(exists.getAversion() + "|" + newExists.getAversion());
        }
    }

}
