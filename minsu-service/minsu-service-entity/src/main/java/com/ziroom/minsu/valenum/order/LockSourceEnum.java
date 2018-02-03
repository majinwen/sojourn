package com.ziroom.minsu.valenum.order;

/**
 * <p>证件类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/18.
 * @version 1.0
 * @since 1.0
 */
public enum LockSourceEnum {

    ZIROOM(0,"自如"),
    AIRBNB(1,"airbnb");

    LockSourceEnum(int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 通过code获取值
     * @param code
     * @return
     */
    public static LockSourceEnum getByCode(int code){
        for(final LockSourceEnum lockSourceEnum : LockSourceEnum.values()){
            if(lockSourceEnum.getCode() == code){
                return lockSourceEnum;
            }
        }
        return null;
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
