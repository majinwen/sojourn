package com.ziroom.minsu.valenum.base;

/**
 * <p>热门区域类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/7.
 * @version 1.0
 * @since 1.0
 */
public enum RegionTypeEnum {

    //1：商圈 2 景点
    BUSINESS(1,"商圈"),

    SCENIC(2,"景点");

    RegionTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
