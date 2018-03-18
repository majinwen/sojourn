package com.zra.common.vo.contract;
/**
 * <p>合同卡片返回信息</p>
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
public class RentContractCardVo {

	private String contractId;//合同ID
	private String contractCode;//合同码
	private Integer operationCode;//操作码
	private String  operation;//操作
	private String handleZOPhone;//zo手机号
	private String rentTitle;//标题
	private String rentTime;//租期
	private String expireDate;//超时时间
	private String expireDateInfo;//超时提示信息
	private String warnInfo;//提示信息
	
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
	public String getHandleZOPhone() {
		return handleZOPhone;
	}
	public void setHandleZOPhone(String handleZOPhone) {
		this.handleZOPhone = handleZOPhone;
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
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getExpireDateInfo() {
		return expireDateInfo;
	}
	public void setExpireDateInfo(String expireDateInfo) {
		this.expireDateInfo = expireDateInfo;
	}
	public String getWarnInfo() {
		return warnInfo;
	}
	public void setWarnInfo(String warnInfo) {
		this.warnInfo = warnInfo;
	}
	
}
