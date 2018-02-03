/**
 * @FileName: ColumnTemplateVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author bushujie
 * @created 2016年11月7日 下午5:36:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;

/**
 * <p>专栏模板vo</p>
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
public class ColumnTemplateVo extends ColumnTemplateEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2569505284340180919L;
	
	/**
	 * 创建人名称
	 */
	private String empName;

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
}
