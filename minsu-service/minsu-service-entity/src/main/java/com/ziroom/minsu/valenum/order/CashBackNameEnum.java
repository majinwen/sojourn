/**
 * @FileName: CashBackNameEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author loushuai
 * @created 2017年8月7日 上午10:56:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>返现活动fid及名称</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum CashBackNameEnum {
 
	EVALUATE_CASEBACK("8a90a36558de8ece0158e1af36ac00b1","评价返现活动"),
	IMMEDIATEBOOK_CASEBACK("8a90a3975b9dd9be015ba44d06d000e6","立即预订房东返现"),
	SPECIALTONIGHT_CASHBACK("8a90a3975c886dc8015c8a8f838a0043","今夜特惠返现"),
	LARLORDJINJIJIHUA_CASHBACK("8a9091a15e37ba52015e37ba528f0000","房东进击计划"),
	LANCOMPENSATION_CASHBACK("8a90a3975ca18d1e015ca55607fc0067","房东赔付");
	
	private String code;
	
	private String name;

	
	private CashBackNameEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}



	/**
	 * 
	 * get  CashBackNameEnum by code
	 *
	 * @author loushuai
	 * @created 2017年8月7日 下午3:43:46
	 *
	 * @param code
	 * @return
	 */
	public static CashBackNameEnum getCashBackNameEnumByCode(String code){
        if(Check.NuNStr(code)){
        	return null;
        }
		for (CashBackNameEnum cashBackNameEnum : CashBackNameEnum.values()) {
			if(code.equals(cashBackNameEnum.getCode())){
				return cashBackNameEnum;
			}
		}
		return null;
	}
}
