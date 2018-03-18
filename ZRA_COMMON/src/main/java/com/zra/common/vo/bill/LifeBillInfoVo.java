package com.zra.common.vo.bill;

import com.zra.common.vo.base.BaseFieldVo;


import java.util.List;

/**
 * <p>生活费用账单Vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月29日 16:13
 * @since 1.0
 */
public class LifeBillInfoVo extends BaseFieldVo {

	private List<BaseFieldVo> list;
	private Integer operationCode; // 操作码1-去支付 2-支付详情 0-无操作
	private String billNum;//账单编码
	private Integer amount;// 账单金额 单位分
	private Integer delCode;//删除标识 0-无操作 1-可删除
	private String delDesc;//删除描述 作废账单
	
	/**
	 * @return the delCode
	 */
	public Integer getDelCode() {
		return delCode;
	}

	/**
	 * @param delCode the delCode to set
	 */
	public void setDelCode(Integer delCode) {
		this.delCode = delCode;
	}

	/**
	 * @return the delDesc
	 */
	public String getDelDesc() {
		return delDesc;
	}

	/**
	 * @param delDesc the delDesc to set
	 */
	public void setDelDesc(String delDesc) {
		this.delDesc = delDesc;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public List<BaseFieldVo> getList() {
		return list;
	}

	public void setList(List<BaseFieldVo> list) {
		this.list = list;
	}

	public Integer getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(Integer operationCode) {
		this.operationCode = operationCode;
	}
}
