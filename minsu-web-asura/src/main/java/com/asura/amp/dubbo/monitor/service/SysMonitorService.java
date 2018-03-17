package com.asura.amp.dubbo.monitor.service;

import java.util.Date;
import java.util.List;

import com.asura.amp.dubbo.monitor.entity.Statistics;
import com.asura.amp.dubbo.monitor.entity.Summary;

public interface SysMonitorService {
	/**
	 * 查询工程的节点菜单
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:11:30
	 *
	 * @param projectid		工程ID
	 * @param parentid		父节点ID
	 * @return
	 */
	public void saveStatistics(Statistics statistics);
	
	/**
	 * 查询工程的节点菜单
	 *
	 * @author zhangshaobin
	 * @created 2013-1-16 下午6:11:30
	 *
	 * @param projectid		工程ID
	 * @param parentid		父节点ID
	 * @return
	 */
	public List<Summary> findSummary(String service, String method, String statisticstype, Date strTime, Date endTime);
}
