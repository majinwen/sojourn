/**
 * @FileName: Version.java
 * @Package com.asura.amp.dubbo.sysinfo.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-18 上午11:27:17
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.sysinfo.entity;

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
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Version extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String name = null;
	
	private int appCount = 0;
	
	public Version() {
	}
	
	public Version(String name, int appCount) {
		this.name = name;
		this.appCount = appCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAppCount() {
		return appCount;
	}

	public void setAppCount(int appCount) {
		this.appCount = appCount;
	}
}
