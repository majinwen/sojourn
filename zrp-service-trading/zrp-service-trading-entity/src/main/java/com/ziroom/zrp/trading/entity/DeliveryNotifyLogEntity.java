package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 物业交割定时任务提示
 * @author jixd
 * @created 2017年09月28日 15:27:54
 * @param
 * @return
 */
public class DeliveryNotifyLogEntity extends BaseEntity{

    private static final long serialVersionUID = 7834797741945724204L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 电话
     */
    private String phoneNum;

    /**
     * 提示类型 1=用户 2=管家
     */
    private Integer msgType;

    /**
     * 时间类型 1=12小时 2-24小时
     */
    private Integer timeType;

    /**
     * 创建时间
     */
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}