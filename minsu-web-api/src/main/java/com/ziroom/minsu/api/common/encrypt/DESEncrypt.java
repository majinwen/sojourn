/**
 * @FileName: DESEncrypt.java
 * @Package: com.ziroom.cleaning.common.encrypt
 * @author sence
 * @created 9/8/2015 4:25 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.encrypt;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>DES加密实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class DESEncrypt implements IEncrypt {
	
	private static final Logger logger = LoggerFactory.getLogger(DESEncrypt.class);
    /**
     * 密钥
     */
    private static final String keyString = "vpRU1kmU";
    /**
     * 虚拟密钥
     */
    private static final String ivString = "EbpZ1WtY";

    /**
     * DES加密
     *
     * @param content
     * @return
     */
    @Override
    public String encrypt(String content) {
        try {
            if (Check.NuNStr(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return DESPlus.byteArr2HexStr(result);
        } catch (Exception e) {
            LogUtil.error(logger,"e:{},content={}",e,content);
        }
        return null;
    }

    @Override
    public String decrypt(String content) {
        try {
            if (Check.NuNStr(content)) {
                return null;
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            DESKeySpec dks = new DESKeySpec(keyString.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
          
            byte[] result = cipher.doFinal(DESPlus.hexStr2ByteArr(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            LogUtil.error(logger,"e:{},content={}",e,content);
        }
        return null;
    }

    public static void main(String[] args) {
        DESEncrypt desEncrypt = new DESEncrypt();
        System.out.println(desEncrypt.encrypt("null"));
        System.out.println(desEncrypt.decrypt(desEncrypt.encrypt("")));
    }
}
