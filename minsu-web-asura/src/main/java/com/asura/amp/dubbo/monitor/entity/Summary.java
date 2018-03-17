package com.asura.amp.dubbo.monitor.entity;

import java.util.Date;

public class Summary {
	private Date time = null;
	
	private Integer summary = 0;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getSummary() {
		return summary;
	}

	public void setSummary(Integer summary) {
		this.summary = summary;
	}
}
