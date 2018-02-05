/**
 * @FileName: StatsHouseEvaVo.java
 * @Package com.ziroom.minsu.api.house.vo
 * 
 * @author bushujie
 * @created 2016年5月3日 下午3:25:01
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.vo;

/**
 * <p>房源总评价</p>
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
public class StatsHouseEvaVo {
	
	/**
	 * 综合评价分
	 */
	private Float totalAvgGrade;
	/**
	 * 卫生环境评分
	 */
	private Float houseCleanAva;
	/**
	 * 描述相符评分
	 */
	private Float desMatchAva;
	/**
	 * 房东帮助总得分
	 */
	private Float safeDegreeAva;
	
	/**
	 * 交通状况评分
	 */
	private Float trafPosAva;
	/**
	 * 性价比总得分
	 */
	private Float costPerforAva;
	/**
	 * @return the totalAvgGrade
	 */
	public Float getTotalAvgGrade() {
		return totalAvgGrade;
	}
	/**
	 * @param totalAvgGrade the totalAvgGrade to set
	 */
	public void setTotalAvgGrade(Float totalAvgGrade) {
		this.totalAvgGrade = totalAvgGrade;
	}
	/**
	 * @return the houseCleanAva
	 */
	public Float getHouseCleanAva() {
		return houseCleanAva;
	}
	/**
	 * @param houseCleanAva the houseCleanAva to set
	 */
	public void setHouseCleanAva(Float houseCleanAva) {
		this.houseCleanAva = houseCleanAva;
	}
	/**
	 * @return the desMatchAva
	 */
	public Float getDesMatchAva() {
		return desMatchAva;
	}
	/**
	 * @param desMatchAva the desMatchAva to set
	 */
	public void setDesMatchAva(Float desMatchAva) {
		this.desMatchAva = desMatchAva;
	}
	/**
	 * @return the safeDegreeAva
	 */
	public Float getSafeDegreeAva() {
		return safeDegreeAva;
	}
	/**
	 * @param safeDegreeAva the safeDegreeAva to set
	 */
	public void setSafeDegreeAva(Float safeDegreeAva) {
		this.safeDegreeAva = safeDegreeAva;
	}
	/**
	 * @return the trafPosAva
	 */
	public Float getTrafPosAva() {
		return trafPosAva;
	}
	/**
	 * @param trafPosAva the trafPosAva to set
	 */
	public void setTrafPosAva(Float trafPosAva) {
		this.trafPosAva = trafPosAva;
	}
	/**
	 * @return the costPerforAva
	 */
	public Float getCostPerforAva() {
		return costPerforAva;
	}
	/**
	 * @param costPerforAva the costPerforAva to set
	 */
	public void setCostPerforAva(Float costPerforAva) {
		this.costPerforAva = costPerforAva;
	}
}
