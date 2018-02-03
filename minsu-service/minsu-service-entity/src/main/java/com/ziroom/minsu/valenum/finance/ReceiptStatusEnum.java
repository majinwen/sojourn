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
public enum ReceiptStatusEnum {
    
	un_sure(1,"未确认"),
	has_sure(2,"已确认"),
	error_sure(3,"付款异常");

    ReceiptStatusEnum(int code ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static ReceiptStatusEnum getByCode(int code){
    	for(final ReceiptStatusEnum ose : ReceiptStatusEnum.values()){
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
