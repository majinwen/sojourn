package com.zra.common.vo.contract;
/**
 * <p>合同列表页</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月1日
 * @since 1.0
 */
public class RentContractListVo {
	
	private String contractId;//合同ID
	private String contractCode;//合同码
	private String contractCodeInfo;//无合同码时的提示信息
	private String rentTitle;//标题
	private String rentTime;//租期
	private String roomSalesPrice;//出租价格
	private String proHeadFigureUrl;//头图地址
	private String handleZOPhone;//ZO手机号
	private Integer showContactZO;//是否显示联系管家
	private String constatus;//合同状态
	private Integer constatusCode;//合同状态码
	private Integer operationCode;//操作码
	private String operation;//操作
	//add by xiangbin 2017年11月3日
	private String renewContractId;//续约合同ID
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getRentTitle() {
		return rentTitle;
	}
	public void setRentTitle(String rentTitle) {
		this.rentTitle = rentTitle;
	}
	public String getRentTime() {
		return rentTime;
	}
	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}
	public String getRoomSalesPrice() {
		return roomSalesPrice;
	}
	public void setRoomSalesPrice(String roomSalesPrice) {
		this.roomSalesPrice = roomSalesPrice;
	}
	public String getProHeadFigureUrl() {
		return proHeadFigureUrl;
	}
	public void setProHeadFigureUrl(String proHeadFigureUrl) {
		this.proHeadFigureUrl = proHeadFigureUrl;
	}
	public String getHandleZOPhone() {
		return handleZOPhone;
	}
	public void setHandleZOPhone(String handleZOPhone) {
		this.handleZOPhone = handleZOPhone;
	}
	public Integer getShowContactZO() {
		return showContactZO;
	}
	public void setShowContactZO(Integer showContactZO) {
		this.showContactZO = showContactZO;
	}
	public String getConstatus() {
		return constatus;
	}
	public void setConstatus(String constatus) {
		this.constatus = constatus;
	}
	public Integer getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(Integer operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getContractCodeInfo() {
		return contractCodeInfo;
	}
	public void setContractCodeInfo(String contractCodeInfo) {
		this.contractCodeInfo = contractCodeInfo;
	}
	public Integer getConstatusCode() {
		return constatusCode;
	}
	public void setConstatusCode(Integer constatusCode) {
		this.constatusCode = constatusCode;
	}
	public String getRenewContractId() {
		return renewContractId;
	}
	public void setRenewContractId(String renewContractId) {
		this.renewContractId = renewContractId;
	}
}
