package com.ziroom.zrp.service.trading.valenum;

import java.util.Arrays;
import java.util.List;

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
public enum CertTypeEnum {
    CERTCARD(1,"身份证"),
    PASSPORT(2,"护照"),
    TAIWANCARD(6,"台胞证"),
    BUSINESSLICENCE(12,"营业执照"),
    PERMITCARD(13,"港澳通行证"),
    ORGANIZATIONCODE(18,"组织机构代码"),

    //兼容线上老数据 显示用，新签不在使用以下选项
    OTHER(0,"其他"),
    HOUSEHOLD(14,"户口本"),
    LOCATIONCARD(15,"居住证");


    CertTypeEnum(int code, String name) {
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

    public static CertTypeEnum getByCode(int code){
        for(final CertTypeEnum certTypeEnum : CertTypeEnum.values()){
            if(certTypeEnum.getCode()== code){
                return certTypeEnum;
            }
        }
        return null;
    }


    public static List<CertTypeEnum> getSelectType(){
        return Arrays.asList(CERTCARD,PASSPORT,TAIWANCARD,PERMITCARD);
    }
}
