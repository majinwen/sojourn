package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * <p>房源锁日志</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
public class HouseLockLogEntity extends BaseEntity{

    private static final long serialVersionUID = 3751540428774619051L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 床铺fid
     */
    private String bedFid;

    /**
     * 出租类型 0：整租 1：合租 2：床位
     */
    private Integer rentWay;

    /**
     * 锁定开始时间
     */
    private Date startLockTime;

    /**
     * 锁定结束时间
     */
    private Date endLockTime;

    /**
     * 0=开启 1=锁定
     */
    private Integer lockType;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getBedFid() {
        return bedFid;
    }

    public void setBedFid(String bedFid) {
        this.bedFid = bedFid == null ? null : bedFid.trim();
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Date getStartLockTime() {
        return startLockTime;
    }

    public void setStartLockTime(Date startLockTime) {
        this.startLockTime = startLockTime;
    }

    public Date getEndLockTime() {
        return endLockTime;
    }

    public void setEndLockTime(Date endLockTime) {
        this.endLockTime = endLockTime;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}