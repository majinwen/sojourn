package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;
import java.util.List;

/**
 * <p>获取收款单列表请求入参</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月21日 11:05
 * @since 1.0
 */
public class ZRAReceiptBillDto implements Serializable {

	private static final long serialVersionUID = 6976792810205438020L;

	/**
	 * bu   0603
	 */
	private String bussinessUnitCode;

	/**
	 * 公司编码
	 */
	private String companyCode;

	/**
	 * 出房合同号 必传
	 */
	private List outContractList;

	/**
	 * 收款单号
	 */
	private String receiptNo;

	/**
	 * 付款人
	 */
	private String payName;

	/**
	 * 创建日（起始）
	 */
	private String startCreateDate;

	/**
	 * 创建日（截止）
	 */
	private String endCreateDate;

	/**
	 * 支付日（起始）
	 */
	private String startPayDate;

	/**
	 * 支付日（截止）
	 */
	private String endPayDate;

	/**
	 * 收款状态:0已收款,1未收款（默认1），2打回
	 */
	private Integer receiptStatus;

	/**
	 * 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过，4 审核中
	 */
	private Integer auditStatus;

	/**
	 * 付款渠道：（对应自如寓线上支付方式）  在线支付-过账户：zxzf 优惠券：yhj 内部：nb 余额：ye 在线支付-不过账户：zxzf_bgzh
	 */
	private String payMethod;

	/**
	 * 支付方式（对应自如寓线下支付方式）
	 */
	private String payTypeCode;

	/**
	 * 起始页  必传
	 */
	private Integer startPage;

	/**
	 * 页面大小 必传
	 */
	private Integer pageSize;

	public String getBussinessUnitCode() {
		return bussinessUnitCode;
	}

	public void setBussinessUnitCode(String bussinessUnitCode) {
		this.bussinessUnitCode = bussinessUnitCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public List getOutContractList() {
		return outContractList;
	}

	public void setOutContractList(List outContractList) {
		this.outContractList = outContractList;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getStartPayDate() {
		return startPayDate;
	}

	public void setStartPayDate(String startPayDate) {
		this.startPayDate = startPayDate;
	}

	public String getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(String endPayDate) {
		this.endPayDate = endPayDate;
	}

	public Integer getReceiptStatus() {
		return receiptStatus;
	}

	public void setReceiptStatus(Integer receiptStatus) {
		this.receiptStatus = receiptStatus;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
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
