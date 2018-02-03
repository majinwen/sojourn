package com.ziroom.minsu.services.order.dto;

/**
 * <p>
 * 订单金额明细出参
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年8月19日
 * @since 1.0
 * @version 1.0
 */
public class OrderDetailFeeItemResponse implements Comparable{

	/**
	 * 金额名称，例如:押金
	 */
	String name;

	/**
	 * 颜色类型，0：无颜色（默认），1：红色
	 */
	Integer colorType = 0;

	/**
	 * 金额
	 */
	String fee;

	/**
	 * 排列序号
	 */
	Integer index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}


	@Override
	public int compareTo(Object obj) {
		OrderDetailFeeItemResponse b = (OrderDetailFeeItemResponse) obj;
		return this.index - b.index;
	}
}
