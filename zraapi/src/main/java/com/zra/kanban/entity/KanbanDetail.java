package com.zra.kanban.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 目标看板-二级数据详情实体
 * 
 * @author tianxf9
 *
 */
@ApiModel(value = "")
public class KanbanDetail {
	/**
	 * 主键 表字段 : kanban_detail.id
	 * 
	 */
	@ApiModelProperty(value = "主键")
	private Integer id;

	/**
	 * 业务id 表字段 : kanban_detail.kanban_detail_bid
	 * 
	 */
	@ApiModelProperty(value = "业务id")
	private String kanbanDetailBid;

	/**
	 * 项目id 表字段 : kanban_detail.project_id
	 * 
	 */
	@ApiModelProperty(value = "项目id")
	private String projectId;

	/**
	 * 管家id 表字段 : kanban_detail.zo_id
	 * 
	 */
	@ApiModelProperty(value = "管家id")
	private String zoId;

	/**
	 * 管家名字 表字段 : kanban_detail.zo_name
	 * 
	 */
	@ApiModelProperty(value = "管家名字")
	private String zoName;

	/**
	 * 周期类型：1：周报；2：月报；3：季报；4：年报 表字段 : kanban_detail.cycle_type
	 * 
	 */
	@ApiModelProperty(value = "周期类型：1：周报；2：月报；3：季报；4：年报")
	private Byte cycleType;
	/**
	 * 周期开始时间 表字段 : kanban_detail.start_date
	 * 
	 */
	@ApiModelProperty(value = "周期开始时间")
	private Date startDate;

	/**
	 * 周期结束时间 表字段 : kanban_detail.end_date
	 * 
	 */
	@ApiModelProperty(value = "周期结束时间")
	private Date endDate;

	/**
	 * 商机约看平均处理时长 表字段 : kanban_detail.yk_deal_avglong
	 * 
	 */
	@ApiModelProperty(value = "商机约看平均处理时长")
	private BigDecimal ykDealAvglong;

	/**
	 * 退租量 表字段 : kanban_detail.quit_count
	 * 
	 */
	@ApiModelProperty(value = "退租量")
	private Integer quitCount;

	/**
	 * 续约量 表字段 : kanban_detail.renew_count
	 * 
	 */
	@ApiModelProperty(value = "续约量")
	private Integer renewCount;

	/**
	 * 新签量 表字段 : kanban_detail.sign_new_count
	 * 
	 */
	@ApiModelProperty(value = "新签量")
	private Integer signNewCount;

	/**
	 * 0~5天空置房源数量 表字段 : kanban_detail.empty_room_range1
	 * 
	 */
	@ApiModelProperty(value = "0~5天空置房源数量")
	private Integer emptyRoomRange1;

	/**
	 * 6~10天空置房源数量 表字段 : kanban_detail.empty_room_range2
	 * 
	 */
	@ApiModelProperty(value = "6~10天空置房源数量")
	private Integer emptyRoomRange2;

	/**
	 * 11～15天空置房源数量 表字段 : kanban_detail.empty_room_range3
	 * 
	 */
	@ApiModelProperty(value = "11～15天空置房源数量")
	private Integer emptyRoomRange3;

	/**
	 * 15天以上空置房源数量 表字段 : kanban_detail.empty_room_range4
	 * 
	 */
	@ApiModelProperty(value = "15天以上空置房源数量")
	private Integer emptyRoomRange4;

	/**
	 * 平均提前回款天数 表字段 : kanban_detail.early_payment_avgdays
	 * 
	 */
	@ApiModelProperty(value = "平均提前回款天数")
	private BigDecimal earlyPaymentAvgdays;

	/**
	 * 创建时间 表字段 : kanban_detail.create_time
	 * 
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 创建人id 表字段 : kanban_detail.creater_id
	 * 
	 */
	@ApiModelProperty(value = "创建人id")
	private String createrId;

	/**
	 * 更新时间 表字段 : kanban_detail.update_time
	 * 
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 更新人id 表字段 : kanban_detail.updater_id
	 * 
	 */
	@ApiModelProperty(value = "更新人id")
	private String updaterId;

	/**
	 * 删除时间 表字段 : kanban_detail.delete_time
	 * 
	 */
	@ApiModelProperty(value = "删除时间")
	private Date deleteTime;

	/**
	 * 删除者id 表字段 : kanban_detail.deleter_id
	 * 
	 */
	@ApiModelProperty(value = "删除者id")
	private String deleterId;

	/**
	 * 删除标志：0：未删除；1：删除； 表字段 : kanban_detail.is_del
	 * 
	 */
	@ApiModelProperty(value = "删除标志：0：未删除；1：删除；")
	private Byte isDel;

	/**
	 * kanban_detail.yk_deal_long
	 */
	@ApiModelProperty(value = "约看处理时长（管家时间段内所有商机约看处理时长的总和）:单位分钟")
	private BigDecimal ykDealLong;

