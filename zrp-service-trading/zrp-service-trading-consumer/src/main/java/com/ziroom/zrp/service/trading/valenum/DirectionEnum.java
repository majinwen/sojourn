package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;


/**
 * 朝向枚举
 * @author Administrator
 *
 */
public class DirectionEnum {

	public static final int  EAST= 1;//东
	public static final int SOUTH = 2;//南 
	public static final int WEST = 3;//西
	public static final int NORTH = 4;//北
	
	//此名固定，不可修改
	@SuppressWarnings("rawtypes")
	public LinkedHashMap enumMap;

	@SuppressWarnings("rawtypes")
	public LinkedHashMap getEnumMap() {
		return enumMap;
	}

	//jsp中按照类似"value=0 text=全部"的规则来操作
	@SuppressWarnings({ "rawtypes" })
	public DirectionEnum() {
		enumMap= new LinkedHashMap();
	}

	public static String getDirection(int value) {
		if (value == 1) {
			return "东";
		} else if (value == 2) {
			return "南";
		} else if (value == 3) {
			return "西";
		} else if (value == 4) {
			return "北";
		}
		return "";
	}
}
