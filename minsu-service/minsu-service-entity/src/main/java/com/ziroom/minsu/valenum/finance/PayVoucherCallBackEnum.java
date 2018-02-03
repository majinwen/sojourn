package com.ziroom.minsu.valenum.finance;

import com.asura.framework.base.util.Check;

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
public enum PayVoucherCallBackEnum {
    
	UN_PAY(1,"未付款"),
	HAS_PAY(2,"已付款"),
	ERROR_PAY(3,"付款异常");

    PayVoucherCallBackEnum( int code ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static PayVoucherCallBackEnum getByCode(int code){
    	for(final PayVoucherCallBackEnum callEnum : PayVoucherCallBackEnum.values()){
    		if(callEnum.getCode() == code){
    			return callEnum;
    		}
    	}
    	return null;
    }
    
    public static String getNameByCode(int code){
    	PayVoucherCallBackEnum callEnum = PayVoucherCallBackEnum.getByCode(code);
    	if(Check.NuNObj(callEnum)){
    		return null;
    	}
    	return callEnum.getName();
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
