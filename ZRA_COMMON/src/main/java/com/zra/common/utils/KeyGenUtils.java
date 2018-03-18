package com.zra.common.utils;

import java.util.UUID;

/**
 * @author yhcui E-mail:cuiyh9@ziroom.com
 * @version 创建时间：Jan 14, 2016 4:09:43 PM
 *          类说明
 *          生成UUID工具类 --目前先用java自带实现
 */
public class KeyGenUtils {

    private KeyGenUtils() {

    }

    /**
     * 统一生成UUID
     *
     * @return
     */
    public static String genKey(){
        String id = UUID.randomUUID().toString();
        id = id.replaceAll("-", "");
        return id;
    }

}
