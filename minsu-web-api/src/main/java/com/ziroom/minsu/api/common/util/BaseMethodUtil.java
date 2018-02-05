/**
 * @FileName: BaseMethodUtil.java
 * @Package com.ziroom.minsu.api.common.util
 * 
 * @author bushujie
 * @created 2017年10月18日 下午5:04:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.dto.CancellationPolicyDto;


/**
 * <p>基础工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class BaseMethodUtil {
	
	
	/**
	 * 
	 * 判断json串里的key值的类型
	 *
	 * @author bushujie
	 * @created 2017年10月18日 下午5:10:54
	 *
	 * @param jsonParam
	 * @param jsonKey
	 * @param classType
	 * @return
	 */
	public static<T> boolean isClassByJsonKey (String jsonParam,String jsonKey,Class<T> classType) {
		JSONObject jsonObject=JSONObject.parseObject(jsonParam);
		Object value=jsonObject.get(jsonKey);
		return classType.isInstance(value);
	}
	
	/**
	 * 
	 * 判断json串里是否包含key参数
	 *
	 * @author bushujie
	 * @created 2017年10月19日 下午2:57:34
	 *
	 * @param jsonParam
	 * @param jsonKey
	 * @return
	 */
	public static boolean isContainKey(String jsonParam ,String jsonKey){
		JSONObject jsonObject=JSONObject.parseObject(jsonParam);
		return jsonObject.containsKey(jsonKey);
	}
	
	/**
	 * 
	 * 判断key值是否是数字
	 *
	 * @author bushujie
	 * @created 2017年10月19日 下午5:53:34
	 *
	 * @param jsonParam
	 * @param jsonKey
	 * @return
	 */
	public static boolean isNumberKey(String jsonParam,String jsonKey){
		JSONObject jsonObject=JSONObject.parseObject(jsonParam);
		return StringUtils.isNumeric(jsonObject.getString(jsonKey));
	}

	/**
	 * TODO
	 *
	 * @author bushujie
	 * @created 2017年10月18日 下午5:04:40
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		CancellationPolicyDto dto=new CancellationPolicyDto();
		dto.setDepositMoney(123);
		dto.setHouseBaseFid("123");
		dto.setRentWay(1);
		String jsonParam=JsonEntityTransform.Object2Json(dto);
		System.err.println(jsonParam);
		System.err.println(isClassByJsonKey(jsonParam, "depositMoney", Integer.class));
		System.err.println(isClassByJsonKey(jsonParam, "houseBaseFid", String.class));
		System.err.println(!isNumberKey(jsonParam, "houseBaseFid"));
	}
	

}
