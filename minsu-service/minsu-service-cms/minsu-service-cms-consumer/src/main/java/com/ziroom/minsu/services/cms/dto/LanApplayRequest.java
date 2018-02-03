package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 种子房东申请记录请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie on 2016年6月8日
 * @since 1.0
 * @version 1.0
 */
public class LanApplayRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 6744182451991830260L;

	private String customerMoblie;

	private String customerName;
	
	//创建起始时间
	private String createTimeStart;
	
	//创建结束时间
	private String createTimeEnd;

	public String getCustomerMoblie() {
		return customerMoblie;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCustomerMoblie(String customerMoblie) {
		this.customerMoblie = customerMoblie;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
