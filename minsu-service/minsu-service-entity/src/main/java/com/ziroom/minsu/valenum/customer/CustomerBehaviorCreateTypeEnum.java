package com.ziroom.minsu.valenum.customer;

/**
 * <p>用户行为创建类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月12日
 * @version 1.0
 * @since 1.0
 */
public enum CustomerBehaviorCreateTypeEnum {

    /**
     * 用户行为创建类型(1-系统生成 2-人工录入)
     */
    SYSTEM_GENERATION(1, "系统生成"),
    MANUAL_GENERATION(2, "人工录入");

    CustomerBehaviorCreateTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code){
        for (CustomerBehaviorCreateTypeEnum type : CustomerBehaviorCreateTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return "";
    }

    /**
     * 行为创建类型(1-系统生成 2-人工录入)
     */
    private Integer code;

    /**
     * 说明
     */
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
