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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

/**
 * <p>TOP50 房源集合</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseTopVo extends BaseEntity {

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -3752046505645929052L;

	/**
	 * top50 fid
	 * 
	 */
	private String houseTopFid;
	
	/**
	 * top50房源分享标题
	 */
	private String topShareTitle;
	
	/**
	 * top50房源标题图片地址
	 */
	private String topTitlePic;
	
	/**
	 * top50房源详情页中部图片地址
	 */
	private String topMiddlePic;
	
	/**
	 * top50房源标题
	 */
	private String topTitle;
	
	/**
	 * 头图
	 */
	private String topHeadImg;
	
	/**
	 * 初始房源top50条目集合
	 */
	private List<HouseTopColumnEntity> houseTopColumnList;
	
	

	/**
	 * @return the topHeadImg
	 */
	public String getTopHeadImg() {
		return topHeadImg;
	}

	/**
	 * @param topHeadImg the topHeadImg to set
	 */
	public void setTopHeadImg(String topHeadImg) {
		this.topHeadImg = topHeadImg;
	}

	/**
	 * @return the houseTopFid
	 */
	public String getHouseTopFid() {
		return houseTopFid;
	}

	/**
	 * @param houseTopFid the houseTopFid to set
	 */
	public void setHouseTopFid(String houseTopFid) {
		this.houseTopFid = houseTopFid;
	}

	/**
	 * @return the topShareTitle
	 */
	public String getTopShareTitle() {
		return topShareTitle;
	}

	/**
	 * @param topShareTitle the topShareTitle to set
	 */
	public void setTopShareTitle(String topShareTitle) {
		this.topShareTitle = topShareTitle;
	}

	/**
	 * @return the topTitlePic
	 */
	public String getTopTitlePic() {
		return topTitlePic;
	}

	/**
	 * @param topTitlePic the topTitlePic to set
	 */
	public void setTopTitlePic(String topTitlePic) {
		this.topTitlePic = topTitlePic;
	}

	/**
	 * @return the topMiddlePic
	 */
	public String getTopMiddlePic() {
		return topMiddlePic;
	}

	/**
	 * @param topMiddlePic the topMiddlePic to set
	 */
	public void setTopMiddlePic(String topMiddlePic) {
		this.topMiddlePic = topMiddlePic;
	}

	/**
	 * @return the topTitle
	 */
	public String getTopTitle() {
		return topTitle;
	}

	/**
	 * @return the houseTopColumnList
	 */
	public List<HouseTopColumnEntity> getHouseTopColumnList() {
		return houseTopColumnList;
	}

	/**
	 * @param houseTopColumnList the houseTopColumnList to set
	 */
	public void setHouseTopColumnList(List<HouseTopColumnEntity> houseTopColumnList) {
		this.houseTopColumnList = houseTopColumnList;
	}
	

	/**
	 * @param topTitle the topTitle to set
	 */
	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}

	
	
	
}
