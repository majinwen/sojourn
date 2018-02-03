package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * <p>airbnb房源关联状态</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @created 2017年04月15日 14:18:28
 * @since 1.0
 * @version 1.0
 */
public class AbCalendarLogEntity extends BaseEntity{

    private static final long serialVersionUID = 1327068566030081051L;
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
    private Long operatorNumber;

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

    public Long getOperatorNumber() {
        return operatorNumber;
    }

    public void setOperatorNumber(Long operatorNumber) {
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