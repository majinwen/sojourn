/**
 * @FileName: MessageConst.java
 * @Package: com.ziroom.sms.services.cleaning.constant
 * @author sence
 * @created 7/27/2015 11:53 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.services.common.constant;

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
     *  成功code
     */
    public static final String SUCCESS_CODE = "success";
    
    /**
     *  失败code
     */
    public static final String FAIL_CODE = "failure";
    
	
	/**
	 * 成功状态success
	 */
	public final static String LOGIN_SUCCESS = "20000";
    
	/**
     * 给 im一次发送的数量
     */
    public  final static Integer  SEND_IM_NUM= 20;
    
    /**
     * im消息内容
     */
    public final static String  IM_CONTENT = "您的房东可能正在忙，我们会尽快通知他，感谢您的耐心等待！如需帮助，请致电自如民宿客服热线（9:00-21:00）：400-7711-666";
    
    /**
     * im消息内容（为长租找找聊天活动提供）
     */
    public final static String  IM_FOR_CHANGZU_CONTENT = "自如提供密室逃脱，周末看片会，一起跑步等各种好玩的活动，约他一起参加吧。";
    
    /**
     * im卡片消息图片地址（为长租找找聊天活动提供）
     */
    public final static String  IM_FOR_CHANGZU_PIC_URL = "http://10.16.34.48:8080/group3/M00/06/35/ChAiKlnMyAmAN_XGAAAGWyQpR0Q287.jpg";
    
    /**
     * im uid前缀
     */
    public final static String  IM_UID_PRE="app_";
    
    /**
     * 环信注册密码
     */
    public final static String IM_USER_PASSARD="home,link/";
    
    /**
     * 环信 未注册错误
     */
    public final static String IM_REGISTER_ERROR="resource_not_found";
    
	/**
     * 分页数
     */
    public  final static Integer  limit= 50;


    /**
     * 私有化其构造
     */
    protected MessageConst() {
    }
}
