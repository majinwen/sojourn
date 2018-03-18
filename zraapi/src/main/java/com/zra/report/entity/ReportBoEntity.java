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
public class ReportBoEntity {

	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 业务id
     */
    private String reportBoId;

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
     * 新增商机量
     */
    private Integer businessCount;

    /**
     * 约看转带看率
     */
    private BigDecimal order2seeRate;

    /**
     * 带看量
     */
    private Integer seeCount;
    
    /**
     * 成交量
     */
    private Integer dealCount;

    /**
     * 带看转成交率
     */
    private BigDecimal see2dealRate;

    /**
     * 新签量---长租
     */
    private Integer longSignCount;

    /**
     * 新签量---1~3个月
     */
    private Integer shortSignCount1;

    /**
     * 新签量---4~6个月
     */
    private Integer shortSignCount2;

    /**
     * 新签总量
     */
    private Integer signTotal;

    /**
     * 平均出房价格---长租
     */
    private BigDecimal longAveragePrice;

    /**
     * 平均出房价格---1~3个月
     */
    private BigDecimal shortAveragePrice1;

    /**
     * 平均出房价格---4~6个月
     */
    private BigDecimal shortAveragePrice2;

    /**
     * 平均出房价格---总量
     */
    private BigDecimal priceAverageTotal;
    
    /**
     * 实际出房价---长租
     */
    private BigDecimal longActualPrice;
    
    /**
     * 实际出房价---1~3个月
     */
    private BigDecimal shortActualPrice1;
    
    /**
     * 实际出房价---4~6个月
     */
    private BigDecimal shortActualPrice2;
    
    /**
     * 实际出房价---总量
     */
    private BigDecimal actualPriceTotal;

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
     * wangws21 2017-1-17 添加客源量
     */
    private Integer kylCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getBusinessCount() {
    	if (businessCount == null) {
    		return 0;
    	} else {
    		return businessCount;
    	}
    }

    public void setBusinessCount(Integer businessCount) {
        this.businessCount = businessCount;
    }

    public BigDecimal getOrder2seeRate() {
        if (order2seeRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return order2seeRate;
    	}
    }

    public void setOrder2seeRate(BigDecimal order2seeRate) {
        this.order2seeRate = order2seeRate;
    }

    public Integer getSeeCount() {
    	if (seeCount == null) {
    		return 0;
    	} else {
    		return seeCount;
    	}
    }

    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    public BigDecimal getSee2dealRate() {
    	if (see2dealRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return see2dealRate;
    	}
    }

    public void setSee2dealRate(BigDecimal see2dealRate) {
        this.see2dealRate = see2dealRate;
    }

    public Integer getLongSignCount() {
    	if (longSignCount == null) {
    		return 0;
    	} else {
    		return longSignCount;
    	}
    }

    public void setLongSignCount(Integer longSignCount) {
        this.longSignCount = longSignCount;
    }

    public Integer getShortSignCount1() {
    	if (shortSignCount1 == null) {
    		return 0;
    	} else {
    		return shortSignCount1;
    	}
    }

    public void setShortSignCount1(Integer shortSignCount1) {
        this.shortSignCount1 = shortSignCount1;
    }

    public Integer getShortSignCount2() {
    	if (shortSignCount2 == null) {
    		return 0;
    	} else {
    		return shortSignCount2;
    	}
    }

    public void setShortSignCount2(Integer shortSignCount2) {
        this.shortSignCount2 = shortSignCount2;
    }

    public Integer getSignTotal() {
    	if (signTotal == null) {
    		return 0;
    	} else {
    		return signTotal;
    	}
    }

    public void setSignTotal(Integer signTotal) {
        this.signTotal = signTotal;
    }

    public BigDecimal getLongAveragePrice() {
    	if (longAveragePrice == null) {
    		return new BigDecimal(0);
    	} else {
    		return longAveragePrice;
    	}
    }

    public void setLongAveragePrice(BigDecimal longAveragePrice) {
        this.longAveragePrice = longAveragePrice;
    }

