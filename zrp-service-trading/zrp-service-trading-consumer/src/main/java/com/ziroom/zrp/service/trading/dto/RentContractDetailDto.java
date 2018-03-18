package com.ziroom.zrp.service.trading.dto;

import com.zra.common.vo.contract.PreContractVo;
import com.zra.common.vo.contract.ProjectInfoVo;
import com.zra.common.vo.pay.RentBillInfoVo;
import com.zra.common.vo.perseon.SignPersonInfoVo;

import java.io.Serializable;
/**
 * <p>查询的签约信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月12日
 * @since 1.0
 */
public class RentContractDetailDto implements Serializable{
	private static final long serialVersionUID = 2760783994395716356L;
		//合同表中的的信息
	    private String contractId;//合同标识
	    private String contractCode;//合同码
	    private Integer closeContract;//是否可以关闭合同0:不可以关闭，1：可以关闭
	    private Integer conStatusCode;//合同状态码
	    private String conStatus;//合同状态名称
	    private String handleZOPhone;//签约管家手机号
	    private String headCarefulInfo;//头部提示信息
	    private String headCarefulDate;//头部提示信息的替换部分
	    private String contactZO;//显示“联系管家”，不赋值则不显示
	    private ProjectInfoVo projectInfo;//合同头部信息
	    private String roomInfo;//房间信息
	    private SignPersonInfoVo signPerson;//签约人信息
	    private Integer contractLinkType;//合同链接类型1：html,2：pdf，3:给予提示"您已签署纸质合同，所有信息均以纸质合同为准"
	    private String contractLinkUrl;//合同链接
	    
	    private String rentTime;//租期
	    private String payItem;//支付方式
	    private String tailCarefulInfo;//提示信息
	    
	    private String operation;//操作描述
	    private Integer operationCode;//操作码
	    
	    private RentBillInfoVo billInfo;//账单总额
	    
	    private PreContractVo preContractInfo;
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

		public Integer getCloseContract() {
			return closeContract;
		}

		public void setCloseContract(Integer closeContract) {
			this.closeContract = closeContract;
		}

		public Integer getConStatusCode() {
			return conStatusCode;
		}

		public void setConStatusCode(Integer conStatusCode) {
			this.conStatusCode = conStatusCode;
		}

		public String getConStatus() {
			return conStatus;
		}

		public void setConStatus(String conStatus) {
			this.conStatus = conStatus;
		}

		public String getHandleZOPhone() {
			return handleZOPhone;
		}

		public void setHandleZOPhone(String handleZOPhone) {
			this.handleZOPhone = handleZOPhone;
		}

		public String getContactZO() {
			return contactZO;
		}

		public void setContactZO(String contactZO) {
			this.contactZO = contactZO;
		}

		public ProjectInfoVo getProjectInfo() {
			return projectInfo;
		}

		public void setProjectInfo(ProjectInfoVo projectInfo) {
			this.projectInfo = projectInfo;
		}

		public String getRoomInfo() {
			return roomInfo;
		}

		public void setRoomInfo(String roomInfo) {
			this.roomInfo = roomInfo;
		}

		public SignPersonInfoVo getSignPerson() {
			return signPerson;
		}

		public void setSignPerson(SignPersonInfoVo signPerson) {
			this.signPerson = signPerson;
		}

		public Integer getContractLinkType() {
			return contractLinkType;
		}

		public void setContractLinkType(Integer contractLinkType) {
			this.contractLinkType = contractLinkType;
		}

		public String getContractLinkUrl() {
			return contractLinkUrl;
		}

		public void setContractLinkUrl(String contractLinkUrl) {
			this.contractLinkUrl = contractLinkUrl;
		}

		public String getRentTime() {
			return rentTime;
		}

		public void setRentTime(String rentTime) {
			this.rentTime = rentTime;
		}

		public String getPayItem() {
			return payItem;
		}

		public void setPayItem(String payItem) {
			this.payItem = payItem;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public Integer getOperationCode() {
			return operationCode;
		}

		public void setOperationCode(Integer operationCode) {
			this.operationCode = operationCode;
		}

		public RentBillInfoVo getBillInfo() {
			return billInfo;
		}

		public void setBillInfo(RentBillInfoVo billInfo) {
			this.billInfo = billInfo;
		}

		public String getHeadCarefulInfo() {
			return headCarefulInfo;
		}

		public void setHeadCarefulInfo(String headCarefulInfo) {
			this.headCarefulInfo = headCarefulInfo;
		}

		public String getTailCarefulInfo() {
			return tailCarefulInfo;
		}

		public void setTailCarefulInfo(String tailCarefulInfo) {
			this.tailCarefulInfo = tailCarefulInfo;
		}

		public String getHeadCarefulDate() {
			return headCarefulDate;
		}

		public void setHeadCarefulDate(String headCarefulDate) {
			this.headCarefulDate = headCarefulDate;
		}

		public PreContractVo getPreContractInfo() {
			return preContractInfo;
		}

		public void setPreContractInfo(PreContractVo preContractInfo) {
			this.preContractInfo = preContractInfo;
		}

		public String getRenewContractId() {
			return renewContractId;
		}

		public void setRenewContractId(String renewContractId) {
			this.renewContractId = renewContractId;
		}
		
}