	/**
	 * kanban_detail.bo_new_count
	 */
	@ApiModelProperty(value = "时间段内商机新增量")
	private Integer boNewCount;

	/**
	 * kanban_detail.payment_count
	 */
	@ApiModelProperty(value = "应收账单数量")
	private Integer paymentCount;

	/**
	 * kanban_detail.early_total_days
	 */
	@ApiModelProperty(value = "应收账单提前处理天数")
	private Integer earlyTotalDays;

	/**
	 * 构造函数初始化一些参数
	 */
	public KanbanDetail(String bid, String projectId, String zoId, String zoName, String createrId, Date createTime,
			Date startDate, Date endDate, Byte cycleType, Byte isDel) {
		super();
		this.ykDealAvglong = new BigDecimal(-1);
		this.quitCount = new Integer(0);
		this.renewCount = new Integer(0);
		this.signNewCount = new Integer(0);
		this.emptyRoomRange1 = new Integer(0);
		this.emptyRoomRange2 = new Integer(0);
		this.emptyRoomRange3 = new Integer(0);
		this.emptyRoomRange4 = new Integer(0);
		this.ykDealLong = new BigDecimal(0);
		this.boNewCount = new Integer(0);
		this.paymentCount = new Integer(0);
		this.earlyTotalDays = new Integer(0);

		// 必须指定的参数
		this.kanbanDetailBid = bid;
		this.projectId = projectId;
		this.zoId = zoId;
		this.zoName = zoName;
		this.createrId = createrId;
		this.createTime = createTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cycleType = cycleType;
		this.isDel = isDel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKanbanDetailBid() {
		return kanbanDetailBid;
	}

	public void setKanbanDetailBid(String kanbanDetailBid) {
		this.kanbanDetailBid = kanbanDetailBid == null ? null : kanbanDetailBid.trim();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getZoId() {
		return zoId;
	}

	public void setZoId(String zoId) {
		this.zoId = zoId == null ? null : zoId.trim();
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName == null ? null : zoName.trim();
	}

	public Byte getCycleType() {
		return cycleType;
	}

	public void setCycleType(Byte cycleType) {
		this.cycleType = cycleType;
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

	public BigDecimal getYkDealAvglong() {
		return ykDealAvglong;
	}

	public void setYkDealAvglong(BigDecimal ykDealAvglong) {
		this.ykDealAvglong = ykDealAvglong;
	}

	public Integer getQuitCount() {
		return quitCount;
	}

	public void setQuitCount(Integer quitCount) {
		this.quitCount = quitCount;
	}

	public Integer getRenewCount() {
		return renewCount;
	}

	public void setRenewCount(Integer renewCount) {
		this.renewCount = renewCount;
	}

	public Integer getSignNewCount() {
		return signNewCount;
	}

	public void setSignNewCount(Integer signNewCount) {
		this.signNewCount = signNewCount;
	}

	public Integer getEmptyRoomRange1() {
		return emptyRoomRange1;
	}

	public void setEmptyRoomRange1(Integer emptyRoomRange1) {
		this.emptyRoomRange1 = emptyRoomRange1;
	}

	public Integer getEmptyRoomRange2() {
		return emptyRoomRange2;
	}

	public void setEmptyRoomRange2(Integer emptyRoomRange2) {
		this.emptyRoomRange2 = emptyRoomRange2;
	}

	public Integer getEmptyRoomRange3() {
		return emptyRoomRange3;
	}

	public void setEmptyRoomRange3(Integer emptyRoomRange3) {
		this.emptyRoomRange3 = emptyRoomRange3;
	}

	public Integer getEmptyRoomRange4() {
		return emptyRoomRange4;
	}

	public void setEmptyRoomRange4(Integer emptyRoomRange4) {
		this.emptyRoomRange4 = emptyRoomRange4;
	}

	public BigDecimal getEarlyPaymentAvgdays() {
		return earlyPaymentAvgdays;
	}

	public void setEarlyPaymentAvgdays(BigDecimal earlyPaymentAvgdays) {
		this.earlyPaymentAvgdays = earlyPaymentAvgdays;
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

	public BigDecimal getYkDealLong() {
		return ykDealLong;
	}

	public void setYkDealLong(BigDecimal ykDealLong) {
		this.ykDealLong = ykDealLong;
	}

	public Integer getBoNewCount() {
		return boNewCount;
	}

	public void setBoNewCount(Integer boNewCount) {
		this.boNewCount = boNewCount;
	}

	public Integer getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	public Integer getEarlyTotalDays() {
		return earlyTotalDays;
	}

	public void setEarlyTotalDays(Integer earlyTotalDays) {
		this.earlyTotalDays = earlyTotalDays;
	}

}