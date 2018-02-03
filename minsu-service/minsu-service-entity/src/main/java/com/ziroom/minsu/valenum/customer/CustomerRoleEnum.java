
package com.ziroom.minsu.valenum.customer;

import com.asura.framework.base.util.Check;

/**
 * 
 * 0=其他 1=种子房东
 * <p>用户角色</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum CustomerRoleEnum {

	OTHER(0,"0","全部"),
	SEED(1,"1","种子房东");

	CustomerRoleEnum(int code,String str, String name){
        this.code = code;
        this.str = str;
        this.name = name;
	}
	
	/**
	 * code值
	 */
	private  int code;


    /**
     * code编码
     */
    private String str;

	/**
	 * z中文含义
	 */
	private String name;

    public int getCode() {
        return code;
    }

    public String getStr() {
        return str;
    }

    public String getName() {
        return name;
    }

    /**
	 * 获取角色信息
	 * @author afi
	 * @param code
	 * @return
	 */
    public static CustomerRoleEnum getCustomerRoleByCode(Integer code) {
        if (Check.NuNObj(code)){
            return null;
        }
        for (final CustomerRoleEnum statu : CustomerRoleEnum.values()) {
            if (statu.getCode() == code.intValue()) {
                return statu;
            }
        }
        return null;
    }
}
