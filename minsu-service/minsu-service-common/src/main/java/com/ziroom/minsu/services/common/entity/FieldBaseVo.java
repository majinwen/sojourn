/**
 * @FileName: FieldBaseVo.java
 * @Package com.ziroom.minsu.services.common.entity
 * 
 * @author bushujie
 * @created 2017年6月14日 下午2:41:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

/**
 * <p>字段对象基类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class FieldBaseVo {
	
	/**
	 * 是否可编辑
	 */
	private boolean isEdit=true;
	
	/**
	 * 是否在审核中
	 */
	private String auditMsg="";

	/**
	 * @return the isEdit
	 */
	public boolean  getIsEdit() {
		return isEdit;
	}

	/**
	 * @return the auditMsg
	 */
	public String getAuditMsg() {
		return auditMsg;
	}

	/**
	 * @param auditMsg the auditMsg to set
	 */
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	/**
	 * @param isEdit the isEdit to set
	 */
	public void setIsEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
}
