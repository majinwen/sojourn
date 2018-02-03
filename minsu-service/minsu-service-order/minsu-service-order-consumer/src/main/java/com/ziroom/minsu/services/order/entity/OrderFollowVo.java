package com.ziroom.minsu.services.order.entity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/22 15:15
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowVo extends OrderHouseVo {

    private static final long serialVersionUID = 2510414883105879102L;

    private Integer followStatus;

    private String createName;

    private String followPeopleStatus;

    public String getFollowPeopleStatus() {
        return followPeopleStatus;
    }

    public void setFollowPeopleStatus(String followPeopleStatus) {
        this.followPeopleStatus = followPeopleStatus;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
