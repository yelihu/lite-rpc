package org.example.rpc;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author yelihu
 */
public class ZooKeeperTest {
    private static final String CONNECT_STRING = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT_MILL_SEC = 3000;
    private static final byte[] DATA_BYTES = "hello".getBytes();

    ZooKeeper zooKeeper;

    {
        try {
            zooKeeper = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT_MILL_SEC, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testNode() {
        //权限：完全开放，任何client都可以操作节点
        List<ACL> openAclUnsafe = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        //创建节点, 选择类型PERSISTENT或者EPHEMERAL，
        // 1. PERSISTENT在客户端下线的时候不会被删除
        String result = null;
        try {
            result = zooKeeper.create("/lite-rpc/yelihu", DATA_BYTES, openAclUnsafe, CreateMode.PERSISTENT);
            System.out.println(result);
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
