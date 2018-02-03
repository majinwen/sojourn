package com.ziroom.minsu.services.house.dto;

/**
 * 
 * <p>校验房源信息请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseCheckDto {

	/**
	 * 房源或房间fid
	 */
	private String fid;
	
	/**
	 * 出租方式
	 */
	private Integer rentWay;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	
	
}
