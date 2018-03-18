package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * 
 * <p>经营数据-房东汇总数据</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class LandlordDataItem extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3685684078715770465L;

	@FieldMeta(name="国家/大区",order=1)
	private String regionName;
	
	/**
	 * 城市编码
	 */
	@FieldMeta(skip=true)
	private String cityCode;

	@FieldMeta(name="城市",order=2)
    private String cityName;
    
	@FieldMeta(name="房源量",order=3)
    private Integer totalHouseNum;

	@FieldMeta(name="房东量",order=4)
    private Integer totalLandNum;
    
	@FieldMeta(name="人均维护房源量",order=5)
    private Double perHouseNum;
    
	@FieldMeta(name="体验型房东量",order=6)
    private Integer expLandNum;
    
	@FieldMeta(name="非专业型房东量",order=7)
	private Integer nonProLandNum;
	
	@FieldMeta(name="专业型房东量",order=8)
	private Integer proLandNum;
	
	@FieldMeta(name="体验型房东占比",order=9)
	private Double expRate;
	
	@FieldMeta(name="非专业型房东占比",order=10)
	private Double nonProRate;
	
	@FieldMeta(name="专业型房东占比",order=11)
	private Double proRate;

	@FieldMeta(name="开通立即预定房源数量",order=11)
	private  Integer immediateBookTypeNum;
	
	/**
	 * @return the immediateBookTypeNum
	 */
	public Integer getImmediateBookTypeNum() {
		return immediateBookTypeNum;
	}

	/**
	 * @param immediateBookTypeNum the immediateBookTypeNum to set
	 */
	public void setImmediateBookTypeNum(Integer immediateBookTypeNum) {
		this.immediateBookTypeNum = immediateBookTypeNum;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
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

	public Integer getTotalHouseNum() {
		return totalHouseNum;
	}

	public void setTotalHouseNum(Integer totalHouseNum) {
		this.totalHouseNum = totalHouseNum;
	}

	public Integer getTotalLandNum() {
		return totalLandNum;
	}

	public void setTotalLandNum(Integer totalLandNum) {
		this.totalLandNum = totalLandNum;
	}

	public Double getPerHouseNum() {
		return perHouseNum;
	}

	public void setPerHouseNum(Double perHouseNum) {
		this.perHouseNum = perHouseNum;
	}

	public Integer getExpLandNum() {
		return expLandNum;
	}

	public void setExpLandNum(Integer expLandNum) {
		this.expLandNum = expLandNum;
	}

	public Integer getNonProLandNum() {
		return nonProLandNum;
	}

	public void setNonProLandNum(Integer nonProLandNum) {
		this.nonProLandNum = nonProLandNum;
	}

	public Integer getProLandNum() {
		return proLandNum;
	}

	public void setProLandNum(Integer proLandNum) {
		this.proLandNum = proLandNum;
	}

	public Double getExpRate() {
		return expRate;
	}

	public void setExpRate(Double expRate) {
		this.expRate = expRate;
	}
	
	public Double getNonProRate() {
		return nonProRate;
	}
	
	public void setNonProRate(Double nonProRate) {
		this.nonProRate = nonProRate;
	}

	public Double getProRate() {
		return proRate;
	}

	public void setProRate(Double proRate) {
		this.proRate = proRate;
	}
}