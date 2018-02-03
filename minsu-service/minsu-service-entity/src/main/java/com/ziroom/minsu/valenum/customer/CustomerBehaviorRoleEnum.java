package com.ziroom.minsu.valenum.customer;

/**
 * <p>客户行为（成长体系）角色</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月11日 
 * @version 1.0
 * @since 1.0
 */
public enum CustomerBehaviorRoleEnum {

    LANDLORD(1, "房东"),
    TENANT(2, "房客");

    CustomerBehaviorRoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * code值
     */
    private int code;

    /**
     * 中文含义
     */
    private String name;

    public static String getNameByCode(int code){
        for (CustomerBehaviorRoleEnum type : CustomerBehaviorRoleEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
