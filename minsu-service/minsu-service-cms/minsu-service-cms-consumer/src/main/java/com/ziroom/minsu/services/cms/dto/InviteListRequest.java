package com.ziroom.minsu.services.cms.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;
import java.util.List;

/**
 * <p>受邀请列表查询</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/11/3 9:58
 * @version 1.0
 * @since 1.0
 */
public class InviteListRequest extends PageRequest {

    private static final long serialVersionUID = -605932423927127197L;

    /**
     * 邀请人uid
     */
    private List<String> uids;

    /**
     * 被邀请人uid
     */
    private List<String> inviteUids;


    /**
     * 邀请码
     */
    private String inviteCode;


    public List<String> getUids() {
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public List<String> getInviteUids() {
        return inviteUids;
    }

    public void setInviteUids(List<String> inviteUids) {
        this.inviteUids = inviteUids;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
