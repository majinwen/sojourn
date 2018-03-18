package com.ziroom.zrp.service.trading.valenum.waterwatt;

import lombok.Getter;

/**
 * <p>结算类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2018年2月08日 10:42
 * @since 1.0
 */
@Getter
public enum WaterwattSettlementTypeEnum {
    MANUAL(0,"手工结算"),
    JOB(1,"定时任务结算");
    WaterwattSettlementTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

}
