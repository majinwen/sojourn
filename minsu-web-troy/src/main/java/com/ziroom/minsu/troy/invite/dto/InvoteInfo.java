package com.ziroom.minsu.troy.invite.dto;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;

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
public class InvoteInfo extends PageRequest {

    private static final long serialVersionUID = 234234279878979803L;

    /** 邀请人名称 */
    private String name;

    /** 邀请人电话 */
    private String tel;

    /** 被邀请人名称 */
    private String invoteName;

    /** 被邀请人电话 */
    private String invoteTel;

    private String inviteCode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInvoteName() {
        return invoteName;
    }

    public void setInvoteName(String invoteName) {
        this.invoteName = invoteName;
    }

    public String getInvoteTel() {
        return invoteTel;
    }

    public void setInvoteTel(String invoteTel) {
        this.invoteTel = invoteTel;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
