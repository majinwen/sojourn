package com.zra.report.entity.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

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
public class ReportPaymentDto extends BaseReportRequest {

   /**
    * 业务id
    */
	@JsonIgnore
    private String reportPaymentId;

    /**
     * 项目id
     */
	@JsonIgnore
    private String projectId;

	/**
	 * 记录时间
	 */
	@JsonIgnore
    private String recordDate;

	/**
	 * 户型id
	 */
	@JsonIgnore
    private String houseTypeId;

	@ApiModelProperty("应回款单量")
	@JsonProperty("paymentCount")
    private Integer paymentCount;

	@ApiModelProperty("实际回款单量")
	@JsonProperty("voucherCount")
    private Integer voucherCount;

	@ApiModelProperty("未及时回款单量")
	@JsonProperty("delayVoucherCount")
    private Integer delayVoucherCount;

	@ApiModelProperty("总回款率单量")
	@JsonProperty("voucherRate")
    private String voucherRate;

	@ApiModelProperty("及时回款率单量")
	@JsonProperty("voucherIntimeRate")
    private String voucherIntimeRate;

	@ApiModelProperty("应回款总金额")
	@JsonProperty("paymentAmount")
    private String paymentAmount;

	@ApiModelProperty("实收金额")
	@JsonProperty("voucherAmount")
    private String voucherAmount;

	@ApiModelProperty("未及时回款金额")
	@JsonProperty("delayVoucherAmount")
    private String delayVoucherAmount;
	
	
    /**
     * 应回款单量(时间范围内) 不需要存到数据库中
     */
	@JsonIgnore
    private Integer paymentCountRange;
    
    /**
     * 应回款金额(时间范围内) 不需要存到数据库中
     */
	@JsonIgnore
    private BigDecimal paymentAmountRange;

    /**
     * 是否删除
     */
	@JsonIgnore
    private Integer isDel;

	/**
	 * 创建时间
	 */
	@JsonIgnore
    private String createTime;

	/**
	 * 删除时间
	 */
	@JsonIgnore
    private String deleteTime;

	/**
	 * 更新时间
	 */
	@JsonIgnore
    private String updateTime;

	/**
	 * 创建人
	 */
	@JsonIgnore
    private String createId;

	/**
	 * 修改人
	 */
	@JsonIgnore
    private String updateId;
	
	/**
	 * 删除人
	 */
	@JsonIgnore
    private String deleteId;

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

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public Integer getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public Integer getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(Integer voucherCount) {
        this.voucherCount = voucherCount;
    }

    public Integer getDelayVoucherCount() {
        return delayVoucherCount;
    }

    public void setDelayVoucherCount(Integer delayVoucherCount) {
        this.delayVoucherCount = delayVoucherCount;
    }

    public String getVoucherRate() {
    	return voucherRate;
    }

    public void setVoucherRate(String voucherRate) {
    	if (voucherRate != null) {
			this.voucherRate = voucherRate + "%";
		} else {
			this.voucherRate = voucherRate;
		}
    }

    public String getVoucherIntimeRate() {
    	return voucherIntimeRate;
    }

    public void setVoucherIntimeRate(String voucherIntimeRate) {
    	if (voucherIntimeRate != null) {
			this.voucherIntimeRate = voucherIntimeRate + "%";
		} else {
			this.voucherIntimeRate = voucherIntimeRate;
		}
    }

    public String getPaymentAmount() {
    	return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
    	this.paymentAmount = paymentAmount;
    }

    public String getVoucherAmount() {
    	return voucherAmount;
    }

    public void setVoucherAmount(String voucherAmount) {
    	this.voucherAmount = voucherAmount;
    }

    public String getDelayVoucherAmount() {
    	return delayVoucherAmount;
    }

    public void setDelayVoucherAmount(String delayVoucherAmount) {
    	this.delayVoucherAmount = delayVoucherAmount;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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
		return "ReportPaymentDto [reportPaymentId=" + reportPaymentId + ", projectId=" + projectId + ", recordDate="
				+ recordDate + ", houseTypeId=" + houseTypeId + ", paymentCount="
				+ paymentCount + ", voucherCount=" + voucherCount + ", delayVoucherCount=" + delayVoucherCount
				+ ", voucherRate=" + voucherRate + ", voucherIntimeRate=" + voucherIntimeRate + ", paymentAmount="
				+ paymentAmount + ", voucherAmount=" + voucherAmount + ", delayVoucherAmount=" + delayVoucherAmount
				+ ", isDel=" + isDel + ", createTime=" + createTime + ", deleteTime=" + deleteTime + ", updateTime="
				+ updateTime + ", createId=" + createId + ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}
}