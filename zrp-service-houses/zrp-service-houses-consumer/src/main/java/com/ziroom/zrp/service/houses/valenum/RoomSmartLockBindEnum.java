package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>房间绑定智能锁枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年12月11日 14:07
 * @since 1.0
 */
public enum RoomSmartLockBindEnum {

    WBD(0,"未绑定"),
    YBD(1,"已绑定");
    RoomSmartLockBindEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 类型
     */
    private int code;
    /**
     * 名字
     */
    private String name;


    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
