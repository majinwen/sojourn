package com.ziroom.minsu.services.house.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.entity.house.HouseDescEntity;

/**
 * 
 * <p>
 * 房源物理信息dto
 * </p>
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
public class HouseDescDto extends HouseDescEntity {

	/**
	 * 序列化字段
	 */
	private static final long serialVersionUID = 6891942329983092205L;

	/** 房源基础信息begin */
	// 操作步骤
	private Integer operateSeq;

	// 信息完整率
	private Double intactRate;
	/** 房源基础信息end */

	/** 房源基础信息扩展begin */
	// 房源基础信息扩展表逻辑id
	private String houseExtFid;

	// 楼号
	@NotEmpty(message = "{house.phy.buildingNum.null}")
	private String buildingNum;

	// 单元号
	@NotEmpty(message = "{house.phy.unitNum.null}")
	private String unitNum;

	// 楼层
	@NotEmpty(message = "{house.phy.floorNum.null}")
	private String floorNum;

	// 门牌号
	@NotEmpty(message = "{house.phy.houseNum.null}")
	private String houseNum;

	// 最少入住天数
	private Integer minDay;

	// 入住时间
	private String checkInTime;
	
    //退订时间配置
    private String checkOutTime;

	// 床单更换规则
	private Integer sheetsReplaceRules;
	/** 房源基础信息扩展end */

	public Integer getOperateSeq() {
		return operateSeq;
	}

	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}

	public Double getIntactRate() {
		return intactRate;
	}

	public void setIntactRate(Double intactRate) {
		this.intactRate = intactRate;
	}

	public String getHouseExtFid() {
		return houseExtFid;
	}

	public void setHouseExtFid(String houseExtFid) {
		this.houseExtFid = houseExtFid;
	}

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

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	
	public String getCheckOutTime() {
		return checkOutTime;
	}
	
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Integer getSheetsReplaceRules() {
		return sheetsReplaceRules;
	}

	public void setSheetsReplaceRules(Integer sheetsReplaceRules) {
		this.sheetsReplaceRules = sheetsReplaceRules;
	}

}
