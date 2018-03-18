package com.zra.report.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.zra.common.constant.HouseTypeConstant;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class ReportPaymentEntity {

	/**
	 * 主键id
	 */
    private Integer id;

    /**
     * 业务id
     */
    private String reportPaymentId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 记录时间
     */
    private Date recordDate;

    /**
     * 户型id
     */
    private String houseTypeId;

    /**
     * 应回款单量（本月内）
     */
    private Integer paymentCount;

    /**
     * 实际回款单量
     */
    private Integer voucherCount;
    
    /**
     * 及时回款单量
     */
    private Integer intimeVoucherCount;

    /**
     * 未及时回款单量
     */
    private Integer delayVoucherCount;

    /**
     * 总回款率单量
     */
    private BigDecimal voucherRate;

    /**
     * 及时回款率单量
     */
    private BigDecimal voucherIntimeRate;

    /**
     * 应回款总金额
     */
    private BigDecimal paymentAmount;

    /**
     * 实收金额
     */
    private BigDecimal voucherAmount;
    
    /**
     * 及时回款金额
     */
    private BigDecimal intimeVoucherAmount;

    /**
     * 未及时回款金额
     */
    private BigDecimal delayVoucherAmount;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 修改人id
     */
    private String updateId;

    /**
     * 删除人id
     */
    private String deleteId;
    
    /**
     * 应回款单量(月初截止到今天应) 不需要存到数据库中
     */
    private Integer paymentCountRange;
    
    /**
     * 应回款金额(月初截止到今天应) 不需要存到数据库中
     */
    private BigDecimal paymentAmountRange;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportPaymentId() {
        return reportPaymentId;
    }

    public void setReportPaymentId(String reportPaymentId) {
        this.reportPaymentId = reportPaymentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
    	if (houseTypeId == null) {
    		this.houseTypeId = HouseTypeConstant.NULL_TYPE;
    	} else if (houseTypeId.isEmpty()) {
    		this.houseTypeId = HouseTypeConstant.EMPTY_TYPE;
    	} else {
    		this.houseTypeId = houseTypeId;
    	}
    }

    public Integer getPaymentCount() {
    	if (paymentCount == null) {
    		return 0;
    	} else {
    		return paymentCount;
    	}
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public Integer getVoucherCount() {
    	if (voucherCount == null) {
    		return 0;
    	}
    	else {
    		return voucherCount;
    	}
    }

    public void setVoucherCount(Integer voucherCount) {
        this.voucherCount = voucherCount;
    }

    public Integer getDelayVoucherCount() {
    	if (delayVoucherCount == null) {
    		return 0;
    	} else {
    		 return delayVoucherCount;
        }
    }
       
    public void setDelayVoucherCount(Integer delayVoucherCount) {
        this.delayVoucherCount = delayVoucherCount;
    }

    public BigDecimal getVoucherRate() {
        if (voucherRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return voucherRate;
    	}
    }

    public void setVoucherRate(BigDecimal voucherRate) {
    	this.voucherRate = voucherRate;
    }

    public BigDecimal getVoucherIntimeRate() {
        if (voucherIntimeRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return voucherIntimeRate;
    	}
    }

    public void setVoucherIntimeRate(BigDecimal voucherIntimeRate) {
    	this.voucherIntimeRate = voucherIntimeRate;
    }

    public BigDecimal getPaymentAmount() {
        if (paymentAmount == null) {
    		return new BigDecimal(0);
    	} else {
    		return paymentAmount;
    	}
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
    	this.paymentAmount = paymentAmount;
    }

    public BigDecimal getVoucherAmount() {
        if (voucherAmount == null) {
    		return new BigDecimal(0);
    	} else {
    		return voucherAmount;
    	}
    }

    public void setVoucherAmount(BigDecimal voucherAmount) {
    	this.voucherAmount = voucherAmount;
    }

    public BigDecimal getDelayVoucherAmount() {
        if (delayVoucherAmount == null) {
    		return new BigDecimal(0);
    	} else {
    		return delayVoucherAmount;
    	}
    }

    public void setDelayVoucherAmount(BigDecimal delayVoucherAmount) {
    	this.delayVoucherAmount = delayVoucherAmount;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

	public Integer getIntimeVoucherCount() {
		if (intimeVoucherCount == null) {
    		return 0;
    	} else {
    		 return intimeVoucherCount;
        }
	}

	public void setIntimeVoucherCount(Integer intimeVoucherCount) {
		this.intimeVoucherCount = intimeVoucherCount;
	}

	public BigDecimal getIntimeVoucherAmount() {
		if (intimeVoucherAmount == null) {
    		return new BigDecimal(0);
    	} else {
    		return intimeVoucherAmount;
    	}
	}

	public void setIntimeVoucherAmount(BigDecimal intimeVoucherAmount) {
		this.intimeVoucherAmount = intimeVoucherAmount;
	}
	
	

	public Integer getPaymentCountRange() {
		if(paymentCountRange==null) {
			return new Integer(0);
		}else {
			return paymentCountRange;
		}
	}

	public void setPaymentCountRange(Integer paymentCountRange) {
		this.paymentCountRange = paymentCountRange;
	}

	public BigDecimal getPaymentAmountRange() {
		if(paymentAmountRange==null) {
			return new BigDecimal(0);
		}else {
			return paymentAmountRange;
		}
	}

	public void setPaymentAmountRange(BigDecimal paymentAmountRange) {
		this.paymentAmountRange = paymentAmountRange;
	}

	@Override
	public String toString() {
		return "ReportPaymentEntity [id=" + id + ", reportPaymentId=" + reportPaymentId + ", projectId=" + projectId
				+ ", recordDate=" + recordDate + ", houseTypeId=" + houseTypeId + ", paymentCount=" + paymentCount
				+ ", voucherCount=" + voucherCount + ", intimeVoucherCount=" + intimeVoucherCount
				+ ", delayVoucherCount=" + delayVoucherCount + ", voucherRate=" + voucherRate + ", voucherIntimeRate="
				+ voucherIntimeRate + ", paymentAmount=" + paymentAmount + ", voucherAmount=" + voucherAmount
				+ ", intimeVoucherAmount=" + intimeVoucherAmount + ", delayVoucherAmount=" + delayVoucherAmount
				+ ", isDel=" + isDel + ", createTime=" + createTime + ", deleteTime=" + deleteTime + ", updateTime="
				+ updateTime + ", createId=" + createId + ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}
}