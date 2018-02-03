/**
 * @FileName: OrderLandlordStaticsDto.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author zl
 * @created 2016年11月4日 上午11:35:47
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.services.order.dto;

import java.io.Serializable;


/**
 * 房东订单统计数据
 * <p>
 * TODO
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class OrderLandlordStaticsDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8324409124404234313L;

	/**
	 * 待房东评价订单总数
	 */
	private Long waitLandlordEvaNum = 0L;
	
	/**
	 * 待房东的房客评价订单总数
	 */
	private Long waitTenantEvaNum = 0L;
	
	/**
	 * 当前房东的可评价订单总数（房东/房东的房客）
	 */
	private Long canEvaNum = 0L;
	
	/**
	 * 房东的房客已评价订单数
	 */
	private Long tenantEvaedNum = 0L;
	
	/**
	 * 房东已评价订单数
	 */
	private Long landlordEvaedNum = 0L;
	
	/**
	 * 房东订单总数量
	 */
	private Long lanOrderNum = 0L;
	
	/**
	 * 房东拒绝的订单数量
	 */
	private Long lanRefuseOrderNum = 0L;
	
	/**
	 * 超时系统拒绝的订单数量
	 */
	private Long sysRefuseOrderNum = 0L;
	
	
	/**
	 * 房东接受订单数量
	 */
	private Long acceptOrderNum = 0L;


	public Long getWaitLandlordEvaNum() {
		return waitLandlordEvaNum==null?0:waitLandlordEvaNum;
	}


	public Long getWaitTenantEvaNum() {
		return waitTenantEvaNum==null?0:waitTenantEvaNum;
	}


	public Long getCanEvaNum() {
		return canEvaNum==null?0:canEvaNum;
	}


	public Long getTenantEvaedNum() {
		return tenantEvaedNum==null?0:tenantEvaedNum;
	}


	public Long getLandlordEvaedNum() {
		return landlordEvaedNum==null?0:landlordEvaedNum;
	}


	public Long getLanOrderNum() {
		return lanOrderNum==null?0:lanOrderNum;
	}


	public Long getLanRefuseOrderNum() {
		return lanRefuseOrderNum==null?0:lanRefuseOrderNum;
	}


	public Long getSysRefuseOrderNum() {
		return sysRefuseOrderNum==null?0:sysRefuseOrderNum;
	}


	public Long getAcceptOrderNum() {
		return acceptOrderNum==null?0:acceptOrderNum;
	}


	public void setWaitLandlordEvaNum(Long waitLandlordEvaNum) {
		this.waitLandlordEvaNum = waitLandlordEvaNum;
	}


	public void setWaitTenantEvaNum(Long waitTenantEvaNum) {
		this.waitTenantEvaNum = waitTenantEvaNum;
	}


	public void setCanEvaNum(Long canEvaNum) {
		this.canEvaNum = canEvaNum;
	}


	public void setTenantEvaedNum(Long tenantEvaedNum) {
		this.tenantEvaedNum = tenantEvaedNum;
	}


	public void setLandlordEvaedNum(Long landlordEvaedNum) {
		this.landlordEvaedNum = landlordEvaedNum;
	}


	public void setLanOrderNum(Long lanOrderNum) {
		this.lanOrderNum = lanOrderNum;
	}


	public void setLanRefuseOrderNum(Long lanRefuseOrderNum) {
		this.lanRefuseOrderNum = lanRefuseOrderNum;
	}


	public void setSysRefuseOrderNum(Long sysRefuseOrderNum) {
		this.sysRefuseOrderNum = sysRefuseOrderNum;
	}


	public void setAcceptOrderNum(Long acceptOrderNum) {
		this.acceptOrderNum = acceptOrderNum;
	}

}
