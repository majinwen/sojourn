package com.ziroom.minsu.services.customer.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>客户房源收藏分页查询参数</p>
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
public class CustomerCollectionDto extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -312502354504593135L;
	
	@NotNull(message = "{uid.null}")
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
