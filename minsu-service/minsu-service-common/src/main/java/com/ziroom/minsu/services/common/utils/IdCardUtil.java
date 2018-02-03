package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.util.Check;

/**
 * <p>
 * 身份证号工具类
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
public class IdCardUtil {

	private final static int cardLength15 = 15;
	private final static int cardLength18 = 18;

	/**
	 * 根据身份证号获取性别
	 * 
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param card
	 * @return 1：男性，2：女性，-1：错误
	 */
	public static int getSex(String card) {
		if (Check.NuNStr(card)) {
			return -1;
		}

		// 15位身份证倒数第一位，奇数男性，偶数女性
		if (card.length() == cardLength15) {
			if (card.charAt(cardLength15 - 1) % 2 == 0) {
				return 2;
			} else {
				return 1;
			}
		}

		// 18位身份证倒数第二位，奇数男性，偶数女性
		if (card.length() == cardLength18) {
			if (card.charAt(cardLength18 - 2) % 2 == 0) {
				return 2;
			} else {
				return 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int sex = IdCardUtil.getSex("542129198708285884");
		if (sex == 1) {
			System.out.println("男性");
		} else if (sex == 2) {
			System.out.println("女性");
		} else {
			System.out.println("错误");
		}
	}
}
