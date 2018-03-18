package com.ziroom.zrp.service.trading.dto;

/*
 * <P>个人用户信息dto</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 18日 14:07
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class EspUserInfoDto implements Serializable{

    private static final long serialVersionUID = 1719784168920553573L;

    //用户uid,创建用户时无此字段
    private String userId;

    //用户类型，0：个人用户，1：企业用户
    private Integer userType;

    //姓名
    private String fullname;

    //居民身份证号
    private String idCardNum;

    //手机号
    private String mobile;

    //是否自动生成个人证书、签章
    private Boolean autoCert;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getAutoCert() {
        return autoCert;
    }

    public void setAutoCert(Boolean autoCert) {
        this.autoCert = autoCert;
    }
}
