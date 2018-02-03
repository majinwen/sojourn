/**
 * @FileName: EvaluateUtils.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author yd
 * @created 2016年4月9日 上午10:33:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.utils;

import java.text.DecimalFormat;

/**
 * <p>评论工具类</p>
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
public class EvaluateUtils {
	
	
	/*
	 * 
	 * 获取float值
	 *
	 * @author yd
	 * @created 2016年4月8日 下午5:43:58
	 *
	 * @param total
	 * @param oneTal
	 * @return
	 */
	public static Float getFloatValue(int total,int oneTal,DecimalFormat df){
		if(df == null) df=new DecimalFormat("0.0");
		if(total>0){
			return Float.parseFloat(df.format(oneTal/(float)total));
		}
		return 0f;
	}
}
