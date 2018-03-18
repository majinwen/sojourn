package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>是否绑定智能锁 枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月13日 20:30
 * @since 1.0
 */
public enum IsBindLockEnum {

    IS_NOT_BIND(0,"未绑定"),
    IS_BIND(1,"已绑定");


    private int code;

    private String val;

    IsBindLockEnum(int code,String val){

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
