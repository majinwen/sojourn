
package com.ziroom.minsu.valenum.customer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>性别枚举</p>
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
public enum CustomerSexEnum {

	GIRL(1,"女"),
	BOY(2,"男");
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
	 */
	private String value;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (CustomerSexEnum enumration : CustomerSexEnum.values()) {  
			enumMap.put(enumration.getCode(), enumration.getValue());  
        }  
	}
	
	CustomerSexEnum(int code,String value){
		
		this.code = code;
		this.value = value;
	}

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
	 * get  CustomerSexEnum by code
	 *
	 * @author yd
	 * @created 2016年5月6日 下午9:16:40
	 *
	 * @param code
	 * @return
	 */
	public static CustomerSexEnum getCustomerSexEnumByCode(int code){
		
		for (CustomerSexEnum customerSexEnum : CustomerSexEnum.values()) {
			
			if(customerSexEnum.getCode() == code){
				return customerSexEnum;
			}
		}
		
		return null;
	}

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
}
