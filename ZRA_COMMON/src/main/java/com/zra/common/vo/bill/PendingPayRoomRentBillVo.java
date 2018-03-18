package com.zra.common.vo.bill;

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
public class PendingPayRoomRentBillVo extends ReceivableBillInfoVo{
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
}
