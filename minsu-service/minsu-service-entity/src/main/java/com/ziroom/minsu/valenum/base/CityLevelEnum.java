package com.ziroom.minsu.valenum.base;

/**
 * <p>城市层级</p>
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
public enum CityLevelEnum {

    NATION(1,"国家"),
    PROVINCE(2,"省"),
    CITY(3,"城市"),
    AREA(4,"区域");

    CityLevelEnum(int code, String name) {
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
