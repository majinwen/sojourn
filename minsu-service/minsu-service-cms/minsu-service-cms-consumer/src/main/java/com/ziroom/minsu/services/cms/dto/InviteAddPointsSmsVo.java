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
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月18日 17:15
 * @since 1.0
 */
public class InviteAddPointsSmsVo extends BaseEntity {

    private static final long serialVersionUID = -5780969436961422897L;

    private String uid;
    private String inviteUid;
    private Integer points;
    private Integer sumPoints;

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(Integer sumPoints) {
        this.sumPoints = sumPoints;
    }
}
