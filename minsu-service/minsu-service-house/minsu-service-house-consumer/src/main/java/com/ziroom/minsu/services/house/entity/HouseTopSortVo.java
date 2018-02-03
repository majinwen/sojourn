/**
 * @FileName: HouseTopVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2017年3月17日 下午4:00:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.house.HouseTopEntity;

import java.util.List;

/**
 * <p>TOP50 排序修改对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
public class HouseTopSortVo extends HouseTopEntity {

	private static final long serialVersionUID = 787366630224920883L;

	/**
	 *  新的排序号(原有的排序要更换成此排序号)
	 */
	private Integer newTopSort;

	public Integer getNewTopSort() {
		return newTopSort;
	}

	public void setNewTopSort(Integer newTopSort) {
		this.newTopSort = newTopSort;
	}
}
