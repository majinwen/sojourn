/**
 * @FileName: EncryptFactory.java
 * @Package: com.ziroom.cleaning.common.encrypt
 * @author sence
 * @created 9/8/2015 3:10 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.encrypt;

import com.asura.framework.base.util.Check;

/**
 * 
 * <p>加密方式工厂</p>
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
public class EncryptFactory {

    public static final String ENCRYPT_DES = "DES";

    /**
     * 私有化构造
     */
    private EncryptFactory(){

    }
    /**
     * 创建加密解密实现
     *
     * @param encryptType
     * @return
     */
    public static IEncrypt createEncryption(String encryptType) {
        if (Check.NuNStr(encryptType)){
            return null;
        }
        if (encryptType.equals(ENCRYPT_DES)) {
            return new DESEncrypt();
        }
        return null;
    }

}
