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
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class ResourceRequest extends PageRequest {

	/**
	 * 父类id查询
	 */
	private String parent_fid;

	public String getParent_fid() {
		return parent_fid;
	}

	public void setParent_fid(String parent_fid) {
		this.parent_fid = parent_fid;
	}

}
