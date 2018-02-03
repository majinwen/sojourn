/**
 * @FileName: ColumnRegionUpVo.java
 * @Package com.ziroom.minsu.services.cms.entity
 * 
 * @author bushujie
 * @created 2016年11月14日 下午4:42:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.cms.ColumnRegionEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionItemEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionPicEntity;

/**
 * <p>更新专栏景点商圈vo</p>
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
public class ColumnRegionUpVo extends ColumnRegionEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8924356429257094764L;
	
	/**
	 * 图片集合
	 */
	private List<ColumnRegionPicEntity> picList=new ArrayList<ColumnRegionPicEntity>();
	
	/**
	 * 推荐项目集合
	 */
	private List<ColumnRegionItemEntity> itemList=new ArrayList<ColumnRegionItemEntity>();
	
	/**
	 * @return the picList
	 */
	public List<ColumnRegionPicEntity> getPicList() {
		return picList;
	}

	/**
	 * @param picList the picList to set
	 */
	public void setPicList(List<ColumnRegionPicEntity> picList) {
		this.picList = picList;
	}

	/**
	 * @return the itemList
	 */
	public List<ColumnRegionItemEntity> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ColumnRegionItemEntity> itemList) {
		this.itemList = itemList;
	}

}
