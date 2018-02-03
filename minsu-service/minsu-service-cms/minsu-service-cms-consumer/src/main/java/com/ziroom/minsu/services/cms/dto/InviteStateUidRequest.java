package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.entity.cms.InviteEntity;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 * 根据uid查询用户是否已经参加过邀请活动以及当前状态
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月13日 10:11
 * @since 1.0
 */
public class InviteStateUidRequest extends InviteEntity {

    private static final long serialVersionUID = -232650367028855301L;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 邀请码来源 0，老版邀请下单活动 1，新版邀请好友下单  默认0
     */
    private Integer inviteSource;
    /**
     * @see com.ziroom.minsu.valenum.cms.InviteStatusEnum
     * 状态 0:初始化
     *  1：已被邀请且给被邀请人送券
     *  2：已给邀请人送券(老版活动)
     */
    private Integer inviteStatus;
    /**
     * 是否给邀请人增加积分 0，尚未给邀请人增加积分 1，已给邀请人增加积分  默认0
     */
    private Integer isGiveInviterPoints;

    /** 最后修改时间 */
    private Date lastModifyDate;

    @Override
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    @Override
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public Integer getInviteSource() {
        return inviteSource;
    }

    @Override
    public void setInviteSource(Integer inviteSource) {
        this.inviteSource = inviteSource;
    }

    @Override
    public Integer getInviteStatus() {
        return inviteStatus;
    }

    @Override
    public void setInviteStatus(Integer inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    @Override
    public Integer getIsGiveInviterPoints() {
        return isGiveInviterPoints;
    }

    @Override
    public void setIsGiveInviterPoints(Integer isGiveInviterPoints) {
        this.isGiveInviterPoints = isGiveInviterPoints;
    }
}
