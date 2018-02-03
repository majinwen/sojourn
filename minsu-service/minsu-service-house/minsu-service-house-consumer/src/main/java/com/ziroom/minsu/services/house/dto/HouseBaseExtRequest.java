package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>房源基本信息扩展dto</p>
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
public class HouseBaseExtRequest extends PageRequest {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1382339619051206973L;
	
	//楼号
    private String buildingNum;

    //单元号
    private String unitNum;

    //楼层
    private String floorNum;

    //门牌号
    private String houseNum;

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

}
