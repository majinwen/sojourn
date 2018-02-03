package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>自如驿项目和群组关联实体</p>
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
public class ZryProjectGroupEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -8091658738598785207L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 项目编号
     */
    private String projectBid;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 是否是默认群组 0=是  1=不是  默认1
     */
    private Integer isDefault;

    /**
     * 最新一次操作人业务编号（自如客为uid）
     */
    private String opBid;

    /**
     * 操作人类型 1=自如业务人员 2=自如客
     */
    private Integer opType;

    /**
     * 是否删除  0=不删除  1=删除  默认0 
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最新修改时间
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

    public String getProjectBid() {
        return projectBid;
    }

    public void setProjectBid(String projectBid) {
        this.projectBid = projectBid == null ? null : projectBid.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getOpBid() {
        return opBid;
    }

    public void setOpBid(String opBid) {
        this.opBid = opBid == null ? null : opBid.trim();
    }

  

    /**
	 * @return the opType
	 */
	public Integer getOpType() {
		return opType;
	}

	/**
	 * @param opType the opType to set
	 */
	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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