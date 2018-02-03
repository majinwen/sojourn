package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>房源基本信息修改记录表 实体</p>
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
public class HouseUpdateHistoryLogEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -5839060920003712120L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务fid
     */
    private String fid;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;

    /**
     * 修改字段的路径  (例如： com.ziroom.minsu.entity.house.AbHouseRelateEntity.getXXX)
     */
    private String fieldPath;

    /**
     * field_path的md5字符串
     */
    private String fieldPathKey;

    /**
     * 修改字段的名称描述
     */
    private String fieldDesc;

    /**
     * 修改字段来源 0=其他 1=app  2=M站  3=pc  4=troy
     */
    private Integer sourceType;

    /**
     * 修改字段的旧值
     */
    private String oldValue;

    /**
     * 修改字段的新值
     */
    private String newValue;

    /**
     * 是否是大字段 0=否  1=是  (超过1024 即是大字段)
     */
    private Integer isText;

    /**
     * 修改备注
     */
    private String remark;

    /**
     * 创建人fid （房东是uid，业务人员是系统号）
     */
    private String createrFid;

    /**
     * 创建人类型 0=其他 1=房东 2=业务人员 
     */
    private Integer createrType;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    public Integer getOperateSource() {
        return operateSource;
    }

    public void setOperateSource(Integer operateSource) {
        this.operateSource = operateSource;
    }

    /**
     * 新增加字段,操作来源
     * 用于标识是否来自于运营人员的直接修改
     * 2.业务人员 枚举类CreaterTypeEnum.GUARD
     * @author yanb
     * @return
     */
    private Integer operateSource;

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

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath == null ? null : fieldPath.trim();
    }

    public String getFieldPathKey() {
        return fieldPathKey;
    }

    public void setFieldPathKey(String fieldPathKey) {
        this.fieldPathKey = fieldPathKey == null ? null : fieldPathKey.trim();
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc == null ? null : fieldDesc.trim();
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    public Integer getIsText() {
        return isText;
    }

    public void setIsText(Integer isText) {
        this.isText = isText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    /**
	 * @return the createrType
	 */
	public Integer getCreaterType() {
		return createrType;
	}

	/**
	 * @param createrType the createrType to set
	 */
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