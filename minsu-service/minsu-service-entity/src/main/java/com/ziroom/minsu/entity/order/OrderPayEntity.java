package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class OrderPayEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -221672672185423748L;

	/** id */
    private Integer id;

    /** 逻辑fid */
    private String fid;

    /** 业务编号 */
    private String paySn;
    
    /** 订单编号 */
    private String orderSn;
    
    /** 城市code */
	private String cityCode;
    
    /** 用户uid */
    private String payUid;

    /** 支付类型 */
    private Integer payType;

    /** 支付金额 */
    private Integer payMoney;
    
    /** 应付金额 */
    private Integer needMoney;

    /** 支付状态 */
    private Integer payStatus;

    /** 交易流水号 */
    private String tradeNo;
    
    /** 支付单号 */
    private String payCode;
    
    /** 创建人 fid */
    private String createFid;

    /** 创建时间  */
    private Date createTime;

    /** 最后更新时间 */
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

    public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

   public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public Integer getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(Integer needMoney) {
		this.needMoney = needMoney;
	}

	

	public String getPayUid() {
		return payUid;
	}

	public void setPayUid(String payUid) {
		this.payUid = payUid;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
    
    
}