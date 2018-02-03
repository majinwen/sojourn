package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>TODO</p>
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
public class HuanxinImGroupMemberEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 5654851657021659379L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * 群成员的环信ID
     */
    private String member;

    /**
     * 群成员角色  0=普通成员  1=管理员  2=黑名单成员  3=禁言人员
     */
    private Integer memberRole;

    /**
     * 最新一次操作人fid（业务人员：存业务人员系统号  自如客：存uid ）
     */
    private String opFid;

    /**
     * 最新一次操作人类型1=业务人员   2=自如客
     */
    private Integer opType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除  0=不删除  1=删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;
    
    /**
     * 成员状态 0=正常 1=禁言 2=黑名单
     */
    private Integer memberStatu;

    /**
     * 恢复禁言时间
     */
    private Date recoveryGagTime;
    
    /**
	 * @return the recoveryGagTime
	 */
	public Date getRecoveryGagTime() {
		return recoveryGagTime;
	}

	/**
	 * @param recoveryGagTime the recoveryGagTime to set
	 */
	public void setRecoveryGagTime(Date recoveryGagTime) {
		this.recoveryGagTime = recoveryGagTime;
	}

	/**
	 * @return the memberStatu
	 */
	public Integer getMemberStatu() {
		return memberStatu;
	}

	/**
	 * @param memberStatu the memberStatu to set
	 */
	public void setMemberStatu(Integer memberStatu) {
		this.memberStatu = memberStatu;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public Integer getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(Integer memberRole) {
        this.memberRole = memberRole;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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