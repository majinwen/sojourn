package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.trading.entity.RentContractActivityEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>付款项类vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月13日 10:29
 * @since 1.0
 */
public class PaymentTermsDto implements Serializable {

	private BigDecimal totalMoney;// 总金额
	private BigDecimal rentPrice; // 日/月房租
	private BigDecimal servicePrice; // 服务费
	private BigDecimal originServicePrice; //原始服务费
	private BigDecimal depositPrice; // 押金 如果为续约则为前合同的押金
	private BigDecimal totalRentPrice;// 总房租
	private Integer conRentYear;// 租赁 日/月
	private BigDecimal renewDepositPriceDiff;// 续约押金价格差值=当前合同押金-前合同押金
	private List<RoomRentBillDto> roomRentBillDtos;// 房租应收账单
	private List<RentContractActivityEntity> actList;//活动信息
	public List<RentContractActivityEntity> getActList() {
		return actList;
	}

	public BigDecimal getOriginServicePrice() {
		return originServicePrice;
	}

	public void setOriginServicePrice(BigDecimal originServicePrice) {
		this.originServicePrice = originServicePrice;
	}

	public void setActList(List<RentContractActivityEntity> actList) {
		this.actList = actList;
	}

	public List<RoomRentBillDto> getRoomRentBillDtos() {
		return roomRentBillDtos;
	}

	public void setRoomRentBillDtos(List<RoomRentBillDto> roomRentBillDtos) {
		this.roomRentBillDtos = roomRentBillDtos;
	}

	public BigDecimal getTotalRentPrice() {
		return totalRentPrice;
	}

	public void setTotalRentPrice(BigDecimal totalRentPrice) {
		this.totalRentPrice = totalRentPrice;
	}

	public Integer getConRentYear() {
		return conRentYear;
	}

	public void setConRentYear(Integer conRentYear) {
		this.conRentYear = conRentYear;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
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

	public BigDecimal getRenewDepositPriceDiff() {
		return renewDepositPriceDiff;
	}

	public void setRenewDepositPriceDiff(BigDecimal renewDepositPriceDiff) {
		this.renewDepositPriceDiff = renewDepositPriceDiff;
	}

}
