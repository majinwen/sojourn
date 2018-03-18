package com.zra.common.vo.contract;

/**
 * <p>合同详情页信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月19日
 * @since 1.0
 */
public class PreContractVo {
	
	private String preContractId; //前合同ID
	private String preContractCode; //前合同code
	
	private String text;//文本

	public String getPreContractId() {
		return preContractId;
	}

	public void setPreContractId(String preContractId) {
		this.preContractId = preContractId;
	}

	public String getPreContractCode() {
		return preContractCode;
	}

	public void setPreContractCode(String preContractCode) {
		this.preContractCode = preContractCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
