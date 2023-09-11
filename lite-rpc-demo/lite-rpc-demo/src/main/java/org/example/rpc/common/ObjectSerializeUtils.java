package org.example.rpc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yelihu
 */
public abstract class ObjectSerializeUtils {
    public static byte[] toByteArray(Object target) throws IOException {
        if (Objects.isNull(target)) {
            throw new NullPointerException("Target is null");
        }
        if (!(target instanceof Serializable)) {
            throw new RuntimeException("Target must be serializable");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(target);
        return outputStream.toByteArray();
    }
}
