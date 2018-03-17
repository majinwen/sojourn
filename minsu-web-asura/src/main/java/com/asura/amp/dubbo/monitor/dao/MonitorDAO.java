/**
 * @FileName: LoginDao.java
 * @Package com.asura.amp.dubbo.monitor.dao
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.dubbo.monitor.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asura.amp.dubbo.monitor.entity.Statistics;
import com.asura.amp.dubbo.monitor.entity.Summary;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * <p>登录Dao</p>
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
@Repository("monitorDao")
public class MonitorDAO extends BaseIbatisDAO {
	
	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}
	
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
	public void saveStatistics(Statistics statistics) {
		if(statistics.getSuccess() + statistics.getFailure() + statistics.getElapsed() + statistics.getConcurrent() + statistics.getMax_concurrent() + statistics.getMax_elapsed() == 0) {
			return ;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("checkcode", statistics.getCheckcode());
		
		Integer statisticsKeyId = findOne("com.asura.management.servicecenter.monitor.dao.getStatisticsKey", Integer.class, params);
		if(statisticsKeyId == null) {
			statisticsKeyId = (Integer) save("com.asura.management.servicecenter.monitor.dao.saveStatisticsKey", statistics);
		}
		
		statistics.setKeyid(statisticsKeyId);
		
		save("com.asura.management.servicecenter.monitor.dao.saveStatisticsDetail", statistics);
	}
	
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
	public List<Summary> findSummary(String service, String method, String statisticstype, Date strTime, Date endTime) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("service", service);
		params.put("method", method);
		params.put("statisticstype", statisticstype);
		params.put("strTime", strTime);
		params.put("endTime", endTime);
		
		return findAll("com.asura.management.servicecenter.monitor.dao.summary", Summary.class, params);
	}
}
