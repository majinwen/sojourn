package com.ziroom.minsu.services.basedata.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>热门区域查询参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HotRegionRequest extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3480224001709714521L;

	// 国家code 
    private String nationCode;
    
    // 省份code 
    private String provinceCode;
    
    // 城市code 
    private String cityCode;
    
    // 区域code 
    private String areaCode;
    
    // 类型 1：商圈 2 景点 
    private Integer regionType;
    
    // 名称 
    private String regionName;
    
    // 创建人 
    private String createFid;
    
    // 经纬度 
    private String location;
    
    // 半径
    private Integer radii;

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

	public Integer getRegionType() {
		return regionType;
	}

	public void setRegionType(Integer regionType) {
		this.regionType = regionType;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getRadii() {
		return radii;
	}

	public void setRadii(Integer radii) {
		this.radii = radii;
	}
    
}
