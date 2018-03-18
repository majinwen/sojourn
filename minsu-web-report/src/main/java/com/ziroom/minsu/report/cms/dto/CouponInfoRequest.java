package com.ziroom.minsu.report.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/15 10:59
 * @version 1.0
 * @since 1.0
 */
public class CouponInfoRequest extends PageRequest {

    private String actCreateStartTime;
    private String actCreateEndTime;
    private String actSn;
    private String couponCreateStartTime;
    private String couponCreateEndTime;
    private String couponStartTimeStart;
    private String couponStartTimeEnd;
    private String couponStatus;
    private String gotTimeStart;
    private String gotTimeEnd;

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getActCreateStartTime() {
        return actCreateStartTime;
    }

    public void setActCreateStartTime(String actCreateStartTime) {
        this.actCreateStartTime = actCreateStartTime;
    }

    public String getActCreateEndTime() {
        return actCreateEndTime;
    }

    public void setActCreateEndTime(String actCreateEndTime) {
        this.actCreateEndTime = actCreateEndTime;
    }

    public String getCouponCreateStartTime() {
        return couponCreateStartTime;
    }

    public void setCouponCreateStartTime(String couponCreateStartTime) {
        this.couponCreateStartTime = couponCreateStartTime;
    }

    public String getCouponCreateEndTime() {
        return couponCreateEndTime;
    }

    public void setCouponCreateEndTime(String couponCreateEndTime) {
        this.couponCreateEndTime = couponCreateEndTime;
    }

    public String getCouponStartTimeStart() {
        return couponStartTimeStart;
    }

    public void setCouponStartTimeStart(String couponStartTimeStart) {
        this.couponStartTimeStart = couponStartTimeStart;
    }

    public String getCouponStartTimeEnd() {
        return couponStartTimeEnd;
    }

    public void setCouponStartTimeEnd(String couponStartTimeEnd) {
        this.couponStartTimeEnd = couponStartTimeEnd;
    }

    public String getGotTimeStart() {
        return gotTimeStart;
    }

    public void setGotTimeStart(String gotTimeStart) {
        this.gotTimeStart = gotTimeStart;
    }

    public String getGotTimeEnd() {
        return gotTimeEnd;
    }

    public void setGotTimeEnd(String gotTimeEnd) {
        this.gotTimeEnd = gotTimeEnd;
    }
}
