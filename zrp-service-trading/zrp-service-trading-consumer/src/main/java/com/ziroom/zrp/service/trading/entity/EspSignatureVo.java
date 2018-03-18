package com.ziroom.zrp.service.trading.entity;

/*
 * <P>天威服务端响应的签章信息</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 19日 10:33
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class EspSignatureVo implements Serializable{

    private static final long serialVersionUID = 7632492031485640166L;

    //签章标识UUID
    private String signatureId;

    //合同标识UUID
    private String contractId;

    //签章编号
    private String signatureNum;

    //用户id
    private String userId;

    //签章在合同文档中的页码
    private String page;

    //签章在页面内的x坐标
    private String positionX;

    //签章在页面内的y坐标
    private String positionY;

    //签章是否已被签署，布尔型，false表示未签，true表示已签
    private String isSigned;

    //签署时间（已签署则有此字段）
    private String signingTime;

    //签署所使用的印章标识（已签署则有此字段）
    private String sealId;

    //签署所使用的证书标识（已签署则有此字段）
    private String certId;

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSignatureNum() {
        return signatureNum;
    }

    public void setSignatureNum(String signatureNum) {
        this.signatureNum = signatureNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(String isSigned) {
        this.isSigned = isSigned;
    }

    public String getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(String signingTime) {
        this.signingTime = signingTime;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }
}
