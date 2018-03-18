package com.ziroom.zrp.service.trading.valenum.finance;

import lombok.Getter;

/**
 * <p>回调支付类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月22日 11:24
 * @since 1.0
 */
public enum  CallTypeEnum {

    NORMAL_PAY("1","普通支付"),
    BATCH_WITHHOLD("2","批量代扣"),
    EASY_PAY("3","轻松付"),
    ZIROOM_STAGE("4","自如分期");

    CallTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;

}
