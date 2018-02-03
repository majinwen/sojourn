package com.ziroom.minsu.services.order.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;

/**
 * <p>订单详情金额</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月22日
 * @since 1.0
 * @version 1.0
 */
public class OrderDetailFeeResponse extends BaseEntity{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4087673579448241457L;

	/**
	 * 总金额
	 */
	Integer totalFee;
	
	/**
	 * 单位，例如：$
	 */
	String feeUnit;
	
	/**
	 * 金额明细list
	 */
	List<OrderDetailFeeItemResponse> feeItemList = new ArrayList<OrderDetailFeeItemResponse>();

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getFeeUnit() {
		return feeUnit;
	}

	public void setFeeUnit(String feeUnit) {
		this.feeUnit = feeUnit;
	}

	public List<OrderDetailFeeItemResponse> getFeeItemList() {
		Collections.sort(feeItemList);
		return feeItemList;
	}

	public void setFeeItemList(List<OrderDetailFeeItemResponse> feeItemList) {
		this.feeItemList = feeItemList;
	}

	
	
	public static void main(String[] args) {
		OrderDetailFeeResponse need = new OrderDetailFeeResponse();
		need.setTotalFee(100);
		need.setFeeUnit("羊");
		
		OrderDetailFeeItemResponse item = new OrderDetailFeeItemResponse();
		item.setName("押金");
		item.setColorType(0);
		item.setFee("50");
		item.setIndex(1);
		
		OrderDetailFeeItemResponse item2 = new OrderDetailFeeItemResponse();
		item2.setName("房租");
		item2.setColorType(0);
		item2.setFee("50");
		item2.setIndex(2);
		
		need.getFeeItemList().add(item);
		need.getFeeItemList().add(item2);
		
		
		DataTransferObject dto = new DataTransferObject(DataTransferObject.SUCCESS, null ,need.toMap());
        System.err.println(dto.toJsonString());
	}
}
