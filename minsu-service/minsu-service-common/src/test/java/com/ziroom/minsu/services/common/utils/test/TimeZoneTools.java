/**
 * @FileName: Date.java
 * @Package com.ziroom.minsu.services.common.utils.test
 * 
 * @author yd
 * @created 2017年3月23日 下午4:39:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;

/**
 * <p>时区处理</p>
 * http://wenku.baidu.com/link?url=iinOu-7PDSVAI6dxTnhQYknNLB99Er_gq3K5cAq9wXpOnoMPvX_xO764bwy7n6m2Xc7yL11GDg5KZEiY5HbjZX68M9i-MTPXYpVmEoyDrFq
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
public class TimeZoneTools {




	/**
	 * 
	 * TODO
	 *
	 * @author yd
	 * @created 2017年3月23日 下午4:54:25
	 *
	 * @param d
	 * @param amount
	 * @return
	 */
	public static Date calculateByDate(Date d, int amount) {

		return calculate(d, GregorianCalendar.DATE, amount);

	}

	/**
	 * 
	 * 对日期(时间)中由field参数指定的日期成员进行加减计算
	 * 例子: <br>

	 * 如果Date类型的d为 2005年8月20日,那么 <br>

	 * calculate(d,GregorianCalendar.YEAR,-10)的值为1995年8月20日 <br>

	 * 而calculate(d,GregorianCalendar.YEAR,+10)的值为2015年8月20日 <br>
	 *
	 * @author yd
	 * @created 2017年3月23日 下午4:58:34
	 *
	 * @param d
	 * @param field
	 *  日期成员

	 * 日期成员主要有: <br>

	 * 年:GregorianCalendar.YEAR <br>

	 * 月:GregorianCalendar.MONTH <br>

	 * 日:GregorianCalendar.DATE <br>

	 * 时:GregorianCalendar.HOUR <br>

	 * 分:GregorianCalendar.MINUTE <br>

	 * 秒:GregorianCalendar.SECOND <br>

	 * 毫秒:GregorianCalendar.MILLISECOND <br>
	 * @param amount 加减计算的幅度.+n=加n个由参数field指定的日期成员值;-n=减n个由参数field代表的日期成员值.
	 * @return 计算后的日期(时间).
	 */
	private static Date calculate(Date d, int field, int amount){

		if(d == null) return null;
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(d);
		g.add(field, amount);
		return g.getTime();
	}

	/**
	 * 获取所有的时区编号.
	 * 排序规则:按照ASCII字符的正序进行排序.
	 * 排序时候忽略字符大小写.
	 * @author yd
	 * @created 2017年3月23日 下午5:05:51
	 *
	 * @return  所有的时区编号(时区编号已经按照字符[忽略大小写]排序).
	 */
	public static String[] fecthAllTimeZoneIds(){
		Vector<String> v = new Vector<String>();
		String[] ids = TimeZone.getAvailableIDs();
		for (int i = 0; i < ids.length; i++) {
			v.add(ids[i]);
		}
		Collections.sort(v, String.CASE_INSENSITIVE_ORDER);

		v.copyInto(ids);

		v = null;

		return ids;
	}
	
	 /**

	 * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)

	*

	 * @param timeZoneId

	* 时区Id

	 * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)

	 */

	/* private static int getDiffTimeZoneRawOffset(String timeZoneId) {

	  return TimeZone.getDefault().getRawOffset();

	  TimeZone.getTimeZone(timeZoneId).getRawOffset();

	 }*/

	/**
	 * 
	 * 将日期时间字符串根据转换为指定时区的日期时间.
	 *
	 * @author yd
	 * @created 2017年3月23日 下午5:15:44
	 *
	 * @param srcFormater 待转化的日期时间的格式.
	 * @param srcDateTime 待转化的日期时间.
	 * @param dstFormater 目标的日期时间的格式.
	 * @param dstTimeZoneId 目标的时区编号.
	 * @return
	 */
	/*public static String string2Timezone(String srcFormater,
			String srcDateTime, String dstFormater, String dstTimeZoneId){

		if (srcFormater == null || "".equals(srcFormater))

			return null;

		if (srcDateTime == null || "".equals(srcDateTime))

			return null;

		if (dstFormater == null || "".equals(dstFormater))

			return null;

		if (dstTimeZoneId == null || "".equals(dstTimeZoneId))

			return null;

		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);

		try {

			int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);

			Date d = sdf.parse(srcDateTime);

			long nowTime = d.getTime();

			long newNowTime = nowTime - diffTime;

			d = new Date(newNowTime);

			return date2String(dstFormater, d);

		} catch (ParseException e) {

			Log.output(e.toString(), Log.STD_ERR);

			return null;

		} finally {

			sdf = null;

		}

	}*/
	public static void main(String[] args) {
		TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区

		time = TimeZone.getDefault();// 这个是国际化所用的
		System.out.println(time);
		TimeZone.setDefault(time);// 设置时区
		Calendar calendar = Calendar.getInstance();// 获取实例
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//构造格式化模板
		java.util.Date date = calendar.getTime(); //获取Date对象
		String str = new String();
		str = format1.format(date);//对象进行格式化，获取字符串格式的输出
		System.out.println(str);
	}
}
