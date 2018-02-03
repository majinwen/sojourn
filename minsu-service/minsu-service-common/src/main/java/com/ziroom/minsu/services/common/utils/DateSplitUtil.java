package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>时间截取</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/15.
 * @version 1.0
 * @since 1.0
 */
public class DateSplitUtil {


    //日期格式
    public static String dateFormatPattern = "yyyy-MM-dd";
    //时间格式
    public static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

    public static String lastTime = "23:59:59";


    /**
     * 将当前时间转化成当前的第一秒
     * @author afi
     * @param date
     * @return
     */
    public static Date transDateTime2Date(Date date){
        String dateStr = DateUtil.dateFormat(date);
        dateStr = dateStr + " 00:00:00";
        try {
            return DateUtil.parseDate(dateStr,timestampPattern);
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }

    /**
     * 将当前时间转化成当前的第一秒
     * @author afi
     * @param date
     * @return
     */
    public static Date transDateTime2Last(Date date){
        String dateStr = DateUtil.dateFormat(date);
        dateStr = dateStr + " 23:59:59";
        try {
            return DateUtil.parseDate(dateStr,timestampPattern);
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }


    /**
     * 获取当前区间内的天数列表
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) {
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startDate);
        Date tomorrow = getTomorrow(startDate);
        while (transDateTime2Date(tomorrow).before(transDateTime2Date(endDate))){
            dateList.add(tomorrow);
            tomorrow = getTomorrow(tomorrow);
        }
        List<Date> rst = new ArrayList<Date>();
        for(int i=dateList.size()-1;i>=0;i--){
            rst.add(dateList.get(i));
        }
        return rst;
    }


    /**
     * 获取当前区间内的天数
     * @author afi
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Integer countDateSplit(Date startDate, Date endDate){
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        int count = 1;
        Date tomorrow = getTomorrow(startDate);
        while (transDateTime2Date(tomorrow).before(transDateTime2Date(endDate))){
            tomorrow = getTomorrow(tomorrow);
            count ++;
        }
        return count;
    }

    public static Integer countDateSplitNeq(Date startDate, Date endDate){
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        if (DateUtil.dateFormat(startDate).equals(DateUtil.dateFormat(endDate))){
            return 0;
        }
        return countDateSplit(startDate,endDate);
    }


    /**
     * 获取当前区间的天数集合
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Set<String> getDateSplitSet(Date startDate, Date endDate){

        Set<String>  daySet = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        List<Date> dateList = dateSplit(startDate, endDate);
        if(dateList != null){
            for(Date dateInfo : dateList){
                daySet.add(sdf.format(dateInfo));
            }
        }
        return daySet;
    }

    /**
     * 获取当前区间的天数集合
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Set<String> getDateSplitFullDaySet(Date startDate, Date endDate) {

        Set<String>  daySet = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        List<Date> dateList = dateSplit(startDate, endDate);
        if(dateList != null){
            for(Date dateInfo : dateList){
                daySet.add(sdf.format(dateInfo));
            }
        }
        return daySet;
    }


    /**
     * 获取时间段时间的查询条件
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String getParStr(Date startDate, Date endDate) throws Exception {
        int i=0;
        Set<String>  daySet = getDateSplitSet(startDate, endDate);
        StringBuffer sb = new StringBuffer();
        for(String day : daySet){
            i++;
            if(i==1){
                sb.append("(");
            }else{
                sb.append(" OR ");
            }
            sb.append(day);
            if(i==daySet.size()){
                sb.append(")");
            }
        }
        return sb.toString();
    }


    /**
     * 获取时间段时间的查询条件
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String getFullDayParStr(Date startDate, Date endDate) throws Exception {
        int i=0;
        Set<String>  daySet = getDateSplitFullDaySet(startDate, endDate);
        StringBuffer sb = new StringBuffer();
        for(String day : daySet){
            i++;
            if(i==1){
                sb.append("('");
            }else{
                sb.append("','");
            }
            sb.append(day);
            if(i==daySet.size()){
                sb.append("')");
            }
        }
        return sb.toString();
    }


    /**
     * 获取时间段时间的查询条件
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String getParStrByStr(String startDate, String endDate) throws Exception {
        int i=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        Set<String>  daySet = getDateSplitSet(start, end);
        StringBuffer sb = new StringBuffer();
        for(String day : daySet){
            i++;
            if(i==1){
                sb.append("(");
            }else{
                sb.append(" OR ");
            }
            sb.append(day);
            if(i==daySet.size()){
                sb.append(")");
            }
        }
        return sb.toString();
    }


    /**
     * 获取跳跃之后的时间：按天
     * @param current
     * @param jump
     * @return
     */
    public static Date jumpDate(Date current,int jump) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.DATE, jump);    //跳跃
        return cal.getTime();
    }
    
