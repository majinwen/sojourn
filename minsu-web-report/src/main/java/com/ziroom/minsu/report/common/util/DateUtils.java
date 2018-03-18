package com.ziroom.minsu.report.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>日期处理类工具类/p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
public class DateUtils {

    /**
     * 获取月份第一天
     * @author jixd
     * @created 2017年01月16日 20:39:12
     * @param
     * @return
     */
    public static Date getMonthFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }

    /**
     * 获取月份最后一天
     * @author jixd
     * @created 2017年01月16日 20:39:23
     * @param
     * @return
     */
    public static Date getMonthLastDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTime();
    }

    /**
     *获取上个月月份
     * @author jixd
     * @created 2017年01月17日 09:45:21
     * @param
     * @return
     */
    public static Date getLastMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTime();
    }
}
