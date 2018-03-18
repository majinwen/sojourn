package com.zra.task.report;

import java.util.Date;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.common.utils.DateUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.report.logic.ReportBoLogic;
import com.zra.report.logic.ReportRenewLogic;
import com.zra.report.logic.ReportStockLogic;

/**
 * 统计当日运营报表中的商机数据，库存数据，续约数据的自动任务
 * @author tianxf9
 *
 */
@Component
public class ReportTdayTask {
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportTdayTask.class);
	
	@Autowired
	private ReportBoLogic reportBoLogic;
	
	@Autowired
	private ReportRenewLogic reportRenewLogic;
	
	@Autowired
	private ReportStockLogic reportStockLogic;
	
	@Scheduled(cron = "0 0 0/2 * * ?")
	public void  getTdayReportData() {
   	 if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
         LOGGER.info("===================统计当日报表数据（商机数据，库存数据，续约数据）任务开始=========================");
        
         String recordDate = DateUtil.DateToStr(new Date(), DateUtil.DATE_FORMAT);
         //商机数据
         LOGGER.info("=====开始删除当日报表数据（商机数据）=========================");
         reportBoLogic.delReportBoDataByRecordDate(recordDate);
         LOGGER.info("=====删除当日报表数据（商机数据）结束=========================");
         LOGGER.info("=====开始生成当日报表数据（商机数据）=========================");
         reportBoLogic.saveReportBoDate(recordDate);
         LOGGER.info("=====生成当日报表数据（商机数据）结束=========================");
         
         //续约数据
         LOGGER.info("=====开始删除当日报表数据（续约数据）=========================");
         reportRenewLogic.delReportRenewDate(recordDate);
         LOGGER.info("=====删除当日报表数据（续约数据）结束=========================");
         LOGGER.info("=====开始生成当日报表数据（续约数据）=========================");
         reportRenewLogic.saveReportRenewDate(recordDate);
         LOGGER.info("=====生成当日报表数据（续约数据）结束=========================");
         
         //库存数据
         LOGGER.info("=====开始删除当日报表数据（库存数据）=========================");
         reportStockLogic.delEntitysByRecordDate(recordDate);
         LOGGER.info("=====删除当日报表数据（库存数据）结束=========================");
         LOGGER.info("=====开始生成当日报表数据（库存数据）=========================");
         reportStockLogic.saveReportStockCount(recordDate);
         LOGGER.info("=====生成当日报表数据（库存数据）结束=========================");
         
         LOGGER.info("====================统计当日报表数据（商机数据，库存数据，续约数据）任务结束==========================");
     } else {
         LOGGER.info("getTdayReportData未获得任务锁，任务取消");
     }
	}

}
