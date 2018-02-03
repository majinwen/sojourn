/**
 * @FileName: UsualContactVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author jixd
 * @created 2016年5月19日 下午11:03:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.order.UsualContactEntity;

/**
 * <p>显示联系人列表</p>
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
public class UsualContactVo extends UsualContactEntity {
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 5521852060903860643L;
	/**
	 * 联系人年龄
	 */
	private Integer conAge;

	public Integer getConAge() {
		return conAge;
	}

	public void setConAge(Integer conAge) {
		this.conAge = conAge;
	}
}
