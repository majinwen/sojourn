package com.zra.task.report;

import com.zra.common.utils.DateUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.report.logic.ReportPaymentLogic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 回款报表定时任务
 * @author huangy168@ziroom.com
 * @Date 2016年11月1日
 * @Time 下午3:23:23
 */
@Component
public class ReportPaymentTask {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportPaymentTask.class);

	@Autowired
	private ReportPaymentLogic reportPaymentLogic;
	

	/**
	 * 每天统计回款报表数据
	 */
	@Scheduled(cron = "0 30 1 * * ?")
    public void updateReportPaymentDaily() {
    	 if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
             LOGGER.info("===================更新回款报表数据任务开始=========================");
             Date date=new Date();//取当前时间
             //取昨天（endDate）
             Calendar calendar = new GregorianCalendar();
             calendar.setTime(date);
             calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
             Date endDate =calendar.getTime(); //这个时间就是日期往后推一天的结果 
             //取月初
             calendar.setTime(endDate);
             calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
             Date startDate =calendar.getTime();
             
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             String startDateStr = formatter.format(startDate);
             String endDateStr = formatter.format(endDate);
             
            // String recordDate = DateUtil.DateToStr(new Date((new Date()).getTime() - 1000*60*60*24), DateUtil.MONTH_FORMAT);
             LOGGER.info("===================自动任务统计回款数据参数==startDateStr="+startDateStr+";endDateStr="+endDateStr);
             reportPaymentLogic.updateReportPaymentDaily(startDateStr,endDateStr);
             
             LOGGER.info("===================更新回款报表数据任务结束==========================");
         } else {
             LOGGER.info("updateReportPaymentDaily未获得任务锁，任务取消");
         }
    }
}
