package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
/**
 * 渠道类型
 * @author tianxf9
 *
 */
public enum MkChannelTypeEunm {
	
	ONLINE((byte)1,"线上渠道"),
	LINE((byte)2,"线下渠道");
	
	private Byte index;
	private String value;

	/*枚举Map*/
	protected static final Map<Byte, String> enum2Map = new HashMap();
    static {
        for (MkChannelTypeEunm channelType : EnumSet.allOf(MkChannelTypeEunm.class)) {
            enum2Map.put(channelType.getIndex(), channelType.getValue());
        }
    }
	
	private MkChannelTypeEunm(Byte index,String value) {
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
