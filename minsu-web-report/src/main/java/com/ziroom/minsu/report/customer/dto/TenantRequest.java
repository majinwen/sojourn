package com.ziroom.minsu.report.customer.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房客统计信息查询条件</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on on 2017/5/2.
 * @version 1.0
 * @since 1.0
 */
public class TenantRequest extends PageRequest {


    /**
	 * 
	 */
	private static final long serialVersionUID = 4217976478146157107L;

	/**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    

    
}
