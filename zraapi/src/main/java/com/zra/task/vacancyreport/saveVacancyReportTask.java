package com.zra.task.vacancyreport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zra.common.utils.TaskConcurrentControl;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.vacancyreport.logic.VacancyReportLogic;

/**
 * 统计昨天的空置天数并且保存
 * @author tianxf9
 *
 */
@Component
public class saveVacancyReportTask {
	
	@Autowired
	private VacancyReportLogic reportLogic;
	
	@Autowired
	private ProjectLogic projectLogic; 
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(saveVacancyReportTask.class);
	 
	@Scheduled(cron = "0 30 2 * * ?")
	public void saveReportEntitys() {
		
		if(TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
	         Date date=new Date();//取时间
	         Calendar calendar = new GregorianCalendar();
	         calendar.setTime(date);
	         calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
	         date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	         String recordDate = formatter.format(date);
	 		 //获取所有项目
	 		 List<ProjectListReturnDto> projectList = projectLogic.getProjectList();
	 		 LOGGER.info("=========="+recordDate+"日的空置报表数据！ 开始.........");
	 		 for(ProjectListReturnDto projectDto:projectList) {
	 			 LOGGER.info("=========="+date+"时：统计项目："+projectList+";"+recordDate+"日的空置报表数据！ 开始.....");
	 			 Map<String,Object> resultMap = this.reportLogic.getVacancyReportEntitys(recordDate, projectDto.getProjId(), true);
	 			 String resultStr = (String)resultMap.get("status");
	 			 if(resultStr.equals("success")) {
	 				 LOGGER.info("=========="+date+"时：统计项目："+projectDto.getProjId()+";"+recordDate+"日的空置报表数据成功！");
	 			 }else {
	 				 LOGGER.info("=========="+date+"时：统计项目："+projectDto.getProjId()+";"+recordDate+"日的空置报表数据失败!"); 
	 			 }
	 		 }
	 		LOGGER.info("=========="+recordDate+"日的空置报表数据！ 结束");	
		}else {
			 LOGGER.info("saveVacancyReportTask未获得任务锁，任务取消");
		}


	}

}
