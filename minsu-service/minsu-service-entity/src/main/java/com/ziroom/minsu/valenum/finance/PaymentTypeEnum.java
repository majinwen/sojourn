package com.ziroom.minsu.valenum.finance;

/**
 * <p>收款单类型</p>
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
public enum PaymentTypeEnum {
    
	order(1,"订单",1,"msfz"),
	punish(2,"账单",0,"msfk"),//财务使用的code暂时未确认
	coupon(3,"优惠券",5,"msyhq"),
	cashback(4,"返现",5,"msfx");

    PaymentTypeEnum(int code ,String name,int financeCode,String financeName) {
        this.code = code;
        this.name = name;
        this.financeCode = financeCode;
        this.financeName = financeName;
    }

    public static PaymentTypeEnum getByCode(int code){
    	for(final PaymentTypeEnum ose : PaymentTypeEnum.values()){
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

    /** financeCode */
    private int financeCode;
    
    /** 名称 */
    private String financeName;
       
    public String getName() {
        return name;
    }

	public int getCode() {
		return code;
	}
	
	public int getFinanceCode() {
		return financeCode;
	}
    
	public String getFinanceName() {
        return financeName;
    }
}
