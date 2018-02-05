package com.ziroom.minsu.api.evaluate.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>房客评价请求参数</p>
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
public class TenantEvaApiRequest extends BaseParamDto{

	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 评价人类型（房东=1 房客=2）
	 */
	private Integer evaUserType;

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

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getEvaUserType() {
		return evaUserType;
	}

	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}
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


}
