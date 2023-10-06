package org.example.rpc.remoting.zookeeper.service.entity;

import lombok.Getter;

/**
 * @author yelihu
 */
@Getter
public class ZNode {

    private String path;

    private byte[] data;


    private ZNode(String path, byte[] data) {
        this.path = path;
        this.data = data;
    }

    public static ZNode create(String path, byte[] data) {
        return new ZNode(path, data);
    }

    public static ZNode createEmpty(String path) {
        return new ZNode(path, null);
    }
    public static ZNode of(String path) {
        return new ZNode(path, null);
    }
}
