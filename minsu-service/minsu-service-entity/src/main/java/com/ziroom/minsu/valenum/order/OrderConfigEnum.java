
package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>扩展订单的配置项</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum OrderConfigEnum {

	
	LAN_COMM_FREE("LAN_COMM_FREE","房东免佣金"),
    USER_COMM_FREE("USER_COMM_FREE","用户免佣金");

	OrderConfigEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}


	/** code */
	private String code;

	/** 名称 */
	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	/**
	 * get  OrderConfigEnum by code
     * @author afi
	 * @param code
	 * @return
	 */
	public static OrderConfigEnum getOrderConfigEnumByCode(String code){
        if (Check.NuNStr(code)){
            return null;
        }
		for (OrderConfigEnum config : OrderConfigEnum.values()) {
			if(code.equals(config.getCode())){
				return config;
			}
		}
		return null;
	}
}
