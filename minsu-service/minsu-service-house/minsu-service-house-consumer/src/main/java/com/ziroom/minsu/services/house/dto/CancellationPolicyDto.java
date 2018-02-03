package com.ziroom.minsu.services.house.dto;

public class CancellationPolicyDto extends HouseBaseParamsDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6643128860057592038L;
	/**
	 * 预定类型
	 */
	private Integer orderType;
	
	/**
	 * 房屋守则可选项（多个以“,”分隔）
	 */
	private String houseRulesArray;
	
	/**
	 * 房屋守则
	 */
	private String houseRules;
	/**
	 * 押金
	 */
	private Integer depositMoney;
	/**
	 * 退订政策
	 */
	private String cancellationPolicy;
	
	/**
	 * 步骤 
	 */
	private Integer step;
	
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public String getHouseRulesArray() {
		return houseRulesArray;
	}
	public void setHouseRulesArray(String houseRulesArray) {
		this.houseRulesArray = houseRulesArray;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public String getHouseRules() {
		return houseRules;
	}
	public Integer getDepositMoney() {
		return depositMoney;
	}
	public String getCancellationPolicy() {
		return cancellationPolicy;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setHouseRules(String houseRules) {
		this.houseRules = houseRules;
	}
	public void setDepositMoney(Integer depositMoney) {
		this.depositMoney = depositMoney;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}
	

}
