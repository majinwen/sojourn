package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/19 14:03
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowQueryVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 6743519083984451438L;

    @FieldMeta(name="订单号",order=10)
    private String orderSn;

    @FieldMeta(name="国家",order=20)
    private String nationName;

    @FieldMeta(name="城市",order=30)
    private String cityName;

    @FieldMeta(name="预订人姓名",order=40)
    private String userName;

    @FieldMeta(name="预订人手机号",order=50)
    private String userTel;

    @FieldMeta(name="房东姓名",order=60)
    private String landlordName;

    @FieldMeta(name="房东电话",order=70)
    private String landlordTel;

    @FieldMeta(name="地推管家",order=80)
    private String empPushName;

    @FieldMeta(name="维护管家",order=90)
    private String empGuardName;

    @FieldMeta(name="创建时间",order=100)
    private Date createTime;

    @FieldMeta(name="入住时间",order=110)
    private Date startTime;

    @FieldMeta(name="离开时间",order=120)
    private Date endTime;

    @FieldMeta(skip = true)
    private Integer payStatus;

    @FieldMeta(name="支付状态",order=130)
    private String payStatusName;

    @FieldMeta(name="订单金额",order=140)
    private Integer orderMoney;

    @FieldMeta(name="实际成交金额",order=150)
    private Integer realMoney;

    @FieldMeta(skip = true)
    private Integer orderStatus;

    @FieldMeta(name="订单状态",order=160)
    private String orderStatusName;

    @FieldMeta(skip = true)
    private Integer followOrderStatus;

    @FieldMeta(name="客服跟进时订单状态",order=170)
    private String followOrderStatusName;

    @FieldMeta(name="跟进人",order=180)
    private String followPeople;
    
    /*
     * 国家码
     */
    private String nationCode;
    
    /*
     * 城市码
     */
    private String cityCode;
    

    public String getFollowOrderStatusName() {
        return followOrderStatusName;
    }

    public void setFollowOrderStatusName(String followOrderStatusName) {
        this.followOrderStatusName = followOrderStatusName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getLandlordTel() {
        return landlordTel;
    }

    public void setLandlordTel(String landlordTel) {
        this.landlordTel = landlordTel;
    }

    public String getEmpPushName() {
        return empPushName;
    }

    public void setEmpPushName(String empPushName) {
        this.empPushName = empPushName;
    }

    public String getEmpGuardName() {
        return empGuardName;
    }

    public void setEmpGuardName(String empGuardName) {
        this.empGuardName = empGuardName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getFollowPeople() {
        return followPeople;
    }

    public void setFollowPeople(String followPeople) {
        this.followPeople = followPeople;
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getFollowOrderStatus() {
        return followOrderStatus;
    }

    public void setFollowOrderStatus(Integer followOrderStatus) {
        this.followOrderStatus = followOrderStatus;
    }

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}
