package com.zra.report.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 报表请求基本参数
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午5:22:38
 */
public class BaseReportRequest {

	/**
	 * 查询开始时间
	 */
	@JsonIgnore
	private String beginTime;
	
	/**
	 * 查询结束时间
	 */
	@JsonIgnore
	private String endTime;

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "BaseReportRequest [beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
}
