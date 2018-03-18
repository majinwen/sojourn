package com.zra.kanban.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询新增商机dto
 * @author tianxf9
 *
 */
public class KanBanCountDto {
	
	//bid
	private String Bid;
	//项目id
	private String projectId;
	//管家id
	private String zoId;
	//管家Name
	private String zoName;
	//创建时间
	private Date createTime;
	//处理时间
	private Date handTime;
	//处理时长
	private BigDecimal sumLong;
	//数量
	private int count;
	
	public KanBanCountDto() {
		this.sumLong = new BigDecimal(0);
		this.count = 0;
	}
	
	public String getBid() {
		return Bid;
	}
	public void setBid(String bid) {
		Bid = bid;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getZoId() {
		return zoId;
	}
	public void setZoId(String zoId) {
		this.zoId = zoId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getHandTime() {
		return handTime;
	}
	public void setHandTime(Date handTime) {
		this.handTime = handTime;
	}
	public BigDecimal getSumLong() {
		return sumLong;
	}
	public void setSumLong(BigDecimal sumLong) {
		this.sumLong = sumLong;
	}
	public String getZoName() {
		return zoName;
	}
	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

}
