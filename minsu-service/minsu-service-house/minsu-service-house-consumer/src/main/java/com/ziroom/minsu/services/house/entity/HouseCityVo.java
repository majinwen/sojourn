package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>房源信息&城市信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class HouseCityVo extends HouseBaseMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6957990102386704679L;
    
	// 国家code
	private String nationCode;

	// 省code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 区code
	private String areaCode;

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
