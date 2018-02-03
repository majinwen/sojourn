package com.ziroom.minsu.services.search.vo;

/**
 * 
 * <p>自如驿行政区映射</p>
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
public enum ZryAreaMappedEnum {
    DCQ("110101", "东城区", "110101", "东城区"),
    XCQ("110102", "西城区", "110102", "西城区"),
    CYQ("110105", "朝阳区", "110105", "朝阳区"),
    FTQ("110106", "丰台区", "110106", "丰台区"),
    SJSQ("110107", "石景山区", "110107", "石景山区"),
    HDQ("110108", "海淀区", "110108", "海淀区"),
    MTGQ("110109", "门头沟区", "110109", "门头沟区"),
    FSQ("110111", "房山区", "110111", "房山区"),
    TZQ("110112", "通州区", "110112", "通州区"),
    SYQ("110113", "顺义区", "110113", "顺义区"),
    CPQ("110114", "昌平区", "110114", "昌平区"),
    DXQ("110115", "大兴区", "110115", "大兴区"),
    HRQ("110116", "怀柔区", "110116", "怀柔区"),
    PGQ("110117", "平谷区", "110117", "平谷区"),
    CWQ("110103", "崇文区", "", ""),
    XWQ("110104", "宣武区", "", "");

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
	private ZryAreaMappedEnum(String zryCode, String zryName, String minsuCode, String minsuName) {
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
