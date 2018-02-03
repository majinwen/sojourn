package com.ziroom.minsu.services.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.ziroom.minsu.entity.search.LabelTipsEntity;

/**
 * <p>下单金额出参</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月19日
 * @since 1.0
 * @version 1.0
 */
public class NeedPayFeeResponse extends BaseEntity{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4277273291917138760L;

	/**
	 * 总金额
	 */
	Integer totalFee;
	
    /** 
     * 原始价格 
     */
    private Integer originalPrice;
	
	/**
	 * 单位，例如：$
	 */
	String feeUnit;
	
	/**
	 * 金额明细list
	 */
	List<NeedPayFeeItemResponse> feeItemList = new ArrayList<NeedPayFeeItemResponse>();
	
	/**
	 * 提示标签列表
	 */
	private List<LabelTipsEntity> labelTipsList;



	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public List<LabelTipsEntity> getLabelTipsList() {
		return labelTipsList;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public void setLabelTipsList(List<LabelTipsEntity> labelTipsList) {
		this.labelTipsList = labelTipsList;
	}

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

	public List<NeedPayFeeItemResponse> getFeeItemList() {
		return feeItemList;
	}

	public void setFeeItemList(List<NeedPayFeeItemResponse> feeItemList) {
		this.feeItemList = feeItemList;
	}

	
	
	public static void main(String[] args) {
		NeedPayFeeResponse need = new NeedPayFeeResponse();
		need.setTotalFee(100);
		need.setFeeUnit("羊");
		
		NeedPayFeeItemResponse item = new NeedPayFeeItemResponse();
		item.setName("押金");
		item.setColorType(0);
		item.setFee("50");
		item.setIndex(1);
		
		NeedPayFeeItemResponse item2 = new NeedPayFeeItemResponse();
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
