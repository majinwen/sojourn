/**
 * @FileName: UserLivenessReqeust.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年9月18日 下午5:47:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class UserLivenessReqeust extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户uid集合
	 */
	private List<String> uidList;

	public List<String> getUidList() {
		return uidList;
	}

	public void setUidList(List<String> uidList) {
		this.uidList = uidList;
	}

	

}
