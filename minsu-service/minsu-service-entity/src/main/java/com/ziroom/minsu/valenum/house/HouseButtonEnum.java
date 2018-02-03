package com.ziroom.minsu.valenum.house;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangwt
 * @version 1.0
 * @Date Created in 2017年06月26日 17:42
 * @since 1.0
 */
public enum HouseButtonEnum {
    FBFY("发布房源"),
    QXFB("取消发布"),
    XJFY("下架房源");

    private String name;

    HouseButtonEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
