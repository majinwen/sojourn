package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>合同信息参数</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月20日 17:05
 * @since 1.0
 */
public class ContractParamDto extends BaseEntity{

    // 父合同id
    private String surParentRentId;

    //前合同号
    private String preConRentCode;

    // 合同id --预计给个人新签使用
    private String contractId;

    // 日租、月租、年租
    private String conType;

    //付款周期 月付、季付、半年付、年付 一次性付清
    private String conCycleCode;

    //合同起租日期
    private Date conStartDate;

    //合同到期日期
    private Date conEndDate;

    //签约周期
    private Integer conRentYear;

    //操作人员id
    private String updateId;

    public ContractParamDto() {

    }

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public String getPreConRentCode() {
        return preConRentCode;
    }

    public void setPreConRentCode(String preConRentCode) {
        this.preConRentCode = preConRentCode;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
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

    public Integer getConRentYear() {
        return conRentYear;
    }

    public void setConRentYear(Integer conRentYear) {
        this.conRentYear = conRentYear;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}
