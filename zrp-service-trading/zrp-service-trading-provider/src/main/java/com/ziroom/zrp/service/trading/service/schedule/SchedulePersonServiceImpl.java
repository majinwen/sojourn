package com.ziroom.zrp.service.trading.service.schedule;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.SchedulePersonDao;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;

@Service("trading.schedulePersonServiceImpl")
public class SchedulePersonServiceImpl {
	
	@Resource(name="trading.schedulePersonDao")
	private SchedulePersonDao schedulePersonDao;
	
	/**
     * <p>通过ID查询排班人员信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public SchedulePersonEntity findSchedulePersonById(String id){
    	return this.schedulePersonDao.findSchedulePersonById(id);
    }

}
