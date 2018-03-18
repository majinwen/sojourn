package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费记录来源
 * @author tianxf9
 *
 */
public enum CardCouponComeSourceEnum {
	
    APP(1, "app"),
    RENTSIGN(2, "合同首次签约");
	
	
    private int index; // 数据库实际存此值
    private String value; // 描述
    
    /*枚举Map*/
    protected static final Map<Integer, String> enum2Map = new HashMap();
    static {
        for (CardCouponComeSourceEnum sourceType : EnumSet.allOf(CardCouponComeSourceEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    CardCouponComeSourceEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }
    
	public int getIndex() {
		return index;
	}
	public String getValue() {
		return value;
	}

}
