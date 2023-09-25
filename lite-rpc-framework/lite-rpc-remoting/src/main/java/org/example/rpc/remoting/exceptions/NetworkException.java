package org.example.rpc.remoting.exceptions;

/**
 * define the exception of network exception
 *
 * @author yelihu
 */
public class NetworkException extends RuntimeException {
    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
