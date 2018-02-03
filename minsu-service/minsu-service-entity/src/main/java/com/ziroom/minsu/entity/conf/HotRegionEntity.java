package com.ziroom.minsu.entity.conf;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>热门区域</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
public class HotRegionEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;

    // id 
    private Integer id;

    // 业务id 
    private String fid;

    // 国家code 
    private String nationCode;
    
    // 省份code 
    private String provinceCode;
    
    // 城市code 
    private String cityCode;
    
    // 区域code 
    private String areaCode;

	// 名称 
    private String regionName;

    // 热度 
    private Integer hot;
    
    // 半径
    private Integer radii;

    // 热门区域状态 
    private Integer regionStatus;

    // 类型 1：商圈 2 景点 
    private Integer regionType;

    // 经度 
    private Double longitude;

    // 纬度 
    private Double latitude;
    
    // 是否建立档案 0：否 1:是
    private Integer isFile;

    // 创建时间 
    private Date createDate;

    // 修改时间 
    private Date lastModifyDate;

    // 创建人 
    private String createFid;

    // 是否删除 0：未删除 1已经删除 
    private Integer isDel;

    // 经度 
    private Double googleLongitude;

    // 纬度 
    private Double googleLatitude;
    
    /**
	 * @return the googleLongitude
	 */
	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	/**
	 * @param googleLongitude the googleLongitude to set
	 */
	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	/**
	 * @return the googleLatitude
	 */
	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	/**
	 * @param googleLatitude the googleLatitude to set
	 */
	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
    
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getRegionStatus() {
        return regionStatus;
    }

    public void setRegionStatus(Integer regionStatus) {
        this.regionStatus = regionStatus;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getIsFile() {
		return isFile;
	}

	public void setIsFile(Integer isFile) {
		this.isFile = isFile;
	}

	public Integer getRadii() {
		return radii;
	}

	public void setRadii(Integer radii) {
		this.radii = radii;
	}

}
