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
public class ReportBoDto extends BaseReportRequest {

	/**
	 * 业务id
	 */
	@JsonIgnore
    private String reportBoId;

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

	@ApiModelProperty("新增商机量")
	@JsonProperty("businessCount")
    private Integer businessCount;
	
	@ApiModelProperty("客源量")
    @JsonProperty("kylCount")
	private Integer kylCount;

	@ApiModelProperty("约看转带看率")
	@JsonProperty("order2seeRate")
    private String order2seeRate;

	@ApiModelProperty("带看量")
	@JsonProperty("seeCount")
    private Integer seeCount;
	
	/**
	 * 成交量
	 */
	@JsonIgnore
	private Integer dealCount;

	@ApiModelProperty("带看转成交率")
	@JsonProperty("see2dealRate")
    private String see2dealRate;

	@ApiModelProperty("新签总量")
	@JsonProperty("signTotal")
    private Integer signTotal;
	
	@ApiModelProperty("长租新签量")
	@JsonProperty("longSignCount")
    private Integer longSignCount;

	@ApiModelProperty("1-3个月新签量")
	@JsonProperty("shortSignCount1")
    private Integer shortSignCount1;

	@ApiModelProperty("4-6个月新签量")
	@JsonProperty("shortSignCount2")
    private Integer shortSignCount2;
	
	@ApiModelProperty("平均出房价格总量")
	@JsonProperty("priceAverageTotal")
    private String priceAverageTotal;

	@ApiModelProperty("长租平均出房价格")
	@JsonProperty("longAveragePrice")
    private String longAveragePrice;

	@ApiModelProperty("1-3个月平均出房价格")
	@JsonProperty("shortAveragePrice1")
    private String shortAveragePrice1;

	@ApiModelProperty("4-6个月平均出房价格")
	@JsonProperty("shortAveragePrice2")
    private String shortAveragePrice2;

	/**
     * 实际出房价---长租
     */
	@JsonIgnore
    private String longActualPrice;
    
    /**
     * 实际出房价---1~3个月
     */
	@JsonIgnore
    private String shortActualPrice1;
    
    /**
     * 实际出房价---4~6个月
     */
	@JsonIgnore
    private String shortActualPrice2;
    
    /**
     * 实际出房价---总量
     */
	@JsonIgnore
    private String actualPriceTotal;

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

	public String getReportBoId() {
		return reportBoId;
	}

	public void setReportBoId(String reportBoId) {
		this.reportBoId = reportBoId;
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

	public Integer getBusinessCount() {
		return businessCount;
	}
	
	public void setBusinessCount(Integer businessCount) {
		this.businessCount = businessCount;
	}

	public String getOrder2seeRate() {
		return order2seeRate;
	}

	public void setOrder2seeRate(String order2seeRate) {
		if (order2seeRate != null) {
		    if("-".equals(order2seeRate)){
	            this.order2seeRate = "-";
		    }else{
		        this.order2seeRate = order2seeRate + "%";
		    }
		} else {
			this.order2seeRate = order2seeRate;
		}
	}

	public Integer getSeeCount() {
		return seeCount;
	}

	public void setSeeCount(Integer seeCount) {
		this.seeCount = seeCount;
	}

	public String getSee2dealRate() {
		return see2dealRate;
	}

	public void setSee2dealRate(String see2dealRate) {
		if (see2dealRate != null) {
			this.see2dealRate = see2dealRate + "%";
		} else {
			this.see2dealRate = see2dealRate;
		}
	}

	public Integer getLongSignCount() {
		return longSignCount;
	}

	public void setLongSignCount(Integer longSignCount) {
		this.longSignCount = longSignCount;
	}

	public Integer getShortSignCount1() {
		return shortSignCount1;
	}

	public void setShortSignCount1(Integer shortSignCount1) {
		this.shortSignCount1 = shortSignCount1;
	}

	public Integer getShortSignCount2() {
		return shortSignCount2;
	}

	public void setShortSignCount2(Integer shortSignCount2) {
		this.shortSignCount2 = shortSignCount2;
	}

	public Integer getSignTotal() {
		return signTotal;
	}

	public void setSignTotal(Integer signTotal) {
		this.signTotal = signTotal;
	}

	public String getLongAveragePrice() {
		return longAveragePrice;
	}

	public void setLongAveragePrice(String longAveragePrice) {
		this.longAveragePrice = longAveragePrice;
	}

	public String getShortAveragePrice1() {
		return shortAveragePrice1;
	}

	public void setShortAveragePrice1(String shortAveragePrice1) {
		this.shortAveragePrice1 = shortAveragePrice1;
	}

	public String getShortAveragePrice2() {
		return shortAveragePrice2;
	}

	public void setShortAveragePrice2(String shortAveragePrice2) {
		this.shortAveragePrice2 = shortAveragePrice2;
	}

	public String getPriceAverageTotal() {
		return priceAverageTotal;
	}

	public void setPriceAverageTotal(String priceAverageTotal) {
		this.priceAverageTotal = priceAverageTotal;
	}

	public String getLongActualPrice() {
		return longActualPrice;
	}

	public void setLongActualPrice(String longActualPrice) {
		this.longActualPrice = longActualPrice;
	}

	public String getShortActualPrice1() {
		return shortActualPrice1;
	}

	public void setShortActualPrice1(String shortActualPrice1) {
		this.shortActualPrice1 = shortActualPrice1;
	}

	public String getShortActualPrice2() {
		return shortActualPrice2;
	}

	public void setShortActualPrice2(String shortActualPrice2) {
		this.shortActualPrice2 = shortActualPrice2;
	}

	public String getActualPriceTotal() {
		return actualPriceTotal;
	}

	public void setActualPriceTotal(String actualPriceTotal) {
		this.actualPriceTotal = actualPriceTotal;
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
	
	public Integer getDealCount() {
		return dealCount;
	}

	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}

	@Override
	public String toString() {
		return "ReportBoDto [reportBoId=" + reportBoId + ", projectId=" + projectId + ", recordDate=" + recordDate
				+ ", houseTypeId=" + houseTypeId + ", businessCount=" + businessCount + ", order2seeRate="
				+ order2seeRate + ", seeCount=" + seeCount + ", dealCount=" + dealCount + ", see2dealRate="
				+ see2dealRate + ", signTotal=" + signTotal + ", longSignCount=" + longSignCount + ", shortSignCount1="
				+ shortSignCount1 + ", shortSignCount2=" + shortSignCount2 + ", priceAverageTotal=" + priceAverageTotal
				+ ", longAveragePrice=" + longAveragePrice + ", shortAveragePrice1=" + shortAveragePrice1
				+ ", shortAveragePrice2=" + shortAveragePrice2 + ", longActualPrice=" + longActualPrice
				+ ", shortActualPrice1=" + shortActualPrice1 + ", shortActualPrice2=" + shortActualPrice2
				+ ", actualPriceTotal=" + actualPriceTotal + ", isDel=" + isDel + ", createTime=" + createTime
				+ ", deleteTime=" + deleteTime + ", updateTime=" + updateTime + ", createId=" + createId + ", updateId="
				+ updateId + ", deleteId=" + deleteId + "]";
	}

    public Integer getKylCount() {
        return kylCount;
    }

    public void setKylCount(Integer kylCount) {
        this.kylCount = kylCount;
    }
}