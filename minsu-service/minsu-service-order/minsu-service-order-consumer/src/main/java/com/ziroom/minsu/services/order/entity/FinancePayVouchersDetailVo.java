package com.ziroom.minsu.services.order.entity;

import org.springframework.beans.BeanUtils;

import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;

public class FinancePayVouchersDetailVo {

	/** 费用项目 费用项目: 1：房租 2：押金 3：赔付款 4：违约金 */
	private Integer feeItemCode;

	/** 费用项目金额 */
	private Integer itemMoney;
	
	/**
	 * 获取付款单明细实体
	 * @author lishaochuan
	 * @create 2016年5月7日上午10:54:29
	 * @return
	 */
	public FinancePayVouchersDetailEntity getFinancePayVouchersDetailEntity() {
		FinancePayVouchersDetailEntity financePayVouchersDetailEntity = new FinancePayVouchersDetailEntity();
		BeanUtils.copyProperties(this, financePayVouchersDetailEntity);
		return financePayVouchersDetailEntity;
	}

	public Integer getFeeItemCode() {
		return feeItemCode;
	}

	public void setFeeItemCode(Integer feeItemCode) {
		this.feeItemCode = feeItemCode;
	}

	public Integer getItemMoney() {
		return itemMoney;
	}

	public void setItemMoney(Integer itemMoney) {
		this.itemMoney = itemMoney;
	}


}
