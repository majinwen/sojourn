package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 收入记录表
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月20日
 * @since 1.0
 * @version 1.0
 */
public class FinanceIncomeEntity extends BaseEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8321922953984782424L;

	/** 主键id*/
	private Integer id;

	/** 收入单号*/
	private String incomeSn;

	/** 收入来源*/
	private Integer incomeSourceType;

	/** 收入类型*/
	private Integer incomeType;

	/** 订单编号*/
	private String orderSn;

	/** 城市code */
    private String cityCode;

	/** 付款人uid */
    private String payUid;

	/** 付款人类型 1：房东、2：租客*/
    private Integer payType;

	/** 收入金额 分*/
    private Integer totalFee;

	/** 收入状态*/
	private Integer incomeStatus;

	/** 产生费用时间*/
	private Date generateFeeTime;

	/** 实际收款时间*/
	private Date actualIncomeTime;

	/** 执行时间*/
	private Date runTime;

	/** 同步状态*/
    private Integer syncStatus;

	/** 同步时间*/
    private Date actualSyncTime;

	/** 创建人id*/
	private String createId;

	/** 创建时间*/
    private Date createTime;

    private Date lastModifyDate;

	/** 是否发送*/
    private Integer isSend;

	/** 是否删除 0：否，1：是*/
    private Integer isDel;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getIncomeSn() {
        return incomeSn;
    }

    public void setIncomeSn(String incomeSn) {
        this.incomeSn = incomeSn == null ? null : incomeSn.trim();
    }

    public Integer getIncomeSourceType() {
        return incomeSourceType;
    }

    public void setIncomeSourceType(Integer incomeSourceType) {
        this.incomeSourceType = incomeSourceType;
    }

	public Integer getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}

	public String getOrderSn() {
		return orderSn;
	}

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

	public String getPayUid() {
		return payUid;
	}

    public void setPayUid(String payUid) {
        this.payUid = payUid == null ? null : payUid.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getIncomeStatus() {
		return incomeStatus;
	}

	public void setIncomeStatus(Integer incomeStatus) {
		this.incomeStatus = incomeStatus;
	}

	public Date getGenerateFeeTime() {
		return generateFeeTime;
	}

	public void setGenerateFeeTime(Date generateFeeTime) {
		this.generateFeeTime = generateFeeTime;
	}

	public Date getActualIncomeTime() {
		return actualIncomeTime;
	}

	public void setActualIncomeTime(Date actualIncomeTime) {
		this.actualIncomeTime = actualIncomeTime;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getActualSyncTime() {
        return actualSyncTime;
    }

    public void setActualSyncTime(Date actualSyncTime) {
        this.actualSyncTime = actualSyncTime;
    }

	public String getCreateId() {
		return createId;
	}

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

}
