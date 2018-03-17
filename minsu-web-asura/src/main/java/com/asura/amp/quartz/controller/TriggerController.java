/**
 * @FileName: TaskController.java
 * @Package com.asura.amp.quartz.controller
 * 
 * @author zhangshaobin
 * @created 2013-2-19 下午1:43:56
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.quartz.controller;

import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;
import com.asura.amp.quartz.entity.Trigger;
import com.asura.amp.quartz.executor.SchedulerExecutor;
import com.asura.amp.quartz.service.TriggerService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.base.util.DateUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * <p>
 * quartz任务调度器后台控制器
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("quartz")
public class TriggerController {

	@Autowired
	private TriggerService triggerService;

	/**
	 * 
	 * 查询所有的触发器信息
	 * 
	 * @author zhangshaobin
	 * @created 2013-2-20 下午4:47:58
	 * 
	 * @param model
	 */
	@RequestMapping("triggerList")
	public void triggerList(SearchModel searchModel, Model model, HttpServletRequest request) {
		String resId = request.getParameter("resId");
		if (searchModel.getPage() == 0) {
			searchModel.setPage(1);
		}
		PagingResult<Trigger> triggers = this.triggerService.findForPage(searchModel);
		model.addAttribute("PAGING_RESULT", triggers);
		model.addAttribute("resId", resId);
	}

	/**
	 * 
	 * 跳转到触发器添加页面
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午3:40:40
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("toTriggerAdd")
	public String toTriggerAdd(Model model, HttpServletRequest request) {
		//		model.addAttribute("resId", request.getParameter("resId"));
		return "quartz/add";
	}

	public static void main(String[] args) {
		String jobClass = "com.asura.TestJob";
		String[] ss = jobClass.split("\\.");
		System.out.println(ss[ss.length - 1]);
	}

	/**
	 * 
	 * 保存触发器信息
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午3:49:45
	 *
	 * @param operator
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("add")
	@ResponseBody
	public BaseAjaxValue add(HttpServletRequest request) {
		String resultFlag = "";
		try {
			String triggerGroup = request.getParameter("group");
			String jobClass = request.getParameter("jobClass");
//			Method method =
			String cronExpression = request.getParameter("cronExpression");
			String[] ss = jobClass.split("\\.");
			String triggerName = ss[ss.length - 1];
			resultFlag = addTrigger(triggerName, triggerGroup, cronExpression, ((Job) Class.forName(jobClass)
					.newInstance()).getClass());
		} catch (InstantiationException e) {
			return new Failure(e.getMessage());
		} catch (IllegalAccessException e) {
			return new Failure(e.getMessage());
		} catch (ClassNotFoundException e) {
			return new Failure(e.getMessage());
		} catch (SchedulerException e) {
			return new Failure(e.getMessage());
		} catch (ParseException e) {
			return new Failure(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if (resultFlag == null) {
			return new Failure("已经存在");
		} else {
			return new Success("保存成功");
		}
	}

	/**
	 * 
	 * 跳转到触发器修改页面
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午3:40:40
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("toTriggerEdit")
	public String toTriggerEdit(Model model, HttpServletRequest request) {
		//		model.addAttribute("resId", request.getParameter("resId"));
		String triggerName = request.getParameter("name");
		String triggerGroup = request.getParameter("group");
		String cronExpression = getCronExpression(triggerName, triggerGroup);
		model.addAttribute("triggerName", triggerName);
		model.addAttribute("triggerGroup", triggerGroup);
		model.addAttribute("cronExpression", cronExpression);
		return "quartz/edit";
	}

	/**
	 * 
	 * 修改cron表达式
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午3:49:45
	 *
	 * @param operator
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("edit")
	@ResponseBody
	public BaseAjaxValue edit(HttpServletRequest request) {
		String triggerName = request.getParameter("name");
		String triggerGroup = request.getParameter("group");
		String cronExpression = request.getParameter("cronExpression");
		try {
			updateCronExpression(triggerName, triggerGroup, cronExpression);
		} catch (SchedulerException e) {
			return new Failure("错误的修改");
		} catch (ParseException e) {
			return new Failure("cron表达式错误");
		}
		return new Success("修改成功");
	}

	/**
	 * 
	 * 暂停触发器
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午5:33:30
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping("pauseTrigger")
	@ResponseBody
	public BaseAjaxValue pauseTrigger(String[] ids) {
		for (String id : ids) {
			String[] idArray = id.split("\\|");
			pauseTrigger(idArray[0], idArray[1]);
		}
		return new Success("暂停成功");
	}

	/**
	 * 
	 * 恢复触发器
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午5:41:05
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping("resumeTrigger")
	@ResponseBody
	public BaseAjaxValue resumeTrigger(String[] ids) {
		for (String id : ids) {
			String[] idArray = id.split("\\|");
			resumeTrigger(idArray[0], idArray[1]);
		}
		return new Success("恢复成功");
	}

	/**
	 * 
	 * 删除触发器
	 *
	 * @author zhangshaobin
	 * @created 2013-2-21 下午5:41:05
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseAjaxValue delete(String[] ids) {
		for (String id : ids) {
			String[] idArray = id.split("\\|");
			deleteTrigger(idArray[0], idArray[1]);
		}
		return new Success("删除成功");
	}

	/**
	 * 
	 * 新建工作任务、触发器
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-18 上午9:50:56
	 * 
	 * @param triggerName
	 *            触发器名称
	 * @param cronExpression
	 *            Cron表达式
	 * @param jobClass
	 *            Job类
	 */
	private String addTrigger(String triggerName, String triggerGroup, String cronExpression,
			Class<? extends Job> jobClass) throws SchedulerException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = jobClass.getMethod("test");
		method.invoke(jobClass);

		org.quartz.Trigger trigger = SchedulerExecutor.getScheduler().getTrigger(triggerName, triggerGroup);
		if (trigger == null) {// 判断是否有重复的触发器
			JobDetail jobDetail = new JobDetail(triggerName, Scheduler.DEFAULT_GROUP, jobClass);
			JobDataMap map = new JobDataMap();
			map.put("msg", "Your remotely added job has executed!");
			jobDetail.setJobDataMap(map);
			CronTrigger cronTrigger = null;
			cronTrigger = new CronTrigger(triggerName, triggerGroup, jobDetail.getName(), jobDetail.getGroup(),
					cronExpression);
			cronTrigger.setStartTime(DateUtil.intervalDate(10, DateUtil.IntervalUnit.MINUTE));
			SchedulerExecutor.getScheduler().addJob(jobDetail, true);
			SchedulerExecutor.getScheduler().scheduleJob(cronTrigger);
			SchedulerExecutor.getScheduler().rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
			return triggerName;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * 暂停触发器
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-18 上午9:51:48
	 * 
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroup
	 *            触发器分组
	 */
	private void pauseTrigger(String triggerName, String triggerGroup) {
		try {
			SchedulerExecutor.getScheduler().pauseTrigger(triggerName, triggerGroup);// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 恢复触发器
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-18 上午9:52:59
	 * 
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroup
	 *            触发器分组
	 */
	private void resumeTrigger(String triggerName, String triggerGroup) {
		try {
			SchedulerExecutor.getScheduler().resumeTrigger(triggerName, triggerGroup);// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 删除任务及触发器
	 * 
	 * @author zhangshaobin
	 * @created 2012-12-18 上午9:53:31
	 * 
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroup
	 *            触发器分组
	 * @return
	 */
	private boolean deleteTrigger(String triggerName, String triggerGroup) {
		try {
			SchedulerExecutor.getScheduler().pauseTrigger(triggerName, triggerGroup);// 停止触发器
			return SchedulerExecutor.getScheduler().unscheduleJob(triggerName, triggerGroup);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 获取cron表达式
	 *
	 * @author zhangshaobin
	 * @created 2013-2-22 下午2:27:35
	 *
	 * @param triggerName
	 * @param triggerGroup
	 * @return
	 */
	private String getCronExpression(String triggerName, String triggerGroup) {
		CronTrigger cronTrigger;
		try {
			cronTrigger = (CronTrigger) SchedulerExecutor.getScheduler().getTrigger(triggerName, triggerGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return "";
		}
		return cronTrigger.getCronExpression();
	}

	/**
	 * 
	 * 修改cron表达式
	 * 
	 * @author zhangshaobin
	 * @created 2013-2-22 下午2:27:35
	 * 
	 * @param triggerName
	 *            触发器名称
	 * @param cronExpression
	 *            Cron表达式
	 * @param jobClass
	 *            Job类
	 */
	@SuppressWarnings("unchecked")
	private String updateCronExpression(String triggerName, String triggerGroup, String cronExpression)
			throws SchedulerException, ParseException {
		// 删除旧的触发器
		JobDetail jobDetail = SchedulerExecutor.getScheduler().getJobDetail(triggerName, Scheduler.DEFAULT_GROUP);
		deleteTrigger(triggerName, triggerGroup);
		// 新增新的触发器
		try {
			triggerName = addTrigger(triggerName, triggerGroup, cronExpression, jobDetail.getJobClass());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return triggerName;
	}

}
