package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 同步房源锁操作日历
 * @author jixd
 * @created 2017年04月20日 10:27:48
 * @param
 * @return
 */
public class AbHouseLockLogEntity extends BaseEntity{
    private static final long serialVersionUID = 3577469172957623120L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid 年月日
     */
    private String roomFid;

    /**
     * 出租类型 0：整租 1：合租 
     */
    private Integer rentWay;

    /**
     * 操作类型 1：ab删除 2：ab插入
     */
    private Integer operatorType;

    /**
     * 更新数量
     */
    private Integer operatorNumber;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getOperatorNumber() {
        return operatorNumber;
    }

    public void setOperatorNumber(Integer operatorNumber) {
        this.operatorNumber = operatorNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}