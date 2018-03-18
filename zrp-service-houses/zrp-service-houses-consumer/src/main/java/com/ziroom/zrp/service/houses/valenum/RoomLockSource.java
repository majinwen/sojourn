package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>房间锁定来源枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月11日 10:33
 * @since 1.0
 */

public enum RoomLockSource {

    ZRPMS("3","自如寓后台"),
    MAPP("2","自如寓m站"),
    APP("1","自如寓APP"),
    ZRPMS_E("31", "自如寓后台企业");

    RoomLockSource(String code,String name){
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
