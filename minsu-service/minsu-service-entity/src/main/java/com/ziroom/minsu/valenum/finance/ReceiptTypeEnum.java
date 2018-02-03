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
public enum ReceiptTypeEnum {
    
	rent_money(3,"房租");

    ReceiptTypeEnum(int code ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static ReceiptTypeEnum getByCode(int code){
    	for(final ReceiptTypeEnum ose : ReceiptTypeEnum.values()){
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
