/**
 * @FileName: BaseSLVo.java
 * @Package com.ziroom.minsu.services.common.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月23日 上午10:20:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.vo;

import java.io.Serializable;

/**
 * <p>智能锁接口返回基础VO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class BaseSLVo implements Serializable{
	
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -6862517279514066265L;
	/**
	 * 请求Id
	 */
	private String ReqID;
	/**
	 * 错误编码
	 */
	private Integer ErrNo;
	/**
	 * 原因描述
	 */
	private String ErrMsg;
	
	public String getReqID() {
		return ReqID;
	}
	public void setReqID(String reqID) {
		ReqID = reqID;
	}
	public Integer getErrNo() {
		return ErrNo;
	}
	public void setErrNo(Integer errNo) {
		ErrNo = errNo;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	
	
	
}