    public BigDecimal getShortAveragePrice1() {
        if (shortAveragePrice1 == null) {
    		return new BigDecimal(0);
    	} else {
    		return shortAveragePrice1;
    	}
    }

    public void setShortAveragePrice1(BigDecimal shortAveragePrice1) {
        this.shortAveragePrice1 = shortAveragePrice1;
    }

    public BigDecimal getShortAveragePrice2() {
        if (shortAveragePrice2 == null) {
    		return new BigDecimal(0);
    	} else {
    		return shortAveragePrice2;
    	}
    }

    public void setShortAveragePrice2(BigDecimal shortAveragePrice2) {
        this.shortAveragePrice2 = shortAveragePrice2;
    }

    public BigDecimal getPriceAverageTotal() {
        if (priceAverageTotal == null) {
    		return new BigDecimal(0);
    	} else {
    		return priceAverageTotal;
    	}
    }

    public void setPriceAverageTotal(BigDecimal priceAverageTotal) {
        this.priceAverageTotal = priceAverageTotal;
    }
    
    public BigDecimal getLongActualPrice() {
		if (longActualPrice == null) {
    		return new BigDecimal(0);
    	} else {
    		return longActualPrice;
    	}
	}

	public void setLongActualPrice(BigDecimal longActualPrice) {
		this.longActualPrice = longActualPrice;
	}

	public BigDecimal getShortActualPrice1() {
		if (shortActualPrice1 == null) {
    		return new BigDecimal(0);
    	} else {
    		return shortActualPrice1;
    	}
	}

	public void setShortActualPrice1(BigDecimal shortActualPrice1) {
		this.shortActualPrice1 = shortActualPrice1;
	}

	public BigDecimal getShortActualPrice2() {
		if (shortActualPrice2 == null) {
    		return new BigDecimal(0);
    	} else {
    		return shortActualPrice2;
    	}
	}

	public void setShortActualPrice2(BigDecimal shortActualPrice2) {
		this.shortActualPrice2 = shortActualPrice2;
	}

	public BigDecimal getActualPriceTotal() {
		if (actualPriceTotal == null) {
    		return new BigDecimal(0);
    	} else {
    		return actualPriceTotal;
    	}
	}

	public void setActualPriceTotal(BigDecimal actualPriceTotal) {
		this.actualPriceTotal = actualPriceTotal;
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
    
	public Integer getDealCount() {
		if (dealCount == null) {
    		return 0;
    	} else {
    		return dealCount;
    	}
	}

	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}

	@Override
	public String toString() {
		return "ReportBoEntity [id=" + id + ", reportBoId=" + reportBoId + ", projectId=" + projectId + ", recordDate="
				+ recordDate + ", houseTypeId=" + houseTypeId + ", businessCount=" + businessCount + ", order2seeRate="
				+ order2seeRate + ", seeCount=" + seeCount + ", dealCount=" + dealCount + ", see2dealRate="
				+ see2dealRate + ", longSignCount=" + longSignCount + ", shortSignCount1=" + shortSignCount1
				+ ", shortSignCount2=" + shortSignCount2 + ", signTotal=" + signTotal + ", longAveragePrice="
				+ longAveragePrice + ", shortAveragePrice1=" + shortAveragePrice1 + ", shortAveragePrice2="
				+ shortAveragePrice2 + ", priceAverageTotal=" + priceAverageTotal + ", longActualPrice="
				+ longActualPrice + ", shortActualPrice1=" + shortActualPrice1 + ", shortActualPrice2="
				+ shortActualPrice2 + ", actualPriceTotal=" + actualPriceTotal + ", isDel=" + isDel + ", createTime="
				+ createTime + ", deleteTime=" + deleteTime + ", updateTime=" + updateTime + ", createId=" + createId
				+ ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}

    public Integer getKylCount() {
        return kylCount;
    }

    public void setKylCount(Integer kylCount) {
        this.kylCount = kylCount;
    }
}