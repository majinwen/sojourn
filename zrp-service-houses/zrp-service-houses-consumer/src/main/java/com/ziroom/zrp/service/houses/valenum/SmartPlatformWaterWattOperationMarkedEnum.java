package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>智能平台水电操作指令</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2018年01月15日
 * @since 1.0
 */
@AllArgsConstructor
public enum SmartPlatformWaterWattOperationMarkedEnum {

    ELECTRICMETERSTATE("electricMeterState", "获取电表详情"),
    WATERMETERSTATE("waterMeterState", "获取水表详情"),
    READELECTRICMETER("readElectricMeter", "抄表电表"),
    ELEMETERFETCHPOWERHISTORY("elemeterFetchPowerHistory", "获取智能电表设备充值记录"),
    HISTORYELECTRICMETER("historyElectricMeter", "获取智能电表抄表历史"),
    SETROOMPAYTYPE("setRoomPayType", "设置房间电表付费模式"),
    METEROVERDRAFTQUOTA("meterOverdraftQuota", "设置房间电表电表透支额度"),
    METERCHARGING("meterCharging", "电表充电(有异步回调)"),
    ELECTRICMETERRESET("electricMeterReset", "电表清零(有异步回调)"),
    READWATERMETER("readWaterMeter", "水表抄表(有异步回调)"),
    HISTORYWATERMETER("historyWaterMeter", "水表抄表历史");

    @Getter
    private String code;

    @Getter
    private String desc;

    /**
     * 是否合法
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2018年01月16日 15:01
     */
    public static boolean checkLegalCode(String code) {
        if (!Check.NuNStr(code)) {
            for (SmartPlatformWaterWattOperationMarkedEnum operationMarkedEnum : SmartPlatformWaterWattOperationMarkedEnum.values()) {
                if (operationMarkedEnum.getCode().equals(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getDescBycode(String code) {
        if(!Check.NuNStr(code)){
            for (SmartPlatformWaterWattOperationMarkedEnum operationMarkedEnum : SmartPlatformWaterWattOperationMarkedEnum.values()) {
                if (operationMarkedEnum.getCode().equals(code)) {
                    return operationMarkedEnum.getDesc();
                }
            }
        }
        return null;
    }

}
