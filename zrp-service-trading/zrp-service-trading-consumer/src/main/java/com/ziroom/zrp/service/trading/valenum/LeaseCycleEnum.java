package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>租赁周期枚举类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月12日 17:51
 * @version 1.0
 * @since 1.0
 */
public enum LeaseCycleEnum {
	YEAR("1","年租"),
	MONTH("2","月租"),
	DAY("3","日租");

	private String code;
	private String name;

	LeaseCycleEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}


	public static LeaseCycleEnum getByCode(String code){
		for(final LeaseCycleEnum leaseCycleEnum : LeaseCycleEnum.values()){
			if(leaseCycleEnum.getCode().equals(code)){
				return leaseCycleEnum;
			}
		}
		return null;
	}

	public static Map<String,String> getMap(String code){
		Map<String,String> map = new LinkedHashMap<>();
		for (LeaseCycleEnum leaseCycleEnum : LeaseCycleEnum.values()){
			if (code.equals(LeaseCycleEnum.YEAR.getCode()) && code.equals(leaseCycleEnum.getCode())){
					map.put(leaseCycleEnum.getCode(),leaseCycleEnum.getName());
			}
			if (code.equals(LeaseCycleEnum.MONTH.getCode()) && !leaseCycleEnum.getCode().equals(LeaseCycleEnum.DAY.getCode())){
				map.put(leaseCycleEnum.getCode(),leaseCycleEnum.getName());
			}
			if (code.equals(LeaseCycleEnum.DAY.getCode())){
				map.put(leaseCycleEnum.getCode(),leaseCycleEnum.getName());
			}

		}
		return map;
	}

	public static Map<String ,String> toMap(){
		Map<String,String> map = new LinkedHashMap<>();
		LeaseCycleEnum[] values = LeaseCycleEnum.values();
		for (int i=0;i<values.length;i++){
			map.put(values[i].getCode(),values[i].getName());
		}
		return map;
	}


}
