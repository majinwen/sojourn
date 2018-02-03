package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>条件活动组领取优惠券</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author jixd on 2017年5月27日
 * @since 1.0
 * @version 1.0
 */
public class GroupConditionRequest{

    /**
     * 组码
     */
    private String groupSn;

    /**
     * 用户uid
     */
    private String uid;
    /**
     * 1=房东 2=房客
     */
    private Integer userType;
    /**
     * 入住间夜
     */
    private Integer roomNight;

    /**
     * 来源
     */
    private Integer sourceType;

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getRoomNight() {
        return roomNight;
    }

    public void setRoomNight(Integer roomNight) {
        this.roomNight = roomNight;
    }
}
