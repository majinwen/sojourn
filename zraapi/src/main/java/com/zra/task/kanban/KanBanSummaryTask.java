package com.zra.task.kanban;

import java.util.Date;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.common.utils.DateUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.kanban.logic.KanbanSummaryLogic;

/**
 * 目标看板核心数据自动任务
 * @author tianxf9
 *
 */
@Component
public class KanBanSummaryTask {
	
	private static Logger LOGGER =LoggerFactoryProxy.getLogger(KanBanSummaryTask.class);
	
	@Autowired
	private KanbanSummaryLogic summaryLogic; 
	
	@Scheduled(cron = "0 0 3 * * ?")
	public void runTask() {
		
		LOGGER.info("begin run  KanBanSummaryTask .......");
		if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            //取昨天
            Date date = DateUtil.getYesterDay(new Date());
            summaryLogic.taskKanbanSummary(date);
		}else {
			LOGGER.info("KanBanSummaryTask未获得任务锁，任务取消");
		}
		LOGGER.info("end run  KanBanSummaryTask .......");
	}

}
