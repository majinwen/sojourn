package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>智能设备类型  1=智能水  2=智能电</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月18日 17:44
 * @since 1.0
 */
public enum  IntellectTypeEnum {

    INTELLECT_WATER(1,"智能水 "),

    INTELLECT_WATT(2,"智能电 ");


    private int code;

    private String desc;

    IntellectTypeEnum(int code,String desc){

        this.code = code;
        this.desc = desc;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
