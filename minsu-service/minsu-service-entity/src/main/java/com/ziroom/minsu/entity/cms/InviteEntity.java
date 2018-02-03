package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 邀请码表
 */
public class InviteEntity extends BaseEntity {

    /**
     * 序列化Id
     */
    private static final long serialVersionUID = 1556122057979375287L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 邀请码
     */
    private String inviteCode;
    
    /**
     * 邀请码来源 0，老版邀请下单活动 1，新版邀请好友下单  默认0
     */
    private Integer inviteSource;
    
    /**
     * 是否给邀请人增加积分 0，尚未给邀请人增加积分 1，已给邀请人增加积分  默认0
     */
    private Integer isGiveInviterPoints;
    

    /**
     * @see com.ziroom.minsu.valenum.cms.InviteStatusEnum
     * 状态 0:初始化
     *  1：已被邀请且给被邀请人送券
     *  2：已给邀请人送券
     */
    private Integer inviteStatus;

    /**
     * 邀请人 （邀请此用户的人）
     */
    private String inviteUid;

    /**
     * 邀请时间
     */
    private Date inviteTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }
    
	public Integer getInviteSource() {
		return inviteSource;
	}

	public void setInviteSource(Integer inviteSource) {
		this.inviteSource = inviteSource;
	}

	public Integer getIsGiveInviterPoints() {
		return isGiveInviterPoints;
	}

	public void setIsGiveInviterPoints(Integer isGiveInviterPoints) {
		this.isGiveInviterPoints = isGiveInviterPoints;
	}

	public Integer getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Integer inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getInviteUid() {
        return inviteUid;
    }

    public void setInviteUid(String inviteUid) {
        this.inviteUid = inviteUid == null ? null : inviteUid.trim();
    }

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
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
}