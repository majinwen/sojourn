package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;
import java.util.List;
/**
 * <p>查询实收信息返回实体</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月17日
 * @since 1.0
 */
public class ReceiptInfoResponseDto implements Serializable{
	
	private String billNum;//String类型 账单编号
	private String outContractCode;//String类型 出房合同号
	private String uid;//String类型
	private String costCode;//String类型 费用项编码
	private List<ReceiptListDto> receiptList;//每次收款的具体信息
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getOutContractCode() {
		return outContractCode;
	}
	public void setOutContractCode(String outContractCode) {
		this.outContractCode = outContractCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public List<ReceiptListDto> getReceiptList() {
		return receiptList;
	}
	public void setReceiptList(List<ReceiptListDto> receiptList) {
		this.receiptList = receiptList;
	}

}
