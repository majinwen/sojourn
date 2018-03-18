package com.zra.common.utils;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import java.io.*;

/**
 * 对象学序列化、反序列化工具类
 *
 * @author wgh
 * @since 2013-7-2
 */
public class ObjectTranscoder {

    private final static Logger LOGGER = LoggerFactoryProxy.getLogger(ObjectTranscoder.class);

    public static byte[] serialize(Object value) {
        LOGGER.info("[序列化]被序列化对象:" + value);
        if (value == null) {
            throw new NullPointerException("序列化对象为空");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("[序列化]出错:", e);
            throw new IllegalArgumentException("序列化失败", e);
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    public static Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            LOGGER.error("[反序列化]失败", e);
        } finally {
            close(is);
            close(bis);
        }
        return rv;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.error("无法关闭流", e);
            }
        }
    }
}
