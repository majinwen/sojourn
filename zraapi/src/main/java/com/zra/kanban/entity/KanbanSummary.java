package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 目标看板-核心数据实体
 * @author tianxf9
 *
 */
@ApiModel(value="")
public class KanbanSummary {
    /**
     * 主键
     * 表字段 : kanban_summary.id
     * 
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 业务id
     * 表字段 : kanban_summary.kanban_summary_bid
     * 
     */
    @ApiModelProperty(value="业务id")
    private String kanbanSummaryBid;

    /**
     * 项目id
     * 表字段 : kanban_summary.project_id
     * 
     */
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 周期开始时间-yyyy-MM-dd 00:00:00
     * 表字段 : kanban_summary.start_date
     * 
     */
    @ApiModelProperty(value="周期开始时间-yyyy-MM-dd 00:00:00")
    private Date startDate;

    /**
     * 周期结束时间yyyy-MM-dd 23:59:59
     * 表字段 : kanban_summary.end_date
     * 
     */
    @ApiModelProperty(value="周期结束时间yyyy-MM-dd 23:59:59")
    private Date endDate;

    /**
     * 周期类型：1：周报；2：月报；3：季报；4：年报
     * 表字段 : kanban_summary.cycle_type
     * 
     */
    @ApiModelProperty(value="周期类型：1：周报；2：月报；3：季报；4：年报")
    private Byte cycleType;

    /**
     * 商机约看及时跟进率
     * 表字段 : kanban_summary.yk_deal_rate
     * 
     */
    @ApiModelProperty(value="商机约看及时跟进率")
    private BigDecimal ykDealRate;

    /**
     * 出租率
     * 表字段 : kanban_summary.lease_rate
     * 
     */
    @ApiModelProperty(value="出租率")
    private BigDecimal leaseRate;

    /**
     * 日均入住率
     * 表字段 : kanban_summary.occupancy_rate
     * 
     */
    @ApiModelProperty(value="日均入住率")
    private BigDecimal occupancyRate;

    /**
     * 出租周期
     * 表字段 : kanban_summary.lease_cycle
     * 
     */
    @ApiModelProperty(value="出租周期")
    private BigDecimal leaseCycle;

    /**
     * 回款率
     * 表字段 : kanban_summary.voucher_rate
     * 
     */
    @ApiModelProperty(value="回款率")
    private BigDecimal voucherRate;

    /**
     * 创建时间
     * 表字段 : kanban_summary.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 创建人id
     * 表字段 : kanban_summary.creater_id
     * 
     */
    @ApiModelProperty(value="创建人id")
    private String createrId;

    /**
     * 更新时间
     * 表字段 : kanban_summary.update_time
     * 
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 更新人id
     * 表字段 : kanban_summary.updater_id
     * 
     */
    @ApiModelProperty(value="更新人id")
    private String updaterId;

    /**
     * 删除时间
     * 表字段 : kanban_summary.delete_time
     * 
     */
    @ApiModelProperty(value="删除时间")
    private Date deleteTime;

    /**
     * 删除者id
     * 表字段 : kanban_summary.deleter_id
     * 
     */
    @ApiModelProperty(value="删除者id")
    private String deleterId;

    /**
     * 删除标志：0：未删除；1：删除；
     * 表字段 : kanban_summary.is_del
     * 
     */
    @ApiModelProperty(value="删除标志：0：未删除；1：删除；")
    private Byte isDel;
    
    
    /**
     * 构造函数，初始化一些数据
     */
    public KanbanSummary() {
    	 super();
    	 this.ykDealRate = new BigDecimal(0);
    	 this.leaseRate = new BigDecimal(0);
    	 this.occupancyRate = new BigDecimal(0);
    	 //分母为零时赋值为-1
    	 this.leaseCycle = new BigDecimal(-1);
    	 this.voucherRate = new BigDecimal(-1);	 
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKanbanSummaryBid() {
        return kanbanSummaryBid;
    }

    public void setKanbanSummaryBid(String kanbanSummaryBid) {
        this.kanbanSummaryBid = kanbanSummaryBid == null ? null : kanbanSummaryBid.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getCycleType() {
		return cycleType;
	}

	public void setCycleType(Byte cycleType) {
		this.cycleType = cycleType;
	}

	public BigDecimal getYkDealRate() {
        return ykDealRate;
    }

    public void setYkDealRate(BigDecimal ykDealRate) {
        this.ykDealRate = ykDealRate;
    }

    public BigDecimal getLeaseRate() {
        return leaseRate;
    }

    public void setLeaseRate(BigDecimal leaseRate) {
        this.leaseRate = leaseRate;
    }

    public BigDecimal getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(BigDecimal occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public BigDecimal getLeaseCycle() {
        return leaseCycle;
    }

    public void setLeaseCycle(BigDecimal leaseCycle) {
        this.leaseCycle = leaseCycle;
    }

    public BigDecimal getVoucherRate() {
        return voucherRate;
    }

    public void setVoucherRate(BigDecimal voucherRate) {
        this.voucherRate = voucherRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId == null ? null : deleterId.trim();
    }

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}
}