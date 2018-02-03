/**
 * @FileName: HouseRankEnum.java
 * @Package com.ziroom.minsu.valenum.productrules
 * 
 * @author zl
 * @created 2016年11月8日 下午4:51:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.productrules;

/**
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
public enum HouseRankEnum {
	HouseRankEnum001("HouseRankEnum001","咨询回复率平均值"),
	HouseRankEnum002("HouseRankEnum002","房客评价率平均值"),
	HouseRankEnum003("HouseRankEnum003","房东评价率平均值"),
	HouseRankEnum004("HouseRankEnum004","订单接受率平均值")
    ;

	
	private final String value;

    private final String name;

    HouseRankEnum(String value,String name) {
        this.value = value;
        this.name = name;
    }

	public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
