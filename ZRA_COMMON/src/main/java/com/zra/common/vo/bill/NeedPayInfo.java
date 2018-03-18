package com.zra.common.vo.bill;

import com.zra.common.vo.base.BaseFieldVo;

/**
 * <p>需要支付的信息类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月11日 10:11
 * @since 1.0
 */
public class NeedPayInfo extends BaseFieldVo {
	private Integer needPay; //支付金额
	private String isChangePay; //是否可以修改支付金额	0-不可以 1-可以
	private String isChangePayDesc; // 可以编辑时的提示信息
	private String minPayMoney; // 最小支付金额
	private String minPayMoneyDesc; //最小支付金额提示

	public Integer getNeedPay() {
		return needPay;
	}

	public void setNeedPay(Integer needPay) {
		this.needPay = needPay;
	}

	public String getIsChangePay() {
		return isChangePay;
	}

	public void setIsChangePay(String isChangePay) {
		this.isChangePay = isChangePay;
	}

	public String getIsChangePayDesc() {
		return isChangePayDesc;
	}

	public void setIsChangePayDesc(String isChangePayDesc) {
		this.isChangePayDesc = isChangePayDesc;
	}

	public String getMinPayMoney() {
		return minPayMoney;
	}

	public void setMinPayMoney(String minPayMoney) {
		this.minPayMoney = minPayMoney;
	}

	public String getMinPayMoneyDesc() {
		return minPayMoneyDesc;
	}

	public void setMinPayMoneyDesc(String minPayMoneyDesc) {
		this.minPayMoneyDesc = minPayMoneyDesc;
	}
}
