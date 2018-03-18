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
public class ReportStockEntity {

	/**
	 * 主键id
	 */
    private Integer id;

    /**
	 * 业务id
	 */
    private String reportStockId;

    /**
	 * 项目id
	 */
    private String projectId;

    /**
	 * 户型id
	 */
    private String houseTypeId;

    /**
	 * 记录时间
	 */
    private Date recordDate;

    /**
	 * 库存总数
	 */
    private Integer stockCount;
   
    /**
     * 可出租数  //and by tianxf9
     */
    private Integer rentableCount;

    /**
	 * 已出租数
	 */
    private Integer leasedCount;

    /**
	 * 出租率
	 */
    private BigDecimal occupancyRate;
    
    /**
     * 短租出租数
     */
    private Integer shortLeasedCount;

    /**
	 * 短租占比
	 */
    private BigDecimal shortLeasedRate;

    /**
	 * 剩余库存---配置中
	 */
    private Integer leaveStockConfig;

    /**
	 * 剩余库存---待租中
	 */
    private Integer leaveStockWait;

    /**
	 * 剩余库存---其他
	 */
    private Integer leaveStockOther;

    /**
	 * 剩余库存---总量
	 */
    private Integer leaveStockTotal;

    /**
	 * 退租量---正退
	 */
    private Integer quitNormal;

    /**
	 * 退租量---非正退
	 */
    private Integer quitUnnormal;

    /**
	 * 退租量---单解
	 */
    private Integer quitCustomer;

    /**
	 * 退租量---3天不满意
	 */
    private Integer quitUnsatisfied;

    /**
	 * 退租量---换租
	 */
    private Integer quitChange;

    /**
	 * 退租量---转租
	 */
    private Integer quitExchange;
    
    /**
     * 退租量---总量
     */
    private Integer quitTotal;

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
	 * 更新时间
	 */
    private Date updateTime;

    /**
	 * 创建人
	 */
    private String createId;

    /**
	 * 修改人
	 */
    private String updateId;

