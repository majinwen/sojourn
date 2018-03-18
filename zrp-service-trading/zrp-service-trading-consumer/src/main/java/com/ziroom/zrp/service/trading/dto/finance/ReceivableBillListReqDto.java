package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p>应收账单列表请求参数</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月14日 15:30
 * @since 1.0
 */
public class ReceivableBillListReqDto {
	private List<String> outContractList; //合同号列表
	private String uid; // 用户id
	private List<String> billNumList; // 账单编号
	private String startDate; // 开始日期
	private String endDate; // 结束日期
	private String houseId; // 项目标识
	private String houseCode;// 房间编号
	private String documentType;// 账单类型
	private Integer verificateStatus;// 核销状态
	private String createDate;// 创建日期
	private Integer startPage;// 起始页
	private Integer pageSize; // 页面大小

	public List<String> getOutContractList() {
		return outContractList;
	}

	public void setOutContractList(List<String> outContractList) {
		this.outContractList = outContractList;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<String> getBillNumList() {
		return billNumList;
	}

	public void setBillNumList(List<String> billNumList) {
		this.billNumList = billNumList;
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

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Integer getVerificateStatus() {
		return verificateStatus;
	}

	public void setVerificateStatus(Integer verificateStatus) {
		this.verificateStatus = verificateStatus;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
