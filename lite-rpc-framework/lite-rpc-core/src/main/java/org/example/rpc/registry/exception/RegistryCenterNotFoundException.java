package org.example.rpc.registry.exception;

/**
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
