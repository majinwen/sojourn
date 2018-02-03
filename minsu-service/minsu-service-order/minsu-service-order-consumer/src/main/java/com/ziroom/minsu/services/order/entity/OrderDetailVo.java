package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.entity.order.OrderParamEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>保存订单需要的信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderDetailVo extends OrderInfoVo{

    /** 序列化id */
    private static final long serialVersionUID = -86654255665455454L;
	/**服务费*/
	private Integer serviceCharge;
	/**常用联系人*/
	private List<UsualContactEntity> listUsualContactEntities;
	
	/**预订时间字符串格式 yyyy-MM-dd*/
	private String createTimeStr;
	
	/**订单参数列表  */
	private List<OrderParamEntity> listOrderParamEntities;
	
	/**订单配置列表  */
	private List<OrderConfigEntity> listOrderConfigEntities;
	
	/**联系热门列表*/
	private List<String> contacts;
	
	/**额外消费明细*/
	private String otherMoneyDes;
	
	/**房东拒绝原因*/
	private String refuseReason;
	
	
	
	/**参数列表的map*/
	private Map<String, Object> paramMap;
	
	public OrderDetailVo(){}
	
	public String getOtherMoneyDes() {
		return otherMoneyDes;
	}


	public void setOtherMoneyDes(String otherMoneyDes) {
		this.otherMoneyDes = otherMoneyDes;
	}

	public Integer getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Integer serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public List<UsualContactEntity> getListUsualContactEntities() {
		return listUsualContactEntities;
	}

	public void setListUsualContactEntities(
			List<UsualContactEntity> listUsualContactEntities) {
		this.listUsualContactEntities = listUsualContactEntities;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public List<OrderParamEntity> getListOrderParamEntities() {
		return listOrderParamEntities;
	}

	public void setListOrderParamEntities(
			List<OrderParamEntity> listOrderParamEntities) {
		this.listOrderParamEntities = listOrderParamEntities;
	}

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public List<OrderConfigEntity> getListOrderConfigEntities() {
		return listOrderConfigEntities;
	}

	public void setListOrderConfigEntities(
			List<OrderConfigEntity> listOrderConfigEntities) {
		this.listOrderConfigEntities = listOrderConfigEntities;
	}

}
