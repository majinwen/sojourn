package com.zra.common.dto.log;

import java.util.Date;

/**
 * @author cuiyh9
 *  日志记录dto
 */
public class LogRecordParam {

	//业务id
	private String systemId;
	
	//操作模块ID
	private String operModId;
	
	//操作对象Id
	private String operObjId;
	
	private int page;
	
	private int size;
	
	public LogRecordParam(){
		
	}

		

	public LogRecordParam(String systemId, String operModId, String operObjId, int page, int size) {
		super();
		this.systemId = systemId;
		this.operModId = operModId;
		this.operObjId = operObjId;
		this.page = page;
		this.size = size;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	

}
