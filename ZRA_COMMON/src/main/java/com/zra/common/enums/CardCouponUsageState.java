package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡券使用情况状态
 * 
 * @author tianxf9
 *
 */
public enum CardCouponUsageState {

	SUCCESS(1, "消费成功"), 
	RECOVERY_SUCCESS(2, "已回退"),
	RECOVERY_FAIL(2, "回退失败"),
	SAVE(4,"保存态"),
	CONSUM_FAIL(5,"消费失败");

	private int index;
	private String value;
    /*枚举Map*/
    protected static final Map<Integer, String> enum2Map = new HashMap();
    static {
        for (CardCouponUsageState sourceType : EnumSet.allOf(CardCouponUsageState.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
	CardCouponUsageState(int index, String value) {
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
