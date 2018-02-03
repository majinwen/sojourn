package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>
 * 房源图片信息实体
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
public class HousePicMsgEntity extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3647836420622742597L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 房源房间信息表逻辑id
	private String roomFid;

	// 房源床位信息表逻辑id
	private String bedFid;

	// 操作类型(0：房东，1：业务人员)
	private Integer operateType;

	// 图片名称
	private String picName;
	
	// 图片类型
	private Integer picType;

	// 图片访问基础地址
	private String picBaseUrl;

	// 图片后缀
	private String picSuffix;

	// 图片服务器唯一标识
	private String picServerUuid;

	// 审核状态
	private Integer auditStatus;

	// 被替换图片逻辑id
	private String replaceFid;

	// 是否默认图片(0:否,1:是)
	private Integer isDefault;
	
	// 图片顺序
	private Integer picSort;
	
    // 宽度像素：属性值为正整数，单位是pixel
    private Integer widthPixel;

    // 高度像素：属性值为正整数，单位是pixel
    private Integer heightPixel;

    // 水平分辨率：属性值为正整数，单位是dpi
    private Integer widthDpi;

    // 垂直分辨率：属性值为正整数，单位是dpi
    private Integer heightDpi;

    // 图片大小：属性值为正整数，单位是kb
    private Double picSize;
	
	// 创建人id
	private String createFid;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 是否修改(0:否,1:是)
	private Integer isDel;

	//大图url
	private String maxPicUrl;

	//缩略图url
	private String minPicUrl;
	
	//审核信息
	private String picAuditMsg;

	/**
	 * @return the picAuditMsg
	 */
	public String getPicAuditMsg() {
		return picAuditMsg;
	}

	/**
	 * @param picAuditMsg the picAuditMsg to set
	 */
	public void setPicAuditMsg(String picAuditMsg) {
		this.picAuditMsg = picAuditMsg;
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

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid == null ? null : roomFid.trim();
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid == null ? null : bedFid.trim();
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName == null ? null : picName.trim();
	}
	
	public Integer getPicType() {
		return picType;
	}
	
	public void setPicType(Integer picType) {
		this.picType = picType;
	}

	public String getPicBaseUrl() {
		return picBaseUrl;
	}

	public void setPicBaseUrl(String picBaseUrl) {
		this.picBaseUrl = picBaseUrl == null ? null : picBaseUrl.trim();
	}

	public String getPicSuffix() {
		return picSuffix;
	}

	public void setPicSuffix(String picSuffix) {
		this.picSuffix = picSuffix == null ? null : picSuffix.trim();
	}

	public String getPicServerUuid() {
		return picServerUuid;
	}

	public void setPicServerUuid(String picServerUuid) {
		this.picServerUuid = picServerUuid == null ? null : picServerUuid
				.trim();
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getReplaceFid() {
		return replaceFid;
	}

	public void setReplaceFid(String replaceFid) {
		this.replaceFid = replaceFid == null ? null : replaceFid.trim();
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid == null ? null : createFid.trim();
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

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getPicSort() {
		return picSort;
	}

	public void setPicSort(Integer picSort) {
		this.picSort = picSort;
	}

	public Integer getWidthPixel() {
		return widthPixel;
	}

	public void setWidthPixel(Integer widthPixel) {
		this.widthPixel = widthPixel;
	}

	public Integer getHeightPixel() {
		return heightPixel;
	}

	public void setHeightPixel(Integer heightPixel) {
		this.heightPixel = heightPixel;
	}

	public Integer getWidthDpi() {
		return widthDpi;
	}

	public void setWidthDpi(Integer widthDpi) {
		this.widthDpi = widthDpi;
	}

	public Integer getHeightDpi() {
		return heightDpi;
	}

	public void setHeightDpi(Integer heightDpi) {
		this.heightDpi = heightDpi;
	}

	public Double getPicSize() {
		return picSize;
	}

	public void setPicSize(Double picSize) {
		this.picSize = picSize;
	}

	public String getMaxPicUrl() {
		return maxPicUrl;
	}

	public void setMaxPicUrl(String maxPicUrl) {
		this.maxPicUrl = maxPicUrl;
	}

	public String getMinPicUrl() {
		return minPicUrl;
	}

	public void setMinPicUrl(String minPicUrl) {
		this.minPicUrl = minPicUrl;
	}
}