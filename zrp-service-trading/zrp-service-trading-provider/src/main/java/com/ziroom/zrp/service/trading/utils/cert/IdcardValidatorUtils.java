package com.ziroom.zrp.service.trading.utils.cert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.asura.framework.base.util.Check;

public class IdcardValidatorUtils {
	
	/**
	 * 根据签约人的身份身份证号校验是否允许签约的年龄（大于0岁小于40岁）
	 * @author tianxf9
	 * @param cus
	 * @return -1:身份证不合法；0：年龄不合法；1：年龄合法；
	 */
	public static int isReasonableAge(String certNum) {
		if(Check.NuNStr(certNum)){
			return 0;
		}
		 try {
			 IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor(certNum);
			 Date birthday = idcardInfo.getBirthday();
			 if(birthday!=null) {
				 if(idcardInfo.getAge()>40) {
					 return 0;
				 }else if(idcardInfo.getAge()==40){
					 Date today = new Date();
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					 String todayStr = sdf.format(today);
					 String birthdayStr = sdf.format(birthday);
					 String compBirthDayStr = todayStr.substring(0, 4)+birthdayStr.substring(4, 8);
					 long todayL = sdf.parse(todayStr).getTime();
					 long compBirthDayL = sdf.parse(compBirthDayStr).getTime();
					 if(todayL<compBirthDayL) {
						 return 1;
					 }else {
						 return 0;
					 } 
				 }else {
					 return 1;
				 }
			 }else {
				 return -1;
			 }
		} catch (ParseException e) {
//			LOGGER.error("", e);
		}
		 return 0;
	}

}
