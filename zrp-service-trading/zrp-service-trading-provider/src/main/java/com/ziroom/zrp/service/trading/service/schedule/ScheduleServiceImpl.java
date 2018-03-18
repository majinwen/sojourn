package com.ziroom.zrp.service.trading.service.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.ScheduleDao;
import com.ziroom.zrp.trading.entity.ScheduleEntity;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;

@Service("trading.scheduleServiceImpl")
public class ScheduleServiceImpl {
	
	@Resource(name="trading.scheduleDao")
	private ScheduleDao scheduleDao;
	
	
	
	/**
     * <p>通过ID查询排班表信息</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public ScheduleEntity findScheduleById(String id){
    	return scheduleDao.findScheduleById(id);
    }
    /**
     * <p>通过项目ID查询当天值班的管家列表</p>
     * @author xiangb
     * @created 2017年11月2日
     */
    public List<SchedulePersonEntity> findSchedulePersonByProjectId(String projectId,String week){
    	return scheduleDao.findSchedulePersonByProjectId(projectId,week);
    }

}
