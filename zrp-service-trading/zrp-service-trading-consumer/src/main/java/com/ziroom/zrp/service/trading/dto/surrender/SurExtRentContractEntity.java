package com.ziroom.zrp.service.trading.dto.surrender;

import com.ziroom.zrp.trading.entity.RentContractEntity;

/**
 * <p>解约用的的扩展合同信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年12月19日
 * @since 1.0
 */
public class SurExtRentContractEntity extends RentContractEntity{

	private static final long serialVersionUID = 3790353260358396162L;
	
	private String customerCard;

	public String getCustomerCard() {
		return customerCard;
	}

	public void setCustomerCard(String customerCard) {
		this.customerCard = customerCard;
	}
}
