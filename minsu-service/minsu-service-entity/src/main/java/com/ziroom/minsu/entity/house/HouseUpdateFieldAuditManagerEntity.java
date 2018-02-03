package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>房源审核字段管理表 实体</p>
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
public class HouseUpdateFieldAuditManagerEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -1241296690927372357L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * field_path的md5字符串
     */
    private String fid;

    /**
     * 审核字段的路径  (例如： com.ziroom.minsu.entity.house.AbHouseRelateEntity.getXXX)
     */
    private String fieldPath;

    /**
     * 审核字段的名称描述
     */
    private String fieldDesc;

    /**
     * 审核字段类型：0：审核未通过待审核 1：上架需要审核 2：审核未通过和上架都需要审核
     */
    private Integer type;

    /**
     * 创建人fid （业务人员是系统号）
     */
    private String createrFid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
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
}