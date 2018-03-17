package com.asura.amp.dubbo.monitor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.dubbo.monitor.dao.MonitorDAO;
import com.asura.amp.dubbo.monitor.entity.Statistics;
import com.asura.amp.dubbo.monitor.entity.Summary;
import com.asura.amp.dubbo.monitor.service.SysMonitorService;

@Service("sysMonitorService")
public class SysMonitorServiceImpl implements SysMonitorService {
	
	@Autowired
	private MonitorDAO monitorDao;

	@Override
	public void saveStatistics(Statistics statistics) {
		monitorDao.saveStatistics(statistics);
	}

	@Override
	public List<Summary> findSummary(String service, String method, String statisticstype, Date strTime, Date endTime) {
		return monitorDao.findSummary(service, method, statisticstype, strTime, endTime);
	}
	
}
