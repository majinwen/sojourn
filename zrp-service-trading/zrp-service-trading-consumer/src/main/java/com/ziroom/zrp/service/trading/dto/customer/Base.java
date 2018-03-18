package com.ziroom.zrp.service.trading.dto.customer;

import java.io.Serializable;
/**
 * <p>查询友家个人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * <BR> 2017年10月9日         xiangb			customerFlag改为String类型
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月21日
 * @since 1.0
 */
public class Base implements Serializable{
	
	private static final long serialVersionUID = -7111488552056621477L;
	/**
	 * 客户类型（注册用户 2 客户是9   业主是 5    即使客户又是业主是 13）
	 */
	private int customer_flag;
	public int getCustomer_flag() {
		return customer_flag;
	}
	public void setCustomer_flag(int customer_flag) {
		this.customer_flag = customer_flag;
	}

}
