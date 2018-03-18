package com.zra.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/3.
 */
public final class DateUtil {
    private DateUtil() {

    }

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    /**
     * 返回指定格式的字符串日期
     * @param date
     * @param format
     * @return
     */
    public static String DateToStr(Date date, String format) {
        String dateStr = null;
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            dateStr = simpleDateFormat.format(date);
        }
        return dateStr;
    }

    /**
     * 将dateString按给定的pattern转成Date类型，注意要严格匹配
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public static Date castString2Date(String dateString, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern);
        sdf.setLenient(false);
        return sdf.parse(dateString);
    }

    /**
     * 根据处理截止日期判断处理状态
     * 处理状态（处理进度）1：超时未处理  2：今日代办  3：次日代办  4：周内代办  5:7天后代办  6：完成'
     * <p>
     * （1）超时未处理：超过截止处理时间
     * （2）当日待办：没有超过截止处理时间，且时间在截止处理时间当天00:00:00～23:59:59之间
     * （3）次日待办：时间在截止处理时间第二天00:00:00～23:59:59
     * （4）周内待办：时间在截止处理时间次天后并在未来7天之内
     * （5）7天后待办：时间超出截止处理时间7天
     * （6）已完成：商机阶段“成交”或“未成交”，状态变更为“已完成”
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public static Byte getDealStatusByEndTime(Date endDate) throws ParseException {
        SimpleDateFormat sdfNoH = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
//        Date nowDate = sdfNoH.parse(sdfNoH.format(now));//今天，只有年月日小时分钟
        Date endDateNew = sdfNoH.parse(sdfNoH.format(endDate));
        long interval = endDateNew.getTime() - sdf.parse(sdf.format(now)).getTime();//处理截止日期距离今天零点的毫秒数
        long oneDayMilliSeconds = 24L * 60 * 60 * 1000;
        if (endDateNew.before(now)) {
            return 1;
        } else if (interval < oneDayMilliSeconds) {//当日待办
            return 2;
        } else if (interval < 2 * oneDayMilliSeconds) {//次日待办
            return 3;
        } else if (interval < 7 * oneDayMilliSeconds) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * 判断给定的日期是周几，返回值中1：周一；2：周二；3：周三 ……
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public static int getWeekByDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date.getTime() - 24 * 60 * 60 * 1000));
        return c.get(Calendar.DAY_OF_WEEK);
    }
    
    
    /**
     * 根据时间获取该时间的前一天
     * @author tianxf9
     * @param today
     * @return
     * @throws ParseException 
     */
    public static String getYesterDay(String today) throws ParseException {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date= formatter.parse(today);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
        String yesterday = formatter.format(date);
        return yesterday;
    }
    
    /**
     * 根据时间获取该时间的前一天
     * @author tianxf9
     * @param today
     * @return
     * @throws ParseException 
     */
    public static Date getYesterDay(Date today){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE,-1);
        return calendar.getTime(); 
    }
    
    /**
     * 获取tday的下一天
     * @author tianxf9
     * @param preDate
     * @return
     */
    public static Date getNextDay(Date tDay) {
		Calendar calendar = new GregorianCalendar();
        calendar.setTime(tDay);
        calendar.add(calendar.DATE,1);
        return calendar.getTime(); 
    }
    
    /**获取前七天
     * @author homelink
     * @return
     */
    public static Date getProSevenDay(Date tday) {
		Calendar calendar = new GregorianCalendar();
        calendar.setTime(tday);
        calendar.add(calendar.DATE,-6);
        return calendar.getTime(); 
    }
    
    /**
     * 计算两个日期之间相差天数 tdate-condate相差天数
     * @author tianxf9
     * @param tdate
     * @param condate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date tdate,Date condate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        tdate=sdf.parse(sdf.format(tdate));  
        condate=sdf.parse(sdf.format(condate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(tdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(condate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time1-time2)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
    
    /**
     * 返回日期dataStr当天的开始时间
     * dataStr:"yyyy-MM-dd" 
     * @author tianxf9
     * @param dateStr
     * @return
     */
    public static String getDayBeginTime(String dateStr) {
    	return dateStr +" 00:00:00";
    }
    
    /**
     * 返回日期dataStr当天的结束时间
     * @author tianxf9
     * @param dateStr
     * @return
     */
    public static String getDayEndTime(String dateStr) {
    	return dateStr + " 23:59:59";
    }
}
