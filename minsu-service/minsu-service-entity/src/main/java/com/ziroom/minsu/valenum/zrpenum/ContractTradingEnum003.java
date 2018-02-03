package com.ziroom.minsu.valenum.zrpenum;

/**
 * <p>出租期限方式</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 15:42
 * @since 1.0
 */
public enum ContractTradingEnum003 {

    ContractTradingEnum003001("ContractTradingEnum003001", "日租", 3),
    ContractTradingEnum003002("ContractTradingEnum003002", "月租", 2),
    ContractTradingEnum003003("ContractTradingEnum003003", "年租", 1);

    private final String value;

    private final String name;

    private final int code;

    ContractTradingEnum003(String value, String name, int code) {
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


    public static ContractTradingEnum003 getByCode(int code) {
        for (final ContractTradingEnum003 ose : ContractTradingEnum003.values()) {
            if (ose.getCode() == code) {
                return ose;
            }
        }
        return null;
    }
}
