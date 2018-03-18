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
 * @Date Create in 2017年09月 19日 11:01
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EspContractVo implements Serializable{

    private static final long serialVersionUID = -6950983968512907843L;

    //合同标识，以UUID表示
    private String contractId;

    //合同标题
    private String title;

    //合同创建者制定的文档编号
    private String docNum;

    //合同文档格式，支持pdf、doc和docx
    private String docType;

    //以BASE64编码的合同文档，仅客户端要求时包含
    private String doc;

    //签章集合
    private List<EspSignatureVo> signatures;

    //合同状态，数字类型，0:未签署，1:签署中，2:签署完成，3:签署中止
    private Integer status;

    //创建时间
    private Date dateCreated;

    //最后更新时间
    private Date lastUpdated;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public List<EspSignatureVo> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<EspSignatureVo> signatures) {
        this.signatures = signatures;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
