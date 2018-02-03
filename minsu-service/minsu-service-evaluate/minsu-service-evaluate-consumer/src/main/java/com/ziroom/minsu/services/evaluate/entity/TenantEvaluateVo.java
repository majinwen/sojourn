/**
 * @FileName: TenantEvaluateViewObj.java
 * @Package com.ziroom.minsu.services.evaluate.entity
 * 
 * @author yd
 * @created 2016年4月7日 下午3:17:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import java.util.Date;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;

/**
 * <p>评价返回vo</p>
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
public class TenantEvaluateVo extends EvaluateOrderEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 7095105485959389681L;
	
	   /**
     * 评价内容
     */
    private String content;

   /**
    * 整洁卫生星级（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
    */
    private Integer houseClean;

    /**
     * 描述相符星级（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意
     */
    private Integer descriptionMatch;

    /**
     * 安全程度（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
     */
    private Integer safetyDegree;

    /**
     * 交通位置星级(1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
     */
    private Integer trafficPosition;

   /**
    * 性价比（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
    */
    private Integer costPerformance;

    /**
     * 评价4（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
     */
    private Integer evaFour;

    /***
     * 评价5（1颗星 2颗星 3颗星 4颗星 5颗星 5为最满意）
     */
    private Integer evaFive;
    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;
    
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getHouseClean() {
		return houseClean;
	}

	public void setHouseClean(Integer houseClean) {
		this.houseClean = houseClean;
	}

	public Integer getDescriptionMatch() {
		return descriptionMatch;
	}

	public void setDescriptionMatch(Integer descriptionMatch) {
		this.descriptionMatch = descriptionMatch;
	}

	public Integer getSafetyDegree() {
		return safetyDegree;
	}

	public void setSafetyDegree(Integer safetyDegree) {
		this.safetyDegree = safetyDegree;
	}

	public Integer getTrafficPosition() {
		return trafficPosition;
	}

	public void setTrafficPosition(Integer trafficPosition) {
		this.trafficPosition = trafficPosition;
	}

	public Integer getCostPerformance() {
		return costPerformance;
	}

	public void setCostPerformance(Integer costPerformance) {
		this.costPerformance = costPerformance;
	}

	public Integer getEvaFour() {
		return evaFour;
	}

	public void setEvaFour(Integer evaFour) {
		this.evaFour = evaFour;
	}

	public Integer getEvaFive() {
		return evaFive;
	}

	public void setEvaFive(Integer evaFive) {
		this.evaFive = evaFive;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
    
    

}
