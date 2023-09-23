package org.example.rpc;


import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import static lombok.AccessLevel.PRIVATE;

/**
 * @author yelihu
 */
@NoArgsConstructor(access = PRIVATE)
@Accessors(chain = true)
public class LiteRpcProviderBootstrap {

    private static final LiteRpcProviderBootstrap singletonInstance = new LiteRpcProviderBootstrap();

    /**
     * application name
     */
    private String appName;

    /**
     * registry center configuration
     */
    private RegistryCenter registry;

    /**
     * serialization configuration
     */
    private SerializationConfig serialize;

    /**
     * protocol configuration
     */
    private Protocol protocol;

    public static LiteRpcProviderBootstrap getInstance() {
        return new LiteRpcProviderBootstrap();
    }

    /**
     * publish already <b>initialized<b/> provider service
     *
     * @return {@link LiteRpcProviderBootstrap}
     */
    public LiteRpcProviderBootstrap publish() {

        return this;
    }

    /**
     * start this bootstrap
     */
    public void start() {

    }
}
