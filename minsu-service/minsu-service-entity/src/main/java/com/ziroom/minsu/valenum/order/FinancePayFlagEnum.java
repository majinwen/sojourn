package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>调用财务查询付款单的付款状态枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月30日
 * @since 1.0
 * @version 1.0
 */
public enum FinancePayFlagEnum {

	NONE("0", "没有付款单"),
	UNDO("1", "未付款"),
	DO("2", "已付款"),
	EXCEPTION("3", "付款异常"),
	DOING("4", "付款中");

	FinancePayFlagEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 根据code获取name
	 * @author lishaochuan
	 * @create 2016年8月30日下午4:30:01
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		if(Check.NuNStr(code)){
			return null;
		}
		for (FinancePayFlagEnum flagEnum : FinancePayFlagEnum.values()) {
			if(flagEnum.getCode().equals(code)){
				return flagEnum.getName();
			}
		}
		return null;
	}

	/** 编码 */
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
