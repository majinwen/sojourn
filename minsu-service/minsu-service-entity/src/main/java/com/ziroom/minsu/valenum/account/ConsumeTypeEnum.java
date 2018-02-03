package com.ziroom.minsu.valenum.account;

/**
 * <p>消费类型</p>
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
public enum ConsumeTypeEnum {
	un_fill(0,"不充值"),
	balance(2,"消费余额"),
	freeze(4,"消费冻结"),
	filling_freeze(3,"充值冻结"),
	filling_balance(1,"充值余额");

    ConsumeTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static ConsumeTypeEnum getByCode(int code){
    	for(final ConsumeTypeEnum ose : ConsumeTypeEnum.values()){
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