package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;


/**
 * <p>收款人类型</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月29日
 * @since 1.0
 * @version 1.0
 */
public enum ReceiveTypeEnum {
	LANDLORD(1,"房东","yjyz"),
	TENANT(2,"房客","zrk"),
	COMPANY(3,"公司个人账户","zrk");

	ReceiveTypeEnum(int code, String name,String financeCode) {
		this.code = code;
		this.name = name;
		this.financeCode = financeCode;
	}
	public static ReceiveTypeEnum getByCode(int code){
    	for(final ReceiveTypeEnum ose : ReceiveTypeEnum.values()){
    		if(ose.getCode() == code){
    			return ose;
    		}
    	}
    	return null;
    }
	
	public static String getReceiveTypeEnumName(Integer code) {
		if(Check.NuNObj(code)){
			return "";
		}
    	for (final ReceiveTypeEnum ose : ReceiveTypeEnum.values()) {
            if (ose.getCode() == code) {
                return ose.getName();
            }
        }
        return "";
    }
	
	
	/** code */
	private int code;

	/** 名称 */
	private String name;
	
	/** 财务使用码 */
	private String financeCode;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getFinanceCode() {
		return financeCode;
	}
}
