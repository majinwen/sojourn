package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>操作人类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月18日 17:54
 * @since 1.0
 */
public enum HandlerTypeEnum {

    EMP(0,"内部员工"),
    USER(1,"用户");


    private  int code;

    private  String val;

    HandlerTypeEnum(int code,String val){
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
