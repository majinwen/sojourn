package com.zra.common.utils;


import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 */
public class MD5Util {
    private static Logger logger = Logger.getLogger(MD5Util.class);

    public static String encodeMD5(String str, String key) {
        try {
            return MD5(str + key);
        } catch (Exception e) {
            logger.error("encodeMD5 异常", e);
        }
        return null;
    }

    /**
     * 进行MD5加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的字符串
     */
    private static String MD5(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            if (StringUtil.isBlank(str)) {
                logger.info("MD5 加密字符串为空");
                return null;
            }
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char strs[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(strs).toLowerCase();
        } catch (Exception e) {
            logger.error("MD5 异常：", e);
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "你好啊";
        logger.info("result:" + encodeMD5(str, ""));
    }
}
