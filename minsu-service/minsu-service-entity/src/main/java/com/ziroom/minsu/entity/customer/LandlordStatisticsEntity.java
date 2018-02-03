package com.ziroom.minsu.entity.customer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class LandlordStatisticsEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9213620074721525749L;

	/** */
    private Integer id;

    /** */
    private String fid;

    /** */
    private String landlordUid;

    /** */
    private Integer replyPeopleNum;

    /** */
    private Integer imReplySumTime;
    
    /** */
    private Integer orderSumNum;
    

    /** */
    private Integer replyOrderNum;

    /** */
    private Integer lanRefuseOrderNum;

    /** */
    private Integer sysRefuseOrderNum;

    /** */
    private Integer orderReplySumTime;

    /** */
    private Date createTime;

    /** */
    private Date lastModifyDate;

    /** */
    private Integer isDel;

    
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

    public String getLandlordUid() {
        return landlordUid;
    }

    
    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid == null ? null : landlordUid.trim();
    }

    
    public Integer getReplyPeopleNum() {
        return replyPeopleNum;
    }

   
    public void setReplyPeopleNum(Integer replyPeopleNum) {
        this.replyPeopleNum = replyPeopleNum;
    }

   
    public Integer getImReplySumTime() {
        return imReplySumTime;
    }

    
    public void setImReplySumTime(Integer imReplySumTime) {
        this.imReplySumTime = imReplySumTime;
    }

    
    public Integer getReplyOrderNum() {
        return replyOrderNum;
    }

    
    public void setReplyOrderNum(Integer replyOrderNum) {
        this.replyOrderNum = replyOrderNum;
    }

  
    public Integer getLanRefuseOrderNum() {
        return lanRefuseOrderNum;
    }

    
    public void setLanRefuseOrderNum(Integer lanRefuseOrderNum) {
        this.lanRefuseOrderNum = lanRefuseOrderNum;
    }

   
    public Integer getSysRefuseOrderNum() {
        return sysRefuseOrderNum;
    }

    
    public void setSysRefuseOrderNum(Integer sysRefuseOrderNum) {
        this.sysRefuseOrderNum = sysRefuseOrderNum;
    }

    
    public Integer getOrderReplySumTime() {
        return orderReplySumTime;
    }

   
    public void setOrderReplySumTime(Integer orderReplySumTime) {
        this.orderReplySumTime = orderReplySumTime;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

   
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

   
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

   
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

   
    public Integer getIsDel() {
        return isDel;
    }

    
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


	public Integer getOrderSumNum() {
		return orderSumNum;
	}


	public void setOrderSumNum(Integer orderSumNum) {
		this.orderSumNum = orderSumNum;
	}
    
    
}