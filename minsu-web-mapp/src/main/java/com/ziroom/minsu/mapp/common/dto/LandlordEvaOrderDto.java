package com.ziroom.minsu.mapp.common.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.valenum.evaluate.EvaStatusEnum;

/**
 * <p>房东请求订单评价列表参数</p>
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
public class LandlordEvaOrderDto extends BaseParamDto{
 
	/**
	 * 请求类型  1:用户 2：房东 3：后台 
	 */
	private Integer requestType = 2;
	
	/**
	 * 评价状态集合 100 未评价 101 用户已评价 110 房东已评价 111 都已经评价
	 * @see EvaStatusEnum
	 */
	@NotEmpty(message="{order.evaluate.list.null}")
	private List<Integer> listEvaStatus;

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public List<Integer> getListEvaStatus() {
		return listEvaStatus;
	}

	public void setListEvaStatus(List<Integer> listEvaStatus) {
		this.listEvaStatus = listEvaStatus;
	}
	
}
