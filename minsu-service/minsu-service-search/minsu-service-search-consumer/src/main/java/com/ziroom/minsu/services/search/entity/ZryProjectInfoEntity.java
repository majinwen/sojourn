package com.ziroom.minsu.services.search.entity;


import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

/**
 * 
 * <p>自如驿项目entity</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public class ZryProjectInfoEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7889610105781393076L;

	/**
	 * 项目id
	 */
	private String projectBid;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 城市
	 */
	private String cityName;
	
	/**
	 * 区域
	 */
	private String areaName;
	
	/**
	 * 房型数量
	 */
	private Integer houseModelCount;
	
	/**
	 * 评价数量
	 */
	private Integer evaluateCount;
	
	/**
	 * 评分
	 */
	private Double evaluateScore;
	
	/**
	 * 最低价格
	 */
	private Integer minPrice;
	
	/**
	 * 最高价格
	 */
	private Integer maxPrice;
	
	/**
	 * 图片
	 */
	private String picUrl;
	
	/**
	 * 房态标签
	 */
	private List<LabelTipsEntity> labelTipsList;
	
	/**
	 * 状态 默认-0，新上-1，未上-2
	 */
	private Integer status;

	/**
	 * @return the projectBid
	 */
	public String getProjectBid() {
		return projectBid;
	}

	/**
	 * @param projectBid the projectBid to set
	 */
	public void setProjectBid(String projectBid) {
		this.projectBid = projectBid;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the houseModelCount
	 */
	public Integer getHouseModelCount() {
		return houseModelCount;
	}

	/**
	 * @param houseModelCount the houseModelCount to set
	 */
	public void setHouseModelCount(Integer houseModelCount) {
		this.houseModelCount = houseModelCount;
	}

	/**
	 * @return the evaluateCount
	 */
	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	/**
	 * @param evaluateCount the evaluateCount to set
	 */
	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	/**
	 * @return the evaluateScore
	 */
	public Double getEvaluateScore() {
		return evaluateScore;
	}

	/**
	 * @param evaluateScore the evaluateScore to set
	 */
	public void setEvaluateScore(Double evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	/**
	 * @return the minPrice
	 */
	public Integer getMinPrice() {
		return minPrice;
	}

	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * @return the maxPrice
	 */
	public Integer getMaxPrice() {
		return maxPrice;
	}

	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @return the labelTipsList
	 */
	public List<LabelTipsEntity> getLabelTipsList() {
		return labelTipsList;
	}

	/**
	 * @param labelTipsList the labelTipsList to set
	 */
	public void setLabelTipsList(List<LabelTipsEntity> labelTipsList) {
		this.labelTipsList = labelTipsList;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
