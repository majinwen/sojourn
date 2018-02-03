package com.ziroom.minsu.services.house.entity;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>房东-我的收益</p>
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
public class LandlordRevenueVo extends BaseEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5793930858331914098L;
	
	/**
	 * 房源逻辑id
	 */
	private String houseBaseFid;

	/**
	 * 累计收益
	 */
	private Integer totalRevenue;
	
	/**
	 * 月累计收益
	 */
	private Integer monthRevenue;
	
	/**
	 * 周累计收益
	 */
	private Integer weekRevenue;
	
	/**
	 * 收益名称
	 */
	private String revenueName;
	
	/**
	 * 月收益列表
	 */
	private List<LandlordRevenueVo> monthRevenueList;

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public Integer getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Integer totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Integer getMonthRevenue() {
		return monthRevenue;
	}

	public void setMonthRevenue(Integer monthRevenue) {
		this.monthRevenue = monthRevenue;
	}

	public Integer getWeekRevenue() {
		return weekRevenue;
	}

	public void setWeekRevenue(Integer weekRevenue) {
		this.weekRevenue = weekRevenue;
	}

	public String getRevenueName() {
		return revenueName;
	}

	public void setRevenueName(String revenueName) {
		this.revenueName = revenueName;
	}

	public List<LandlordRevenueVo> getMonthRevenueList() {
		return monthRevenueList;
	}

	public void setMonthRevenueList(List<LandlordRevenueVo> monthRevenueList) {
		this.monthRevenueList = monthRevenueList;
	}

}
