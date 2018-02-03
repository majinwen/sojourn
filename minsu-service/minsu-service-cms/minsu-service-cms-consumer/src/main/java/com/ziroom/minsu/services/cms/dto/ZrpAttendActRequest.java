package com.ziroom.minsu.services.cms.dto;

import java.io.Serializable;

/**
 * <p>参加自如寓活动</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2018年01月26日 14:13
 * @since 1.0
 */
public class ZrpAttendActRequest implements Serializable {

    private static final long serialVersionUID = 1646208051391230022L;

    private String groupUserFid;

    private String uid;

    private String createFid;

    private String createName;

    public String getGroupUserFid() {
        return groupUserFid;
    }

    public void setGroupUserFid(String groupUserFid) {
        this.groupUserFid = groupUserFid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
