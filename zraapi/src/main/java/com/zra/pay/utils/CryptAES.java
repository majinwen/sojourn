package com.zra.pay.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;

/**
 * ClassName: CryptAES
 *
 * @author liuxm
 * @Description: AES加密，解密
 * @date 2014年11月21日
 */
public class CryptAES {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CryptAES.class);

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    /**
     * @param keyStr    密钥
     * @param plainText 加密数据
     * @return String 加密完数据
     * @throws @author liuxm
     * @Description: AES 加密
     * @date 2014年11月21日
     */
    public static String AES_Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            LOGGER.info("[加密]被加密内容:" + plainText);
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            LOGGER.error("[加密]错误:", e);
        }
        return new String(Base64.encodeBase64(encrypt));
    }

    /**
     * @param keyStr      密钥
     * @param encryptData 解密数据
     * @return String
     * @throws @author liuxm
     * @Description: 解密
     * @date 2014年11月21日
     */
    public static String AES_Decrypt(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try {
            LOGGER.info("[解密]被解密内容:" + encryptData);
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData.getBytes()));
        } catch (Exception e) {
            LOGGER.error("[解密]错误:", e);
        }
        return new String(decrypt).trim();
    }

    /**
     * @param key
     * @param @throws Exception
     * @return Key
     * @throws @author liuxm
     * @Description: 封装KEY值
     * @date 2014年11月21日
     */
    private static Key generateKey(String key) throws Exception {

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            LOGGER.error("[生成秘钥]错误:", e);
            throw e;
        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String keyStr = "8w091ql5l2tt6qxj";
        String plainText = "this is a string will be AES_Encrypt";
        // 测试获取账户接口
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("uid", "c1543e4d-41df-adf4-0a73-03ad9b50c338");
        String encText = AES_Encrypt(keyStr, jsonObj.toString());
        String a = URLDecoder.decode(
                "UKNoziCbgRFQfK%2FsvQDyRd8DMeX8Z949qmbd0A1Fer5nuJuq25rqLnBcdEnXjOHPxg5gRjTgbJEN9YUQWVWrfKAM%2F%2Bow754599zMEybvYpEY0ow%2FcKCV7L4lxbcZrIJ9Ah23XJZmMFWN6zu5B2dfnYs0CJLaqRL47Nk0iEMUS2617ELdMVIIaZBMqnDNEPpZJSc7yax%2FTmoJVe%2BLfjjTQNlt%2B%2BGM49b8dwL2hjnjVvEEiBlKCabOPubnZ6%2FngOn1zWGp7S8RRW8lwCk08ts2Ix5BEjpuMTuU4mNzEcuH5hsjeW%2BpBLXkDKtxRtCk0p%2BJMhr0VYsZljotSGD9Bj4sX59GWLR9fW6BgNHDcEBqKgDkbzM%2BMRJ39U8TYqcNrQ20",
                "UTF-8");
        String decString = AES_Decrypt(keyStr, a);


    }
}