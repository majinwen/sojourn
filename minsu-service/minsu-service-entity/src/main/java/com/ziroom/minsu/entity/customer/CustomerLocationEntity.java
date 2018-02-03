package com.ziroom.minsu.entity.customer;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>用户的位置信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/29.
 * @version 1.0
 * @since 1.0
 */
public class CustomerLocationEntity   extends BaseEntity{


    private static final long serialVersionUID = 3987897001446703L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * uid
     */
    private String uid;
    
    /**
     * 客户端app名
     */
    private String appName;
    
    /**
     * 客户端渠道名称
     */
    private String channelName;
    
    /**
     * 手机系统版本号
     */
    private String osVersion;
    
    /**
     * imei号
     */
    private String imei;

    /**
     * imsi
     */
    private String imsi;


    /**
     * 手机型号
     */
    private String phoneModel;

    /**
     * 设备号deviceId
     */
    private String deviceNo;

    /**
     * ip
     */
    private Long deviceIp;

    /**
     * 版本号
     */
    private String versionCode;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 城市code
     */
    private String cityCode;


    /**
     * 位置收集入口
     * @see com.ziroom.minsu.valenum.customer.LocationTypeEnum
     */
    private Integer locationType;


    /**
     * 城市name
     */
    private String cityName;

    /**
     * 操作时间
     */
    private Date createDate;

    /**
     * 同步状态
     * 0:未同步 1:异常ip 2:同步成功
     */
    private Integer synStatus;
    
    /**
     * 编号：如果location_type是订单则值是订单编号；是IM聊天、收藏、分享、浏览则是房源编号
     */
    private String serialNumber;
    
    /**
     * 经度
     */
    private Double googleLongitude;

    /**
     * 纬度
     */
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

	/**
     * 出租方式 0：整租，1：合租 (serial_number为房源编号时不为空)
     * @see com.ziroom.minsu.valenum.house.RentWayEnum
     */
    private Integer rentWay;

    public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public Integer getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Integer synStatus) {
        this.synStatus = synStatus;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
    
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }


    public Long getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(Long deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
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


}
