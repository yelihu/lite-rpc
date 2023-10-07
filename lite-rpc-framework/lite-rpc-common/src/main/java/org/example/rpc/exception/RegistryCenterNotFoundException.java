package org.example.rpc.exception;

/**
 * define the exception of registry center not found exception
 *
 * @author yelihu
 */
public class RegistryCenterNotFoundException extends RuntimeException {
    public RegistryCenterNotFoundException(String message) {
        super(message);
    }

    public RegistryCenterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
