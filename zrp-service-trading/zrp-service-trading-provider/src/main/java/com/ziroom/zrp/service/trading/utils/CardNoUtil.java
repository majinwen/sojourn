package com.ziroom.zrp.service.trading.utils;

import org.apache.commons.lang.StringUtils;

import com.asura.framework.base.util.Check;

/**
 * <p>隐藏手机号或者证件号中间位为*</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月25日
 * @since 1.0
 */
public class CardNoUtil {
	/**
	 * <p>隐藏手机号中间四位</p>
	 * @author xiangb
	 * @created 2017年10月25日
	 * @param
	 * @return
	 */
	public static String phoneNoHide(String phoneNo){
		if(Check.NuNStr(phoneNo)){
			return null;
		}
		if(phoneNo.length() != 11){
			return null;
		}
		if(!StringUtils.isNumeric(phoneNo)){
			return null;
		}
		phoneNo = phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
		return phoneNo;
	}
	
	/**
	 * <p>隐藏证件号中间几位，只保留两端各两位</p>
	 * @author xiangb
	 * @created 2017年10月25日
	 * @param
	 * @return
	 */
	public static String cardNoHide(String cardNo){
		if(Check.NuNStr(cardNo)){
			return null;
		}
		if(cardNo.length() < 4){
			return null;
		}
		cardNo = cardNo.replaceAll("(\\w{2})\\w*(\\w{2})","$1****$2");
		return cardNo;
	}
}
