/**
 * @FileName: IEncrypt.java
 * @Package: com.ziroom.cleaning.common.encrypt
 * @author sence
 * @created 9/8/2015 3:11 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.encrypt;

/**
 * 
 * <p>TODO</p>
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
public interface IEncrypt {

    /**
     * 对字符串进行加密
     *
     * @param content
     * @return
     */
    String encrypt(String content);

    /**
     * 对字符串进行解密
     *
     * @param content
     * @return
     */
    String decrypt(String content);

}
