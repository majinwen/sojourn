package com.ziroom.minsu.services.cms.dto;

import java.io.Serializable;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/2 9:58
 * @version 1.0
 * @since 1.0
 */
public class InviteAcceptRequest implements Serializable {

    private static final long serialVersionUID = -6059610245927127197L;

    /**
     * 被邀请人uid
     */
    String uid;

    /**
     * 被邀请人手机号
     */
    String mobile;

    /**
     * 邀请码
     */
    String inviteCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
