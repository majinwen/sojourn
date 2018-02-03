/**
 * @FileName: HouseTopUpdateVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2017年3月22日 上午10:01:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseTagEntity;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.house.HouseTopEntity;

/**
 * <p>top房源详细信息</p>
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
public class HouseTopDetail extends HouseTopEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 760036170127389049L;
	
	/**
	 * top房源标签集合
	 */
	private List<String> tagFidList=new ArrayList<>();
	
	/**
	 * top房源条目集合
	 */
	private List<HouseTopColumnEntity> topColumnList=new ArrayList<>();
	
	/**
	 * 亮点标题
	 */
	private HouseTopColumnEntity sternTitle;
	
	/**
	 * 亮点内容
	 */
	private HouseTopColumnEntity sternContent;
	
	/**
	 * 封面图片
	 */
	private HouseTopColumnEntity coverPicParam;

	/**
	 * @return the sternTitle
	 */
	public HouseTopColumnEntity getSternTitle() {
		return sternTitle;
	}

	/**
	 * @param sternTitle the sternTitle to set
	 */
	public void setSternTitle(HouseTopColumnEntity sternTitle) {
		this.sternTitle = sternTitle;
	}

	/**
	 * @return the sternContent
	 */
	public HouseTopColumnEntity getSternContent() {
		return sternContent;
	}

	/**
	 * @param sternContent the sternContent to set
	 */
	public void setSternContent(HouseTopColumnEntity sternContent) {
		this.sternContent = sternContent;
	}

	/**
	 * @return the coverPicParam
	 */
	public HouseTopColumnEntity getCoverPicParam() {
		return coverPicParam;
	}

	/**
	 * @param coverPicParam the coverPicParam to set
	 */
	public void setCoverPicParam(HouseTopColumnEntity coverPicParam) {
		this.coverPicParam = coverPicParam;
	}

	/**
	 * @return the tagFidList
	 */
	public List<String> getTagFidList() {
		return tagFidList;
	}

	/**
	 * @param tagFidList the tagFidList to set
	 */
	public void setTagFidList(List<String> tagFidList) {
		this.tagFidList = tagFidList;
	}

	/**
	 * @return the topColumnList
	 */
	public List<HouseTopColumnEntity> getTopColumnList() {
		return topColumnList;
	}

	/**
	 * @param topColumnList the topColumnList to set
	 */
	public void setTopColumnList(List<HouseTopColumnEntity> topColumnList) {
		this.topColumnList = topColumnList;
	}
}
