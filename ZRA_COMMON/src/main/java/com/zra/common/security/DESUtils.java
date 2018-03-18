package com.zra.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

/**
 * The type Des utils created by cuigh6 on 2016/05/13.
 */
public class DESUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(DESUtils.class);
    private final static String DES = "DES";

    /**
     * 密钥
     */
    private static final String keyString = "ZiR00mYi";
    /**
     * 虚拟密钥
     */
    private static final String ivString = "vpRZ1kmU";

    /**
     * The entry point of application created by cuigh6.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        //String data = "{\"orderBid\":\"23fb6075a3344afda428aac28989df22\"}";
        String data = "{\"uid\":\"08d1dfaa-c3b2-e4f4-8f66-03ced4bd15b9\"}";
        //String key = "wang!@#$%";
        String key = "ziroom12";
        //System.err.println(encrypt(data, key));
        //System.err.println(decrypt(encrypt(data, key), key));
        //System.err.println(decrypt(data, key));
        //System.out.println(URLEncoder.encode(" ", "utf-8"));
        //System.out.print(URLDecoder.decode(" ", "utf-8"));
        //m+RI0VeEqJM=
        // System.out.println(URLEncoder.encode(DESUtils.encrypt(data,key),"utf-8"));
        System.out.println(DESUtils.encrypt(data));
        //System.out.println(DESUtils.decrypt("046d0b22decfc945ba11945df093b5cc1c1dce027406d62ffee9b4760c974d010ff855483b194e8a14a208a6d8a659a8"));
        System.out.println(Md5Utils.md5s(data));
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data the data
     * @return string string
     * @throws Exception the exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(), keyString.getBytes());
        String strs = DESPlus.byteArr2HexStr(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data the data
     * @return string string
     * @throws IOException the io exception
     * @throws Exception   the exception
     */
    public static String decrypt(String data) throws Exception {
        if (data == null)
            return null;
        byte[] buf = DESPlus.hexStr2ByteArr(data);
        byte[] bt = decrypt(buf, keyString.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        //SecureRandom sr = new SecureRandom();
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        //SecureRandom sr = new SecureRandom();
        IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);

        return cipher.doFinal(data);
    }


    /**
     * Des encrypt for finance created by cuigh6 .
     *
     * @param message   the message
     * @param keyString the key string
     * @return the string
     */
    public static String desEncryptForFinance(String message,String keyString) {
        String result = ""; // DES加密字符串
        try {
            SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");// 获得密钥
            Cipher   cipher = null;
			/* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
            cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
            byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
            BASE64Encoder enc = new BASE64Encoder();
            result = enc.encode(resultBytes);// 进行BASE64编码
        } catch (Exception e) {
            LOGGER.error("DES error",e);
        }
        return result;
    }

}