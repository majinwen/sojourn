/**
 * @FileName: SmartResult.java
 * @Package com.ziroom.minsu.services.house.smartlock.param
 * 
 * @author yd
 * @created 2016年6月25日 上午11:20:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

import java.io.Serializable;

/**
 * <p>TODO</p>
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
public class SmartResult implements Serializable{
	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -1285280224691788595L;

	/**
	 * 返回code
	 */
	private Integer ErrNo;
	
	/**
	 * 返回信息
	 */
	private String ErrMsg;

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
