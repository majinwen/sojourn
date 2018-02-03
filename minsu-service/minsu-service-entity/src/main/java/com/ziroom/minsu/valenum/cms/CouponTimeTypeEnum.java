package com.ziroom.minsu.valenum.cms;

import com.asura.framework.base.util.Check;

/**
 * <p>优惠券的时间限制类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/10/31.
 * @version 1.0
 * @since 1.0
 */
public enum  CouponTimeTypeEnum {


    FIX(1,"固定时间"),
    COUNT(2,"有效天数");

    CouponTimeTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 通过code获取
     * @param code
     * @return
     */
    public static CouponTimeTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final CouponTimeTypeEnum couponTimeTypeEnum : CouponTimeTypeEnum.values()){
            if(couponTimeTypeEnum.getCode() == code){
                return couponTimeTypeEnum;
            }
        }
        return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
