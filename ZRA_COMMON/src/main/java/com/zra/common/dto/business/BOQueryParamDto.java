package com.zra.common.dto.business;

/**
 * 商机查询条件Dto
 * @author tianxf9
 *
 */
public class BOQueryParamDto {
	
	private String filter_and_projectId;
	private String filter_and_projectZOId;
	private String filter_and_step;
	private String filter_and_handState;
	private String filter_and_source;
	private String filter_and_typeBusiness;
	private String filter_and_endTimeBeg;
	private String filter_and_endTimeEnd;
	private String filter_and_createTimeBeg;
	private String filter_and_createTimeEnd;
	private String filter_and_customerName;
    private String filter_and_customerNumber;
    private int pageNum;
    private int rows;
    
    /*当前登录用户的userId 用于过滤管家有权限的项目商机，为了解决管家查询商机能查到所有项目商机的问题   
     *add bywangws21 2017-7-25*/
    private String projectIdStr;  //用户有权限的项目id字符串 （1,2,3）形式
    
    
	public String getFilter_and_projectId() {
		return filter_and_projectId;
	}
	public void setFilter_and_projectId(String filter_and_projectId) {
		this.filter_and_projectId = filter_and_projectId;
	}
	public String getFilter_and_projectZOId() {
		return filter_and_projectZOId;
	}
	public void setFilter_and_projectZOId(String filter_and_projectZOId) {
		this.filter_and_projectZOId = filter_and_projectZOId;
	}
	public String getFilter_and_step() {
		return filter_and_step;
	}
	public void setFilter_and_step(String filter_and_step) {
		this.filter_and_step = filter_and_step;
	}
	public String getFilter_and_handState() {
		return filter_and_handState;
	}
	public void setFilter_and_handState(String filter_and_handState) {
		this.filter_and_handState = filter_and_handState;
	}
	public String getFilter_and_source() {
		return filter_and_source;
	}
	public void setFilter_and_source(String filter_and_source) {
		this.filter_and_source = filter_and_source;
	}
	public String getFilter_and_typeBusiness() {
		return filter_and_typeBusiness;
	}
	public void setFilter_and_typeBusiness(String filter_and_typeBusiness) {
		this.filter_and_typeBusiness = filter_and_typeBusiness;
	}
	public String getFilter_and_endTimeBeg() {
		return filter_and_endTimeBeg;
	}
	public void setFilter_and_endTimeBeg(String filter_and_endTimeBeg) {
		this.filter_and_endTimeBeg = filter_and_endTimeBeg;
	}
	public String getFilter_and_endTimeEnd() {
		return filter_and_endTimeEnd;
	}
	public void setFilter_and_endTimeEnd(String filter_and_endTimeEnd) {
		this.filter_and_endTimeEnd = filter_and_endTimeEnd;
	}
	public String getFilter_and_createTimeBeg() {
		return filter_and_createTimeBeg;
	}
	public void setFilter_and_createTimeBeg(String filter_and_createTimeBeg) {
		this.filter_and_createTimeBeg = filter_and_createTimeBeg;
	}
	public String getFilter_and_createTimeEnd() {
		return filter_and_createTimeEnd;
	}
	public void setFilter_and_createTimeEnd(String filter_and_createTimeEnd) {
		this.filter_and_createTimeEnd = filter_and_createTimeEnd;
	}
	public String getFilter_and_customerName() {
		return filter_and_customerName;
	}
	public void setFilter_and_customerName(String filter_and_customerName) {
		this.filter_and_customerName = filter_and_customerName;
	}
	public String getFilter_and_customerNumber() {
		return filter_and_customerNumber;
	}
	public void setFilter_and_customerNumber(String filter_and_customerNumber) {
		this.filter_and_customerNumber = filter_and_customerNumber;
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
    public String getProjectIdStr() {
        return projectIdStr;
    }
    public void setProjectIdStr(String projectIdStr) {
        this.projectIdStr = projectIdStr;
    }
	
}
