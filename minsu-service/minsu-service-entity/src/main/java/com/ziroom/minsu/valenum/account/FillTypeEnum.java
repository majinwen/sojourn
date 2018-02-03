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
public enum FillTypeEnum {
    
	cash(1,"现金"),
	coupon(2,"优惠券"),
	cashback(3,"返现");

    FillTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static FillTypeEnum getByCode(int code){
    	for(final FillTypeEnum ose : FillTypeEnum.values()){
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
