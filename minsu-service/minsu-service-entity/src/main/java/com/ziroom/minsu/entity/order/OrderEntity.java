package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;
import java.util.Date;

/**
 * <p>订单实体类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderEntity extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -1312312937998L;

    /** id */
    private Integer id;

    /** fid */
    private String fid;

    /** 订单编号 */
    private String orderSn;

    /**  国家的code */
    private String nationCode;

    /**  国家的code */
    private String nationName;

    /**  省code */
    private String provinceCode;
    /** 城市code */
    private String cityCode;

    /** 区域code */
    private String areaCode;

    /**
     * @see com.ziroom.minsu.valenum.order.CheckTypeEnum
     * 结算方式 */
    private Integer checkType;

    /** 订单来源 */
    private Integer orderSource;

    /** 订单状态
     * 10:待确认
     * 20:待入住
     * 30:强制取消
     * 31:房东已拒绝
     * 32:房客取消
     * 33:未支付超时取消
     * 40:已入住
     * 50:退房中
     * 60:待用户确认额外消费
     * 71:提前退房完成
     * 72:正常退房完成 */
    private Integer orderStatus;

    /**
     * 订单原始状态 只用在做更新操作用
     */
    transient private Integer oldStatus;

    /** 付款单状态 */
    private Integer accountsStatus;

    /** 支付状态 */
    private Integer payStatus;

    /** 房东Uid */
    private String landlordUid;

    /** 房东电话 */
    private String landlordTel;

    /** 房东名称 */
    private String landlordName;
    
    /** 入住人数 */
    private Integer peopleNum;
    
    /** 用户Uid */
    private String userUid;

    /** 用户电话 */
    private String userTel;

    /** 用户名称 */
    private String userName;

    /** 订单开始时间 */
    private Date startTime;

    /** 订单结束时间 */
    private Date endTime;

    /** 订单的实际结束时间 */
    private Date realEndTime;
    
    /** 订单支付时间 */
    private Date payTime;

    /** 创建时间 */
    private Date createTime;

    /** 最后修改时间 */
    private Date lastModifyDate;

    /** 是否删除 0：否，1：是 */
    private Integer isDel;

    /** 出行目的 */
    private String tripPurpose;

    /**
     * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
     */
    private Integer evaStatus;

    /**
     * 房东手机号 国际码
     */
    private String landlordTelCode;
    
    /**
     * 房客手机号 国际码
     */
    private String userTelCode;

    public OrderEntity() {
    }

    

    /**
	 * @return the landlordTelCode
	 */
	public String getLandlordTelCode() {
		return landlordTelCode;
	}




	/**
	 * @param landlordTelCode the landlordTelCode to set
	 */
	public void setLandlordTelCode(String landlordTelCode) {
		this.landlordTelCode = landlordTelCode;
	}




	/**
	 * @return the userTelCode
	 */
	public String getUserTelCode() {
		return userTelCode;
	}




	/**
	 * @param userTelCode the userTelCode to set
	 */
	public void setUserTelCode(String userTelCode) {
		this.userTelCode = userTelCode;
	}




	public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getLandlordTel() {
        return landlordTel;
    }

    public void setLandlordTel(String landlordTel) {
        this.landlordTel = landlordTel;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

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
        this.fid = fid;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }


    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getAccountsStatus() {
        return accountsStatus;
    }

    public void setAccountsStatus(Integer accountsStatus) {
        this.accountsStatus = accountsStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
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


	public Integer getEvaStatus() {
		return evaStatus;
	}


	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}


	public Date getPayTime() {
		return payTime;
	}


	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}


    public String getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(String tripPurpose) {
        this.tripPurpose = tripPurpose;
    }


    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

}
