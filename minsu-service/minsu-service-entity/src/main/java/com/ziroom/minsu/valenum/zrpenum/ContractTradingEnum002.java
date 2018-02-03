package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>付款方式</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 15:36
 * @since 1.0
 */
public enum ContractTradingEnum002 {

    ContractTradingEnum002001("ContractTradingEnum002001", "押一付一", 1),
    ContractTradingEnum002002("ContractTradingEnum002002", "押一付三", 3),
    ContractTradingEnum002003("ContractTradingEnum002003", "押一付六", 6),
    ContractTradingEnum002004("ContractTradingEnum002004", "押一付十二", 12),
    ContractTradingEnum002005("ContractTradingEnum002005", "一次性付清", 9);

    private final String value;

    private final String name;
    /**
     * 公寓code
     */
    private final int code;

    ContractTradingEnum002(String value, String name, int code) {
        this.value = value;
        this.name = name;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    /**
     * 根据枚举值获取
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年09月13日 10:43:54
     */
    public static ContractTradingEnum002 getByValue(String value) {
        for (final ContractTradingEnum002 ose : ContractTradingEnum002.values()) {
            if (ose.getValue().equals(value)) {
                return ose;
            }
        }
        return null;
    }

    /**
     * 根据code获取枚举
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年09月13日 15:23:20
     */
    public static ContractTradingEnum002 getByCode(int code) {
        for (final ContractTradingEnum002 ose : ContractTradingEnum002.values()) {
            if (ose.getCode() == code) {
                return ose;
            }
        }
        return null;
    }
}
