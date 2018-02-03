
package com.ziroom.minsu.valenum.customer;

/**
 * <p>用户来源 用户初始来源 0=其他 1=M站注册 2=M站第一次登录 3=客户端同步 </p>
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
public enum CustomerSourceEnum {

	Other(0,"其他"),
	Mapp_Login_First(1,"M站第一次登录"),
	Api(2,"客户端同步"),
	Mapp_Rigister(3,"M站注册");
	
	CustomerSourceEnum( int code,String value){
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 枚举编码
	 */
	private int code;
	
	/**
	 * 编码中文含义
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
	
	/**
	 * 
	 * get CustomerSourceEnum by code
	 *
	 * @author yd
	 * @created 2016年5月27日 下午4:18:56
	 *
	 * @param code
	 * @return
	 */
	public static CustomerSourceEnum getCustomerSourceEnumByCode(int code){
		
		for (CustomerSourceEnum customerSourceEnum : CustomerSourceEnum.values()) {
			
			if(customerSourceEnum.getCode() == code){
				return customerSourceEnum;
			}
		}
		return null;
	}
	
}
