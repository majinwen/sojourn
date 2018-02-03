/**
 * @FileName: PayMessageConst.java
 * @Package: com.ziroom.sms.services.cleaning.constant
 * @author sence
 * @created 7/27/2015 11:53 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.services.common.constant;

/**
 * 
 * <p>支付消息的常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class PayMessageConst {
	
    /**
     * 下单 失败
     */
    public static final String GET_ORDER_FAIL = "the api ZRGenerOrder to get order return null!";
    
    /**
     * 下单 失败
     */
    public static final String ORDER_PAYCODE_NULL = "the api ZRGenerOrder to get order return a null payCode";
    
    /**
     *  失败
     */
    public static final String GET_PAY_FAIL = "the api paySubmit to get pay linkUrl return null!";
    
    /**
     *  校验 请求 参数用户 uid
     */
    public static final String PARAM_UID_FAIL = "the request param uid is different compared with the in the order !";
    
    
    /**
     *  解密失败
     */
    public static final String DECRYPTION_FAIL = "decryption failed!";
    
    
    /**
     * 私有化其构造
     */
    protected PayMessageConst() {
    }
}
