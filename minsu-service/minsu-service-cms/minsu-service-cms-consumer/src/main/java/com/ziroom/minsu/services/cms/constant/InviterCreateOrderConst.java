/**
 * @FileName: InviterCreateOrderConst.java
 * @Package com.ziroom.minsu.services.cms.constant
 * 
 * @author loushuai
 * @created 2017年12月5日 下午3:42:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.constant;

/**
 * <p>邀请下单活动配置信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class InviterCreateOrderConst {

	
	protected InviterCreateOrderConst() {
	}

	/**
	 * 现金展示单位
	 */
	public static final String moneyUnit  = "元";
	
	/**
	 * 预期奖金标志
	 */
	public static final String expectBonusSymbol = "+";
	
	/**
	 * 预期奖金标志
	 */
	public static final String expectBonusName = "预期奖金";
	
	/**
	 * 积分兑换优惠券的比例（1积分兑换pointsExchageCashRate现金券）
	 */
	public static final Double pointsExchageCashRate = 1.0;
	
	/**
	 * 优惠券过期天数
	 */
	public static final int couponExpireDay = 180;
	
	/**
	 * 邀请人兑换活动组号
	 */
	public static final String inviterGroupSn = "YAOQINGREN";
	
	/**
	 * 被邀请人灌券活动组号
	 */
	public static final String beInviterGroupSn = "SHOUYAOREN";
}
