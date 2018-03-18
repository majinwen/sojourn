package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PC on 2016/8/31.
 */

/**
 * 枚举类显示的是出租方式：按照床位或者是按照房间--xiaona--2016-8-31 18:12:44
 */
public enum RentTypeEunm {
    /* 1：按房间 2：按床位  */
    ROOMTYPE((byte) 1, "按房间"),
    BEDTYPE((byte) 2, "按床位");

    private Byte index; // 数据库实际存此值
    private String value; // 描述
    
    /*枚举Map*/
    protected static final Map<Byte, String> enum2Map = new HashMap();
    static {
        for (RentTypeEunm sourceType : EnumSet.allOf(RentTypeEunm.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    private RentTypeEunm(Byte index, String value) {
        this.index = index;
        this.value = value;
    }

    public Byte getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

	public static Map<Byte, String> getEnum2map() {
		return enum2Map;
	}
}
