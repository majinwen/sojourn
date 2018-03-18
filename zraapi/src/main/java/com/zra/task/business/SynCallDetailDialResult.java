package com.zra.task.business;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.syncc.logic.ZraZrCallDetailLogic;

/**
 * 增加同步400通话详情中转接管家手机的拨号结果（因为拨号结果回写有延时，所以每天凌晨同步昨天的拨号结果）
 * 每天凌晨4点同步昨天一整天的数据
 * @author tianxf9
 *
 */
@Component
public class SynCallDetailDialResult {
	
	@Autowired
	private ZraZrCallDetailLogic callDetailLogic;
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(SynCallDetailDialResult.class);
	
	@Scheduled(cron = "0 0 4 * * ?")
	public void synDialReault() {
		
		try {
			if(TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
				LOGGER.info("================开始同步400通话详情的拨号结果=============================");
				Date todayD=new Date();//取时间
				String todayStr = DateUtil.DateToStr(todayD, DateUtil.DATE_FORMAT);
				String yesterdayStr = DateUtil.getYesterDay(todayStr);
				String startDate = yesterdayStr + " 00:00:00";
				String endDate = yesterdayStr + " 23:59:59";
				this.callDetailLogic.sycDialResult(startDate,endDate);
				LOGGER.info("================结束同步400通话详情的拨号结果=============================");
			}else {
				LOGGER.info("SynCallDetailDialResult未获得任务锁，任务取消");
			}
		} catch (ParseException e) {
			LOGGER.error("SynCallDetailDialResult执行失败", e);
		}
	}

}
