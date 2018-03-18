package com.ziroom.zrp.service.trading.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>合同签约类型枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月18日 18:02
 * @since 1.0
 */
public enum ContractSignTypeEnum {
	NEW("0", "新签"),
	RENEW("1", "续约"),
	CHANGERENT("3", "换租");

	private String name;
	private String value;

	ContractSignTypeEnum(String value, String name) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static Map<String,String> getMap(){
		Map<String,String> map = new LinkedHashMap<>();
		for (ContractSignTypeEnum signTypeEnum : ContractSignTypeEnum.values()){
			map.put(signTypeEnum.getValue(),signTypeEnum.getName());
		}
		return map;
	}
}
