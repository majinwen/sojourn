/**
 * @FileName: CustomerDetailVo.java
 * @Package com.ziroom.minsu.services.customer.entity
 * 
 * @author jixd
 * @created 2016年4月25日 上午9:21:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerOperHistoryEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;

/**
 * <p>客户详细信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class CustomerDetailImageVo extends CustomerBaseMsgEntity{

	/**
	 * 序列Id
	 */
	private static final long serialVersionUID = -1643281707426162631L;
	/**
	 * 客户图片列表
	 */
	private List<CustomerPicMsgEntity> customerPicList = new ArrayList<CustomerPicMsgEntity>();

	
	public List<CustomerPicMsgEntity> getCustomerPicList() {
		return customerPicList;
	}
	public void setCustomerPicList(List<CustomerPicMsgEntity> customerPicList) {
		this.customerPicList = customerPicList;
	}
	
}
