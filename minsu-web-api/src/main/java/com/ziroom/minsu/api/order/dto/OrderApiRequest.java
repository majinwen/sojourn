package com.ziroom.minsu.api.order.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p>订单请求参数</p>
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
public class OrderApiRequest extends BaseParamDto{
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 房客端订单状态，1进行中  2已完成
	 */
	private Integer tenantOrderStatus;
	/**
	 * 房东uid
	 */
	private String landlordUid;
	/**
	 * 请求类型  1:用户 2：房东 3：后台
	 */
	private Integer requestType;
	
	/**
	 *  房东订单状态类型 ：1=待处理  2=进行中  3=已处理
	 */
	private Integer lanOrderType;

	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public Integer getLanOrderType() {
		return lanOrderType;
	}

	public void setLanOrderType(Integer lanOrderType) {
		this.lanOrderType = lanOrderType;
	}

	public Integer getTenantOrderStatus() {
		return tenantOrderStatus;
	}

	public void setTenantOrderStatus(Integer tenantOrderStatus) {
		this.tenantOrderStatus = tenantOrderStatus;
	}
	
}
