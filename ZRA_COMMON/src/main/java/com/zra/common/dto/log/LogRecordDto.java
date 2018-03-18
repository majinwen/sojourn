package com.zra.common.dto.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zra.common.utils.StrUtils;

/**
 * @author cuiyh9
 *  日志记录dto
 */
public class LogRecordDto {

	//业务id
	private String systemId;
	
	//操作模块ID
	private String operModId;
	
	//操作对象Id
	private String operObjId;
	
	//操作人
	private String operator;
	
	//操作时间  -- 非必填，如填写，使用 yyyyMMddHHmmss 格式
	private String operTime;
	
	//操作信息
	private String loginfo;
	
	
	public LogRecordDto(){
		
	}

	
	public LogRecordDto(String systemId, String operModId, String operObjId, String operator,
			String loginfo) {
		this.systemId = systemId;
		this.operModId = operModId;
		this.operObjId = operObjId;
		this.operator = operator;
		this.loginfo = loginfo;
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = df.format(new Date());
		this.operTime = dateStr;
	}
	
	public LogRecordDto(String systemId, String operModId, String operObjId, String operator, String operTime,
			String loginfo) {
		this.systemId = systemId;
		this.operModId = operModId;
		this.operObjId = operObjId;
		this.operator = operator;
		this.operTime = operTime;
		this.loginfo = loginfo;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getOperModId() {
		return operModId;
	}

	public void setOperModId(String operModId) {
		this.operModId = operModId;
	}

	public String getOperObjId() {
		return operObjId;
	}

	public void setOperObjId(String operObjId) {
		this.operObjId = operObjId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getLoginfo() {
		return loginfo;
	}

	public void setLoginfo(String loginfo) {
		this.loginfo = loginfo;
	}
	
	
	/**
	 * 转为log日志
	 * @return
	 */
	public String toLogString(){
		StringBuffer sb = new StringBuffer(500);
		sb.append(this.systemId);
		sb.append(" ");
		sb.append(this.operModId);
		sb.append(" ");
		sb.append(this.operObjId);
		sb.append(" ");
		sb.append(this.operator);
		sb.append(" ");
		if(StrUtils.isNotNullOrBlank(this.operTime)){
			sb.append(this.operTime);
		}else{
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateStr = df.format(new Date());
			sb.append(dateStr);
		}
		sb.append(" ");
		sb.append(this.loginfo);
		return sb.toString();
	}
}
