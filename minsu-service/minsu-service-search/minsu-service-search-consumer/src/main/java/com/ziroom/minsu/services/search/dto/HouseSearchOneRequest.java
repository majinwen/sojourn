package com.ziroom.minsu.services.search.dto;


public class HouseSearchOneRequest extends BaseSearchRequest {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5943408714415187488L;

	/**
     * 房源或者房间fid
     */
    private String fid;

    /**
     * 出租方式 0：整租，1：合租
     */
    private Integer rentWay; 
    /**
     * 起始时间
     */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;

	public String getFid() {
		return fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


}
