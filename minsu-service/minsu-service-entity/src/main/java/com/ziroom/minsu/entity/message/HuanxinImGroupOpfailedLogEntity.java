package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>群组操作失败实体</p>
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
public class HuanxinImGroupOpfailedLogEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 9052222439569033457L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 自如客uid
     */
    private String uid;

    /**
     * 群组 ID
     */
    private String groupId;

    /**
     * 同步状态  0=未同步 1=同步失败  2=同步成功
     */
    private Integer sysStatu;

    /**
     * 操作人fid（自如客为uid）
     */
    private String opFid;

    /**
     * 操作人类型 1=业务人员  2=自如客
     */
    private Integer opType;

    /**
     * 来源  1=自如驿下单  2=自如驿退房  3=申请退款成功
     */
    private Integer sourceType;

    /**
     * 操作失败原因 1=添加群组失败  2=剔除群组失败
     */
    private Integer failedReason;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最新修改时间
     */
    private Date lastModifyDate;
    
    /**
     * 项目id
     */
    private String projectBid;
    

    /**
	 * @return the projectBid
	 */
	public String getProjectBid() {
		return projectBid;
	}

	/**
	 * @param projectBid the projectBid to set
	 */
	public void setProjectBid(String projectBid) {
		this.projectBid = projectBid;
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
        this.fid = fid == null ? null : fid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getSysStatu() {
        return sysStatu;
    }

    public void setSysStatu(Integer sysStatu) {
        this.sysStatu = sysStatu;
    }

    public String getOpFid() {
        return opFid;
    }

    public void setOpFid(String opFid) {
        this.opFid = opFid == null ? null : opFid.trim();
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(Integer failedReason) {
        this.failedReason = failedReason;
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