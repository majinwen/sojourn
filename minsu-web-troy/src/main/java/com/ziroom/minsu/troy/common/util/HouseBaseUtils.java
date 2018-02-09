/**
 * @FileName: HouseBaseUtils.java
 * @Package com.ziroom.minsu.troy.common.util
 * 
 * @author yd
 * @created 2017年9月21日 下午9:13:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.common.util;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.troy.house.controller.HouseMgtController;

/**
 * <p>设置房源相关信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseUtils {

	
	private static Logger logger =LoggerFactory.getLogger(HouseBaseUtils.class);
	
	/**
	 * 
	 * 上架房源审核 获取短信信息
	 *
	 * @author yd
	 * @created 2017年9月21日 下午9:15:33
	 *
	 * @param json
	 * @return
	 */
	public static String  fillHouseAuditSms(String resultJson,int auditPassPicNum ){
		
		String houseSms = "";
		
		if(Check.NuNStr(resultJson)) {
			return houseSms;
		}
		//从返回值中获取审核通过字段的相关信息，从中解析字段描述，供发送短信使用
		List<HouseFieldAuditLogVo> houseFieldAuditLogVos;
		try {
			houseFieldAuditLogVos = SOAResParseUtil.getListValueFromDataByKey(resultJson,"houseFieldAuditLogVos",HouseFieldAuditLogVo.class);
			StringBuilder auditPassFieldStrs = new StringBuilder();
			if(!Check.NuNCollection(houseFieldAuditLogVos)){
				for(HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVos){
					auditPassFieldStrs.append(houseFieldAuditLogVo.getFieldDesc());
					auditPassFieldStrs.append("、");
				}
				auditPassFieldStrs.deleteCharAt(auditPassFieldStrs.length()-1);
			}
			if(auditPassPicNum!=0){
				if(!Check.NuNStr(auditPassFieldStrs.toString())){
					auditPassFieldStrs.append("、");
				}
				auditPassFieldStrs.append(auditPassPicNum).append("张房源照片");
			}
			houseSms = auditPassFieldStrs.toString();
		} catch (SOAParseException e) {
			LogUtil.error(logger, "【上架房源审核获取短信内容异常】resultJson={},auditPassPicNum={},e={}", resultJson,auditPassPicNum,e);
		}
	
		return houseSms;
	}
}
