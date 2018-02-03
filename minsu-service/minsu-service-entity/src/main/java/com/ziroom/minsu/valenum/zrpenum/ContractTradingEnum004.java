package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>续约服务费折扣规则</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 15:45
 * @since 1.0
 */
public enum ContractTradingEnum004 {

    ContractTradingEnum004001("ContractTradingEnum004001", "提前续租（年租）"),
    ContractTradingEnum004002("ContractTradingEnum004002", "老客户（日月年）");

    private final String value;

    private final String name;

    ContractTradingEnum004(String value, String name) {
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
