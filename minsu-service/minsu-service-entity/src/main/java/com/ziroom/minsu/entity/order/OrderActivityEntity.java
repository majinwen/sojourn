package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * 
 * <p>订单活动 记录表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class OrderActivityEntity extends BaseEntity{
    /** 序列化id */
	private static final long serialVersionUID = 7380668097871543955L;

	/** id*/
    private Integer id;

    /** 订单编号  */
    private String orderSn;

    /** 活动id*/
    private String acFid;

    /** 活动名称 */
    private String acName;

    /** 活动 金额*/
    private Integer acMoney;

    /**
     * 活动类型
     * @see com.ziroom.minsu.valenum.order.OrderAcTypeEnum
     */
    private Integer acType;

    /**  使用时间 */
    private Date usedTime;

    /**
     * 优惠券状态
     * @see com.ziroom.minsu.valenum.order.CouponStatusEnum
     */
    private Integer acStatus;
    
    /**
     * 活动优惠金额 分 此值下单固定不在改变
     */
    private  Integer acMoneyAll;

    /** 优惠券状态是否同步*/
    private Integer isSyns;
    
    /**
     * 优惠来源 0：其他 1：房东 3：平台
     */
    private  Integer preferentialSources;
    
    /**
     * 优惠对象 0：其他 1：房东 2：房客
     */
    private  Integer preferentialUser;
    
    /**
     * 活动权重（权重越高 表明有限满足 当前默认给出10）
     */
    private Integer acWeight;
    
    /**
     * 创建时间
     */
    private  Date createTime;
    
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;
    

    /**
	 * @return the acMoneyAll
	 */
	public Integer getAcMoneyAll() {
		return acMoneyAll;
	}

	/**
	 * @param acMoneyAll the acMoneyAll to set
	 */
	public void setAcMoneyAll(Integer acMoneyAll) {
		this.acMoneyAll = acMoneyAll;
	}

	/**
	 * @return the preferentialSources
	 */
	public Integer getPreferentialSources() {
		return preferentialSources;
	}

	/**
	 * @param preferentialSources the preferentialSources to set
	 */
	public void setPreferentialSources(Integer preferentialSources) {
		this.preferentialSources = preferentialSources;
	}

	/**
	 * @return the preferentialUser
	 */
	public Integer getPreferentialUser() {
		return preferentialUser;
	}

	/**
	 * @param preferentialUser the preferentialUser to set
	 */
	public void setPreferentialUser(Integer preferentialUser) {
		this.preferentialUser = preferentialUser;
	}

	/**
	 * @return the acWeight
	 */
	public Integer getAcWeight() {
		return acWeight;
	}

	/**
	 * @param acWeight the acWeight to set
	 */
	public void setAcWeight(Integer acWeight) {
		this.acWeight = acWeight;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifyDate
	 */
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * @param lastModifyDate the lastModifyDate to set
	 */
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Integer getAcType() {
        return acType;
    }

    public void setAcType(Integer acType) {
        this.acType = acType;
    }

    public Integer getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(Integer acStatus) {
        this.acStatus = acStatus;
    }

    public Integer getIsSyns() {
        return isSyns;
    }

    public void setIsSyns(Integer isSyns) {
        this.isSyns = isSyns;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public String getAcFid() {
        return acFid;
    }

    public void setAcFid(String acFid) {
        this.acFid = acFid == null ? null : acFid.trim();
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    public Integer getAcMoney() {
        return acMoney;
    }

    public void setAcMoney(Integer acMoney) {
        this.acMoney = acMoney;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }
}