package com.ziroom.minsu.services.search.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>获取楼盘信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/4.
 * @version 1.0
 * @since 1.0
 */
public class LandHouseRequest extends PageRequest {

    private static final long serialVersionUID = 52154235853565403L;

    /** 房东uid */
    private String landlordUid;

    /** 房源或者房间id */
    private String fid;

    /** 出租类型 */
    private Integer rentWay;
    
    
    /** 是否已经上线的top50房源 */
    private Integer isTop50Online;
    
    /** 房源fid */
    private String houseFid;
    
    /** 开始时间  */
	private String startTime;

	/** 结束时间  */
	private String endTime;
	
	/** 入住人数 */
	private Integer personCount;
	
	/**
     * 版本号，移动端每次加1，需要转化为数字
     */
    private Integer versionCode;
	
    public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Integer getPersonCount() {
		return personCount;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getIsTop50Online() {
		return isTop50Online;
	}

	public void setIsTop50Online(Integer isTop50Online) {
		this.isTop50Online = isTop50Online;
	}

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

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }
}
