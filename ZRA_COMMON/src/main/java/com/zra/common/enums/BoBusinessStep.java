package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangws21 2016年8月4日
 * 商机阶段枚举
 */
public enum BoBusinessStep {
	
	/*1：待约看 2：待带看 3：待回访 4：成交 5：未成交*/
	D_YK((byte)1,"待约看"),D_DK((byte)2,"待带看"),D_HF((byte)3,"待回访"),Y_CJ((byte)4,"成交"),W_CJ((byte)5,"未成交");
	
	private Byte index;
	private String value;
	
	 /*枚举Map*/
	protected static final Map<Byte, String> enum2Map = new HashMap();
    static {
        for (BoBusinessStep sourceType : EnumSet.allOf(BoBusinessStep.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
	
	private BoBusinessStep(Byte index, String value){
		this.index=index;
		this.value=value;
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
