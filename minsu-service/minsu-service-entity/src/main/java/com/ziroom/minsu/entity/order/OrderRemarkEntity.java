package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class OrderRemarkEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5842946546880945406L;

	/** 自增id */
    private Integer id;

    /** fid */
    private String fid;

    /** 订单编号*/
    private String orderSn;

    /** 备注内容 */
    private String remarkContent;

    /** 用户uid */
    private String uid;

    /** 创建时间*/
    private Date createTime;

    /** 是否删除 */
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

   
    public String getOrderSn() {
        return orderSn;
    }

   
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    
    public String getRemarkContent() {
        return remarkContent;
    }

    
    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent == null ? null : remarkContent.trim();
    }

    
    public String getUid() {
        return uid;
    }

    
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

   
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public Integer getIsDel() {
        return isDel;
    }

    
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}