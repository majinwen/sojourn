package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房间扩展表实体类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class HouseRoomExtEntity extends BaseEntity{
	
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8555630490159534018L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 下单类型 1：立即预订，2：申请预订
     */
    private Integer orderType;

    /**
     * 退订政策code
     */
    private String checkOutRulesCode;

    /**
     * 押金规则code
     */
    private String depositRulesCode;

    /**
     * 房间房屋守则
     */
    private String roomRules;

    /**
     * 最少入住天数
     */
    private Integer minDay;

    /**
     * 入住时间
     */
    private String checkInTime;

    /**
     * 退订时间
     */
    private String checkOutTime;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

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

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getCheckOutRulesCode() {
        return checkOutRulesCode;
    }

    public void setCheckOutRulesCode(String checkOutRulesCode) {
        this.checkOutRulesCode = checkOutRulesCode == null ? null : checkOutRulesCode.trim();
    }

    public String getDepositRulesCode() {
        return depositRulesCode;
    }

    public void setDepositRulesCode(String depositRulesCode) {
        this.depositRulesCode = depositRulesCode == null ? null : depositRulesCode.trim();
    }

    public String getRoomRules() {
        return roomRules;
    }

    public void setRoomRules(String roomRules) {
        this.roomRules = roomRules == null ? null : roomRules.trim();
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
        this.checkInTime = checkInTime == null ? null : checkInTime.trim();
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime == null ? null : checkOutTime.trim();
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
}