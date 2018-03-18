package com.ziroom.zrp.service.houses.dto;

import java.io.Serializable;

/**
 * <p>合同详情中的房间信息，房间类型信息，项目信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月24日
 * @since 1.0
 */
public class RentRoomInfoDto implements Serializable{
	
	private static final long serialVersionUID = 1337145257399582777L;
	//项目表查询信息
    /**
 	 * 项目名称
 	 */
    private String proName;

    /**
 	 * 项目地址
 	 */
    private String proAddress;
    /**
     * 项目头图地址
     */
    private String proHeadFigureUrl;
	/**
	 * 用电类别
	 */
    private String electricityType;
	/**
	 * 用水类别
	 */
    private String waterType;

    //房间表查询信息
    /**
	 * 楼层
	 */
	private Integer floorNumber;
	/**
	 * 房间号
	 */
	private String roomNumber;
	/**
	 * 房间面积
	 */
	private Double roomArea;
	/**
	 * 朝向
	 */
	private String roomDirection;
	/**
	 * 类型：0:-房间；1-床位
	 */
	private String type;

	/**
	 * 出租方式   1:年租;2:月租;3日租
	 */
    private String shortRent;

	//房型表中查
	/**
     * 房型名称
     */
    private String houseTypeName;
	/**
	 * 户型id
	 */
    private String houseTypeId;

    private String projectId;

	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProAddress() {
		return proAddress;
	}
	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}
	public String getProHeadFigureUrl() {
		return proHeadFigureUrl;
	}
	public void setProHeadFigureUrl(String proHeadFigureUrl) {
		this.proHeadFigureUrl = proHeadFigureUrl;
	}
	public Integer getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Double getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(Double roomArea) {
		this.roomArea = roomArea;
	}
	public String getRoomDirection() {
		return roomDirection;
	}
	public void setRoomDirection(String roomDirection) {
		this.roomDirection = roomDirection;
	}
	public String getHouseTypeName() {
		return houseTypeName;
	}
	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getShortRent() {
		return shortRent;
	}

	public void setShortRent(String shortRent) {
		this.shortRent = shortRent;
	}

	public String getElectricityType() {
		return electricityType;
	}

	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
