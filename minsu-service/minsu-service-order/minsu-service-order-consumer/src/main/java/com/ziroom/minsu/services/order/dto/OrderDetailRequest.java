package com.ziroom.minsu.services.order.dto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单详情请求参数
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
public class OrderDetailRequest extends OrderOpBaseRequest{


    /** 序列化id */
    private static final long serialVersionUID = -54654512156309673L;
    
	/**
	 * 请求类型  1:用户 2：房东 3：后台
	 */
	private Integer requestType;

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}
	
	

}
