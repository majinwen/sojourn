package com.ziroom.minsu.services.search.dto;

import java.io.Serializable;
import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class HouseCurrentStatsDto extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7161323736224863836L;

	/**
     * 房源(房间)fid
     */
    private String houseFid;

    /**
     * 出租方式 0：整租，1：合租
     */
    private Integer rentWay; 

    /**
     * 省code
     */
    private String provinceCode;

    /**
     * 市code
     */
    private String cityCode;
	
	/**
	 * 多少天内
	 */
	private Integer days=7;
	
    /**
     * 交易量權重
     */
    private Integer tradeNumWeight=60;

    /**
     * 咨询量權重
     */
    private Integer consultNumWeight=40;

	public String getHouseFid() {
		return houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public Integer getDays() {
		return days;
	}

	public Integer getTradeNumWeight() {
		return tradeNumWeight;
	}

	public Integer getConsultNumWeight() {
		return consultNumWeight;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public void setTradeNumWeight(Integer tradeNumWeight) {
		this.tradeNumWeight = tradeNumWeight;
	}

	public void setConsultNumWeight(Integer consultNumWeight) {
		this.consultNumWeight = consultNumWeight;
	}
	
}
