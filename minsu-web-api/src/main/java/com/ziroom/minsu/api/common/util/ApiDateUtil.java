/**
 * @FileName: ApiDateUtil.java
 * @Package com.ziroom.minsu.api.common.util
 * 
 * @author jixd
 * @created 2016年5月14日 上午11:06:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.asura.framework.base.util.Check;

/**
 * <p>TODO</p>
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
public class ApiDateUtil {
	
	/**
	 * 
	 * 6月3日14:00入住
	 *
	 * @author jixd
	 * @created 2016年5月8日 下午2:15:33
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
	 * 获取超时时间
	 *
	 * @author jixd
	 * @created 2016年5月14日 上午11:07:14
	 *
	 * @param date
	 * @param minute
	 * @return
	 */
	public static String getExpireTime(Date date,int minute){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 评价时间是否超时
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
