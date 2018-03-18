package com.zra.task.kanban;

import java.util.Date;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.common.utils.DateUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.kanban.logic.KanbanDetailLogic;
/**
 * 目标看板核心数据
 * @author tianxf9
 *
 */
@Component
public class KanBanDetailTask {
	
	private static Logger LOGGER = LoggerFactoryProxy.getLogger(KanBanDetailTask.class);
	
	@Autowired
	private KanbanDetailLogic detailLogic;
	
	@Scheduled(cron = "0 20 3 * * ?")
	public void runTask() {
		LOGGER.info("begin run  KanBanDetailTask .......");
		if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            //取昨天
            Date date = DateUtil.getYesterDay(new Date());
            detailLogic.taskCreateDetail(date);
		}else {
			LOGGER.info("KanBanDetailTask未获得任务锁，任务取消");
		}
		LOGGER.info("end run  KanBanDetailTask .......");
	}

}
