package com.ziroom.minsu.entity.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券绑定关系实体
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
public class ActCouponUserEntity extends ActCouponEntity {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -817926209180463816L;

	/** 绑定用户uid */
	private String uid;
	/** 绑定用户手机号 */
	private String customerMobile;
	/** 使用时间 */
	private Date usedTime;
	/** 订单编号 */
	private String orderSn;
	/**
	 * 城市列表
	 */
    List<ActivityCityEntity> cityList = new ArrayList();
	/**
	 * 限制房源列表
	 */
	List<ActivityHouseEntity> limitHouseList = new ArrayList<>();


    public List<ActivityCityEntity> getCityList() {
        return cityList;
    }

    public void setCityList(List<ActivityCityEntity> cityList) {
        this.cityList = cityList;
    }

    public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public List<ActivityHouseEntity> getLimitHouseList() {
		return limitHouseList;
	}

	public void setLimitHouseList(List<ActivityHouseEntity> limitHouseList) {
		this.limitHouseList = limitHouseList;
	}
}
