package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p>参悟 账单类型 </p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月22日 17:50
 * @since 1.0
 */
public enum DocumentTypeEnum {

    LIFE_FEE("1001", "生活费用"),
    PENALTY_FEE("1012", "出房解约违约金"),
    RENT_FEE("1007", "房租"),
    SERVICE_FEE("1007", "服务费"),
    DEPOSIT_FEE("1007", "押金"),
    OVERDUE_FEE("1009", "逾期违约金");


    DocumentTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
