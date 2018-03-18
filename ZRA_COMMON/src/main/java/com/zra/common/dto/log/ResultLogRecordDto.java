package com.zra.common.dto.log;

import java.util.List;

/**
 * @author cuiyh9
 *  查询日志结果表
 */
public class ResultLogRecordDto {

	private int total;
	
	private List<LogRecordDto> logRecordList;
	
	
	
	

	public ResultLogRecordDto() {
		super();
	}

	public ResultLogRecordDto(int total, List<LogRecordDto> logRecordList) {
		super();
		this.total = total;
		this.logRecordList = logRecordList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<LogRecordDto> getLogRecordList() {
		return logRecordList;
	}

	public void setLogRecordList(List<LogRecordDto> logRecordList) {
		this.logRecordList = logRecordList;
	}
	
	
	
}
