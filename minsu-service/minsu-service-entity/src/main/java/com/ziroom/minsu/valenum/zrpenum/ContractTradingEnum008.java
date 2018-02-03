package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>首次支付超时参数配置</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月15日 17:34
 * @since 1.0
 */
public enum ContractTradingEnum008 {

    ContractTradingEnum008001("ContractTradingEnum008001", "支付金额临界值"),
    ContractTradingEnum008002("ContractTradingEnum008002", "小于临界值超时小时数"),
    ContractTradingEnum008003("ContractTradingEnum008003", "大于等于临界值超时小时数");

    private final String value;

    private final String name;

    ContractTradingEnum008(String value, String name) {
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
