package com.zra.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateTool {
	
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
	private DateTool(){
		
	}
	
	public static String tranDateToYYYYMMDD(Date date) {
        return tranDateToStr(date, FORMAT_YYYYMMDD);
    }

	public static String tranDateToStr(Date date, String format) {
	    DateFormat df = new SimpleDateFormat(format);
	    return df.format(date);
	}
	
	/**
	 * 获取周
	 * @param date()
	 * @return
	 */
	public static Integer getWeek(Date date) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取周|月|年中的天数
	 * @param date 给定时间
	 * @param type 0:周 1:月 2:年
	 * @return
	 */
	public static int getDayOfTime(Date date,Integer type){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(type == 1){
			return calendar.get(Calendar.DAY_OF_MONTH);
		}else if(type == 2){
			return calendar.get(Calendar.DAY_OF_YEAR);
		}else{
			return calendar.get(Calendar.DAY_OF_WEEK);
		}
	}
	
	
	/**
     * 获取当天起始日期 
     * @param date 给定时间
     * @return date eg:Mon May 11 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getDate(Date date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return sdf.parse(dateStr);
	}
	

    /**
     * 获取date所在周的周一（type=1），月的月初(type=2)，季度的季度初(type=3)，年初(type=4)
     * @author tianxf9
     * @param date
     * @param type
     * @return
     * @throws ParseException
     */
	public static Date getStartDate(Date date ,Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(date));
		if(type == 1){
			int offset = getDayOfTime(date,0) == Calendar.SUNDAY ? //偏移量
					-6 : Calendar.MONDAY-getDayOfTime(date,0);
			calendar.add(Calendar.DATE, offset);
		} else if(type == 2){
			calendar.set(Calendar.DAY_OF_MONTH , 1);
		} else if(type==3){
			calendar.set(Calendar.DAY_OF_MONTH , 1);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			int month = calendar.get(Calendar.MONTH)+1;
			int year = calendar.get(Calendar.YEAR);
			if(month==1||month==2||month==3) {
				return sdf.parse(year+"-01-01");
			}else if(month==4||month==5||month==6) {
				return sdf.parse(year+"-04-01");
			}else if(month==7||month==8||month==9) {
				return sdf.parse(year+"-07-01");
			}else {
				return sdf.parse(year+"-10-01");
			}
		}else {
			calendar.set(Calendar.DAY_OF_YEAR , 1);
		}
		return calendar.getTime();
	}
	
	/**
     * 获取当周|月|年结束日期
     * @param date 给定时间
	 * @param type 0:周 1:月 2:年
     * @return Date eg:Sun May 17 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getEndDate(Date date, Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(date));
		if(type == 0){
			int offset = getDayOfTime(date,0) == Calendar.SUNDAY ? 
					-6 : Calendar.MONDAY-getDayOfTime(date,0);
			calendar.add(Calendar.DATE, offset + 6);
		} else if(type == 1){
			calendar.set(Calendar.DAY_OF_MONTH , 1);
			calendar.roll(Calendar.DAY_OF_MONTH , -1);
		} else if(type == 2){
			calendar.set(Calendar.DAY_OF_YEAR , 1);
			calendar.roll(Calendar.DAY_OF_YEAR , -1);
		}
		return calendar.getTime();
	}
	
	/**
     * 获取上周|月|年起始日期
     * @param date 给定时间
	 * @param type 0:周 1:月 2:年
     * @return Date eg:Mon May 11 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getLastStartDate(Date date, Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getStartDate(date,type));
		if(type == 0){
			calendar.add(Calendar.DATE, -7);
		} else if(type == 1){
			calendar.add(Calendar.MONTH , -1);
		} else if(type == 2){
			calendar.add(Calendar.YEAR , -1);
		}
		return calendar.getTime();
	}
	
	
	/**
     * 获取上周|月|年结束日期
     * @param date 给定时间
	 * @param type 0:周 1:月 2:年
     * @return Date eg:Sun May 17 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getLastEndDate(Date date, Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getStartDate(date,type));
		calendar.add(Calendar.DATE , -1);
		return calendar.getTime();
	}
	
	/**
     * 获取下周|月|年起始日期
     * @param date 给定时间
	 * @param type 0:周 1:月 2:年
     * @return Date eg:Mon May 11 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getNextStartDate(Date date, Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getStartDate(date,type));
		if(type == 0){
			calendar.add(Calendar.DATE, 7);
		} else if(type == 1){
			calendar.add(Calendar.MONTH , 1);
		} else if(type == 2){
			calendar.add(Calendar.YEAR , 1);
		}
		return calendar.getTime();
	}

	/**
	 * 获取给定日期的下x天|月|年起始日期
	 *
	 * @param date 给定时间
	 * @param type 0:周 1:月 2:年
	 * @param day  天/月/年 数量
	 * @return 日期
	 */
	public static Date getNextXDate(Date date, Integer type, int day) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (type == 0) {
			calendar.add(Calendar.DATE, day);
		} else if (type == 1) {
			calendar.add(Calendar.MONTH, day);
		} else if (type == 2) {
			calendar.add(Calendar.YEAR, day);
		}
		return calendar.getTime();
	}
	
	
	/**
     * 获取下周|月|年结束日期
     * @param date 给定时间
	 * @param type 0:周 1:月 2:年
     * @return Date eg:Sun May 17 00:00:00 CST 2015
	 * @throws ParseException 
	 */
	public static Date getNextEndDate(Date date, Integer type) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getStartDate(date,type));
		if(type == 0){
			calendar.add(Calendar.DATE, 13);
		} else if(type == 1){
			calendar.add(Calendar.MONTH, 1);
			calendar.roll(Calendar.DAY_OF_MONTH, -1);
		} else if(type == 2){
			calendar.add(Calendar.YEAR, 1);
			calendar.roll(Calendar.DAY_OF_YEAR, -1);
		}
		return calendar.getTime();
	}
	
	/**
	 * 比较两个日期，计算中间的秒数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getIntervalSeconds(Date start, Date end){
		long diff = end.getTime() - start.getTime();
		int seconds  = (int)(diff / 1000);
		return seconds ;
	}
	
	/**
	 * 得到距离现在X小时的时间
	 * @author tianxf9
	 * @param xHours
	 * @return
	 */
	public static Date getDistanceNowXHours(int xHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + xHours);
		return calendar.getTime();
	}

	/**
	 * 当前日志增加几个小时
	 * @author jixd
	 * @created 2017年09月25日 11:12:49
	 * @param
	 * @return
	 */
	public static Date getDatePlusHours(Date date,int hours){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY,hours);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差的月份
	 * @author cuigh6
	 * @Date 2017年10月12日
	 * @param date1 开始日期
	 * @param date2 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2) throws ParseException {
		DateTimeFormatter sdf =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date2, sdf).plus(1, ChronoUnit.DAYS);
		YearMonth before = YearMonth.of(localDate.getYear(), localDate.getMonth());

		YearMonth after = YearMonth.parse(date1,sdf);

		int result = before.getMonthValue() - after.getMonthValue();

		int month = (before.getYear() - after.getYear())*12;

		return month+result;
	}

	public static void main(String[] args) {
		SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time=1515139200*1000L;

		Date t1 = new Date(1515139200*1000);
		String d = format.format(time);

		System.out.println("Format To String(Date):"+d);
		System.out.println("Format To String(Date):"+t1);


	}
}