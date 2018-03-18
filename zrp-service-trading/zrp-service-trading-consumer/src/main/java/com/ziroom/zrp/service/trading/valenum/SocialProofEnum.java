package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>证件类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月21日 10:47
 * @since 1.0
 */
public enum SocialProofEnum {

    COMPANYCARD(1,"单位工牌"),
    WORKCARD(2,"工作证"),
    STUDENTCARD(3,"学生证"),
    LABORCONTRACT(4,"劳动合同"),
    WORKPROOF(5,"工作证明"),
    EMPLOYEEAGREEMENT(6,"就业协议"),
    BUSINESSLICENCE(7,"营业执照副本"),
    INTRODUCTIONLETTER(8,"介绍信"),
    INCOMEPROOF(9,"收入证明"),
    GUARANTEEAGREEMENT(10,"京籍人士担保协议");

    SocialProofEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SocialProofEnum getByCode(int code){
        for(final SocialProofEnum certTypeEnum : SocialProofEnum.values()){
            if(certTypeEnum.getCode()== code){
                return certTypeEnum;
            }
        }
        return null;
    }
}
