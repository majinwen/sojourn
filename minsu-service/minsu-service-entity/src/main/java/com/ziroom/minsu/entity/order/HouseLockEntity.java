package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>房源的锁定</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public class HouseLockEntity extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -657858928264L;

    /** id */
    private Integer id;

    /** fid */
    private String fid;

    /** 房源fid */
    private String houseFid;

    /** 房间FId */
    private String roomFid;

    /**  床fid  */
    private String bedFid;

    /** 订饭编号 */
    private String orderSn;

    /** 出租类型 0：整租 1：合租 2：床位 */
    private Integer rentWay;

    /** 锁定时间 年-月-日*/
    private Date lockTime;

    /** 锁定类型 1：订单锁定 2：房东 */
    private Integer lockType;

    /** 支付状态 */
    private Integer payStatus;
    /**锁定来源*/
    private Integer lockSource;

    /** 创建时间 */
    private Date createTime;

    /** 最后一次修改时间 */
    private Date lastModifyDate;

    /** 是否删除 0：否，1：是 默认0 不删除 1：删除 */
    private Integer isDel;


    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

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
        this.fid = fid;
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

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }


    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getLockSource() {
        return lockSource;
    }

    public void setLockSource(Integer lockSource) {
        this.lockSource = lockSource;
    }
}
