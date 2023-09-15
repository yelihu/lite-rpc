package org.example.rpc.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Watcher <b>ONLY</b> focus on connection events,
 *
 * @author yelihu
 */
@Slf4j
public class MyWatcher implements Watcher {
    private static void printConnectionEvent(WatchedEvent event) {
        switch (event.getState()) {
            case SyncConnected:
                log.info("Zookeeper client is connected to the server");
                break;
            case Disconnected:
                log.info("Zookeeper client is disconnected from the server, authentication failed or some internal error");
                break;
            default:
                break;
        }
    }

    /**
     * @param event event
     */
    @Override
    public void process(WatchedEvent event) {
        log.info(" event = {}", event);
        //判断事件类型
        switch (event.getType()) {
            case None:
                printConnectionEvent(event);
                break;
            case NodeCreated:
                log.info("Node (path={})  created", event.getPath());
                break;
            case NodeDeleted:
                log.info("Node (path={}) deleted", event.getPath());
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getType());
        }
    }
}