    /**
     * 获取跳跃后的时间：按小时
     * @author lishaochuan
     * @create 2016年5月12日上午11:14:50
     * @param current
     * @param jump
     * @return
     */
    public static Date jumpHours(Date current,int jump) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.HOUR_OF_DAY, jump);    //跳跃
        return cal.getTime();
    }

    /**
     * 获取跳跃后的时间：按秒
     * @author afi
     * @create 2016年10月31日
     * @param current
     * @param jump
     * @return
     */
    public static Date jumpSecond(Date current,int jump) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.SECOND, jump);    //跳跃
        return cal.getTime();
    }

    /**
     * 获取跳跃后的时间：按分钟
     * @author lishaochuan
     * @create 2016年5月12日下午1:06:25
     * @param current
     * @param jump
     * @return
     */
    public static Date jumpMinute(Date current,int jump) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.MINUTE, jump);    //跳跃
        return cal.getTime();
    }


    /**
     * 获取昨天日期
     * @param current
     * @return
     */
    public static Date getYesterday(Date current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.DATE, -1);    //得到前一天
        return cal.getTime();
    }

    /**
     * 获取明天日期
     * @param current
     * @return
     */
    public static Date getTomorrow(Date current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.DATE, 1);    //明天
        return cal.getTime();
    }

    /**
     * 获取昨天日期
     * @param current
     * @return
     */
    public static Date getNextYear(Date current) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.YEAR, 10);    //得到明年的时间
        return cal.getTime();
    }


    /**
     * 将时间拼接在一起
     * @author afi
     * @param day
     * @param timeStr
     * @return
     */
    public static Date connectDate(Date day,String timeStr){
        if(Check.NuNObj(day)){
            return null;
        }
        if(Check.NuNStr(timeStr)){
            return null;
        }
        String str = DateUtil.dateFormat(day) + " " + timeStr.trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date rst = null;
        try {
            rst =  sdf.parse(str);
        }catch (Exception e){
            throw new BusinessException(e);
        }
        return rst;
    }


    /**
     * 获取有效的开始时间
     * 这个取下单的开始时间和活动的开始时间的最迟的值
     * @param couponDate
     * @param orderDate
     */
    public static Date getCompareDateFrom(Date couponDate,Date orderDate){
        if (Check.NuNObj(couponDate)){
            return orderDate;
        }
        Date time = null;
        //校验开始时间
        if (orderDate.after(couponDate)){
            time = orderDate;
        }else {
            time = couponDate;
        }
        return time;
    }


    /**
     * 获取有效的结束时间
     * 这个取下单的结束时间和活动的结束时间的最早的值
     * @param couponDate
     * @param orderDate
     */
    public   static Date getCompareDateTo(Date couponDate,Date orderDate){
        if (Check.NuNObj(couponDate)){
            return orderDate;
        }
        Date dateOrder = DateSplitUtil.transDateTime2Date(orderDate);
        Date dateCoupon = DateSplitUtil.transDateTime2Date(couponDate);
        Date time = null;
        //校验结束时间
        if (dateOrder.after(dateCoupon)){
            //这个一定要注意，如果是活动的最后一天需要加一天的
            //这个一定要体系体会
            time = DateSplitUtil.getTomorrow(couponDate);
        }else {
            time = orderDate;
        }
        return time;
    }


    /**
     * 获取时间差，返回字符串
     * @author lishaochuan
     * @create 2016年8月3日上午11:48:52
     * @param begin
     * @param end
     * @return
     */
    public static String getDiffTimeStr(Date begin,Date end){
    	if(Check.NuNObj(begin)){
            return null;
        }
        if(Check.NuNObj(end)){
            return null;
        }
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 ;
		StringBuilder dateStr = new StringBuilder();
		if(day != 0L){
			dateStr.append(day);
			dateStr.append("天");
		}
		if(day != 0L || hour != 0L){
			dateStr.append(hour);
			dateStr.append("小时");
		}
		if(day != 0L || hour != 0L || minute != 0L){
			dateStr.append(minute);
			dateStr.append("分");
		}
		if(day != 0L || hour != 0L || minute != 0L || second != 0L){
			dateStr.append(second);
			dateStr.append("秒");
		}
		return dateStr.toString();
    }
    
    
    
    /**
     * 转化日期到当天第一秒
     * @author lishaochuan
     * @create 2016年9月14日下午3:57:52
     * @param date
     * @return
     */
    public static Date getFirstSecondOfDay(Date date){
    	Calendar ca = Calendar.getInstance();
		ca.setTime(date);
	    ca.set(Calendar.HOUR_OF_DAY, 0);
	    ca.set(Calendar.MINUTE, 0);
	    ca.set(Calendar.SECOND, 0);
	    return ca.getTime();
    }
    
    
    /**
     * 转化日期到当天最后一秒
     * @author lishaochuan
     * @create 2016年9月14日下午3:58:41
     * @param date
     * @return
     */
    public static Date getLastSecondOfDay(Date date){
    	Calendar ca = Calendar.getInstance();
	    ca.setTime(date);
	    ca.set(Calendar.HOUR_OF_DAY, 23);
	    ca.set(Calendar.MINUTE, 59);
	    ca.set(Calendar.SECOND, 59);
	    return ca.getTime();
    }

    /**
     * 转化日期到当月第一秒
     * @author lishaochuan
     * @create 2017/2/10 14:34
     * @param 
     * @return 
     */
    public static Date getFirstSecondOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 转化日期到当月最后一秒
     * @author lishaochuan
     * @create 2017/2/10 14:34
     * @param 
     * @return 
     */
    public static Date getLastSecondOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    /**
     * 
     * 获取时间的时分秒
     * 时间格式： 时分秒 为24小时制
     *
     * @author yd
     * @created 2017年1月5日 上午11:28:58
     *
     * @param date
     * @return
     */
    public static String getDateHMS(Date date){
	
    	String hms = "00:00:00";
    	try {
			if(!Check.NuNObj(date)){
				Calendar ca = Calendar.getInstance();
	    	    ca.setTime(date);
	    	    String hour=ca.get(Calendar.HOUR_OF_DAY)<10?"0"+ca.get(Calendar.HOUR_OF_DAY):ca.get(Calendar.HOUR_OF_DAY)+"";//小时
	    	    String minute=ca.get(Calendar.MINUTE)<10?"0"+ca.get(Calendar.MINUTE):+ca.get(Calendar.MINUTE)+"";//分           
	    	    String second=ca.get(Calendar.SECOND)<10?"0"+ca.get(Calendar.SECOND):+ca.get(Calendar.SECOND)+"";//秒
	            hms = hour+":"+minute+":"+second;
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
    	return hms;
    }
    
    
    /**
     * 
     * 比较t1和t2 t1>t2 返回1 
     *
     * @author yd
     * @created 2017年1月5日 上午11:42:58
     *
     * @param t1
     * @param t2
     * @return
     */
    public static int complareDate(Date t1,Date t2){
    	
    	int i = -1;
    	if(Check.NuNObj(t1)
    			||Check.NuNObj(t2)){
    		throw new BusinessException("t1 or t2 is null");
    	}
    	
    	try {
			t1 = DateUtil.parseDate(DateUtil.dateFormat(t1), timestampPattern);
			t2 = DateUtil.parseDate(DateUtil.dateFormat(t2), timestampPattern);
			
			if(t1.getTime()>t2.getTime()){
				i = 1;
			}
			if(t1.getTime()==t2.getTime()){
				i = 0;
			}
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
    	
    	return i;
    }

    /**
     * 获得月份周期,按顺序
     * @author lishaochuan
     * @create 2017/2/10 14:26
     * @return
     */
    public static Map<Integer, Date> getCycleMonth(){
        return getCycleMonth(-9);
    }

    /**
     * 获得月份周期,按顺序
     * @author lishaochuan
     * @create 2017/2/10 14:26
     * @param leftShift:偏移月份
     * @return
     */
    public static Map<Integer, Date> getCycleMonth(int leftShift){
        Map<Integer, Date> mouthMap = new LinkedHashMap<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.setLenient(true);
        c.add(Calendar.MONTH, leftShift);
        for (int i=0; i<12; i++){
            if(i == 0){
                mouthMap.put(c.get(Calendar.MONTH)+1, c.getTime());
            }else{
                c.add(Calendar.MONTH, 1);
                mouthMap.put(c.get(Calendar.MONTH)+1, c.getTime());
            }
        }
        return mouthMap;
    }

    /**
     * 获取周
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static void main(String[] args) throws ParseException {
    	System.err.println(getCycleMonth());
        System.err.println(DateUtil.dateFormat(getLastSecondOfMonth(new Date()), "yyyy-MM-dd HH:mm:ss"));

        System.err.println(getWeekOfDate(new Date()));
        ;
    }

}
