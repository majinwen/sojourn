package com.ziroom.minsu.services.search.vo;


import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.search.entity.ChildEntity;
import com.ziroom.minsu.services.search.entity.LabelEntity;

/**
 * 
 * <p>top50列表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class Top50HouseListVo extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6654732329841100026L;

	/** 房源或者房间fid */
    private String fid;

    /** 名称 */
    private String houseName; 

    /** 房屋类型 整租 合租 */
    private Integer rentWay;

    /** 图片 */
    private String picUrl; 
     
    /** 个性化标签 */
    private List<LabelTipsEntity> indivLabelTipsList;

    
	public String getFid() {
		return fid;
	}

	public String getHouseName() {
		return houseName;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public List<LabelTipsEntity> getIndivLabelTipsList() {
		return indivLabelTipsList;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setIndivLabelTipsList(List<LabelTipsEntity> indivLabelTipsList) {
		this.indivLabelTipsList = indivLabelTipsList;
	}
    
}
