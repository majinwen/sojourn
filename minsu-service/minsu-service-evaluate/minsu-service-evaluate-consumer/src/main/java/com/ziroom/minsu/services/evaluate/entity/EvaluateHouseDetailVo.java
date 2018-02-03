/**
 * @FileName: EvaluateHouseDetailVo.java
 * @Package com.ziroom.minsu.services.evaluate.entity
 * 
 * @author jixd
 * @created 2016年8月6日 下午2:21:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import java.io.Serializable;

/**
 * <p>PC房源详情中，显示评价信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluateHouseDetailVo implements Serializable{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -8812620990755664405L;

	/**
	 * 房源被评价数量
	 */
	private Long houseEvaNum;
	
	/**
	 * 房东被评价数量
	 */
	private Long lanEvaNum;
	
	/**
	 * 评价平均值
	 */
	private Float avgAll;
	
	 /**
     * 整洁卫生星级平均值
     */
    private Float houseCleanAva;

    /**
     * 描述相符星级平均值
     */
    private Float desMatchAva;

    /**
     * 安全程度星级平均值
     */
    private Float safeDegreeAva;

    /**
     * 交通位置星级平均值
     */
    private Float trafPosAva;

    /**
     * 性价比星级平均值
     */
    private Float costPerforAva;


	public Long getHouseEvaNum() {
		return houseEvaNum;
	}

	public void setHouseEvaNum(Long houseEvaNum) {
		this.houseEvaNum = houseEvaNum;
	}

	public Long getLanEvaNum() {
		return lanEvaNum;
	}

	public void setLanEvaNum(Long lanEvaNum) {
		this.lanEvaNum = lanEvaNum;
	}

	public Float getAvgAll() {
		return avgAll;
	}

	public void setAvgAll(Float avgAll) {
		this.avgAll = avgAll;
	}

	public Float getHouseCleanAva() {
		return houseCleanAva;
	}

	public void setHouseCleanAva(Float houseCleanAva) {
		this.houseCleanAva = houseCleanAva;
	}

	public Float getDesMatchAva() {
		return desMatchAva;
	}

	public void setDesMatchAva(Float desMatchAva) {
		this.desMatchAva = desMatchAva;
	}

	public Float getSafeDegreeAva() {
		return safeDegreeAva;
	}

	public void setSafeDegreeAva(Float safeDegreeAva) {
		this.safeDegreeAva = safeDegreeAva;
	}

	public Float getTrafPosAva() {
		return trafPosAva;
	}

	public void setTrafPosAva(Float trafPosAva) {
		this.trafPosAva = trafPosAva;
	}

	public Float getCostPerforAva() {
		return costPerforAva;
	}

	public void setCostPerforAva(Float costPerforAva) {
		this.costPerforAva = costPerforAva;
	}
    
}
