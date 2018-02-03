package com.ziroom.minsu.valenum.evaluate;

/**
 * <p>评价类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/14.
 * @version 1.0
 * @since 1.0
 */
public enum  OrderEvalTypeEnum {

    ALL(0,"所有"),
    WAITING(1,"待评价"),
    HAS(2,"已评价"),
    LAND_EVL_SUCCESS_LIST(10,"房东评价成功,展示房东未评价列表");


    OrderEvalTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 编码 */
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
