package com.zra.common.dto.business;
/**
 * 约看量dto
 * @author tianxf9
 *
 */
public class BoReportCountDto {
	
	//数量
	private int count;
	//来源
	private Byte source;
	//项目
	private String projectId;
	//管家
	private String projectZoId;
	//管家名称
	private String projectZoName;
	//管家手机号
	private String zoPhone;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Byte getSource() {
		return source;
	}
	public void setSource(Byte source) {
		this.source = source;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectZoId() {
		return projectZoId;
	}
	public void setProjectZoId(String projectZoId) {
		this.projectZoId = projectZoId;
	}
	public String getProjectZoName() {
		return projectZoName;
	}
	public void setProjectZoName(String projectZoName) {
		this.projectZoName = projectZoName;
	}
	public String getZoPhone() {
		return zoPhone;
	}
	public void setZoPhone(String zoPhone) {
		this.zoPhone = zoPhone;
	}
}
