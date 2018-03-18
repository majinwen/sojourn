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
public enum MarriageEnum {

    SINGLE(1,"未婚"),
    MARRIED(2,"结婚"),
    DIVORCED(3,"离异");

    MarriageEnum(int code, String name) {
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

    public static MarriageEnum getByCode(int code){
        for(final MarriageEnum certTypeEnum : MarriageEnum.values()){
            if(certTypeEnum.getCode()== code){
                return certTypeEnum;
            }
        }
        return null;
    }
}
