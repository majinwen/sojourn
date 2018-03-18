package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>智能平台水表类型</p>
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
public enum SmartPlatformWaterTypeEnum {

    COLD_WATER_METER(1,"冷水表"),
    HOT_WATER_METER(2,"热水表");

    @Getter
    private int code;

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
    public static boolean checkLegalCode(Integer code){
        if(!Check.NuNObj(code)){
            for (SmartPlatformWaterTypeEnum waterTypeEnum : SmartPlatformWaterTypeEnum.values()){
                if (waterTypeEnum.getCode() == code){
                    return true;
                }
            }
        }
        return false;
    }

}
