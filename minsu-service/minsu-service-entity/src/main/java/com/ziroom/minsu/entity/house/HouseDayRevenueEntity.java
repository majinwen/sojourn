package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * <p>房东房源日收益</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseDayRevenueEntity extends BaseEntity {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2101589933813037680L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 日期（天）yyyy-MM-dd
     */
    private Date statisticsDateDay;

    /**
     * 日期（月）MM
     */
    private Integer statisticsDateMonth;
    
    /**
     * 日期（年）yyyy
     */
    private Integer statisticsDateYear;

    /**
     * 日收益
     */
    private Integer dayRevenue;

    /**
     * 房东uid
     */
    private String landlordUid;

    /**
     * 房源fid
     */
    private String houseBaseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床位fid
     */
    private String bedFid;
    
    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;

	/**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public Date getStatisticsDateDay() {
        return statisticsDateDay;
    }

    public void setStatisticsDateDay(Date statisticsDateDay) {
        this.statisticsDateDay = statisticsDateDay;
    }

	public Integer getStatisticsDateMonth() {
		return statisticsDateMonth;
	}

	public void setStatisticsDateMonth(Integer statisticsDateMonth) {
		this.statisticsDateMonth = statisticsDateMonth;
	}

	public Integer getStatisticsDateYear() {
		return statisticsDateYear;
	}

	public void setStatisticsDateYear(Integer statisticsDateYear) {
		this.statisticsDateYear = statisticsDateYear;
	}

	public Integer getDayRevenue() {
        return dayRevenue;
    }

    public void setDayRevenue(Integer dayRevenue) {
        this.dayRevenue = dayRevenue;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid == null ? null : bedFid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
}