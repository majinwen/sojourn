package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class FinanceSyncLogEntity extends BaseEntity{
    /**
     * 自增id
     */
    private Integer id;

    /** 逻辑id*/
    private String fid;

    /** 关联单号*/
    private String syncSn;

    /** 关联订单号*/
    private String orderSn;

    /** 调用状态 0：失败，1：成功*/
    private Integer callStatus;

    /** 返回code*/
    private String resultCode;

    /** 返回描述*/
    private String resultMsg;

    /** 创建人id*/
    private String createId;

    /** 创建时间*/
    private Date createDate;

    /** 是否删除 0：否，1：是*/
    private Integer isDel;

    /** 备注*/
    private String remark;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

   
    public String getFid() {
        return fid;
    }

   
    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    
    public String getSyncSn() {
        return syncSn;
    }

   
    public void setSyncSn(String syncSn) {
        this.syncSn = syncSn == null ? null : syncSn.trim();
    }

    /** 逻辑id*/
    public String getOrderSn() {
        return orderSn;
    }

    /** 逻辑id*/
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    
    public Integer getCallStatus() {
        return callStatus;
    }

    
    public void setCallStatus(Integer callStatus) {
        this.callStatus = callStatus;
    }

    
    

   
    public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getResultMsg() {
        return resultMsg;
    }

    
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    
    public String getCreateId() {
        return createId;
    }

    
    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public Integer getIsDel() {
        return isDel;
    }

    
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}