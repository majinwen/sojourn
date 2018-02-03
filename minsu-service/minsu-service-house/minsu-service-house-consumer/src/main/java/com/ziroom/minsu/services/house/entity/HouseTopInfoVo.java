/**
 * @FileName: HouseTopInfoVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author yd
 * @created 2017年3月15日 下午6:09:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

/**
 * <p>房源信息： top50的相关信息</p>
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
public class HouseTopInfoVo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 344661438879787725L;
	
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
	 * top50 标签
	 */
	private List<LabelTipsEntity> labelTipsTopList = new ArrayList<LabelTipsEntity>();
	
	/**
	 * 房源top50条目集合
	 */
	private List<HouseTopColumnVo> houseTopColumnVoList = new LinkedList<HouseTopColumnVo>();
	
	/**
	 * top 50 房源头图
	 */
	private String topHeadImg;
	
	/**
	 * 分享 内容
	 */
	private String shareContent;
	
	
	/**
	 * @return the shareContent
	 */
	public String getShareContent() {
		return shareContent;
	}

	/**
	 * @param shareContent the shareContent to set
	 */
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

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
	 * @return the labelTipsTopList
	 */
	public List<LabelTipsEntity> getLabelTipsTopList() {
		return labelTipsTopList;
	}

	/**
	 * @param labelTipsTopList the labelTipsTopList to set
	 */
	public void setLabelTipsTopList(List<LabelTipsEntity> labelTipsTopList) {
		this.labelTipsTopList = labelTipsTopList;
	}

	/**
	 * @return the houseTopColumnVoList
	 */
	public List<HouseTopColumnVo> getHouseTopColumnVoList() {
		return houseTopColumnVoList;
	}

	/**
	 * @param houseTopColumnVoList the houseTopColumnVoList to set
	 */
	public void setHouseTopColumnVoList(List<HouseTopColumnVo> houseTopColumnVoList) {
		this.houseTopColumnVoList = houseTopColumnVoList;
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
	 * @param topTitle the topTitle to set
	 */
	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}
	
	

}
