package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>价格调幅策略类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月14日 11:11
 * @version 1.0
 * @since 1.0
 */
public class RentPriceStrategyEntity extends BaseEntity {
	private Integer id;

	/**
	 * 项目id
	 */
	private String projectId;

	/**
	 * 出租方式：1按房间2按床位
	 */
	private Integer rentType;

	/**
	 * 长租房价上调比例
	 */
	private Double longPriceRate;

	/**
	 * 短租房价上调比例(1-3个月，包括3个月)
	 */
	private Double shortPriceRate;

	/**
	 * 短租房价上调比例(3-6个月，不包括3个月)
	 */
	private Double shortPrice2Rate;

	/**
	 * 是否删除 0：否  1：是
	 */
	private Integer isDeleted;

	/**
	 * 删除人
	 */
	private String deleterId;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 创建人
	 */
	private String createrId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 更新人
	 */
	private String updaterId;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 长租服务费上调比例
	 */
	private Double longSPriceRate;

	/**
	 * 短租服务费上调比例(1-3个月，包括3个月)
	 */
	private Double shortSPriceRate;

	/**
	 * 短租服务费上调比例(3-6个月，不包括3个月)
	 */
	private Double shortSPrice2Rate;

	/**
	 * 短租服务费上调比例(小于15天)
	 */
	private Double shortSPrice3Rate;

	/**
	 * 短租房租上调比例(小于15天)
	 */
	private Double shortPrice3Rate;

	/**
	 * 城市标识
	 */
	private String city;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public Integer getRentType() {
		return rentType;
	}

	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public Double getLongPriceRate() {
		return longPriceRate;
	}

	public void setLongPriceRate(Double longPriceRate) {
		this.longPriceRate = longPriceRate;
	}

	public Double getShortPriceRate() {
		return shortPriceRate;
	}

	public void setShortPriceRate(Double shortPriceRate) {
		this.shortPriceRate = shortPriceRate;
	}

	public Double getShortPrice2Rate() {
		return shortPrice2Rate;
	}

	public void setShortPrice2Rate(Double shortPrice2Rate) {
		this.shortPrice2Rate = shortPrice2Rate;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDeleterId() {
		return deleterId;
	}

	public void setDeleterId(String deleterId) {
		this.deleterId = deleterId == null ? null : deleterId.trim();
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId == null ? null : createrId.trim();
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
		this.updaterId = updaterId == null ? null : updaterId.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getLongSPriceRate() {
		return longSPriceRate;
	}

	public void setLongSPriceRate(Double longSPriceRate) {
		this.longSPriceRate = longSPriceRate;
	}

	public Double getShortSPriceRate() {
		return shortSPriceRate;
	}

	public void setShortSPriceRate(Double shortSPriceRate) {
		this.shortSPriceRate = shortSPriceRate;
	}

	public Double getShortSPrice2Rate() {
		return shortSPrice2Rate;
	}

	public void setShortSPrice2Rate(Double shortSPrice2Rate) {
		this.shortSPrice2Rate = shortSPrice2Rate;
	}

	public Double getShortSPrice3Rate() {
		return shortSPrice3Rate;
	}

	public void setShortSPrice3Rate(Double shortSPrice3Rate) {
		this.shortSPrice3Rate = shortSPrice3Rate;
	}

	public Double getShortPrice3Rate() {
		return shortPrice3Rate;
	}

	public void setShortPrice3Rate(Double shortPrice3Rate) {
		this.shortPrice3Rate = shortPrice3Rate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}
}
