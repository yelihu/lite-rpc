package org.example.rpc.zookeeper.service.entity;

import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

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
}
