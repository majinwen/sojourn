package com.ziroom.minsu.valenum.account;

/**
 * <p>充值类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie
 * @version 1.0
 * @since 1.0
 */
public enum FillBussinessTypeEnum {
    
	receive_fill(1,"收款充值"),
	checkout_fill(2,"退租充值"),
	coupon_fill(3,"优惠券充值"),
    act_fill(4,"活动充值");

    FillBussinessTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static FillBussinessTypeEnum getByCode(int code){
    	for(final FillBussinessTypeEnum ose : FillBussinessTypeEnum.values()){
    		if(ose.getCode() == code){
    			return ose;
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
