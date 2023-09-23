package org.example.rpc.demo;

/**
 *
 * @author yelihu
 */
public class ProxyReference<T> {
    private Class<T> anInterface;

    public void setInterface(Class<T> anInterface) {
        this.anInterface = anInterface;
    }

    public Class<T> getInterface() {
        return anInterface;
    }

    /**
     *
     * @return {@link T}
     */
    public T get() {


        return null;
    }
}
