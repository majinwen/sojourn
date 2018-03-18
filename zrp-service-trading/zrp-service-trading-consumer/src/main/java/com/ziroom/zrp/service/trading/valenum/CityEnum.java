package com.ziroom.zrp.service.trading.valenum;
/**
 * <p>城市ID枚举类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月13日
 * @since 1.0
 */
public enum CityEnum {
	BEIJING(110000, "北京"),
	SHANGHAI(310000, "上海");
	
	private int cityId;
	private String cityName;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	private CityEnum(int cityId, String cityName) {
		this.cityId = cityId;
		this.cityName = cityName;
	}
}
