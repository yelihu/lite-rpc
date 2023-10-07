package org.example.rpc.registry.exception;

/**
 * define the exception of provider
 *
 * @author yelihu
 */
public class ProviderException extends RuntimeException {

    private String code;

    public static String PROVIDER_SERVICE_NOT_FOUND = "P001";

    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(String code, String message) {
        super(message);
        this.code = code;
    }


    public static ProviderException create(String message) {
        return new ProviderException(message);
    }

    public static ProviderException createProviderServiceNotFound(String message) {
        return new ProviderException(PROVIDER_SERVICE_NOT_FOUND, message);
    }

    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
