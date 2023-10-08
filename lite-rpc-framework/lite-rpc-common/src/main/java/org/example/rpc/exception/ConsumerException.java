package org.example.rpc.exception;

/**
 * define the exception of consumer
 *
 * @author yelihu
 */
public class ConsumerException extends RuntimeException {

    private String code;

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static ConsumerException create(String message) {
        return new ConsumerException(message);
    }

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
