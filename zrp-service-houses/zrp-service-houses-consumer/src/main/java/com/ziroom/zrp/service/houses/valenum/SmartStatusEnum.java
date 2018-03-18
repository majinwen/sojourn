package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>智能锁状态</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月07日 11:39
 * @since 1.0
 */
public enum SmartStatusEnum{

    SENDING(0,"获取中"),
    SEND_SUCCESS(1,"成功"),
    SEND_FAILED(2,"失败"),
    INVALID(3, "失效");

    SmartStatusEnum(int code,String val){

        this.code = code;
        this.val = val;
    }


    private int code;

    private String val;

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

    public static SmartStatusEnum valueOf(Integer status) {
        for (SmartStatusEnum e : SmartStatusEnum.values()
             ) {
            if (e.getCode() == status) {
                return e;
            }
        }
        return null;
    }
}
