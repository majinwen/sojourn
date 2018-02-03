package com.ziroom.minsu.valenum.finance;

/**
 * <p>财务付款对象类型</p>
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
public enum PaymentObjTypeEnum {
    
	ziroom("zrk","自如客"),
	landlord("yjyz","业主"),
	bussness("sh","商户");

    PaymentObjTypeEnum( String code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static PaymentObjTypeEnum getByCode(String code){
    	for(final PaymentObjTypeEnum ose : PaymentObjTypeEnum.values()){
    		if(ose.getCode().equals(code)){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private String code;

    /** 名称 */
    private String name;
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    
}
