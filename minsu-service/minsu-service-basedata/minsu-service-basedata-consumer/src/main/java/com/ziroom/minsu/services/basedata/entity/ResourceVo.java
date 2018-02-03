/**
 * @FileName: ResourceVo.java
 * @Package com.ziroom.minsu.services.basedata.entity
 * 
 * @author bushujie
 * @created 2016年3月16日 下午9:50:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.sys.ResourceEntity;

/**
 * <p>资源功能树vo</p>
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
public class ResourceVo extends ResourceEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8705569785821676406L;
	
	/**
	 * 用户id
	 */
	private String currentuserFid;
	
	/**
	  * 子权限
	  */
	private List<ResourceVo> childRes=new ArrayList<ResourceVo>();
	
	 /**
	 * @return the currentuserFid
	 */
	public String getCurrentuserFid() {
		return currentuserFid;
	}

	/**
	 * @param currentuserFid the currentuserFid to set
	 */
	public void setCurrentuserFid(String currentuserFid) {
		this.currentuserFid = currentuserFid;
	}

	/**
	 * @return the childRes
	 */
	public List<ResourceVo> getChildRes() {
		return childRes;
	}

	/**
	 * @param childRes the childRes to set
	 */
	public void setChildRes(List<ResourceVo> childRes) {
		this.childRes = childRes;
	}
}
