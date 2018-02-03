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
public enum CustomerBehaviorAttributeEnum {

    /**
     * 行为属性(1-限制行为 2-激励行为 3-中性行为)
     */
    LIMIT_BEHAVIOR(1, "限制行为"),
    EXCITATION_BEHAVIOR(2, "激励行为"),
    NEUTRAL_BEHAVIOR(3, "中性行为");

    CustomerBehaviorAttributeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 行为属性(1-限制行为 2-激励行为 3-中性行为)
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

    public static String getNameByCode(int code){
        for (CustomerBehaviorAttributeEnum type : CustomerBehaviorAttributeEnum.values()) {
            if (type.getCode() == code) {
                return type.getName();
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
