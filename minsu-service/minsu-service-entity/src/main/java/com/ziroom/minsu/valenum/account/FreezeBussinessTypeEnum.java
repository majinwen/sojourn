package com.ziroom.minsu.valenum.account;

/**
 * <p>消费冻结类型</p>
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
public enum FreezeBussinessTypeEnum {
    
	automatic_withdraw(3,"自动提现"),
	service_charges(4,"公司服务费");

    FreezeBussinessTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static FreezeBussinessTypeEnum getByCode(int code){
    	for(final FreezeBussinessTypeEnum ose : FreezeBussinessTypeEnum.values()){
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
