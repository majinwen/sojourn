/**
 * @FileName: LanguageEnum.java
 * @Package com.ziroom.minsu.spider.airbnb.entity.enums
 * 
 * @author zl
 * @created 2016年10月9日 下午9:36:43
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.entity.enums;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum LanguageEnum {
	

	English(1,"English"),
	Francais(2,"Français"),
	Deutsch(4,"Deutsch"),
	Italiano(16,"Italiano"),
	Russian(32,"Русский"),
	Spanish(64,"Español"),
	Hindi(512,"Hindi"),
	Portugues(1024,"Português"),
	Turkce(2048,"Türkçe"),
	Indonesia(4096,"Bahasa Indonesia"),
	Nederlands(8192,"Nederlands"),
	Malaysia(4194304,"Bahasa Malaysia"),
	Dansk(16777216,"Dansk"),
	Magyar(536870912,"Magyar"),
	Norsk(67108864,"Norsk"),
	Polski(2097152,"Polski"),
	Punjabi(131072,"Punjabi"),
	Signlanguage(524288,"Sign Language"),
	Suomi(134217728,"Suomi"),
	Svenska(33554432,"Svenska"),
	Tagalog(8388608,"Tagalog"),
	Czech(268435456,"Čeština"),
	Greek(262144,"Ελληνικά"),
	Ukrainian(1073741824,"українська"),
	Hebrew(1048576,"עברית"),
	Arabic(256,"العربية"),
	Thai(65536,"ภาษาไทย"),
	Chinese(128,"中文"),
	Japanese(8,"日本語"),
	Korean(16384,"한국어");
	
	LanguageEnum(Integer code,String name) {
		this.code = code;
		this.name = name; 
	}

	/** code */
	private Integer code;

	/** 名称 */
	private String name;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
    
}
