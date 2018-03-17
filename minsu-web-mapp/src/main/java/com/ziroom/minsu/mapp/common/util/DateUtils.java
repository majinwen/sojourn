/**
 * @FileName: DateUtils.java
 * @Package com.ziroom.minsu.mapp.common
 * 
 * @author jixd
 * @created 2016年5月10日 下午5:36:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.asura.framework.base.util.Check;

/**
 * <p>时间工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class DateUtils {
	
	/**
	 * 
	 * 获取日期格式化时间
	 * 
	 *  周二，4月12日，2016
	 *  
	 * @author jixd
	 * @created 2016年5月10日 下午5:37:43
	 *
	 * @param date
	 * @return
	 */
	public static String getOrderShowTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("E，M月d日，yyyy");
		return sdf.format(date);
	}
	
	
	/**
	 * 
	 * 格式化时间
	 *
	 * @author jixd
	 * @created 2016年5月14日 下午7:23:06
	 *
	 * @param date
	 * @param time
	 * @return
	 */
	public static String getDateStr(Date date,String time){
		SimpleDateFormat format = new SimpleDateFormat("M月d日"); 
		if(!Check.NuNStr(time)){
			time = time.substring(0, time.lastIndexOf(":"));
		}
		String str = format.format(date);
		return str +" "+ time;
	}
	
	
	/**
	 * 
	 * 获取时间戳
	 *
	 * @author jixd
	 * @created 2016年5月24日 下午2:21:42
	 *
	 * @return
	 */
	public static String getTimeStamp(){
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time/1000);  
        return t;  
	}

	/**
	 * 是否超期
	 * @param realEndTime
	 * @param limitDay
	 * @return
	 */
	public static boolean isEvaExpire(Date realEndTime,int limitDay){
		if(Check.NuNObj(realEndTime)){
			return false;
		}
		long between = ((new Date()).getTime() - realEndTime.getTime()) / 1000;
		if(between > (limitDay * 24 * 3600)){
			return true;
		}else{
			return false;
		}
	}

}
