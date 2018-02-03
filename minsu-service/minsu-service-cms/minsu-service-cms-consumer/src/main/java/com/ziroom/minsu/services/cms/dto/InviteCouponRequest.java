package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 * 新版邀请好友下单灌券参数实体类
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月14日 15:12
 * @since 1.0
 */
public class InviteCouponRequest extends BaseEntity {

    private static final long serialVersionUID = 3633511544642974998L;

    /**
     * 用户/被邀请人uid
     */
    private String uid;
    /**
     * 邀请人uid
     */
    private String inviteUid;
    /**
     * 活动组号
     */
    private String groupSn;
    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 活动类别
     */
    private Integer inviteSource;

    public Integer getInviteSource() {
        return inviteSource;
    }

    public void setInviteSource(Integer inviteSource) {
        this.inviteSource = inviteSource;
    }

    public InviteCouponRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInviteUid() {
        return inviteUid;
    }

    public void setInviteUid(String inviteUid) {
        this.inviteUid = inviteUid;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
