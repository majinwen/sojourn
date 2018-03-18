package com.ziroom.zrp.service.trading.entity;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 19日 17:02
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;

public class EspCertVo implements Serializable{
    private static final long serialVersionUID = 8438869809099533280L;

    private String certId;

    private String status;

    private String statusDesc;

    private String certSN;
    private String issuer;
    private String subject;
    private String notBefore;
    private String notAfter;
    private String cert;

    public String getCert() {
        return cert;
    }
    public void setCert(String cert) {
        this.cert = cert;
    }
    public String getCertId() {
        return certId;
    }
    public void setCertId(String certId) {
        this.certId = certId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatusDesc() {
        return statusDesc;
    }
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getNotBefore() {
        return notBefore;
    }
    public void setNotBefore(String notBefore) {
        this.notBefore = notBefore;
    }
    public String getNotAfter() {
        return notAfter;
    }
    public void setNotAfter(String notAfter) {
        this.notAfter = notAfter;
    }

    public String getCertSN() {
        return certSN;
    }

    public void setCertSN(String certSN) {
        this.certSN = certSN;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
