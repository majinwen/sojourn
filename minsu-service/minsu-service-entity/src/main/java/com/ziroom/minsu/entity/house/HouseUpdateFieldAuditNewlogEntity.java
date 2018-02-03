package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>房源审核字段修改最新记录表 实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseUpdateFieldAuditNewlogEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 8912283112648188206L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 与t_house_update_history_log 对应, fid=MD5(house_fid+room_fid+rent_way+field_path_key)
     */
    private String fid;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 出租方式：0 整租   1  分租
     */
    private Integer rentWay;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 审核状态  默认0=未审核；1=审核通过；2=审核拒绝
     */
    private Integer fieldAuditStatu;

    /**
     * 审核字段的路径  (例如： com.ziroom.minsu.entity.house.AbHouseRelateEntity.getXXX)
     */
    private String fieldPath;

    /**
     * 审核字段的描述
     */
    private String fieldDesc;

    /**
     * 创建人fid （房东是uid，业务人员是系统号)
     */
    private String createrFid;

    /**
     * 创建人类型 0=其他 1=房东 2=业务人员 
     */
    private Integer createrType;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后更新时间
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

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Integer getFieldAuditStatu() {
        return fieldAuditStatu;
    }

    public void setFieldAuditStatu(Integer fieldAuditStatu) {
        this.fieldAuditStatu = fieldAuditStatu;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath == null ? null : fieldPath.trim();
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc == null ? null : fieldDesc.trim();
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
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