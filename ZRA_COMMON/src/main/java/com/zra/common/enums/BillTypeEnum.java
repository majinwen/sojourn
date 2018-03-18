package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PC on 2016/8/31.
 */

/**
 * 枚举类显示的是账单类型：0-合同计划收款；1-其它收款
 */
public enum BillTypeEnum {
    /* 0：合同计划收款 1：其它收款  */
    CONTRACT_PLAN_COST(0, "合同计划收款"),
    OTHER_COST(1, "其它收款"),
    INTELLECT_WATT_COST(2, "智能电费,客户充值");

    private int index; // 数据库实际存此值
    private String value; // 描述
    
    /*枚举Map*/
    protected static final Map<Integer, String> enum2Map = new HashMap();
    static {
        for (BillTypeEnum sourceType : EnumSet.allOf(BillTypeEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    BillTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

	public static Map<Integer, String> getEnum2map() {
		return enum2Map;
	}
}
