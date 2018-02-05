/**
 * @FileName: TenantEvaluateVo.java
 * @Package com.ziroom.minsu.api.evaluate.entity
 * 
 * @author yd
 * @created 2017年1月20日 下午3:26:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>房客评价Vo</p>
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
public class TenantEvaluateVo implements Serializable{


    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -6128473414684258900L;

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the houseClean
	 */
	public Integer getHouseClean() {
		return houseClean;
	}

	/**
	 * @param houseClean the houseClean to set
	 */
	public void setHouseClean(Integer houseClean) {
		this.houseClean = houseClean;
	}

	/**
	 * @return the descriptionMatch
	 */
	public Integer getDescriptionMatch() {
		return descriptionMatch;
	}

	/**
	 * @param descriptionMatch the descriptionMatch to set
	 */
	public void setDescriptionMatch(Integer descriptionMatch) {
		this.descriptionMatch = descriptionMatch;
	}

	/**
	 * @return the safetyDegree
	 */
	public Integer getSafetyDegree() {
		return safetyDegree;
	}

	/**
	 * @param safetyDegree the safetyDegree to set
	 */
	public void setSafetyDegree(Integer safetyDegree) {
		this.safetyDegree = safetyDegree;
	}

	/**
	 * @return the trafficPosition
	 */
	public Integer getTrafficPosition() {
		return trafficPosition;
	}

	/**
	 * @param trafficPosition the trafficPosition to set
	 */
	public void setTrafficPosition(Integer trafficPosition) {
		this.trafficPosition = trafficPosition;
	}

	/**
	 * @return the costPerformance
	 */
	public Integer getCostPerformance() {
		return costPerformance;
	}

	/**
	 * @param costPerformance the costPerformance to set
	 */
	public void setCostPerformance(Integer costPerformance) {
		this.costPerformance = costPerformance;
	}

	/**
	 * @return the evaFour
	 */
	public Integer getEvaFour() {
		return evaFour;
	}

	/**
	 * @param evaFour the evaFour to set
	 */
	public void setEvaFour(Integer evaFour) {
		this.evaFour = evaFour;
	}

	/**
	 * @return the evaFive
	 */
	public Integer getEvaFive() {
		return evaFive;
	}

	/**
	 * @param evaFive the evaFive to set
	 */
	public void setEvaFive(Integer evaFive) {
		this.evaFive = evaFive;
	}

    
}
