package com.zra.common.dto.bedInfo;

import java.util.Date;

/**
 * 床位配置方案实体
 * @author tianxf9
 *
 */
public class BedStandardDto {
	
	//主键
	private Integer id;

	//业务bid
	private String standardBid;

	//配置方案编码
	private String code;

	//配置方案名字
	private String name;

	//是否删除 0：未删除。1：已删除
	private Byte isDeleted;

	//是否有效 0：有效。1：无效
	private Byte isValid;

	//是否启用 0：启用，1：禁用
	private Byte isEnabled;

	//创建者
	private String createrId;

	//创建时间
	private Date createdTime;

	//修改者
	private String updaterId;

	//更新时间
	private Date updatedTime;

	//删除者
	private String deleterId;

	//删除时间
	private Date deletedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStandardBid() {
		return standardBid;
	}

	public void setStandardBid(String standardBid) {
		this.standardBid = standardBid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public List<StandardItemDto> getItemList() {
//		return itemList;
//	}
//
//	public void setItemList(List<StandardItemDto> itemList) {
//		this.itemList = itemList;
//	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Byte getIsValid() {
		return isValid;
	}

	public void setIsValid(Byte isValid) {
		this.isValid = isValid;
	}

	public Byte getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Byte isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getDeleterId() {
		return deleterId;
	}

	public void setDeleterId(String deleterId) {
		this.deleterId = deleterId;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

}
