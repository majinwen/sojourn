package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>合同活动表</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月17日 16:41
 * @since 1.0
 */
public class RentContractActivityEntity extends BaseEntity {
	private Integer id;

	/**
	 * 合同id
	 */
	private String contractId;

	/**
	 * 活动id
	 */
	private Integer activityId;

	/**
	 * 折扣金额
	 */
	private Double discountAccount;

	/**
	 * 是否删除；0：未删除；1：删除
	 */
	private Integer isDeleted;

	/**
	 * 删除者
	 */
	private String deleterId;

	/**
	 * 删除时间
	 */
	private Date deletedTime;

	/**
	 * 创建者
	 */
	private String createrId;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 更新者
	 */
	private String updaterId;

	/**
	 * 更新时间
	 */
	private Date updatedTime;

	/**
	 * 类别 1免   2减固定金额 3减手动填写金额  4折扣优惠  默认是3  暂时只用1 3 其他代码要适配
	 */
	private Integer type;

	/**
	 * 费用项code  优惠的费用项 常用:服务费 C02   押金 C03
	 */
	private String expenseItemCode;

	/**
	 * 优惠折扣	  type 4时有效
	 */
	private Double discount;

	/**
	 * 活动编码
	 */
	private String activityNumber;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 种类 1-优惠活动 2-付款方式折扣 3-续约折扣
	 */
	private Integer category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId == null ? null : contractId.trim();
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Double getDiscountAccount() {
		return discountAccount;
	}

	public void setDiscountAccount(Double discountAccount) {
		this.discountAccount = discountAccount;
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

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getExpenseItemCode() {
		return expenseItemCode;
	}

	public void setExpenseItemCode(String expenseItemCode) {
		this.expenseItemCode = expenseItemCode == null ? null : expenseItemCode.trim();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber == null ? null : activityNumber.trim();
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName == null ? null : activityName.trim();
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
}
