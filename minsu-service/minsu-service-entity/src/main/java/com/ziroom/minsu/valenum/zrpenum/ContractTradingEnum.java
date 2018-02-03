package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>自如寓配置项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 14:15
 * @since 1.0
 */
public enum ContractTradingEnum {


    ContractTradingEnum001("ContractTradingEnum001", "限制付款方式出租时间"),
    ContractTradingEnum002("ContractTradingEnum002", "付款方式"),
    ContractTradingEnum003("ContractTradingEnum003", "出租期限不同付款方式"),
    ContractTradingEnum004("ContractTradingEnum004", "续租服务费折扣规则"),
    ContractTradingEnum005("ContractTradingEnum005", "出租方式限制最大值"),
    ContractTradingEnum006("ContractTradingEnum006", "标准服务费率"),
    ContractTradingEnum007("ContractTradingEnum007", "物业交割时间（小时）"),
    ContractTradingEnum008("ContractTradingEnum008", "合同首次支付超时配置项"),
    ContractTradingEnum009("ContractTradingEnum009", "距离超时关闭合同前n小时提醒");

    private final String value;

    private final String name;

    ContractTradingEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


}
