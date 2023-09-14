package org.example.rpc.common;

import java.io.*;

/**
 * @author yelihu
 */
public abstract class ObjectSerializer {
    /**
     * serialize object
     *
     * @param target object
     * @return {@link byte[]}
     */
    public static byte[] toByteArray(Object target) throws IOException {
        if (target == null) {
            throw new NullPointerException("Target is null");
        }
        if (!(target instanceof Serializable)) {
            throw new RuntimeException("Target must be serializable");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(target);
        }
        return outputStream.toByteArray();
    }

    public static Object fromByteArray(byte[] byteArray) throws IOException, ClassNotFoundException {
        if (byteArray == null) {
            throw new NullPointerException("Byte array is null");
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return ois.readObject();
        }
    }

}
