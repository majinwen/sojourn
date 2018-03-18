package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 户型标签类别
 * @author tianxf9
 *
 */
public enum HousetypeLabelTypeEnum {
	
	BASIC_LAB(1, "基本标签"),
	ACTIVITY_LAB(2,"活动标签"),
	CORE_LAB(3, "核心标签");
	
	private int index; // 数据库实际存此值
    private String value; // 描述
    
    HousetypeLabelTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /*枚举Map*/
    public static final Map<Integer, String> enum2Map = new HashMap<Integer, String>();
    static {
        for (HousetypeLabelTypeEnum sourceType : EnumSet.allOf(HousetypeLabelTypeEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
	

}
