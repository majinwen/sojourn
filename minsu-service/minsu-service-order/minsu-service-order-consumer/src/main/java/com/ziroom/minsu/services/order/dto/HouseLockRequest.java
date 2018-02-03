package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import java.util.Date;

/**
 * <p>
 * 查看订单的锁定参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class HouseLockRequest extends BaseEntity{


    /** 序列化id */
    private static final long serialVersionUID = -214532456472154563L;

    /** houseFid */
    private String fid;

    /**  roomFid */
    private String roomFid;

    /**
     * 房源类型
     * @see RentWayEnum
     */
    private Integer rentWay;

    /** 开始时间 */
    private Date starTime;


    /** 结束时间 */
    private Date endTime;


    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
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

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
