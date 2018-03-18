package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>房间类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月13日 14:07
 * @since 1.0
 */
public enum RoomTypeEnum {

    BED(1,"床位"),
    ROOM(0,"房间");
    RoomTypeEnum(int code, String name) {
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
