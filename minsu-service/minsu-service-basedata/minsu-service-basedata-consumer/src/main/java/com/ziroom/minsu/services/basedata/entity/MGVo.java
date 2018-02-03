/**
 * @FileName: AppMsgBaseVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2016年9月22日 下午4:16:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 首页动画VO
 * @author yanb
 * @since 1.0
 * @version 1.0
 */
public class MGVo extends BaseEntity{

	private static final long serialVersionUID = 3890282136564070014L;
	/**
	 * 动画url地址
	 */
	private String mgUrl;

	/**
	 * 动画版本
	 */
	private Integer mgVersion;

	/**
	 * 动画状态（0:关 1:展示但不拉取 2:展示并拉取）
	 */
	private Integer mgStatus;

	public String getMgUrl() {
		return mgUrl;
	}

	public void setMgUrl(String mgUrl) {
		this.mgUrl = mgUrl;
	}

	public Integer getMgVersion() {
		return mgVersion;
	}

	public void setMgVersion(Integer mgVersion) {
		this.mgVersion = mgVersion;
	}

	public Integer getMgStatus() {
		return mgStatus;
	}

	public void setMgStatus(Integer mgStatus) {
		this.mgStatus = mgStatus;
	}

}
