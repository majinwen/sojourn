package com.ziroom.minsu.services.account.dto;
/**
 * <p>
 * 账户余额请求接口
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */

public class AccountDetailRequest extends AccountCommonRequest{
	/** 客户类型 */
	private String uidType;

	public String getUidType() {
		return uidType;
	}

	public void setUidType(String uidType) {
		this.uidType = uidType;
	}
	
}
