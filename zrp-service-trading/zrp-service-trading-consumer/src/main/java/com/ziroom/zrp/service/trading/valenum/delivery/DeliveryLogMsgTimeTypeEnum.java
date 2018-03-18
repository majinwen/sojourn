package com.ziroom.zrp.service.trading.valenum.delivery;

import com.ziroom.zrp.service.trading.valenum.CertTypeEnum;

/**
 * <p>物业交割提示信息类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月09日 14:17
 * @since 1.0
 */
public enum DeliveryLogMsgTimeTypeEnum {

    TIME_24(1,1,"24小时"),
    TIME_36(2,2,"36小时");
    DeliveryLogMsgTimeTypeEnum(int code, int hour, String name) {
        this.code = code;
        this.hour = hour;
        this.name = name;
    }

    private int code;

    private int hour;

    private String name;

    public int getCode() {
        return code;
    }

    public int getHour() {
        return hour;
    }

    public String getName() {
        return name;
    }

    public static DeliveryLogMsgTimeTypeEnum getByCode(int code){
        for(final DeliveryLogMsgTimeTypeEnum timeTypeEnum : DeliveryLogMsgTimeTypeEnum.values()){
            if(timeTypeEnum.getCode()== code){
                return timeTypeEnum;
            }
        }
        return null;
    }
}
