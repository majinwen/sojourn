package com.ziroom.minsu.valenum.cms;

/**
 * <p>组类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 17:06
 * @since 1.0
 */
public enum GroupTypeEnum {

    USER(1, "用户组"),
    HOUSE(2, "房源组");

    GroupTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
