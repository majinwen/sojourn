/**
 * @FileName: LockCallBackVo.java
 * @Package com.ziroom.minsu.services.house.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月25日 下午1:14:57
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.vo;

/**
 * <p>TODO</p>
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
public class LockCallBackVo {
	/**
	 * 服务id
	 */
	private String service_id;
	/**
	 * 返回结果  
	 */
	private Integer result;

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	
	
}
