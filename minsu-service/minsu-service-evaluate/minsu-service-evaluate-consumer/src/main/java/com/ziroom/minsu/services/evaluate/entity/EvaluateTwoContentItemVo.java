package com.ziroom.minsu.services.evaluate.entity;

import java.io.Serializable;

/**
 * <p>两次评价列表展示，只展示房客 或者 房东的评论</p>
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
public class EvaluateTwoContentItemVo implements Serializable{
    private static final long serialVersionUID = -8386013184021624191L;
    /**
     * 评价人名称
     */
    public String evaUserName;
    /**
     * 评价人头像
     */
    public String evaUserPic;
    /**
     * 评价时间
     */
    public String evaTime;
    /**
     * 评价人uid
     */
    public String evaUid;
    /**
     * 房源名称
     */
    public String houseName;
    /**
     * 房源或者房间fid
     */
    public String houseFid;
    /**
     * 出租方式
     */
    public Integer rentWay;
    /**
     * 初见评价内容
     */
    public String cjContent;
    /**
     * 评价内容
     */
    public String evaContent;

    public String getEvaUserName() {
        return evaUserName;
    }

    public void setEvaUserName(String evaUserName) {
        this.evaUserName = evaUserName;
    }

    public String getEvaUserPic() {
        return evaUserPic;
    }

    public void setEvaUserPic(String evaUserPic) {
        this.evaUserPic = evaUserPic;
    }

    public String getEvaTime() {
        return evaTime;
    }

    public void setEvaTime(String evaTime) {
        this.evaTime = evaTime;
    }

    public String getEvaUid() {
        return evaUid;
    }

    public void setEvaUid(String evaUid) {
        this.evaUid = evaUid;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getCjContent() {
        return cjContent;
    }

    public void setCjContent(String cjContent) {
        this.cjContent = cjContent;
    }

    public String getEvaContent() {
        return evaContent;
    }

    public void setEvaContent(String evaContent) {
        this.evaContent = evaContent;
    }
}
