package com.ziroom.zrp.service.trading.valenum.waterwatt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>抄表类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月29日
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public enum WaterwattReadTypeEnum {

    DINGSHI(0,"定时"),
    XINQIAN(1,"新签"),
    JIEYUE(2,"解约"),
    DAOQI(3,"到期"),
    XUYUE(4,"续约"),
    RENGONG(5,"人工");

    @Getter
    private int code;

    @Getter
    private String name;

    public static WaterwattReadTypeEnum valueOf(Integer code) {
        for (WaterwattReadTypeEnum wte : WaterwattReadTypeEnum.values()) {
            if (wte.getCode() == code) {
                return wte;
            }
        }
        return null;
    }
}
