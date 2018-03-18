package com.zra.common.dto.bedInfo;
/**
 * 
 * @author tianxf9
 *
 */
public class QueryItemDto {
	
	private String type; // 物品分类 from by ItemTypeEnum(物品分类枚举)
	private String name; // 物品名称
	private String code; // 物品编码


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
