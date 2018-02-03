package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>订单的扩展参数code</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public enum OrderParamEnum {

    LOG("log","该类型的参数，直接记录在log里面并不在参数表"),
    OTHER_COST_DES("otherCostDes","额外消费明细"),
    REFUSE_REASON("refusalReason","房东拒绝订单原因"),
    STATUS_BAK("statusBak","房东点击强制取消订单,备份订单状态"),
    NEGOTIATE_CANCEL("negotiateCancel","客服协商取消原因");

    OrderParamEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * 获取
     * @param code
     * @return
     */
    public static OrderParamEnum geOrderParamByCode(String code) {
        if(Check.NuNStr(code)){
            return null;
        }
        for (final OrderParamEnum para : OrderParamEnum.values()) {
            if (para.getCode().equals(code)) {
                return para;
            }
        }
        return null;
    }



    /** 编码 */
    private String code;

    /** 名称 */
    private String name;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
