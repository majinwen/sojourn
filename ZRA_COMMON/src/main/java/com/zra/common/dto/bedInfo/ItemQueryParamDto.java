package com.zra.common.dto.bedInfo;

/**
 * 物品查询参数Dto
 * @author tianxf9
 *
 */
public class ItemQueryParamDto {
	
	//床位配置标准Bid
	private String bedStandardBid;

	//页数
	private int pageNum;
	//每页显示条数
	private int rows;

    //类型，床位还是标准配置
	private Byte typeInfo;

	public String getBedStandardBid() {
		return bedStandardBid;
	}
	public void setBedStandardBid(String bedStandardBid) {
		this.bedStandardBid = bedStandardBid;
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

	public Byte getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(Byte typeInfo) {
		this.typeInfo = typeInfo;
	}
}
