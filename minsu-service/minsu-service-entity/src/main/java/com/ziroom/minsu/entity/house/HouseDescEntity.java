package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源描述信息实体
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
public class HouseDescEntity extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2787265772846193137L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 创建日期
	private Date createDate;

	// 最后修改日期
	private Date lastModifyDate;

	// 创建人uid
	private String createUid;

	// 是否修改(0:否,1:是)
	private Integer isDel;

	// 房源描述
	private String houseDesc;

	// 周边状况
	private String houseAroundDesc;
	
	// 房屋守则
	private String houseRules;
	
	// 补充信息
	private String addtionalInfo;

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

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc == null ? null : houseDesc.trim();
	}

	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}

	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}

	public String getHouseRules() {
		return houseRules;
	}

	public void setHouseRules(String houseRules) {
		this.houseRules = houseRules;
	}

	public String getAddtionalInfo() {
		return addtionalInfo;
	}

	public void setAddtionalInfo(String addtionalInfo) {
		this.addtionalInfo = addtionalInfo;
	}
}