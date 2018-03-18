package com.ziroom.zrp.service.trading.dto;

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
 * @Date Create in 2017年09月 19日 10:54
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;
import java.util.List;

public class EspContractDto implements Serializable {

    private static final long serialVersionUID = -5834450024236843013L;

    //合同标题
    private String title;

    //合同创建者制定的文档编号
    private String docNum;

    //合同文档格式，支持pdf、doc和docx
    private String docType;

    //以BASE64编码的合同文档
    private String doc;

    private String fileName;

    //签章集合
    private List<EspSignatureDto> signatures;

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

    public List<EspSignatureDto> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<EspSignatureDto> signatures) {
        this.signatures = signatures;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
