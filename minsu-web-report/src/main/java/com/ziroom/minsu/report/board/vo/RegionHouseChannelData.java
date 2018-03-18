/**
 * @FileName: RegionHouseChannelData.java
 * @Package com.ziroom.minsu.report.board.vo
 * 
 * @author bushujie
 * @created 2017年1月17日 下午2:37:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>大区房源渠道数据</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class RegionHouseChannelData extends BaseEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5307290010469123709L;
	/**
	 * 区域名称
	 */
	private String regionName;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 地推目标
	 */
	private Integer toPushTarget;
	/**
	 * 地推发布
	 */
	private Integer toPushNum;
	/**
	 * 地推上架
	 */
	private Integer toPushUpNum;
	/**
	 * 地推达成率
	 */
	private Double toPushRate;
	/**
	 * 地推达成率字符串
	 */
	private String toPushRateS;
	/**
	 * 地推占比
	 */
	private Double toPushDuty;
	/**
	 * 地推占比字符串
	 */
	private String toPushDutyS;
	/**
	 * 地推环比上月
	 */
	private Double toPushQoQ;
	/**
	 * 地推环比上月字符串
	 */
	private String toPushQoQS;
	/**
	 * 自主发布目标
	 */
	private Integer autoTarget;
	/**
	 * 自主发布
	 */
	private Integer autoNum;
	/**
	 * 自主上架
	 */
	private Integer autoUpNum;
	/**
	 * 自主达成率
	 */
	private Double autoRate;
	/**
	 * 自主达成率字符串
	 */
	private String autoRateS;
	/**
	 * @return the toPushRateS
	 */
	public String getToPushRateS() {
		return toPushRateS;
	}

	/**
	 * @param toPushRateS the toPushRateS to set
	 */
	public void setToPushRateS(String toPushRateS) {
		this.toPushRateS = toPushRateS;
	}

	/**
	 * @return the toPushDutyS
	 */
	public String getToPushDutyS() {
		return toPushDutyS;
	}

	/**
	 * @param toPushDutyS the toPushDutyS to set
	 */
	public void setToPushDutyS(String toPushDutyS) {
		this.toPushDutyS = toPushDutyS;
	}

	/**
	 * @return the toPushQoQS
	 */
	public String getToPushQoQS() {
		return toPushQoQS;
	}

	/**
	 * @param toPushQoQS the toPushQoQS to set
	 */
	public void setToPushQoQS(String toPushQoQS) {
		this.toPushQoQS = toPushQoQS;
	}

	/**
	 * @return the autoRateS
	 */
	public String getAutoRateS() {
		return autoRateS;
	}

	/**
	 * @param autoRateS the autoRateS to set
	 */
	public void setAutoRateS(String autoRateS) {
		this.autoRateS = autoRateS;
	}

	/**
	 * @return the autoDutyS
	 */
	public String getAutoDutyS() {
		return autoDutyS;
	}

	/**
	 * @param autoDutyS the autoDutyS to set
	 */
	public void setAutoDutyS(String autoDutyS) {
		this.autoDutyS = autoDutyS;
	}

	/**
	 * @return the autoQoQS
	 */
	public String getAutoQoQS() {
		return autoQoQS;
	}

	/**
	 * @param autoQoQS the autoQoQS to set
	 */
	public void setAutoQoQS(String autoQoQS) {
		this.autoQoQS = autoQoQS;
	}

	/**
	 * 自主占比
	 */
	private Double autoDuty;
	/**
	 * 自主占比字符串
	 */
	private String autoDutyS;
	/**
	 * 自主环比上月
	 */
	private Double autoQoQ;
	/**
	 * 自主环比上月字符串
	 */
	private String autoQoQS;
	
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the toPushTarget
	 */
	public Integer getToPushTarget() {
		return toPushTarget;
	}

	/**
	 * @param toPushTarget the toPushTarget to set
	 */
	public void setToPushTarget(Integer toPushTarget) {
		this.toPushTarget = toPushTarget;
	}

	/**
	 * @return the toPushNum
	 */
	public Integer getToPushNum() {
		return toPushNum;
	}

	/**
	 * @param toPushNum the toPushNum to set
	 */
	public void setToPushNum(Integer toPushNum) {
		this.toPushNum = toPushNum;
	}

	/**
	 * @return the toPushUpNum
	 */
	public Integer getToPushUpNum() {
		return toPushUpNum;
	}

	/**
	 * @param toPushUpNum the toPushUpNum to set
	 */
	public void setToPushUpNum(Integer toPushUpNum) {
		this.toPushUpNum = toPushUpNum;
	}

	/**
	 * @return the toPushRate
	 */
	public Double getToPushRate() {
		return toPushRate;
	}

	/**
	 * @param toPushRate the toPushRate to set
	 */
	public void setToPushRate(Double toPushRate) {
		this.toPushRate = toPushRate;
	}

	/**
	 * @return the toPushDuty
	 */
	public Double getToPushDuty() {
		return toPushDuty;
	}

	/**
	 * @param toPushDuty the toPushDuty to set
	 */
	public void setToPushDuty(Double toPushDuty) {
		this.toPushDuty = toPushDuty;
	}

	/**
	 * @return the toPushQoQ
	 */
	public Double getToPushQoQ() {
		return toPushQoQ;
	}

	/**
	 * @param toPushQoQ the toPushQoQ to set
	 */
	public void setToPushQoQ(Double toPushQoQ) {
		this.toPushQoQ = toPushQoQ;
	}

	/**
	 * @return the autoTarget
	 */
	public Integer getAutoTarget() {
		return autoTarget;
	}

	/**
	 * @param autoTarget the autoTarget to set
	 */
	public void setAutoTarget(Integer autoTarget) {
		this.autoTarget = autoTarget;
	}

	/**
	 * @return the autoNum
	 */
	public Integer getAutoNum() {
		return autoNum;
	}

	/**
	 * @param autoNum the autoNum to set
	 */
	public void setAutoNum(Integer autoNum) {
		this.autoNum = autoNum;
	}

	/**
	 * @return the autoUpNum
	 */
	public Integer getAutoUpNum() {
		return autoUpNum;
	}

	/**
	 * @param autoUpNum the autoUpNum to set
	 */
	public void setAutoUpNum(Integer autoUpNum) {
		this.autoUpNum = autoUpNum;
	}

	/**
	 * @return the autoRate
	 */
	public Double getAutoRate() {
		return autoRate;
	}

	/**
	 * @param autoRate the autoRate to set
	 */
	public void setAutoRate(Double autoRate) {
		this.autoRate = autoRate;
	}

	/**
	 * @return the autoDuty
	 */
	public Double getAutoDuty() {
		return autoDuty;
	}

	/**
	 * @param autoDuty the autoDuty to set
	 */
	public void setAutoDuty(Double autoDuty) {
		this.autoDuty = autoDuty;
	}

	/**
	 * @return the autoQoQ
	 */
	public Double getAutoQoQ() {
		return autoQoQ;
	}

	/**
	 * @param autoQoQ the autoQoQ to set
	 */
	public void setAutoQoQ(Double autoQoQ) {
		this.autoQoQ = autoQoQ;
	}
}
