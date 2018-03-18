package com.zra.report.entity.dto;
/**
 * 计算回款率为目标看板
 * @author tianxf9
 *
 */
public class ReportPaymentForKBDto {
	
	//项目名称
	private String projectId;
	
	//数量
	private int count;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

}
