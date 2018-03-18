package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangws21 on 2016/8/4. 商机处理状态（处理进度）
 */
public enum BoHandState {
	/* 1：超时未处理 2：今日待办 3：次日待办 4：周内待办 5:7天后待办 6：完成' */
	TIMEOVER((byte) 1, "超时未处理"),
	TODAY_TODO((byte) 2, "今日待办"),
	TOMORROW_TODO((byte) 3, "次日待办"),
	WEEK_TODO((byte) 4,"周内待办"),
	QITIAN_TODO((byte) 5,"7天后待办"),
	DOWN((byte) 6, "已完成");

	private Byte index; // 数据库实际存此值
	private String value; // 描述
	
	/* 枚举Map */
	protected static final Map<Byte, String> enum2Map = new HashMap();
	static {
		for (BoHandState sourceType : EnumSet.allOf(BoHandState.class)) {
			enum2Map.put(sourceType.getIndex(), sourceType.getValue());
		}
	}
	
	private BoHandState(Byte index, String value) {
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
