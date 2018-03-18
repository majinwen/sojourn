package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderStaticsVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */

public class OrderStaticsVo extends BaseEntity {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="城市编号",order=1)
	private String cityCode;
	
	@FieldMeta(name="城市名称",order=2)
	private String cityName;
	
	@FieldMeta(name="总订单量",order=3)
	private Integer orderNum;

	@FieldMeta(name="总间夜",order=4)
	private Integer diffDay;
	
	@FieldMeta(name="房租租金",order=5)
	private Double rentalMoney;
	
	@FieldMeta(name="押金",order=6)
	private Double depositMoney;
	
	@FieldMeta(name="服务费",order=7)
	private Double serviceMoney;
	
	@FieldMeta(name="总交易额",order=8)
	private Double needMoney;
	
	@FieldMeta(name="居住间夜",order=9)
	private Integer stayNight;
	
	@FieldMeta(name="居住间夜服务费",order=10)
	private Double stayNightServiceMoney;


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getDiffDay() {
		return diffDay;
	}

	public void setDiffDay(Integer diffDay) {
		this.diffDay = diffDay;
	}

	public Double getRentalMoney() {
		return rentalMoney;
	}

	public void setRentalMoney(Double rentalMoney) {
		this.rentalMoney = rentalMoney;
	}

	public Double getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(Double depositMoney) {
		this.depositMoney = depositMoney;
	}

	public Double getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Double serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public Double getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(Double needMoney) {
		this.needMoney = needMoney;
	}

	public Integer getStayNight() {
		return stayNight;
	}

	public void setStayNight(Integer stayNight) {
		this.stayNight = stayNight;
	}

	public Double getStayNightServiceMoney() {
		return stayNightServiceMoney;
	}

	public void setStayNightServiceMoney(Double stayNightServiceMoney) {
		this.stayNightServiceMoney = stayNightServiceMoney;
	}
}
