package com.zra.common.dto.pay;

/**
 * 卡券dto
 * @author tianxf9
 *
 */
public class CardCouponDto {
	
	//卡券使用情况bid
	private String usageId;
	// 收款单或者应收账单id
	private String receiBillFid;
	//优惠券、租金卡编号，唯一标识
	private String code;

	private String name;
	
	private String desc;
	
	private String rule;
	
	private Integer serviceLineId;
	
	private String startTime;
	
	private String endTime;
	
	private String money;
	
	//是否被选中（1：未选中；2：选中）
	private Integer isSelected = 1;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getServiceLineId() {
		return serviceLineId;
	}

	public void setServiceLineId(Integer serviceLineId) {
		this.serviceLineId = serviceLineId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getUsageId() {
		return usageId;
	}

	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}

	public String getReceiBillFid() {
		return receiBillFid;
	}

	public void setReceiBillFid(String receiBillFid) {
		this.receiBillFid = receiBillFid;
	}

	public Integer getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof CardCouponDto) {
			CardCouponDto objec = (CardCouponDto) obj;
            return this.code.equals(objec.getCode());
        }
        return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
        return this.code.hashCode();
	}

    
}
