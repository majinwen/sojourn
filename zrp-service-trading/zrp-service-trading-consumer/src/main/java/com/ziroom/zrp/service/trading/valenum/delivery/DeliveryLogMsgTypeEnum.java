package com.ziroom.zrp.service.trading.valenum.delivery;

/**
 * <p>提示信息类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月09日 14:15
 * @since 1.0
 */
public enum DeliveryLogMsgTypeEnum {

    USER(1,"用户"),
    ZO(2,"管家");

    DeliveryLogMsgTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
