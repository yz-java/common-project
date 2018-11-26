package com.yz.common.core.utils;

import java.io.*;

/**
 * 对象序列化、反序列化工具类
 *
 * @Auther: yangzhao
 * @Date: 2018/11/26 10:28
 * @Description:
 */
public class SerializeUtil {
    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        // 序列化
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = null;
        // 反序列化
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}
