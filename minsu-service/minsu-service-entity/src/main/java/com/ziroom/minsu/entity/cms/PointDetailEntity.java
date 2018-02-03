package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 *  
 * <p>积分明细表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class PointDetailEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 20577981344168706L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 所得积分人uid
     */
    private String uid;

    /**
     * 被邀请人uid
     */
    private String inviteUid;

    /**
     * 积分来源 1，邀请好友下单活动
     */
    private Integer pointsSource;

    /**
     * 活动号
     */
    private String actSn;

    /**
     * 积分对应的订单号
     */
    private String orderSn;

    /**
     * 积分值
     */
    private Integer points;

    /**
     * 积分状态  1，有效 2，无效
     */
    private Integer pointsStatus;
    
    /**
     * 积分兑换现金优惠券比例
     */
    private Double pointsExchageCashRate;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getInviteUid() {
        return inviteUid;
    }

    public void setInviteUid(String inviteUid) {
        this.inviteUid = inviteUid == null ? null : inviteUid.trim();
    }

    public Integer getPointsSource() {
        return pointsSource;
    }

    public void setPointsSource(Integer pointsSource) {
        this.pointsSource = pointsSource;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn == null ? null : actSn.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPointsStatus() {
        return pointsStatus;
    }

    public void setPointsStatus(Integer pointsStatus) {
        this.pointsStatus = pointsStatus;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

	public Double getPointsExchageCashRate() {
		return pointsExchageCashRate;
	}

	public void setPointsExchageCashRate(Double pointsExchageCashRate) {
		this.pointsExchageCashRate = pointsExchageCashRate;
	}

}