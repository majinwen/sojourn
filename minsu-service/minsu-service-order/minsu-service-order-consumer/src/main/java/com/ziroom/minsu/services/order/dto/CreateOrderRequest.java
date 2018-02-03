package com.ziroom.minsu.services.order.dto;


import com.ziroom.minsu.entity.cms.ActivityInfoEntity;

import java.util.List;

/**
 * <p>
 * 创建订单请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class CreateOrderRequest extends NeedPayFeeRequest{


    /** 序列化id */
    private static final long serialVersionUID = -6822554545526309673L;

    /** 用户电话 */
    private String userTel;
    
    /**  用户名称 */
    private String userName;

    /** 房间 联系人列表 */
    private List<String> tenantFids;

    /**
     * 房客手机号 国际码
     */
    private String userTelCode;


	/**
	 * @return the userTelCode
	 */
	public String getUserTelCode() {
		return userTelCode;
	}

	/**
	 * @param userTelCode the userTelCode to set
	 */
	public void setUserTelCode(String userTelCode) {
		this.userTelCode = userTelCode;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getTenantFids() {
		return tenantFids;
	}

	public void setTenantFids(List<String> tenantFids) {
		this.tenantFids = tenantFids;
	}


}
