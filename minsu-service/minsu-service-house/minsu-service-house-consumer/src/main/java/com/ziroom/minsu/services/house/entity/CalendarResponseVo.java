package com.ziroom.minsu.services.house.entity;


/**
 * <p>出租日历vo</p>
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
public class CalendarResponseVo {
 
	//日历时间
	private String date;
	
	//价格
	private Integer price;
	
	//状态  0:待出租(默认状态) 1:已出租 2:不可租  
	private Integer status = 0;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
