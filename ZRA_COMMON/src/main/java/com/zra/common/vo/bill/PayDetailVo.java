package com.zra.common.vo.bill;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>支付详情类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月10日 11:59
 * @since 1.0
 */
public class PayDetailVo {

	private String contractCode;// 合同号
	private String period; //期数
	private String receivableAmount; //应缴金额
	private String receivedAmount; // 已缴金额
	private String projectAddress; // 项目地址
	private String receivedNum; // 支付次数
	private String tips; // 提示
	private List<PayNumDetailVo> list = new ArrayList<>();

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(String receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getReceivedNum() {
		return receivedNum;
	}

	public void setReceivedNum(String receivedNum) {
		this.receivedNum = receivedNum;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<PayNumDetailVo> getList() {
		return list;
	}

	public void setList(List<PayNumDetailVo> list) {
		this.list = list;
	}
}
