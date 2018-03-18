package com.zra.report.entity.dto;

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
public class ReportStockDto extends BaseReportRequest {

	/**
	 * 业务id
	 */
	@JsonIgnore
    private String reportStockId;

	/**
	 * 项目id
	 */
	@JsonIgnore
    private String projectId;

	/**
	 * 户型id
	 */
	@JsonIgnore
    private String houseTypeId;

    /**
     * 记录时间
     */
	@JsonIgnore
    private String recordDate;

    @ApiModelProperty("库存总数")
   	@JsonProperty("stockCount")
    private Integer stockCount;
    
    /**
     * 可出租数
     */
    @JsonIgnore
    private Integer rentableCount;

    @ApiModelProperty("已出租数")
   	@JsonProperty("leasedCount")
    private Integer leasedCount;

    @ApiModelProperty("出租率")
   	@JsonProperty("occupancyRate")
    private String occupancyRate;
    
    /**
     * 短租出租数
     */
    @JsonIgnore
    private Integer shortLeasedCount;

    @ApiModelProperty("短租占比")
   	@JsonProperty("shortLeasedRate")
    private String shortLeasedRate;
    
    @ApiModelProperty("剩余库存---总量")
   	@JsonProperty("leaveStockTotal")
    private Integer leaveStockTotal;

    @ApiModelProperty("剩余库存---配置中")
   	@JsonProperty("leaveStockConfig")
    private Integer leaveStockConfig;

    @ApiModelProperty("剩余库存---待租中")
   	@JsonProperty("leaveStockWait")
    private Integer leaveStockWait;

    @ApiModelProperty("剩余库存---其他")
   	@JsonProperty("leaveStockOther")
    private Integer leaveStockOther;
    
    @ApiModelProperty("退租量---总量")
   	@JsonProperty("quitTotal")
    private Integer quitTotal;

    @ApiModelProperty("退租量---正退")
   	@JsonProperty("quitNormal")
    private Integer quitNormal;

    @ApiModelProperty("退租量---非正退")
   	@JsonProperty("quitUnnormal")
    private Integer quitUnnormal;
    
    @ApiModelProperty("退租量---单解")
   	@JsonProperty("quitCustomer")
    private Integer quitCustomer;

    @ApiModelProperty("退租量---3天不满意")
   	@JsonProperty("quitUnsatisfied")
    private Integer quitUnsatisfied;

    @ApiModelProperty("退租量---换租")
   	@JsonProperty("quitChange")
    private Integer quitChange;

    @ApiModelProperty("退租量---转租")
   	@JsonProperty("quitExchange")
    private Integer quitExchange;

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
	 * 更新人
	 */
    @JsonIgnore
    private String updateId;
	
	/**
	 * 删除人
	 */
    @JsonIgnore
    private String deleteId;
	
	public String getReportStockId() {
		return reportStockId;
	}

	public void setReportStockId(String reportStockId) {
		this.reportStockId = reportStockId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Integer getRentableCount() {
		return rentableCount;
	}

	public void setRentableCount(Integer rentableCount) {
		this.rentableCount = rentableCount;
	}

	public Integer getLeasedCount() {
		return leasedCount;
	}

	public void setLeasedCount(Integer leasedCount) {
		this.leasedCount = leasedCount;
	}

	public String getOccupancyRate() {
		return occupancyRate;
	}

	public void setOccupancyRate(String occupancyRate) {
		if (occupancyRate != null) {
			this.occupancyRate = occupancyRate + "%";
		} else {
			this.occupancyRate = occupancyRate;
		}
	}

	public Integer getShortLeasedCount() {
		return shortLeasedCount;
	}

	public void setShortLeasedCount(Integer shortLeasedCount) {
		this.shortLeasedCount = shortLeasedCount;
	}

	public String getShortLeasedRate() {
		return shortLeasedRate;
	}

	public void setShortLeasedRate(String shortLeasedRate) {
		if (shortLeasedRate != null) {
			this.shortLeasedRate = shortLeasedRate + "%";
		} else {
			this.shortLeasedRate = shortLeasedRate;
		}
	}

	public Integer getLeaveStockConfig() {
		return leaveStockConfig;
	}

	public void setLeaveStockConfig(Integer leaveStockConfig) {
		this.leaveStockConfig = leaveStockConfig;
	}

	public Integer getLeaveStockWait() {
		return leaveStockWait;
	}

	public void setLeaveStockWait(Integer leaveStockWait) {
		this.leaveStockWait = leaveStockWait;
	}

	public Integer getLeaveStockOther() {
		return leaveStockOther;
	}

	public void setLeaveStockOther(Integer leaveStockOther) {
		this.leaveStockOther = leaveStockOther;
	}

	public Integer getLeaveStockTotal() {
		return leaveStockTotal;
	}

	public void setLeaveStockTotal(Integer leaveStockTotal) {
		this.leaveStockTotal = leaveStockTotal;
	}

	public Integer getQuitNormal() {
		return quitNormal;
	}

	public void setQuitNormal(Integer quitNormal) {
		this.quitNormal = quitNormal;
	}

	public Integer getQuitUnnormal() {
		return quitUnnormal;
	}

	public void setQuitUnnormal(Integer quitUnnormal) {
		this.quitUnnormal = quitUnnormal;
	}

	public Integer getQuitCustomer() {
		return quitCustomer;
	}

	public void setQuitCustomer(Integer quitCustomer) {
		this.quitCustomer = quitCustomer;
	}

	public Integer getQuitUnsatisfied() {
		return quitUnsatisfied;
	}

	public void setQuitUnsatisfied(Integer quitUnsatisfied) {
		this.quitUnsatisfied = quitUnsatisfied;
	}

	public Integer getQuitChange() {
		return quitChange;
	}

	public void setQuitChange(Integer quitChange) {
		this.quitChange = quitChange;
	}

	public Integer getQuitExchange() {
		return quitExchange;
	}

	public void setQuitExchange(Integer quitExchange) {
		this.quitExchange = quitExchange;
	}

	public Integer getQuitTotal() {
		return quitTotal;
	}

	public void setQuitTotal(Integer quitTotal) {
		this.quitTotal = quitTotal;
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

	@Override
	public String toString() {
		return "ReportStockDto [reportStockId=" + reportStockId + ", projectId=" + projectId + ", houseTypeId="
				+ houseTypeId + ", recordDate=" + recordDate + ", stockCount=" + stockCount + ", leasedCount="
				+ leasedCount + ", occupancyRate=" + occupancyRate + ", shortLeasedCount=" + shortLeasedCount
				+ ", shortLeasedRate=" + shortLeasedRate + ", leaveStockConfig=" + leaveStockConfig
				+ ", leaveStockWait=" + leaveStockWait + ", leaveStockOther=" + leaveStockOther + ", leaveStockTotal="
				+ leaveStockTotal + ", quitNormal=" + quitNormal + ", quitUnnormal=" + quitUnnormal + ", quitCustomer="
				+ quitCustomer + ", quitUnsatisfied=" + quitUnsatisfied + ", quitChange=" + quitChange
				+ ", quitExchange=" + quitExchange + ", quitTotal=" + quitTotal + ", isDel=" + isDel + ", createTime="
				+ createTime + ", deleteTime=" + deleteTime + ", updateTime=" + updateTime + ", createId=" + createId
				+ ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}
}