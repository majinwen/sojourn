
package com.ziroom.minsu.valenum.customer;

/**
 * <p>是否房东 0：否，1：是 根据是否发布房源更新</p>
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
public enum IsLandlordEnum {


	NOT_LANDLORD(0,"自如客"),
	IS_LANDLORD(1,"房东");
	
	IsLandlordEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * 中文含义
	 */
	private String value;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
