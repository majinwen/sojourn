package com.ziroom.minsu.services.house.airbnb.dto;

import com.ziroom.minsu.entity.house.AbHouseStatusEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.List;

/**
 * airbnb房源处理请求
 * @author jixd
 * @created 2017年04月15日 15:59:55
 * @param
 * @return
 */
public class AbHouseDto extends PageRequest {

    private static final long serialVersionUID = -5795698310280046685L;
    /**
     * ab房源编号
     */
    private String abSn;
    /**
     * 房源fid
     */
    private String houseFid;
    /**
     * 房间fid
     */
    private String roomFid;
    /**
     * 出租方式
     */
    private Integer rentWay;
    /**
     * 待插入列表
     */
    private List<AbHouseStatusEntity> abStatusList;

    /**
     * 查询开始时间   yyyy-MM-dd
     */
    private String startDate;
    
    
	/**
	 *  房源名称
	 */
	private String houseName;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东电话
	 */
	private String landlordPhone;
	
	/**
	 * 房源编号
	 */
	private String houseSn;
	
	
	/**
	 * 房东uid集合
	 */
	private List<String> landlordUidList;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getLandlordUidList() {
		return landlordUidList;
	}

	public void setLandlordUidList(List<String> landlordUidList) {
		this.landlordUidList = landlordUidList;
	}

	public String getHouseName() {
		return houseName;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public String getLandlordPhone() {
		return landlordPhone;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public void setLandlordPhone(String landlordPhone) {
		this.landlordPhone = landlordPhone;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getAbSn() {
        return abSn;
    }

    public void setAbSn(String abSn) {
        this.abSn = abSn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<AbHouseStatusEntity> getAbStatusList() {
        return abStatusList;
    }

    public void setAbStatusList(List<AbHouseStatusEntity> abStatusList) {
        this.abStatusList = abStatusList;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }
}
