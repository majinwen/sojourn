package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class OrderParamEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6889893630295223440L;

	/** id */
    private Integer id;

    /** 逻辑id */
    private String fid;

    /** 订单编号  */
    private String orderSn;

    /** 参数code */
    private String parCode;

    /** 参数值code */
    private String parValueCode;
    
    /** 参数值 */
    private String parValue;

    /** 创建人 fid*/
    private String createFid;

    /** 创建时间*/
    private Date createTime;

    /** 更新时间*/
    private Date lastModifyDate;

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

    public String getParCode() {
        return parCode;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode == null ? null : parCode.trim();
    }

    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue == null ? null : parValue.trim();
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
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

	public String getParValueCode() {
		return parValueCode;
	}

	public void setParValueCode(String parValueCode) {
		this.parValueCode = parValueCode;
	}
    
}