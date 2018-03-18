package com.zra.common.enums;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月9日
 */
public enum PayWayEnum {
	/*YL_IOS_PAY("yl_ios_pay", 12, 13), // 移动ios银联信用卡支付 12; 移动ios银联借记卡支付 13
	YL_AD_PAY("yl_ad_pay", 12, 13), */// 移动安卓银联信用卡支付 12; 移动安卓银联借记卡支付 13
	WX_IOS_PAY("wx_ios_pay", 42),
	WX_AD_PAY("wx_ad_pay", 42);

	private final String payType;
	private final int[] payChannel;

	PayWayEnum(String payType, int... payChannel) {
		this.payType = payType;
		this.payChannel = payChannel;
	}

	public String getPayType() {
		return payType;
	}

	public int[] getPayChannel() {
		return payChannel;
	}
}
