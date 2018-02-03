package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>出租方式限制最大值	</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 17:34
 * @since 1.0
 */
public enum ContractTradingEnum005 {

    ContractTradingEnum005001("ContractTradingEnum005001", "日租"),
    ContractTradingEnum005002("ContractTradingEnum005002", "月租"),
    ContractTradingEnum005003("ContractTradingEnum005003", "年租");

    private final String value;

    private final String name;

    ContractTradingEnum005(String value, String name) {
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
