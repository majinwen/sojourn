package com.ziroom.minsu.valenum.cms;

/**
 * <p>是否可以叠加</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月12日 14:55
 * @since 1.0
 */
public enum IsStackEnum {

    NO(0, "否"),
    YES(1, "是");

    IsStackEnum(int code, String name) {
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
