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
 * <p>房源审核规则配置</p>
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
public enum HouseAuditEnum {
	 //HouseAuditEnum001:房源是否需要审核,HouseAuditEnum002:房源审核时限,HouseAuditEnum003:房源周边属性是否可编辑,HouseAuditEnum004:评价是否需要审核
	HouseAuditEnum001("HouseAuditEnum001","房源是否需要审核"),
	HouseAuditEnum002("HouseAuditEnum002","房源审核时限"),
	HouseAuditEnum003("HouseAuditEnum003","房源周边属性是否可编辑"),
	HouseAuditEnum004("HouseAuditEnum004","评价是否需要审核"),
	HouseAuditEnum005("HouseAuditEnum005","未审核通过房源跟进规则");
	
	private final String value;

    private final String name;
	HouseAuditEnum(String value,String name) {
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
