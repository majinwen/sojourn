package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>智能平台水电设备类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月15日
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public enum SmartPlatformWaterWattDeivceTypeEnum {

    WATER_METER("ZR0014","水表"),
    WATT_METER("ZR0016","电表");

    @Getter
    private String code;

    @Getter
    private String name;

    /**
     *
     * 是否合法
     *
     * @author zhangyl2
     * @created 2018年01月16日 15:01
     * @param
     * @return
     */
    public static boolean checkLegalCode(String code){
        if(!Check.NuNStr(code)){
            for (SmartPlatformWaterWattDeivceTypeEnum deivceTypeEnum : SmartPlatformWaterWattDeivceTypeEnum.values()){
                if (deivceTypeEnum.getCode().equals(code)){
                    return true;
                }
            }
        }
        return false;
    }

}
