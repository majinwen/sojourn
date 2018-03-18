package com.zra.common.dto.bedInfo;

/**
 * 查询床位配置方案
 * @author tianxf9
 *
 */
public class QueryBedStandardDto {
	
	//配置方案名称
	private String code;
	//配置方案
	private String name;
	//页数
	private int pageNum;
	//每页显示条数
	private int rows;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	} 
}
