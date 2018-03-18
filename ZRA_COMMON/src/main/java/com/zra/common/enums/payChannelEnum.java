package com.zra.common.enums;

public enum payChannelEnum {
	ANDROID(0,"android","android"),
	IOS(1,"ios","ios");

	private int id;

	private String literal;

	private String desc;

	payChannelEnum(int id, String literal, String desc){
		this.id = id;
		this.literal = literal;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString(){
		return this.literal;
	}
	
}
