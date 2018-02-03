package com.ziroom.minsu.valenum.base;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/2/23 15:29
 * @version 1.0
 * @since 1.0
 */
public enum MapTypeEnum {

    BAIDU("1","百度"),
    GOOGLE("2","谷歌"),
    GAODE("3","高德"),
    PC_GOOLGE_M_BAIDU("4","PC和TROY谷歌,M站百度");

    MapTypeEnum(String code, String name) {
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
}
