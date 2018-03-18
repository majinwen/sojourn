package com.zra.common.vo.bill;

import java.util.Date;

/**
 * <p>待支付生活费用账单</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月13日 09:58
 * @since 1.0
 */
public class PendingPayLifeBillVo {
	private String costNameList; // 费用项名称 用逗号 隔开
	private String createDate; // 账单生成日期
	private Integer billStatus; // 账单状态
	private Integer operationCode; //操作码
	private String operation; // 操作文本
	private String amount;// 总金额

	private String projectName;
	private String roomNum;
	private String floorNum;
	private String direction;
	private String proHeadFigureUrl;

	private String contractCode;
	private String contractId;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProHeadFigureUrl() {
		return proHeadFigureUrl;
	}

	public void setProHeadFigureUrl(String proHeadFigureUrl) {
		this.proHeadFigureUrl = proHeadFigureUrl;
	}

	public String getCostNameList() {
		return costNameList;
	}

	public void setCostNameList(String costNameList) {
		this.costNameList = costNameList;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
