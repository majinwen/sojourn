
package com.ziroom.minsu.valenum.customer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.asura.framework.base.util.Check;

/**
 * 
 * 0=其他 1=小学 2=初中 3=高中 4=中技 5=中专 6=大专 7=本科 8=MBA 9=硕士 10=博士)
 * <p>客户学历枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public enum CustomerEduEnum {

	OTHER(0,"其他"),
	PRIMARY(1,"小学"),
	JUNIOR(2,"初中"),
	HIGH(3,"高中"),
	ZHONGJI(4,"中技"),
	ZHONGZHUAN(5,"中专"),
	DAZHUAN(6,"大专"),
	BENKE(7,"本科"),
	MBA(8,"MBA"),
	SUOSHI(9,"硕士"),
	BOSHI(10,"博士");
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
	 */
	private String name;
	
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (CustomerEduEnum enumration : CustomerEduEnum.values()) {  
			enumMap.put(enumration.getCode(), enumration.getName());  
        }  
	}
	
	CustomerEduEnum(int code,String name){
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * 获取学历的名称
	 *
	 * @author jixd
	 * @created 2016年5月15日 下午9:49:02
	 *
	 * @param eduCode
	 * @return
	 */
    public static CustomerEduEnum getCustomerEduByCode(Integer eduCode) {
        if (Check.NuNObj(eduCode)){
            return null;
        }
        for (final CustomerEduEnum statu : CustomerEduEnum.values()) {
            if (statu.getCode() == eduCode.intValue()) {
                return statu;
            }
        }
        return null;
    }
    
	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
}
