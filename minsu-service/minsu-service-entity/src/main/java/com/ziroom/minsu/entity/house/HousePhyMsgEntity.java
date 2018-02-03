package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源物理信息实体
 * </p>
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
public class HousePhyMsgEntity extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -9179482218787411920L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 国家code
	private String nationCode;

	// 省code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 区code
	private String areaCode;

	// 楼盘code
	private String buildingCode;

	// 小区名称
	private String communityName;

	// 管家工号
	private String zoJobNum;

	// 管家姓名
	private String zoName;

	// 管家手机
	private String zoMobile;

	// 经度
	private Double longitude;

	// 维度
	private Double latitude;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 创建人uid
	private String createUid;

	// 是否修改(0:否,1:是)
	private Integer isDel;

	// 经度
	private Double googleLongitude;

	// 维度
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
		this.fid = fid == null ? null : fid.trim();
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode == null ? null : nationCode.trim();
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode == null ? null : provinceCode.trim();
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode == null ? null : buildingCode.trim();
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName == null ? null : communityName
				.trim();
	}

	public String getZoJobNum() {
		return zoJobNum;
	}

	public void setZoJobNum(String zoJobNum) {
		this.zoJobNum = zoJobNum;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public String getZoMobile() {
		return zoMobile;
	}

	public void setZoMobile(String zoMobile) {
		this.zoMobile = zoMobile;
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

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid == null ? null : createUid.trim();
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}