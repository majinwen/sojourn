/**
 * @FileName: ColumnRegionAddRequest.java
 * @Package com.ziroom.minsu.services.cms.dto
 * 
 * @author bushujie
 * @created 2016年11月12日 下午4:18:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dto;

import java.util.List;

import com.ziroom.minsu.entity.cms.ColumnRegionEntity;

/**
 * <p>保存专栏景点商圈</p>
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
public class ColumnRegionAddRequest extends ColumnRegionEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3992100843864673167L;
	
	/**
	 * 主图片参数
	 */
	private String mainPicParam;
	
	/**
	 * 轮播图片参数
	 */
	private List<String> carouselPicParam;
	
	/**
	 * 项目相关参数
	 */
	private List<String> itemPicParam;
	
	/**
	 * 要删除的图片fid
	 */
	private String delPicFids;
	
	/**
	 * 要删除的项目fid
	 */
	private String delItemFids;
	
	/**
	 * 排序顺序
	 */
	private List<Integer> itemSortS;
	
	/**
	 * 顺序以fid的list顺序为准
	 */
	private List<String> itemFidS;

	/**
	 * @return the itemFidS
	 */
	public List<String> getItemFidS() {
		return itemFidS;
	}

	/**
	 * @param itemFidS the itemFidS to set
	 */
	public void setItemFidS(List<String> itemFidS) {
		this.itemFidS = itemFidS;
	}

	/**
	 * @return the itemSortS
	 */
	public List<Integer> getItemSortS() {
		return itemSortS;
	}

	/**
	 * @param itemSortS the itemSortS to set
	 */
	public void setItemSortS(List<Integer> itemSortS) {
		this.itemSortS = itemSortS;
	}

	/**
	 * @return the delPicFids
	 */
	public String getDelPicFids() {
		return delPicFids;
	}

	/**
	 * @param delPicFids the delPicFids to set
	 */
	public void setDelPicFids(String delPicFids) {
		this.delPicFids = delPicFids;
	}

	/**
	 * @return the delItemFids
	 */
	public String getDelItemFids() {
		return delItemFids;
	}

	/**
	 * @param delItemFids the delItemFids to set
	 */
	public void setDelItemFids(String delItemFids) {
		this.delItemFids = delItemFids;
	}

	/**
	 * @return the itemPicParam
	 */
	public List<String> getItemPicParam() {
		return itemPicParam;
	}

	/**
	 * @param itemPicParam the itemPicParam to set
	 */
	public void setItemPicParam(List<String> itemPicParam) {
		this.itemPicParam = itemPicParam;
	}

	/**
	 * @return the mainPicParam
	 */
	public String getMainPicParam() {
		return mainPicParam;
	}

	/**
	 * @param mainPicParam the mainPicParam to set
	 */
	public void setMainPicParam(String mainPicParam) {
		this.mainPicParam = mainPicParam;
	}

	/**
	 * @return the carouselPicParam
	 */
	public List<String> getCarouselPicParam() {
		return carouselPicParam;
	}

	/**
	 * @param carouselPicParam the carouselPicParam to set
	 */
	public void setCarouselPicParam(List<String> carouselPicParam) {
		this.carouselPicParam = carouselPicParam;
	}
}
