package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>热门区域的试图</p>
 * <p/>
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
public class HotRegionVo extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;

    // id
    private Integer id;

    
    // 城市code 
    private String cityCode;

	// 名称 
    private String regionName;

    // 类型 1：商圈 2 景点 
    private Integer regionType;

    // 经度 
    private String longitude;

    // 纬度 
    private String latitude;

    // 热度
    private Integer hot;

    // lineFid
    private String lineFid;
    
    // 半径
    private Integer radii;
    
    public Integer getRadii() {
		return radii;
	}

	public void setRadii(Integer radii) {
		this.radii = radii;
	}

	public String getLineFid() {
        return lineFid;
    }

    public void setLineFid(String lineFid) {
        this.lineFid = lineFid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}
