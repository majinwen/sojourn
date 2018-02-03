/**
 * @FileName: OrderEvaFlagEnum.java
 * @Package com.ziroom.minsu.valenum.evaluate
 * 
 * @author yd
 * @created 2016年4月12日 下午12:03:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.evaluate;

/**
 * <p>订单是否已评价标识 枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum OrderEvaFlagEnum {
	
	ORDER_NOT_HAVE_EVA(0,"订单未修改成已评价"),
	ORDE_HAVE_EVA(1,"代表订单已修改成已评价");
	
	
    OrderEvaFlagEnum(int code,String chineseName){
		this.code = code;
		this.chineseName = chineseName;
	}
	/**
	 * 编码
	 */
	private int code;
	
	/**
	 * 中文名称
	 */
	private String chineseName;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	

	 /**
    * 获取
    * @param OrderEvaFlagEnum
    * @return
    */
   public static OrderEvaFlagEnum getOrderEvaFlagByCode(int code) {
       for (final OrderEvaFlagEnum orderEvaFlagEnum : OrderEvaFlagEnum.values()) {
           if (orderEvaFlagEnum.getCode() == code) {
               return orderEvaFlagEnum;
           }
       }
       return null;
   }

}
