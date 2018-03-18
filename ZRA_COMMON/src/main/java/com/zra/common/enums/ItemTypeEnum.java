package com.zra.common.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 2016/8/31.
 */

/**
 * @author tianxf9
 * 品类
 */
public enum ItemTypeEnum {
 
	FURNITURE((byte) 0, "家具"),
	HOUSE((byte) 1, "家居"),
	BIGJD((byte) 2, "家电"),
	LITTLEJD((byte) 3, "工程"),
	ITEM_NET((byte) 4, "网络"),
    ITEM_LOCK((byte) 5, "锁具"),
    PUBLIC_JD((byte) 6, "公区家电"),
    PUBLIC_JJ((byte) 7, "公区家具"),
    PUBLIC_HOUSE((byte) 8, "公区家居"),
    PUBLIC_PROJECT((byte) 9, "公区工程"),
    PUBLIC_NET((byte) 10, "公区网络"),
    PUBLIC_LOCK((byte) 11, "公区锁具");

    private Byte index; // 数据库实际存此值
    private String value; // 描述
    
    //枚举Map
    protected static final Map<Byte, String> enum2Map = new HashMap();
    static {
        for (ItemTypeEnum sourceType : EnumSet.allOf(ItemTypeEnum.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    protected static final List<String> enum2List = new ArrayList();
    static {
        for (ItemTypeEnum sourceType : EnumSet.allOf(ItemTypeEnum.class)) {
        	enum2List.add("'"+sourceType.getValue()+"'");
        }
    }
    
    private ItemTypeEnum(Byte index, String value) {
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

	public static List<String> getEnum2list() {
		return enum2List;
	}
      
}
