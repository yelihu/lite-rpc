package org.example.rpc.zookeeper;

/**
 * @author yelihu
 */
public interface ZNodePathUtils {
    String ROOT = "/lite-rpc-metadata";

    String PROVIDERS = ROOT + "/providers";

    String CONSUMERS = ROOT + "/consumers";

    /**
     * concat provider path with interface name, as follows: /lite-rpc-metadata/providers/com.example.service.IHelloService
     *
     * @param serviceName interface name
     * @return {@link String}
     */
    static String getProviderServicePath(String serviceName) {
        return PROVIDERS + "/" + serviceName;
    }


    static String getProviderServiceNodePath(String serviceName, String ipAddress, int port) {
        return getProviderServicePath(serviceName) + "/" + ipAddress + ":" + port;
    }


    static String getConsumerPath(String serviceName) {
        return CONSUMERS + "/" + serviceName;
    }
}
