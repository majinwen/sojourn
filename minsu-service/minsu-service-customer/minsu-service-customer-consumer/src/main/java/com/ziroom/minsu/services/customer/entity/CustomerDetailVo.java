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

import java.util.List;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
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
public class CustomerDetailVo extends CustomerBaseMsgEntity{

	/**
	 * 序列Id
	 */
	private static final long serialVersionUID = -1643281707426162631L;
	/**
	 * 客户图片列表
	 */
	private List<CustomerPicMsgEntity> customerPicList;
	/**
	 * 审核客户记录
	 */
	private List<CustomerOperHistoryEntity> customerOperHistoryList;
	
	//客户个人介绍
	private CustomerBaseMsgExtEntity customerBaseMsgExtEntity;
	
	//待审核客户个人介绍
	private CustomerBaseMsgExtEntity unCheckCustomerBaseMsgExt;
	
	//待审核客户个人介绍
	private  CustomerPicMsgEntity latestUnAuditHeadPic;
	
	//当前 审核表的fid
	private String curCustomerFieldAuditFid;
	
	//当前审核的昵称
	private String unAuditNickName;
	
	//待审核昵称，审核表的fid
	private String unAuditNickNameFieldFid;
	
	/**
	 * @return the curCustomerFieldAuditFid
	 */
	public String getCurCustomerFieldAuditFid() {
		return curCustomerFieldAuditFid;
	}
	/**
	 * @param curCustomerFieldAuditFid the curCustomerFieldAuditFid to set
	 */
	public void setCurCustomerFieldAuditFid(String curCustomerFieldAuditFid) {
		this.curCustomerFieldAuditFid = curCustomerFieldAuditFid;
	}
	public List<CustomerPicMsgEntity> getCustomerPicList() {
		return customerPicList;
	}
	public void setCustomerPicList(List<CustomerPicMsgEntity> customerPicList) {
		this.customerPicList = customerPicList;
	}
	public List<CustomerOperHistoryEntity> getCustomerOperHistoryList() {
		return customerOperHistoryList;
	}
	public void setCustomerOperHistoryList(
			List<CustomerOperHistoryEntity> customerOperHistoryList) {
		this.customerOperHistoryList = customerOperHistoryList;
	}
	public CustomerBaseMsgExtEntity getCustomerBaseMsgExtEntity() {
		return customerBaseMsgExtEntity;
	}
	public void setCustomerBaseMsgExtEntity(CustomerBaseMsgExtEntity customerBaseMsgExtEntity) {
		this.customerBaseMsgExtEntity = customerBaseMsgExtEntity;
	}
	
	public CustomerBaseMsgExtEntity getUnCheckCustomerBaseMsgExt() {
		return unCheckCustomerBaseMsgExt;
	}
	public void setUnCheckCustomerBaseMsgExt(
			CustomerBaseMsgExtEntity unCheckCustomerBaseMsgExt) {
		this.unCheckCustomerBaseMsgExt = unCheckCustomerBaseMsgExt;
	}
	public CustomerPicMsgEntity getLatestUnAuditHeadPic() {
		return latestUnAuditHeadPic;
	}
	public void setLatestUnAuditHeadPic(CustomerPicMsgEntity latestUnAuditHeadPic) {
		this.latestUnAuditHeadPic = latestUnAuditHeadPic;
	}
	public String getUnAuditNickName() {
		return unAuditNickName;
	}
	public void setUnAuditNickName(String unAuditNickName) {
		this.unAuditNickName = unAuditNickName;
	}
	public String getUnAuditNickNameFieldFid() {
		return unAuditNickNameFieldFid;
	}
	public void setUnAuditNickNameFieldFid(String unAuditNickNameFieldFid) {
		this.unAuditNickNameFieldFid = unAuditNickNameFieldFid;
	}
	
}