    /**
	 * 删除者
	 */
    private String deleteId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
    	if (houseTypeId == null) {
    		this.houseTypeId = HouseTypeConstant.NULL_TYPE;
    	} else if (houseTypeId.isEmpty()) {
    		this.houseTypeId = HouseTypeConstant.EMPTY_TYPE;
    	} else {
    		this.houseTypeId = houseTypeId;
    	}
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getStockCount() {
    	if (stockCount == null) {
    		return 0;
    	} else {
    		return stockCount;
    	}  
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
    
    public Integer getRentableCount() {
    	
    	if(rentableCount == null) {
    		return 0;
    	}else {
    		return rentableCount;
    	}

	}

	public void setRentableCount(Integer rentableCount) {
		this.rentableCount = rentableCount;
	}

	public Integer getLeasedCount() {
    	if (leasedCount == null) {
    		return 0;
    	} else {
    		return leasedCount;
    	}  
    }

    public void setLeasedCount(Integer leasedCount) {
        this.leasedCount = leasedCount;
    }

    public BigDecimal getOccupancyRate() {
    	if (occupancyRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return occupancyRate;
    	}  
    }

    public void setOccupancyRate(BigDecimal occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public Integer getShortLeasedCount() {
		if (shortLeasedCount == null) {
    		return 0;
    	} else {
    		return shortLeasedCount;
    	}  
	}

	public void setShortLeasedCount(Integer shortLeasedCount) {
		this.shortLeasedCount = shortLeasedCount;
	}

	public BigDecimal getShortLeasedRate() {
    	if (shortLeasedRate == null) {
    		return new BigDecimal(0);
    	} else {
    		return shortLeasedRate;
    	}  
    }

    public void setShortLeasedRate(BigDecimal shortLeasedRate) {
        this.shortLeasedRate = shortLeasedRate;
    }

    public Integer getLeaveStockConfig() {
    	if (leaveStockConfig == null) {
    		return 0;
    	} else {
    		return leaveStockConfig;
    	}  
    }

    public void setLeaveStockConfig(Integer leaveStockConfig) {
        this.leaveStockConfig = leaveStockConfig;
    }

    public Integer getLeaveStockWait() {
    	if (leaveStockWait == null) {
    		return 0;
    	} else {
    		return leaveStockWait;
    	}  
    }

    public void setLeaveStockWait(Integer leaveStockWait) {
        this.leaveStockWait = leaveStockWait;
    }

    public Integer getLeaveStockOther() {
    	if (leaveStockOther == null) {
    		return 0;
    	} else {
    		return leaveStockOther;
    	}  
    }

    public void setLeaveStockOther(Integer leaveStockOther) {
        this.leaveStockOther = leaveStockOther;
    }

    public Integer getLeaveStockTotal() {
    	if (leaveStockTotal == null) {
    		return 0;
    	} else {
    		return leaveStockTotal;
    	}  
    }

    public void setLeaveStockTotal(Integer leaveStockTotal) {
        this.leaveStockTotal = leaveStockTotal;
    }

    public Integer getQuitNormal() {
    	if (quitNormal == null) {
    		return 0;
    	} else {
    		return quitNormal;
    	}  
    }

    public void setQuitNormal(Integer quitNormal) {
        this.quitNormal = quitNormal;
    }

    public Integer getQuitUnnormal() {
    	if (quitUnnormal == null) {
    		return 0;
    	} else {
    		return quitUnnormal;
    	}  
    }

    public void setQuitUnnormal(Integer quitUnnormal) {
        this.quitUnnormal = quitUnnormal;
    }

    public Integer getQuitCustomer() {
    	if (quitCustomer == null) {
    		return 0;
    	} else {
    		return quitCustomer;
    	}  
    }

    public void setQuitCustomer(Integer quitCustomer) {
        this.quitCustomer = quitCustomer;
    }

    public Integer getQuitUnsatisfied() {
    	if (quitUnsatisfied == null) {
    		return 0;
    	} else {
    		return quitUnsatisfied;
    	}  
    }

    public void setQuitUnsatisfied(Integer quitUnsatisfied) {
        this.quitUnsatisfied = quitUnsatisfied;
    }

    public Integer getQuitChange() {
    	if (quitChange == null) {
    		return 0;
    	} else {
    		return quitChange;
    	}  
    }

    public void setQuitChange(Integer quitChange) {
        this.quitChange = quitChange;
    }

    public Integer getQuitExchange() {
    	if (quitExchange == null) {
    		return 0;
    	} else {
    		return quitExchange;
    	}  
    }

    public void setQuitExchange(Integer quitExchange) {
        this.quitExchange = quitExchange;
    }

    public Integer getQuitTotal() {
    	if (quitTotal == null) {
    		return 0;
    	} else {
    		return quitTotal;
    	}  
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

	@Override
	public String toString() {
		return "ReportStockEntity [id=" + id + ", reportStockId=" + reportStockId + ", projectId=" + projectId
				+ ", houseTypeId=" + houseTypeId + ", recordDate=" + recordDate + ", stockCount=" + stockCount
				+ ", leasedCount=" + leasedCount + ", occupancyRate=" + occupancyRate + ", shortLeasedCount="
				+ shortLeasedCount + ", shortLeasedRate=" + shortLeasedRate + ", leaveStockConfig=" + leaveStockConfig
				+ ", leaveStockWait=" + leaveStockWait + ", leaveStockOther=" + leaveStockOther + ", leaveStockTotal="
				+ leaveStockTotal + ", quitNormal=" + quitNormal + ", quitUnnormal=" + quitUnnormal + ", quitCustomer="
				+ quitCustomer + ", quitUnsatisfied=" + quitUnsatisfied + ", quitChange=" + quitChange
				+ ", quitExchange=" + quitExchange + ", quitTotal=" + quitTotal + ", isDel=" + isDel + ", createTime="
				+ createTime + ", deleteTime=" + deleteTime + ", updateTime=" + updateTime + ", createId=" + createId
				+ ", updateId=" + updateId + ", deleteId=" + deleteId + "]";
	}
}