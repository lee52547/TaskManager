package com.jd.bluedragon.sep.task.util;

import java.io.*;

/**
 * 对象序列化工具类
 */
public class SerializeUtil {


    /**
     * 序列化
     *
     * @param object 需要序列化的对象
     * @return 序列化之后的byte数组
     */
    public static byte[] serialize(Object object) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                //
            }
        }
        return null;

    }


    /**
     * 反序列化对象
     *
     * @param bytes 序列化之前的数组对象
     * @return 序列化之后的对象<T>
     */
    public static <T> T unserialize(byte[] bytes, Class<T> clazz) {
        T t = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            if (o != null) {
                t = (T) o;
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                //
            }
        }
        return t;

    }

}
