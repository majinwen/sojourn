package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 * 邀请好友的订单vo
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月13日 15:12
 * @since 1.0
 */
public class InviteOrderRequest extends BaseEntity {

    private static final long serialVersionUID = 6378170515877242958L;
    private String userUid;

    private String OrderSn;

    private Integer inviteSource;

    /** 最后修改时间 */
    private Date lastModifyDate;

    public InviteOrderRequest() {
    }

    public InviteOrderRequest(String userUid, String orderSn, Integer inviteSource, Date lastModifyDate) {
        this.userUid = userUid;
        OrderSn = orderSn;
        this.inviteSource = inviteSource;
        this.lastModifyDate = lastModifyDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getInviteSource() {
        return inviteSource;
    }

    public void setInviteSource(Integer inviteSource) {
        this.inviteSource = inviteSource;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }
}
