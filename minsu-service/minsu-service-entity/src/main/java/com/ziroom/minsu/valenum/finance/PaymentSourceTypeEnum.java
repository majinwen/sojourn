package com.ziroom.minsu.valenum.finance;

/**
 * <p>财务付款金额明细类型</p>
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
public enum PaymentSourceTypeEnum {
    
	customer(2,"房客(订单)"),
	landlord(1,"房东(账单)");

    PaymentSourceTypeEnum(int code ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static PaymentSourceTypeEnum getByCode(int code){
    	for(final PaymentSourceTypeEnum ose : PaymentSourceTypeEnum.values()){
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
       
    public String getName() {
        return name;
    }

	public int getCode() {
		return code;
	}
    
}
