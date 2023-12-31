package org.example.rpc.remoting.zookeeper.exception;

/**
 * @author yelihu
 */
public class ZookeeperException extends RuntimeException {
    public ZookeeperException(String message) {
        super(message);
    }

    public ZookeeperException(String message, Throwable cause) {
        super(message, cause);
    }
}
