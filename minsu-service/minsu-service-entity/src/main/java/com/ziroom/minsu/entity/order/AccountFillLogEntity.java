package com.ziroom.minsu.entity.order;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>支付充值记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/5/3.
 * @version 1.0
 * @since 1.0
 */
public class AccountFillLogEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3716445895814254191L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 业务关联号、优惠券码
     */
    private String fillSn;

    /**
     * 关联订单号
     */
    private String orderSn;

    /**
     * 业务类型：1：收款充值  2：退租充值
     */
    private Integer bussinessType;
    
    /**
     * 支付时间
     */
    private Date payTime;
    
    /**
     * 充值类型：1：余额 3：冻结金
     */
    private Integer fillType;

    /**
     * 支付平台流水号 即 充值流水号
     */
    private String tradeNo;
    
    /**
     * 城市code
     */
    private String cityCode;
    
    /**
     * 支付方式
     */
    private String payType;
    
    /**
     * fill result
     */
    private String resultMsg;

    /**
     * 目标用户uid
     */
    private String targetUid;

    /**
     * 客户类型 1：客户 2：业主
     */
    private Integer targetType;
    
    /**
     * 金额 充值类型 1：冻结  2：余额
     */
    private Integer fillMoneyType;

    /**
     * 充值金额
     */
    private Integer totalFee;

    /**
     * 充值状态： 0：充值成功 1：充值失败
     */
    private Integer fillStatus;
	/**
     * 执行时间
     */
    private Date runTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    
    public Integer getId() {
        return id;
    }

   
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getFillSn() {
        return fillSn;
    }

    
    public void setFillSn(String fillSn) {
        this.fillSn = fillSn == null ? null : fillSn.trim();
    }

    
    public String getOrderSn() {
        return orderSn;
    }

    
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    
    public Integer getFillType() {
        return fillType;
    }

    
    public void setFillType(Integer fillType) {
        this.fillType = fillType;
    }

    
    public String getTradeNo() {
        return tradeNo;
    }

   
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    
    public String getTargetUid() {
        return targetUid;
    }

    
    public void setTargetUid(String targetUid) {
        this.targetUid = targetUid == null ? null : targetUid.trim();
    }

    
    public Integer getTargetType() {
        return targetType;
    }

    
    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    
    public Integer getTotalFee() {
        return totalFee;
    }

    
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }
    public Integer getFillStatus() {
        return fillStatus;
    }

    
    public void setFillStatus(Integer fillStatus) {
        this.fillStatus = fillStatus;
    }

   
    public String getCreateId() {
        return createId;
    }

   
    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
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


	public Integer getFillMoneyType() {
		return fillMoneyType;
	}


	public void setFillMoneyType(Integer fillMoneyType) {
		this.fillMoneyType = fillMoneyType;
	}


	public String getCityCode() {
		return cityCode;
	}


	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public Integer getBussinessType() {
		return bussinessType;
	}


	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}


	public Date getPayTime() {
		return payTime;
	}


	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getResultMsg() {
		return resultMsg;
	}


	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
    
}