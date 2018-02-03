/**
 * @FileName: CityRulesEnum.java
 * @Package com.ziroom.minsu.valenum.city
 * 
 * @author afi
 * @created 2016年5月14日 下午3:27:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.city;

/**
 * <p>城市属性规则枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public enum CityRulesEnum {

    CityRulesEnum001("CityRulesEnum001","商圈/景点半径"),
    
    CityRulesEnum002("CityRulesEnum002","商圈/景点分类");


	private final String value;

    private final String name;

    CityRulesEnum(String value, String name) {
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
