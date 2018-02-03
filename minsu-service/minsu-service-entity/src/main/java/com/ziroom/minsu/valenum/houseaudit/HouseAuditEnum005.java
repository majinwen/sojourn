/**
 * @FileName: HouseAuditEnum.java
 * @Package com.ziroom.minsu.services.basedata.valenum
 * 
 * @author bushujie
 * @created 2016年3月26日 下午6:56:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.houseaudit;

/**
 * <p>房源跟进时间点</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum HouseAuditEnum005 {
	HouseAuditEnum001("HouseAuditEnum005001","客服跟进起始点"),
	HouseAuditEnum002("HouseAuditEnum005002","运营跟进起始点"),
	HouseAuditEnum003("HouseAuditEnum005003","房源周边属性是否可编辑");
	
	private final String value;

    private final String name;
	HouseAuditEnum005(String value,String name) {
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
