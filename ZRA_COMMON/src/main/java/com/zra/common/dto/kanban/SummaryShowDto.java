package com.zra.common.dto.kanban;

/**
 * 目标看板核心数据返回给前端的Dto
 * @author tianxf9
 *
 */
public class SummaryShowDto {
	
	//项目id
	private String projectId;
	//项目名称
	private String projectName;
	//商机及时跟进率目标
	private String yuekanGjRate;
	//实际商机及时跟进率
	private String ykDealRate;
	//出租率目标
	private String rentalRate;
	//出租率达成
	private String leaseRate;
	//日均入住率
	private String occupancyRate;
	//出租周期目标
	private String rentalCycle;
	//出租周期达成
	private String leaseCycle;
	//回款率目标
	private String voucherRateGoal;
	//实际回款率
	private String voucherRateActual;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getYuekanGjRate() {
		return yuekanGjRate;
	}
	public void setYuekanGjRate(String yuekanGjRate) {
		this.yuekanGjRate = yuekanGjRate;
	}
	public String getYkDealRate() {
		return ykDealRate;
	}
	public void setYkDealRate(String ykDealRate) {
		this.ykDealRate = ykDealRate;
	}
	public String getRentalRate() {
		return rentalRate;
	}
	public void setRentalRate(String rentalRate) {
		this.rentalRate = rentalRate;
	}
	public String getLeaseRate() {
		return leaseRate;
	}
	public void setLeaseRate(String leaseRate) {
		this.leaseRate = leaseRate;
	}
	public String getOccupancyRate() {
		return occupancyRate;
	}
	public void setOccupancyRate(String occupancyRate) {
		this.occupancyRate = occupancyRate;
	}
	public String getRentalCycle() {
		return rentalCycle;
	}
	public void setRentalCycle(String rentalCycle) {
		this.rentalCycle = rentalCycle;
	}
	public String getLeaseCycle() {
		return leaseCycle;
	}
	public void setLeaseCycle(String leaseCycle) {
		this.leaseCycle = leaseCycle;
	}
	public String getVoucherRateGoal() {
		return voucherRateGoal;
	}
	public void setVoucherRateGoal(String voucherRateGoal) {
		this.voucherRateGoal = voucherRateGoal;
	}
	public String getVoucherRateActual() {
		return voucherRateActual;
	}
	public void setVoucherRateActual(String voucherRateActual) {
		this.voucherRateActual = voucherRateActual;
	}
}
