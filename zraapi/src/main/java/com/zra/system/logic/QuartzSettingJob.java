package com.zra.system.logic;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 定时任务公共抽象类
 * 
 * @author tianxf9 2016-05-19
 */
@Component
public abstract class QuartzSettingJob {

	private final static Logger logger = LoggerFactoryProxy.getLogger(QuartzSettingJob.class);
	@Autowired
	private SysTimedTaskLogic taskLogic;
	private String taskName;

	// 执行定时任务
	public Map<String, String> runjob() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = System.currentTimeMillis();
		// 定时任务开始时间
		String startDate = simpleDateFormat.format(new Date());
		logger.info(taskName + ":任务start==============================" + startDate);
		Map<String, String> map = null;
		try {
			// 检查是否可运行
			if (!canRunTask(taskName, true)) {
				logger.info(taskName + ":任务无法运行");
				return map;
			}
			// 业务逻辑方法，需重写。
			map = doSomething();
		} catch (Exception e) {
			logger.error("定时任务执行失败：", e);
		}
		updateTaskRunFlagStop(taskName);
		long end = System.currentTimeMillis();
		// 定时任务结束时间
		String taskEndTime = simpleDateFormat.format(new Date());
		logger.info(taskName + ":任务end==============================" + taskEndTime);
		logger.info(taskName + ":任务耗时==============================" + (end - start) + " ms");
		return map;
	};

	// 执行后更改状态
	public void updateTaskRunFlagStop(String taskName) {
		taskLogic.updateTaskRunFlagStop(taskName);
	}

	// 是否可执行
	public boolean canRunTask(String taskName, boolean updateTime) {
		boolean canRunTask = taskLogic.canRunTask(taskName, updateTime);
		return canRunTask;
	}

	public abstract Map<String, String> doSomething();

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
