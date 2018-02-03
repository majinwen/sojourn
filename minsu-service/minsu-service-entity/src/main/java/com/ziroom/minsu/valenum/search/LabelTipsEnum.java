package com.ziroom.minsu.valenum.search;

import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesTonightDisEnum;

/** 
 * 提示标签
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum LabelTipsEnum {


    IS_WEEK_DISCOUNT(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), "周租%s折","按钮类型，返回文字，由客户端定义按钮式样"),
    IS_MONTH_DISCOUNT(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), "月租%s折","按钮类型，返回文字，由客户端定义按钮式样"),
    IS_TODAY_DISCOUNT(ProductRulesEnum020.ProductRulesEnum020001.getValue(), "今夜特价","文字类型，返回文字"),
    IS_JIAXIN_DISCOUNT1(ProductRulesEnum020.ProductRulesEnum020002.getValue(), "闪惠","文字类型，返回文字"),
    IS_JIAXIN_DISCOUNT2(ProductRulesEnum020.ProductRulesEnum020003.getValue(), "闪惠","文字类型，返回文字"),
    IS_NEW("isNew", "New","文字类型，返回文字"),
    IS_TOP50("IS_TOP50", "TOP50","文字类型，返回文字"),
    IS_LANDTOGETHER("IS_LANDTOGETHER", "与房东同住","文字类型，返回文字"),
    IS_JYTJ_DISCOUNT(ProductRulesTonightDisEnum.ProductRulesTonightDisEnum001.getCode(), "今夜特价","文字类型，返回文字"),
    
    /**
     * 自如驿房态标签
     */
    IS_ZRY_FULL("IS_ZRY_MANFAN", "满房", "按钮类型，返回文字，由客户端定义按钮式样"),
    IS_ZRY_NERVOUS("IS_ZRY_NERVOUS", "紧张", "按钮类型，返回文字，由客户端定义按钮式样"),
    IS_ZRY_BED_NOT_ENOUGH("IS_ZRY_NOT_ENOUGH", "床位不足", "按钮类型，返回文字，由客户端定义按钮式样");

    private final String code;

    private final String name;
    
    private final String remark;

	private LabelTipsEnum(String code, String name, String remark) {
		this.code = code;
		this.name = name;
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	} 

	public String getRemark() {
		return remark;
	}
    

}
