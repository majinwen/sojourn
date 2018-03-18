package com.ziroom.zrp.service.trading.dto;

/*
 * <P>个人签章dto</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 19日 10:47
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class EspSignatureDto implements Serializable{

    private static final long serialVersionUID = -6794713345233816075L;

    //0或1,(0:个人用户;1:企业用户)
    private Integer userType;

    //用户证件号
    private String idCardNum;

    //企业用户编号
    private String orgCode;

    //签章在合同文档中的页码
    private Integer page;

    //签章在页面内的x坐标
    private Integer positionX;

    //签章在页面内的y坐标
    private Integer positionY;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }
}
