package com.ziroom.minsu.troy.invite.vo;

import com.ziroom.minsu.entity.cms.InviteEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/3.
 * @version 1.0
 * @since 1.0
 */
public class InviteEntityVo  extends InviteEntity {

    private static final long serialVersionUID = 155612349375287L;



    private String uidName;

    private String uidTel;

    private String inviteUidName;

    private String inviteUidTel;

    public String getUidName() {
        return uidName;
    }

    public void setUidName(String uidName) {
        this.uidName = uidName;
    }

    public String getInviteUidName() {
        return inviteUidName;
    }

    public void setInviteUidName(String inviteUidName) {
        this.inviteUidName = inviteUidName;
    }

    public String getInviteUidTel() {
        return inviteUidTel;
    }

    public void setInviteUidTel(String inviteUidTel) {
        this.inviteUidTel = inviteUidTel;
    }

    public String getUidTel() {
        return uidTel;
    }

    public void setUidTel(String uidTel) {
        this.uidTel = uidTel;
    }
}
