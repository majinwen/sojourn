/**
 * @FileName: AddPassVo.java
 * @Package com.ziroom.minsu.services.common.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月23日 上午10:32:46
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
public class AddPassSLVo extends BaseSLVo{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -4316749545896256407L;

	/**
	 * 密码id，密码id在此设备中唯一
	 */
	private String password_id;
	/**
	 * 可能没有 下划线
	 */
	private String service_id;
	/**
	 * 服务序号，异步操作的序列号，一定时间内系统全局唯一。当密码异步操作完成后，异步操作回调会携带service_id到请求发起方
	 */
	private String serviceid;
	/**
	 * 密码值，该值为用户传入或者为随机生成的
	 */
	private String password;
	
	public String getPassword_id() {
		return password_id;
	}
	public void setPassword_id(String password_id) {
		this.password_id = password_id;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	
}
