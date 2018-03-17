/**
 * @FileName: ConstDef.java
 * @Package: com.ziroom.cleaning.common.constant
 * @author sence
 * @created 9/14/2015 11:20 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.mapp.common.constant;

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
public class ConstDef {


    /**
     * 请求用户中心的成功code
     */
    public static final String USER_RST_SUCCESS = "20000";

    /**
     * 请求用户中心的成功
     */
    public static final String CUSTOMER_RST_SUCCESS = "success";




    /**
     * 解密参数后存放在request内时的参数名称
     */
    public static final String DECRYPT_PARAM_ATTRIBUTE = "param";
    /**
     * 请求处理前的时间戳
     */
    public static final String PRE_REQUEST_MILLSECOND = "_pre_millsecond";
    /**
     * 系统通用编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
    
    /**
     * 非法参数
     */
    public static final String INVALID_PARAM = "1000001";
   
    /**
     * 私有构造
     */
    private ConstDef() {

    }

}
