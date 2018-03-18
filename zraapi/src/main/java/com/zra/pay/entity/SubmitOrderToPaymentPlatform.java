package com.zra.pay.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuigh6 on 2016/12/26.
 */
public class SubmitOrderToPaymentPlatform {
    private String bizCode;// 业务单号 业务系统中唯一标识
    private String uid;// 客户ID
    private BigDecimal totalFee;// 支付总金额 单位：分
    private String notifyUrl;// 异步通知URL
    private List<ContractDetailForPaymentPlatform> payDetail;// 合同详情 无内部支付不用传
    private Integer expiredDate;// 订单失效时间
    private String cityCode;// 城市编码
    private String bizType;// 业务类型
    private String sourceType;// 设备标示
    private String returnUrl;// 同步通知URL
    private Integer passAccount;// 是否传转账
    private String company;// 公司名称
    private String uidType;// 用户角色
    private String actionType;// 动作类型

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public List<ContractDetailForPaymentPlatform> getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(List<ContractDetailForPaymentPlatform> payDetail) {
        this.payDetail = payDetail;
    }

    public Integer getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Integer expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Integer getPassAccount() {
        return passAccount;
    }

    public void setPassAccount(Integer passAccount) {
        this.passAccount = passAccount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUidType() {
        return uidType;
    }

    public void setUidType(String uidType) {
        this.uidType = uidType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

}
