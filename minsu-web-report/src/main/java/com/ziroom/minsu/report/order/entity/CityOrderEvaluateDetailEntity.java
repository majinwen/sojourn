package com.ziroom.minsu.report.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p> 城市订单评价明细</p>
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
public class CityOrderEvaluateDetailEntity extends BaseEntity {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="城市编号",order=1)
	private String cityCode;
	
	@FieldMeta(name="城市名称",order=2)
	private String cityName;
	
	@FieldMeta(name="完成订单量",order=3)
	private Integer orderNum=0;

	@FieldMeta(name="房东评价订单量",order=4)
	private Integer lanOrderNum=0;
	
	@FieldMeta(name="房客评价订单量",order=5)
	private Integer userOrderNum=0;

	
	@FieldMeta(name="房东收到的总平均分",order=6)
	private Double sumAvgLanScore = 0.0;
//
	@FieldMeta(name="客户收到的总平均分",order=7)
	private Double sumAvgUserScore = 0.0;
	
	@FieldMeta(name="整洁卫生总评均分",order=8)
	private Double houseAvgCleanScore = 0.0;
	
	@FieldMeta(name="描述相符总评均分",order=9)
	private Double descriptionAvgMatchScore = 0.0;
	
	@FieldMeta(name="安全程度总评均分",order=10)
	private Double safetyAvgDegreeScore = 0.0;
	
	@FieldMeta(name="交通位置总评均分",order=11)
	private Double trafficPositionAvgScore = 0.0;
	
	@FieldMeta(name="性价比总评均分",order=9)
	private Double costPerformanceAvgScore = 0.0;


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

	public Integer getLanOrderNum() {
		return lanOrderNum;
	}

	public void setLanOrderNum(Integer lanOrderNum) {
		this.lanOrderNum = lanOrderNum;
	}

	public Integer getUserOrderNum() {
		return userOrderNum;
	}

	public void setUserOrderNum(Integer userOrderNum) {
		this.userOrderNum = userOrderNum;
	}

	public Double getHouseAvgCleanScore() {
		return houseAvgCleanScore;
	}

	public void setHouseAvgCleanScore(Double houseAvgCleanScore) {
		this.houseAvgCleanScore = houseAvgCleanScore;
	}

	public Double getDescriptionAvgMatchScore() {
		return descriptionAvgMatchScore;
	}

	public void setDescriptionAvgMatchScore(Double descriptionAvgMatchScore) {
		this.descriptionAvgMatchScore = descriptionAvgMatchScore;
	}

	public Double getSafetyAvgDegreeScore() {
		return safetyAvgDegreeScore;
	}

	public void setSafetyAvgDegreeScore(Double safetyAvgDegreeScore) {
		this.safetyAvgDegreeScore = safetyAvgDegreeScore;
	}

	public Double getTrafficPositionAvgScore() {
		return trafficPositionAvgScore;
	}

	public void setTrafficPositionAvgScore(Double trafficPositionAvgScore) {
		this.trafficPositionAvgScore = trafficPositionAvgScore;
	}

	public Double getCostPerformanceAvgScore() {
		return costPerformanceAvgScore;
	}

	public void setCostPerformanceAvgScore(Double costPerformanceAvgScore) {
		this.costPerformanceAvgScore = costPerformanceAvgScore;
	}

	public Double getSumAvgLanScore() {
		return sumAvgLanScore;
	}

	public void setSumAvgLanScore(Double sumAvgLanScore) {
		this.sumAvgLanScore = sumAvgLanScore;
	}

	public Double getSumAvgUserScore() {
		return sumAvgUserScore;
	}

	public void setSumAvgUserScore(Double sumAvgUserScore) {
		this.sumAvgUserScore = sumAvgUserScore;
	}


}
