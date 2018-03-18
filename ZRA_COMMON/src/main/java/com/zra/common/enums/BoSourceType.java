package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangws21 on 2016/8/3.
 * 商机来源渠道枚举
 */
public enum BoSourceType {
    /*400来电，在线预约，云销，其他*/
    TEL400((byte)1,"400来电"),ONLINE((byte)2,"在线预约"),CLOUDSALE((byte)3,"云销"),OTHER((byte)4,"其他");

    private Byte index;  //数据库实际存此值
    private String value;   //描述
    /*枚举Map*/
    protected static final Map<Byte, String> enum2Map = new HashMap();
    static {
        for (BoSourceType sourceType : EnumSet.allOf(BoSourceType.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    private BoSourceType(Byte index, String value){
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
