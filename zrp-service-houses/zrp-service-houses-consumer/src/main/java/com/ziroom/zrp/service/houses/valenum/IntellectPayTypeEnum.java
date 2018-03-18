package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>付费类类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月18日 17:56
 * @since 1.0
 */
public enum  IntellectPayTypeEnum {

    PRE_PAYMENT(1,"预付费"),
    AFTER_PAYMENT(2,"后付费");


    private  int code;

    private  String val;

    IntellectPayTypeEnum(int code,String val){

        this.code = code;
        this.val = val;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
