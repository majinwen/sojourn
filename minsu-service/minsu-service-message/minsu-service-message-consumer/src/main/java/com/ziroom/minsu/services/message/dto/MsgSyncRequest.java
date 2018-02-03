package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.Date;

/**
 * <p>同步消息请求</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class MsgSyncRequest extends PageRequest {

    private static final long serialVersionUID = 2633982761407160783L;
    /**
     * 登陆用户
     */
    private String uid;
    /**
     * 截止时间
     */
    private Date tillDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }
}
