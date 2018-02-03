package com.ziroom.minsu.valenum.finance;

/**
 * <p>财务回调付款结果类型</p>
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
public enum PayVouchersResCodeEnum {
    
	common100(100,"param is null"),
	common101(101,"busId is null"),
	common102(102,"busId is invalid"),
	common104(104,"update fail!");

    PayVouchersResCodeEnum( int code ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static PayVouchersResCodeEnum getByCode(int codes){
    	for(final PayVouchersResCodeEnum ose : PayVouchersResCodeEnum.values()){
    		if(ose.getCode() == codes){
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
