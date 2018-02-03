/**
 * @FileName: BaseSmartLockParam.java
 * @Package com.ziroom.minsu.services.common.smartlock
 * 
 * @author jixd
 * @created 2016年6月22日 下午5:50:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.param;

import java.io.Serializable;

/**
 * <p>智能锁请求基础类</p>
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
public class BaseSLParam implements Serializable{
	
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -7174793714550910280L;
	/**
	 * 操作人的帐号id，如果是自如员工，就是自如的员工编号，自如业务系统需要携带此参数
	 */
	private String op_userid;
	/**
	 * 操作人姓名，自如业务系统需要携带此参数
	 */
	private String op_name;
	/**
	 * 操作人电话，自如业务系统需要携带此参数
	 */
	private String op_phone;
	
	public String getOp_userid() {
		return op_userid;
	}
	public void setOp_userid(String op_userid) {
		this.op_userid = op_userid;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public String getOp_phone() {
		return op_phone;
	}
	public void setOp_phone(String op_phone) {
		this.op_phone = op_phone;
	}
	
	
	
}
