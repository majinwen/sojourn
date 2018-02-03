package com.ziroom.minsu.valenum.customer;

import com.asura.framework.base.util.Check;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/7.
 * @version 1.0
 * @since 1.0
 */
public enum  RentPaymentEnum {

    ACCOUNT(0,"账户空间"),
    BANK(1,"银行卡");


    /**
     * 通过code获取
     * @param code
     * @return
     */
    public static RentPaymentEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final RentPaymentEnum rentPaymentEnum : RentPaymentEnum.values()){
            if(rentPaymentEnum.getCode() == code){
                return rentPaymentEnum;
            }
        }
        return null;
    }


    RentPaymentEnum(int code,String name){
        this.code  = code;
        this.name = name;
    }

    /**
     * code值
     */
    private  int code;

    /**
     * z中文含义
     */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
