package com.ziroom.zrp.service.houses.valenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p>智能平台水电付费模式</p>
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
public enum SmartPlatformWaterWattPayTypeEnum {

    PREPAYMENT(1, 0, "预付费"),
    AFTERPAYMENT(2, 1, "后付费");

    /**
     * 智能平台的类型
     * 跟智能平台交互才用这个
     */
    @Getter
    private int code;

    /**
     * 自如寓这边的类型
     * 咱们自己这边系统用这个
     */
    @Getter
    private int zyuCode;

    @Getter
    private String name;

    public static String getNameByZyuCode(int zyuCode){
        for (SmartPlatformWaterWattPayTypeEnum payTypeEnum : SmartPlatformWaterWattPayTypeEnum.values()) {
            if (payTypeEnum.getZyuCode() == zyuCode) {
                return payTypeEnum.getName();
            }
        }
        return null;
    }

    public static String getNameByCode(int code) {
        Optional<SmartPlatformWaterWattPayTypeEnum> first = Arrays.stream(SmartPlatformWaterWattPayTypeEnum.values())
                .filter(p -> p.getCode() == code)
                .findFirst();
        return first.isPresent() ? first.get().getName() : null;
    }

    public static SmartPlatformWaterWattPayTypeEnum getEnumByZyuCode(int zyuCode){
        for (SmartPlatformWaterWattPayTypeEnum payTypeEnum : SmartPlatformWaterWattPayTypeEnum.values()) {
            if (payTypeEnum.getZyuCode() == zyuCode) {
                return payTypeEnum;
            }
        }
        return null;
    }
}
