package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 收款单类型
 * @author tianxf9
 *
 */
public enum RentVoucherTypeEnum {
	
    CARD_VOUCHER(1, "租金卡生成"),
    COUPON_VOUCHER(2, "优惠券生成"),
    INPUT_VOCHER(3,"手工录入");
	
	
    private int index; // 数据库实际存此值
    private String value; // 描述
    
    /*枚举Map*/
    protected static final Map<Integer, String> enum2Map = new HashMap();
    static {
        for (RentVoucherTypeEnum sourceType : EnumSet.allOf(RentVoucherTypeEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    RentVoucherTypeEnum(int index, String value) {
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
