package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡券类型
 * @author tianxf9
 *
 */
public enum CardCouponTypeEnum {
	
    CARD(1, "租金卡"),
    COUPON(2, "优惠券");
	
	
    private int index; // 数据库实际存此值
    private String value; // 描述
    
    /*枚举Map*/
    protected static final Map<Integer, String> enum2Map = new HashMap();
    static {
        for (CardCouponTypeEnum sourceType : EnumSet.allOf(CardCouponTypeEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    CardCouponTypeEnum(int index, String value) {
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
