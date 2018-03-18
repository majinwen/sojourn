package com.zra.kanban.entity.dto;

/**
 * 目标看板应收账单信息
 * @author tianxf9
 *
 */
public class PaymentInfoDto {
	
	//应收账单个数
	private Integer count;
	//付款计划的所属项目id（取得是付款计划所属合同的项目）
	private String projectId; 
	//付款计划所有管家（现在取得是付款计划所属合同的创建人）
	private String zoId;
	//管家名字
	private String zoName;
	//相差天数
	private Integer subDays;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public Integer getSubDays() {
		return subDays;
	}

	public void setSubDays(Integer subDays) {
		this.subDays = subDays;
	}
}
