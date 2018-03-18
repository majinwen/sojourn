package com.ziroom.zrp.service.trading.valenum.waterwatt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>抄表状态</p>
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
public enum WaterwattReadStatusEnum {

    SUCCESS(0,"成功"),
    FAIL(1,"失败");

    @Getter
    private int code;

    @Getter
    private String name;
}
