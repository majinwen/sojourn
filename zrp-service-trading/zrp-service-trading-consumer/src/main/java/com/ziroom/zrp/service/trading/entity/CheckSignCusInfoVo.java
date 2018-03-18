package com.ziroom.zrp.service.trading.entity;

/*
 * <P>电子验签用户信息vo</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 12日 14:10
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;
import java.util.Date;

public class CheckSignCusInfoVo implements Serializable{

    /**
     * 序列id
     */
    private static final long serialVersionUID = -6347339661902862440L;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 客户库用户uid
     */
    private String customerUid;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 证件类型(0=其他 1=身份证 2=护照 3=军官证 4=通行证 5=驾驶证 6=台胞证 7=社保卡
     * 8=省份证 9=社保卡 10=学生证 11=回乡证 12=营业执照 13=港澳通行证 14户口本 15=居住证 16=营业执照)
     */
    private Integer certType;

    /**
     * 用户身份证件号码
     */
    private String certNum;

    /**
     * 付款周期：1 月付 3 季付 6 半年付 12 年付 9 一次性付清（短租）
     */
    private String conCycleCode;

    /**
     * 合同生效日期：起租日期
     */
    private Date conStartDate;

    /**
     * 合同截止日期：到期日期
     */
    private Date conEndDate;

    /**
     * 房间id
     */
    private String roomId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCertType() {
        return certType;
    }

    public void setCertType(Integer certType) {
        this.certType = certType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getConCycleCode() {
        return conCycleCode;
    }

    public void setConCycleCode(String conCycleCode) {
        this.conCycleCode = conCycleCode;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(Date conEndDate) {
        this.conEndDate = conEndDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
