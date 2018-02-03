/**
 * @FileName: MenuOperRequest.java
 * @Package com.ziroom.minsu.services.basedata.logic
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 后台菜单查询参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp  2017/5/9
 * @since 1.0
 * @version 1.0
 */
public class OpLogRequest extends PageRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628937312732559551L;


	/**
	 * 操作路径 查询
	 */
	private String opUrl;

	
	/**
	 * 员工号  查询
	 */
	private String empCode;
	
	/**
	 * 员工 名字 查询
	 */
	private String empName;
	
	
	public String getOpUrl() {
		return opUrl;
	}

	public void setOpUrl(String opUrl) {
		this.opUrl = opUrl;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpLogRequest [opUrl=" + opUrl + ", empCode=" + empCode
				+ ", empName=" + empName + "]";
	}

	


	
}
