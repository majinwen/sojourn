package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/14 10:47
 * @version 1.0
 * @since 1.0
 */
public class NpsGetCondiRequest extends PageRequest {

    private String npsCode;

    private String userType;

    private String uid;

    private List<String> uids = new ArrayList<>();

    private String userName;

    private String npsName;

    private String mobile;

    private String score;

    /**
     * nps开始时间
     */
    private String npsStartTime;

    /**
     * nps结束时间
     */
    private String npsEndTime;

    public String getNpsStartTime() {
        return npsStartTime;
    }

    public void setNpsStartTime(String npsStartTime) {
        this.npsStartTime = npsStartTime;
    }

    public String getNpsEndTime() {
        return npsEndTime;
    }

    public void setNpsEndTime(String npsEndTime) {
        this.npsEndTime = npsEndTime;
    }

    public String getNpsName() {
        return npsName;
    }

    public void setNpsName(String npsName) {
        this.npsName = npsName;
    }

    public String getNpsCode() {
        return npsCode;
    }

    public void setNpsCode(String npsCode) {
        this.npsCode = npsCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getUids() {
        return uids;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
