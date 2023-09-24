package org.example.rpc.exceptions;

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
