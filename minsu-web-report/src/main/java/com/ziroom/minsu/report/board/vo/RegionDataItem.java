package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * 
 * <p>目标看板-大区数据</p>
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
public class RegionDataItem extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -955286564219370482L;
	
	/**
	 * 大区(国家)名称
	 */
	@FieldMeta(name="国家/大区",order=1)
	private String regionName;
	
	/**
	 * 城市编码
	 */
	@FieldMeta(skip=true)
	private String cityCode;

    /**
     * 城市名称
     */
	@FieldMeta(name="城市",order=2)
    private String cityName;
    
    /**
     * 累计房源量
     */
	@FieldMeta(name="累计房源量",order=3)
    private Integer totalHouseNum;

    /**
     * 房源目标
     */
	@FieldMeta(name="房源目标",order=4)
    private Integer targetHouseNum;
    
    /**
     * 房源发布
     */
	@FieldMeta(name="房源发布",order=5)
    private Integer issueHouseNum;
    
    /**
     * 上架数量
     */
	@FieldMeta(name="上架数量",order=6)
    private Integer onlineHouseNum;
    
    /**
     * 房源达成率
     */
	@FieldMeta(name="房源达成率",order=7)
    private Double houseRate;
    
    /**
     * 房源环比上月
     */
	@FieldMeta(name="环比上月",order=8)
    private Double houseQoQ;

    /**
     * 订单目标
     */
	@FieldMeta(name="订单目标",order=9)
    private Integer targetOrderNum;
    
    /**
     * 订单数量
     */
	@FieldMeta(name="订单数量",order=10)
    private Integer actualOrderNum;
    
    /**
     * 订单达成率
     */
	@FieldMeta(name="订单达成率",order=11)
    private Double orderRate;
    
    /**
     * 订单环比上月
     */
	@FieldMeta(name="环比上月",order=12)
    private Double orderQoQ;

    /**
     * 间夜目标
     */
	@FieldMeta(name="间夜目标",order=13)
    private Integer targetRentNum;
	
	/**
	 * 可出租天数
	 */
	@FieldMeta(skip=true)
	private Integer rentableNum;
    
    /**
     * 间夜数量
     */
	@FieldMeta(name="间夜数量",order=14)
    private Integer actualRentNum;
    
    /**
     * 间夜达成率
     */
	@FieldMeta(name="间夜达成率",order=15)
    private Double nightRate;
    
    /**
     * 平台达成率
     */
	@FieldMeta(name="平台达成率",order=16)
    private Double rentRate;
    
    /**
     * 出租环比上月
     */
	@FieldMeta(name="环比上月",order=17)
    private Double rentQoQ;

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

	public Integer getTargetHouseNum() {
		return targetHouseNum;
	}

	public void setTargetHouseNum(Integer targetHouseNum) {
		this.targetHouseNum = targetHouseNum;
	}

	public Integer getIssueHouseNum() {
		return issueHouseNum;
	}

	public void setIssueHouseNum(Integer issueHouseNum) {
		this.issueHouseNum = issueHouseNum;
	}

	public Integer getOnlineHouseNum() {
		return onlineHouseNum;
	}

	public void setOnlineHouseNum(Integer onlineHouseNum) {
		this.onlineHouseNum = onlineHouseNum;
	}

	public Double getHouseRate() {
		return houseRate;
	}

	public void setHouseRate(Double houseRate) {
		this.houseRate = houseRate;
	}

	public Double getHouseQoQ() {
		return houseQoQ;
	}

	public void setHouseQoQ(Double houseQoQ) {
		this.houseQoQ = houseQoQ;
	}

	public Integer getTargetOrderNum() {
		return targetOrderNum;
	}

	public void setTargetOrderNum(Integer targetOrderNum) {
		this.targetOrderNum = targetOrderNum;
	}

	public Integer getActualOrderNum() {
		return actualOrderNum;
	}

	public void setActualOrderNum(Integer actualOrderNum) {
		this.actualOrderNum = actualOrderNum;
	}

	public Double getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(Double orderRate) {
		this.orderRate = orderRate;
	}

	public Double getOrderQoQ() {
		return orderQoQ;
	}

	public void setOrderQoQ(Double orderQoQ) {
		this.orderQoQ = orderQoQ;
	}

	public Integer getTargetRentNum() {
		return targetRentNum;
	}

	public void setTargetRentNum(Integer targetRentNum) {
		this.targetRentNum = targetRentNum;
	}

	public Integer getActualRentNum() {
		return actualRentNum;
	}

	public void setActualRentNum(Integer actualRentNum) {
		this.actualRentNum = actualRentNum;
	}

	public Double getNightRate() {
		return nightRate;
	}

	public void setNightRate(Double nightRate) {
		this.nightRate = nightRate;
	}

	public Double getRentRate() {
		return rentRate;
	}

	public void setRentRate(Double rentRate) {
		this.rentRate = rentRate;
	}

	public Double getRentQoQ() {
		return rentQoQ;
	}

	public void setRentQoQ(Double rentQoQ) {
		this.rentQoQ = rentQoQ;
	}

	public Integer getRentableNum() {
		return rentableNum;
	}

	public void setRentableNum(Integer rentableNum) {
		this.rentableNum = rentableNum;
	}

}