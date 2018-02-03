/**
 * @FileName: DynamicPassSlVo.java
 * @Package com.ziroom.minsu.services.house.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月23日 下午12:32:39
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
public class DynamicPassSlVo extends BaseSLVo{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -2482888613820333543L;
	
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 过期时间
	 */
	private Long invalid_time;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getInvalid_time() {
		return invalid_time;
	}
	public void setInvalid_time(Long invalid_time) {
		this.invalid_time = invalid_time;
	}
	
	
}
