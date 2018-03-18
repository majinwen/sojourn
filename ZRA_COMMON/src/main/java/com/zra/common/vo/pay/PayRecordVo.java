package com.zra.common.vo.pay;

import com.zra.common.vo.base.BaseFieldVo;

/**
 * <p>支付记录Vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月30日 13:46
 * @since 1.0
 */
public class PayRecordVo extends BaseFieldVo {
	private String paystatus;// 支付状态

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
}
