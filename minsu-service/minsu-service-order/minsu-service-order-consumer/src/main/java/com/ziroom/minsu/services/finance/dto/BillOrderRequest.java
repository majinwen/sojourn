package com.ziroom.minsu.services.finance.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>账单查询的订单条件</p>
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
public class BillOrderRequest extends PageRequest{
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 3632993878898431076L;

	/**
	 * 客户姓名
	 */
	private String userName;
	
	/**
	 * 用户电话
	 */
	private String userTel;
	
	/**
	 * 预订人uid
	 */
	private String userUid;
	
	/**
	 *  订单编号
	 */
	private String orderSn;

    /**
     *  收入编号
     */
    private String incomeSn;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}


    public String getIncomeSn() {
        return incomeSn;
    }

    public void setIncomeSn(String incomeSn) {
        this.incomeSn = incomeSn;
    }
}
