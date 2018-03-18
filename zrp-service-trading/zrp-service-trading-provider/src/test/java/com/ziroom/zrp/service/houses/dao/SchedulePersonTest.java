package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SchedulePersonDao;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;

public class SchedulePersonTest extends BaseTest{
	
	@Resource(name="trading.schedulePersonDao")
	private SchedulePersonDao schedulePersonDao;
	
	/**
     * <p>通过ID查询排班人员信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
	@Test
    public void findSchedulePersonById(){
		String id = "47";
		SchedulePersonEntity schedulePersonEntity = schedulePersonDao.findSchedulePersonById(id);
    	System.out.println(JsonEntityTransform.Object2Json(schedulePersonEntity));
    }
	

}
