
package com.ziroom.minsu.services.customer.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;

/**
 * <p>用户详细信息</p>
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
public class CustomerInfoDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6608158341304391329L;

	/**
	 * 用户基本信息
	 */
	private CustomerBaseMsgEntity customerBaseMsg;
	
	/**
	 * 用户银行卡信息
	 */
	private CustomerBankCardMsgEntity customerBankCardMsg;
	
	/**
	 * 用户照片信息 可能多个  
	 */
	List<CustomerPicMsgEntity> listCustomerPicMsg;

	public CustomerBaseMsgEntity getCustomerBaseMsg() {
		return customerBaseMsg;
	}

	public void setCustomerBaseMsg(CustomerBaseMsgEntity customerBaseMsg) {
		this.customerBaseMsg = customerBaseMsg;
	}

	public CustomerBankCardMsgEntity getCustomerBankCardMsg() {
		return customerBankCardMsg;
	}

	public void setCustomerBankCardMsg(CustomerBankCardMsgEntity customerBankCardMsg) {
		this.customerBankCardMsg = customerBankCardMsg;
	}

	public List<CustomerPicMsgEntity> getListCustomerPicMsg() {
		return listCustomerPicMsg;
	}

	public void setListCustomerPicMsg(List<CustomerPicMsgEntity> listCustomerPicMsg) {
		this.listCustomerPicMsg = listCustomerPicMsg;
	}
	
	
}
