/**
 * @FileName: DESEncrypt.java
 * @Package: com.ziroom.cleaning.common.encrypt
 * @author sence
 * @created 9/8/2015 4:25 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.activity.common.encrypt;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

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
            LogUtil.error(logger,"e:{}",e);
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
            LogUtil.error(logger,"e:{}",e);
        }
        return null;
    }

    public static void main(String[] args) {
        DESEncrypt desEncrypt = new DESEncrypt();
        System.out.println(desEncrypt.decrypt("b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9acd90143f9ba18d19afad9ff130d4ea84c5f403012324a9182cadae81c0d9d6f03b92db379d3d607892d2e2f9e8720f19453ae72616c44146a848e47eab5ee5c0a1351065f83f484c9f70141c145fe795ff61dbd98561ef0ec9402081f7a4a876b40939e4ba82d357ef348193bce9475635311566eb047a4f144d8c72f4b67fc944ac80528ae2e630fb510bee5b026e9ad0355a5090dd6b47adc95b69c6a67f43d7ff18eef49dd4917809b7634f9bb157bee384e0bb99adf88625c2657df15868fa6e22ad3cd313d7d281a55ea638eb849650762777a9e2131ec8e4a253a1a3e81edb4b1712086e8790693f0d7a9846dedf2cd1cf0525846e241d53a6ee6627092931df2c3b173a2064f7ee5f7f514f29ce6a5e9cf3954b5dc34b1c60e23cb11648b3d2c71ec87785bc29f8f67700c858f52a4f2ee035252483240129d8da2ee633102729b0fcf00e67faae2749bd4ce930084ef6308e429a906366a0e2fee5b1f"));
    }
}
