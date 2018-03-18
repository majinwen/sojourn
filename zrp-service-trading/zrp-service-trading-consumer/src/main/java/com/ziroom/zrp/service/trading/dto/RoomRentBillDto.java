package com.ziroom.zrp.service.trading.dto;

import java.math.BigDecimal;

/**
 * <p>房租账单类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月22日 11:32
 * @since 1.0
 */
public class RoomRentBillDto {
	private Integer period; //期数
	private BigDecimal rentPrice; // 房租
	private BigDecimal servicePrice; // 服务费
	private BigDecimal depositPrice;  //押金
	private String startDate;  // 账单开始日期
	private String endDate;  // 账单结束日期
	private BigDecimal periodTotalMoney;// 期数总金额
	private Integer rentCount;// 租期 月/日
	private BigDecimal periodTotalRentPrice;// 每一期的总房租

	public BigDecimal getPeriodTotalRentPrice() {
		return periodTotalRentPrice;
	}

	public void setPeriodTotalRentPrice(BigDecimal periodTotalRentPrice) {
		this.periodTotalRentPrice = periodTotalRentPrice;
	}

	public Integer getRentCount() {
		return rentCount;
	}

	public void setRentCount(Integer rentCount) {
		this.rentCount = rentCount;
	}

	public BigDecimal getPeriodTotalMoney() {
		return periodTotalMoney;
	}

	public void setPeriodTotalMoney(BigDecimal periodTotalMoney) {
		this.periodTotalMoney = periodTotalMoney;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public BigDecimal getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}

	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}

	public BigDecimal getDepositPrice() {
		return depositPrice;
	}

	public void setDepositPrice(BigDecimal depositPrice) {
		this.depositPrice = depositPrice;
	}
}
