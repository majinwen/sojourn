
package com.ziroom.minsu.api.common.constant;


/**
 * <p>单点登录 请求参数名称</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HeaderParamName {

	/**
	 * 客户端版本
	 */
	public final static String CLIENT_VERSION = "Client-Version";
	/**
	 * 响应数据类型（目前仅支持json格式）
	 */
	public final static String ACCEPT = "Accept";
	/**
	 * 系统名，每个对接系统有一个唯一名字
	 */
	public final static String SYS = "Sys";
	/**
	 * 客户端类型：0.未知，1.Android，2.iOS，3.mobile web，4.PC web
	 */
	public final static String CLIENT_TYPE= "Client-Type";
	/**
	 * 客户端信息
	 */
	public final static String USER_AGENT = "User-Agent";
	/**
	 * 唯一标识一次请求
	 */
	public final static String REQUEST_ID = "Request-Id";
	
	/**
	 * TOKEN
	 */
	public final static String TOKEN= "token";
    /**
     * 网关标示
     */
    public final static String X_Forwarded_Ziroom = "X-Forwarded-Ziroom";


}
