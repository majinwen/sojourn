/**
 * @FileName: ColumnTemplateRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author bushujie
 * @created 2016年11月7日 下午5:32:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>专栏模板查询参数</p>
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
public class ColumnTemplateRequest extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2170959649873024384L;
	
	/**
	 * 模板名称
	 */
	private String tempName;
	/**
	 * 模板类型
	 */
	private Integer tempType;
	
	/**
	 * 员工姓名
	 */
	private String empName;
	
	/**
	 * 创建人fid
	 */
	private String createFid;
	
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the tempName
	 */
	public String getTempName() {
		return tempName;
	}
	/**
	 * @param tempName the tempName to set
	 */
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	/**
	 * @return the tempType
	 */
	public Integer getTempType() {
		return tempType;
	}
	/**
	 * @param tempType the tempType to set
	 */
	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}
	/**
	 * @return the createFid
	 */
	public String getCreateFid() {
		return createFid;
	}
	/**
	 * @param createFid the createFid to set
	 */
	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}
}
