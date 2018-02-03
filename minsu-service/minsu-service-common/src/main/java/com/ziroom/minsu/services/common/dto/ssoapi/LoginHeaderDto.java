
package com.ziroom.minsu.services.common.dto.ssoapi;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.asura.framework.base.util.UUIDGenerator;

/**
 * <p>单点登录记录头部必传信息</p>
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
public class LoginHeaderDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 9155455835414921993L;
	
	/**
	 * 客户端版本  Client-Version
	 */
	private  String clientVersion;
	
	/**
	 * 响应数据类型（目前仅支持json格式） Accept
	 */
	private String accept;
	
	/**
	 * 系统名，每个对接系统有一个唯一名字
	 */
	private String sys;
	
	/**
	 * 客户端类型：0.未知，1.Android，2.iOS，3.mobile web，4.PC web
	 */
	private Integer clientType;
	
	/**
	 * 客户端信息
	 */
	private String userAgent;
	
	/**
	 * 唯一标识一次请求
	 */
	private String requestId;
	
	public LoginHeaderDto(){
		this.setRequestId(UUID.randomUUID().toString().substring(0,8)+":"+(new Date()).getTime());
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	

}
