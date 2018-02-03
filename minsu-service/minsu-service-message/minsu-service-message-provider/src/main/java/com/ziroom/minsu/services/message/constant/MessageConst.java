/**
 * @FileName: MessageConst.java
 * @Package: com.ziroom.sms.services.cleaning.constant
 * @author sence
 * @created 7/27/2015 11:53 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.services.message.constant;


/**
 * 
 * <p>消息的常量</p>
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
public class MessageConst {
	
	/**
     * 通用错误消息，返回:服务错误
     */
    public static final String UNKNOWN_ERROR = "unknown.error";

    /**
     * 通用错误消息，返回的error
     */
    public static final String ERROR_CODE = "error";

    /**
     * 通用提示消息，对象不存在
     */
    public static final String NOT_FOUND = "obj.null";

    /**
     * 通用提示消息，对象不存在
     */
    public static final String PARAM_NULL = "param.null";

    /**
     * 查找条件名字
     */
    public static final String SEARCH_CONDITION_NAME = "searchCondition";


    /**
     * 异常code
     */
    public static final String CODE_ERROR = "code.error";

    /**
     * 私有化其构造
     */
    protected MessageConst() {
    }
}
