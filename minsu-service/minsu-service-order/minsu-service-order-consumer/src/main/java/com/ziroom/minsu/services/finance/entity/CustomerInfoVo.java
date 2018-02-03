package com.ziroom.minsu.services.finance.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;

public class CustomerInfoVo extends BaseEntity {

	private static final long serialVersionUID = -4494481394760621597L;

	CustomerBaseMsgEntity customerBase;

	CustomerBankCardMsgEntity bankcard;
	
	/**
	 * 返回银行卡信息是否有效
	 * @author lishaochuan
	 * @create 2016年4月29日
	 * @return
	 */
	public boolean checkBankCard(){
		if(Check.NuNObj(bankcard)){
			return false;
		}
		if(Check.NuNObj(customerBase.getRealName())){
			return false;
		}
		// 客户真实姓名必须和银行卡姓名一致
		/*if(!customerBase.getRealName().equals(bankcard.getBankcardHolder())){
			return false;
		}*/
		return true;
	}
	
	/**
	 * 是否可以使用银行卡方式提现
	 * @author lishaochuan
	 * @create 2016年4月29日
	 * @return
	 */
	public boolean isBankDefault() {
		if(Check.NuNObj(customerBase)){
			return false;
		}
		if(Check.NuNObj(customerBase.getRentPayment())){
			return false;
		}
		if(customerBase.getRentPayment() != 1){
			return false;
		}
		return checkBankCard();
	}

	public CustomerBaseMsgEntity getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBaseMsgEntity customerBase) {
		this.customerBase = customerBase;
	}

	public CustomerBankCardMsgEntity getBankcard() {
		return bankcard;
	}

	public void setBankcard(CustomerBankCardMsgEntity bankcard) {
		this.bankcard = bankcard;
	}
	
}
