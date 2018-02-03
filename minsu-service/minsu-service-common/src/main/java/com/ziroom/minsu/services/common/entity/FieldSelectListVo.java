/**
 * @FileName: FieldSelectListVo.java
 * @Package com.ziroom.minsu.services.common.entity
 * 
 * @author bushujie
 * @created 2017年6月14日 下午2:52:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>选择型字段对象</p>
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
public class FieldSelectListVo extends FieldBaseVo{
	
	/**
	 * 选项对象列表
	 */
	private List<FieldSelectVo> list=new ArrayList<FieldSelectVo>();
	
	/**
	 * @return the list
	 */
	public List<FieldSelectVo> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<FieldSelectVo> list) {
		this.list = list;
	}
}
