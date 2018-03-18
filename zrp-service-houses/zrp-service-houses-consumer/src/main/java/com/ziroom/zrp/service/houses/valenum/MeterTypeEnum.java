package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;
import lombok.Getter;

/**
 * <p>水电费类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月25日 17:25
 * @since 1.0
 */
@Getter
public enum MeterTypeEnum {
    WATER(0, "水", "冷水表", "吨"),
    ELECTRICITY(1, "电", "电表", "度");

    MeterTypeEnum(int code, String name, String machineName, String unit) {
        this.code = code;
        this.name = name;
        this.machineName = machineName;
        this.unit = unit;
    }

    private int code;

    private String name;
    /**
     * 机器名称
     */
    private String machineName;
    /**
     *
     */
    private String unit;

    public static MeterTypeEnum valueOf(Integer code) {
        if(!Check.NuNObj(code)){
            for (MeterTypeEnum mte : MeterTypeEnum.values()
                    ) {
                if (mte.getCode() == code) {
                    return mte;
                }
            }
        }
        return null;
    }
}
