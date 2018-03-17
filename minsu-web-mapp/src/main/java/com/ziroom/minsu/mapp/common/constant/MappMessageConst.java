/**
 * @FileName: MappMessageConst.java
 * @Package com.ziroom.minsu.mapp.common.constant
 * 
 * @author liujun
 * @created 2016年5月3日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.mapp.common.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>M站消息常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class MappMessageConst extends MessageConst {
	
	/**
	 * 房源出租方式错误
	 */
	public static final String HOUSE_RENTWAY_ERROR = "house.rentway.error";
	
	/**
	 * 注册 手机号不存在
	 */
	public static final String USER_MOBILE_REGISTER_ERROR = "user.mobile.register.error";
	
	
	/**
	 * 注册 用户密码错误
	 */
	public static final String USER_PASSWORD_REGISTER_ERROR = "user.password.register.error";
	
	/**
	 * 登录 手机号不存在
	 */
	public static final String USER_MOBILE_LOGIN_ERROR = "user.mobile.login.error";
	
	
	/**
	 * 登录 用户密码错误
	 */
	public static final String USER_PASSWORD_RLOGIN__ERROR = "user.password.login.error";
	/**
	 * 用户头信息错误
	 */
	public static final String USER_HEADER__ERROR = "user.header.error";
	/**
	 * 获取验证码错误
	 */
	public static final String USER_VERIFY_CODE_ERROR = "user.verify.code.error";
	
	/**
	 * 用户名错误
	 */
	public static final String USER_LOGIN_USERNAME_ERROR = "user.login.username.error";

    /**
     * session_return_url_key
     */
    public static final String SESSION_RETURN_URL_KEY = "session_return_url_key";
    /**
     * 日志
     */
    public static final String LOG_FORMAT = "CLASS:[{}],METHOD:[{}],PARAM:[{}],ERROR:[{}]";
    

    /**
     * session_user_key
     */
    public static final String SESSION_USER_KEY = "session_user_key";
	
    /**
     * 用户信息不存在
     */
    public static final String USER_INFO_NULL = "user.info.null";
    
    /**
     * 用户默认图片地址
     */
    public static final String USER_DEFAULT_PIC_URL="/mapp/images/user.png";
	/**
	 * 评价类容最大长度
	 */
	public static final int EVA_CONTENT_MAX_LENGTH=400;

	/**
	 * customer_introduce  房东自我介绍的最大长度
	 */
	public static final int CUSTOMER_INTRODUCE_MAX_LENGTH=600;
	
	/**
	 * 房源唯一标示不能为空
	 */
	public static final String HOUSE_BASE_FID_NULL= "house.base.fid.null";
	
	/**
	 * 房源或房间信息不存在
	 */
	public static final String HOUSE_OR_ROOM_NULL= "house.or.room.null";
	
	/**
	 * 房东信息不存在
	 */
	public static final String LANDLORD_INFO_NULL= "landlord.info.null";
	
	/**
	 * 房东uid不存在
	 */
	public static final String LANDLORDUID_NULL= "landlordUid.null";

	/**
	 * 私有化构造方法
	 */
	private MappMessageConst () {
		
	}
}
