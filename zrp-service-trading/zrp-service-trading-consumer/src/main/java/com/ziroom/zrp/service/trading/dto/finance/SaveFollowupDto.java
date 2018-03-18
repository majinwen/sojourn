package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月10日 16时46分
 * @Version 1.0
 * @Since 1.0
 */
public class SaveFollowupDto {

    /**
     * code : 20262261
     * name :
     * duty :
     * followType : 1
     * followTime : 2017-05-15 11:30:00
     * urgeAction : 1
     * debtReason : 12
     * urgeDesc : null
     * uploadfile : ["123","321","123"]
     * contractCode : BJZYCW81704050017
     * billId : 47815
     * curPlanNum : 4
     */

    private String code;
    private String name;
    private String duty;
    private String followType;
    private String followTime;
    private String urgeAction;
    private String debtReason;
    private String urgeDesc;
    private String contractCode;
    private Integer billId;
    private Integer curPlanNum;
    private List<String> uploadfile;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getUrgeAction() {
        return urgeAction;
    }

    public void setUrgeAction(String urgeAction) {
        this.urgeAction = urgeAction;
    }

    public String getDebtReason() {
        return debtReason;
    }

    public void setDebtReason(String debtReason) {
        this.debtReason = debtReason;
    }

    public String getUrgeDesc() {
        return urgeDesc;
    }

    public void setUrgeDesc(String urgeDesc) {
        this.urgeDesc = urgeDesc;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getCurPlanNum() {
        return curPlanNum;
    }

    public void setCurPlanNum(Integer curPlanNum) {
        this.curPlanNum = curPlanNum;
    }

    public List<String> getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(List<String> uploadfile) {
        this.uploadfile = uploadfile;
    }
}
