package com.ziroom.minsu.services.evaluate.entity;

import java.io.Serializable;

/**
 * <p>房东房客相互评价 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class EvaluateItemVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7926728862569664810L;
	
	/**
	 * 房源(房间)逻辑id
	 */
	private String houseFid;
	
	/**
	 * 房源(房间)名称
	 */
	private String houseName;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;

	/**
	 * 房东对房客评价
	 */
	private TenantEvaItemVo landlordEvaItem;
	
	/**
	 * 房客对房源(房东)评价
	 */
	private TenantEvaItemVo tenantEvaItem;
	
	public String getHouseFid() {
		return houseFid;
	}
	
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}
	
	public String getHouseName() {
		return houseName;
	}
	
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
	public Integer getRentWay() {
		return rentWay;
	}
	
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public TenantEvaItemVo getLandlordEvaItem() {
		return landlordEvaItem;
	}

	public void setLandlordEvaItem(TenantEvaItemVo landlordEvaItem) {
		this.landlordEvaItem = landlordEvaItem;
	}

	public TenantEvaItemVo getTenantEvaItem() {
		return tenantEvaItem;
	}

	public void setTenantEvaItem(TenantEvaItemVo tenantEvaItem) {
		this.tenantEvaItem = tenantEvaItem;
	}
}
