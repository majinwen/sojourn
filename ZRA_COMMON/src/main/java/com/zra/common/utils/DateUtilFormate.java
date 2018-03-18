package com.zra.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtilFormate {
   
	private static final Logger logger = LoggerFactory.getLogger(DateUtilFormate.class);
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	
	public static  final String DATEFORMAT_1  = "yyyyMMddHHmmss";
	public static  final String DATEFORMAT_2  = "MM-dd HH:mm";
	public static  final String DATEFORMAT_3  = "yyyyMMdd";
	public static  final String DATEFORMAT_4  = "yyyy-MM-dd";
	public static  final String DATEFORMAT_5  = "yyyy-MM-dd HH:mm";
	public static  final String DATEFORMAT_6  = "yyyy-MM-dd HH:mm:ss";

	private DateUtilFormate(){
		
	}
	
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getCurrentDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT_6);
		String dateString = formatter.format(currentTime);

		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			logger.error("获取现在时间", e);
		}
		return date;
	}

	/**
	 * 在给定时间上 加20分钟
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getAddMinutes(Date date, int minute) {
		Date afterDate = new Date(date.getTime() + minute * 60 * 1000);
		return afterDate;
	}

	/**
	 * date 转 String
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDateToString(date,DATEFORMAT_6);
	}

	public static String formatDateToString(Date time,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
	
	/**
	 * String to date
	 * 
	 * @param time
	 * @return
	 */
	public static Date formatStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_6);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
            logger.error("", e);
		}
		return date;
	}

	/**
	 * String to date
	 *
	 * @param time
	 * @return
	 */
	public static Date formatDateStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_4);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return date;
	}
	
	public static Date formatStringToDate(String time,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return date;
	}

	/**
	 * 功能说明：获取两个时间之间相差的月份数组
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 两个时间的相差的月份数组
	 * @DATE:2015-04-02
	 */
	public static Long[] getAllMonths(Date start, Date end) {
		List<Long> months = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		if (c1.get(Calendar.DAY_OF_MONTH) > 1) {
			c2.add(Calendar.MONTH, 1);
		}
		while (c1.compareTo(c2) < 0) {
			Date ss = c1.getTime();
			Long str = Long.valueOf(sdf.format(ss));
			months.add(str);
			c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
		}
		Long[] str = new Long[months.size()];
		for (int i = 0, j = months.size(); i < j; i++) {
			str[i] =  months.get(i);
		}
		return str;
	}

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " 天 " + hours + " 小时 " + minutes + " 分钟 " + seconds
				+ " 秒 ";
	}
	
	
	public static Date setHours(Date date,int hours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}
	
	/**
	 * 加小时
	 * @return
	 */
	public static Date addHours(int hours){
		return addHours(new Date(),hours);
	}
	
	/**
	 * 加小时
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date,int hours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}
	
	/**
	 * 获取几点
	 * @return
	 */
	public static int getHourOfDay(){
		return getHourOfDay(new Date());
	}
	
	/**
	 * 根据日期获取几点
	 * @param date
	 * @return
	 */
	public static int getHourOfDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取明天日期
	 * @return
	 */
	public static Date getTomorrowDate(){
		return addDate(new Date(),1);
	}
	
	/**
	 * 日期加天数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDate(Date date,int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 添加月
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public static Date addMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 添加年
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public static Date addYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}
	
	/**
	 * 获取当前日期为星期几
	 * @return
	 */
	public static int getWeekDay(){
		return getWeekDay(new Date());
	}
	
	/**
	 * 指定日期获取星期几
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int week =  cal.get(Calendar.DAY_OF_WEEK);
		if(week == 1){
			return 7;
		}else{
			return week - 1;
		}
	}
	
    /**
     * 添加一天
     *
     * @param weekDay
     * @return
     */
    public static String getAddWeekDay(int weekDay) {
    	int newweekDay = weekDay;
        if (weekDay == 7) {
        	newweekDay = 1;
        } else {
        	newweekDay++;
        }
        return String.valueOf(newweekDay);
    }

    public static void main(String[] args){
    	String dateStr = "2018-10-22";
    	Date date = formatDateStringToDate(dateStr);
    	System.err.print(date);
	}

}
