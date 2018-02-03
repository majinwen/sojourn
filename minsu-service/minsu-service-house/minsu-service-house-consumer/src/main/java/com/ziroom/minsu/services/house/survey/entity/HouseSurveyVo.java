package com.ziroom.minsu.services.house.survey.entity;

import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;

/**
 * <p>房源实勘请求dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseSurveyVo extends HouseSurveyMsgEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8872790187659653606L;
	
	//房源编号
	private String houseFid;

	//房源编号
	private String houseSn;
	
	//房间编号
	private String roomsSn;
	
	//房源名称
	private String houseName;
	
	//房源地址
	private String houseAddr;
	
	public String getHouseFid() {
		return houseFid;
	}
	
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getRoomsSn() {
		return roomsSn;
	}

	public void setRoomsSn(String roomsSn) {
		this.roomsSn = roomsSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}
	
}
