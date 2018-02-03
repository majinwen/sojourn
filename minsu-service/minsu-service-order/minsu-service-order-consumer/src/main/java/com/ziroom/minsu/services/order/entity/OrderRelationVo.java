
package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.order.OrderRelationEntity;

/**
 * <p>新旧订单查询返回实体</p>
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
public class OrderRelationVo  extends OrderRelationEntity implements Serializable  {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4387978109011282469L;
	/**
	 * 已取消房源别名
	 */
	private String oldHouseName;
	
	/**
	 * 新订单房源别名
	 */
	private String newHouseName;
	
	/**
	 * 新订单城市code
	 */
	private String newCityCode;
	
	/**
	 * 新订单城市名称
	 */
	private String newCityName;
	
	/**
	 * 新订单预订人姓名
	 */
	private String newUserName;
	
	/**
	 * 新订单预订人手机号
	 */
	private String newUserTel;
	
	/**
	 * 旧房源租金（单位：分）
	 */
	private Integer oldRentalMoney;
	/**
	 * 新房源租金（单位：分）
	 */
	private Integer newRentalMoney;
	
	/**
	 * 新订单创建日期
	 */
	private Date newCreateTime;

	
	public String getNewCityCode() {
		return newCityCode;
	}

	public void setNewCityCode(String newCityCode) {
		this.newCityCode = newCityCode;
	}

	public String getOldHouseName() {
		return oldHouseName;
	}

	public void setOldHouseName(String oldHouseName) {
		this.oldHouseName = oldHouseName;
	}

	public String getNewHouseName() {
		return newHouseName;
	}

	public void setNewHouseName(String newHouseName) {
		this.newHouseName = newHouseName;
	}

	public String getNewCityName() {
		return newCityName;
	}

	public void setNewCityName(String newCityName) {
		this.newCityName = newCityName;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public String getNewUserTel() {
		return newUserTel;
	}

	public void setNewUserTel(String newUserTel) {
		this.newUserTel = newUserTel;
	}

	public Integer getOldRentalMoney() {
		return oldRentalMoney;
	}

	public void setOldRentalMoney(Integer oldRentalMoney) {
		this.oldRentalMoney = oldRentalMoney;
	}

	public Integer getNewRentalMoney() {
		return newRentalMoney;
	}

	public void setNewRentalMoney(Integer newRentalMoney) {
		this.newRentalMoney = newRentalMoney;
	}

	public Date getNewCreateTime() {
		return newCreateTime;
	}

	public void setNewCreateTime(Date newCreateTime) {
		this.newCreateTime = newCreateTime;
	}
	
	
	
}
