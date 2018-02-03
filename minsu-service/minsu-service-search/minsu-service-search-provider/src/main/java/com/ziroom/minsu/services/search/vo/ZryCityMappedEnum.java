package com.ziroom.minsu.services.search.vo;

/**
 * 
 * <p>自如驿城市映射</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public enum ZryCityMappedEnum {
    BJ("110000", "北京", "110100", "北京"),
    SH("310000", "上海", "310100", "上海");

    private String zryCode;
    private String zryName;
    private String minsuCode;
    private String minsuName;
	/**
	 * @param zryCode
	 * @param zryName
	 * @param minsuCode
	 * @param minsuName
	 */
	private ZryCityMappedEnum(String zryCode, String zryName, String minsuCode, String minsuName) {
		this.zryCode = zryCode;
		this.zryName = zryName;
		this.minsuCode = minsuCode;
		this.minsuName = minsuName;
	}
	/**
	 * @return the zryCode
	 */
	public String getZryCode() {
		return zryCode;
	}
	/**
	 * @param zryCode the zryCode to set
	 */
	public void setZryCode(String zryCode) {
		this.zryCode = zryCode;
	}
	/**
	 * @return the zryName
	 */
	public String getZryName() {
		return zryName;
	}
	/**
	 * @param zryName the zryName to set
	 */
	public void setZryName(String zryName) {
		this.zryName = zryName;
	}
	/**
	 * @return the minsuCode
	 */
	public String getMinsuCode() {
		return minsuCode;
	}
	/**
	 * @param minsuCode the minsuCode to set
	 */
	public void setMinsuCode(String minsuCode) {
		this.minsuCode = minsuCode;
	}
	/**
	 * @return the minsuName
	 */
	public String getMinsuName() {
		return minsuName;
	}
	/**
	 * @param minsuName the minsuName to set
	 */
	public void setMinsuName(String minsuName) {
		this.minsuName = minsuName;
	}

    
}
