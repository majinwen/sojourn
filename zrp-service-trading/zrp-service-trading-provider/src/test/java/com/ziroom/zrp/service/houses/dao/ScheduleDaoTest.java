package com.ziroom.zrp.service.houses.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.ScheduleDao;
import com.ziroom.zrp.trading.entity.ScheduleEntity;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;

public class ScheduleDaoTest extends BaseTest{
	
	@Resource(name="trading.scheduleDao")
	private ScheduleDao scheduleDao;
	
	
	
	/**
     * <p>通过ID查询排班表信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
	@Test
    public void findScheduleById(){
		String id ="56";
    	ScheduleEntity schedule = scheduleDao.findScheduleById(id);
    	System.out.println(JsonEntityTransform.Object2Json(schedule));
    }
	
	@Test
	public void findSchedulePersonByProjectId(){
		String projectId = "2c908d194f5f09b8014f62b8a9ab0024";
		String week = "1";
		List<SchedulePersonEntity> schedules = scheduleDao.findSchedulePersonByProjectId(projectId, week);
		System.out.println(JsonEntityTransform.Object2Json(schedules));
	}
}
