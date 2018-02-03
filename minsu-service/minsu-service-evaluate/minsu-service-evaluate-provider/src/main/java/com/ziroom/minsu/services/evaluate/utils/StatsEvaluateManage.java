/**
 * @FileName: StatsEvaluateManage.java
 * @Package com.ziroom.minsu.services.evaluate.utils
 * 
 * @author yd
 * @created 2016年4月9日 下午12:14:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.utils;

import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.asura.framework.base.util.Check;

/**
 * <p>关于统计相关线程任务的管理
 *   说明：主要管理 统计信息  1.统计房源的信息  2. 统计房东对房客满意度的信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 * @param <V>
 */
public class StatsEvaluateManage<V> {
	
	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());
	
	/**
	 * 统计房源的线程实例
	 */
	private   StatsHouseEvaTask statsHouseEvaTask;
	
	/**
	 * 房东对房客评价的统计信息实例
	 */
	private  StatsTenantTvaTask statsTenantTvaTask;
	
	public StatsEvaluateManage(){};
	public StatsEvaluateManage(StatsHouseEvaTask statsHouseEvaTask){
		this.statsHouseEvaTask = statsHouseEvaTask;
	}
	public StatsEvaluateManage(StatsTenantTvaTask statsTenantTvaTask){
		this.statsTenantTvaTask = statsTenantTvaTask;
	}
	public StatsEvaluateManage(StatsHouseEvaTask statsHouseEvaTask,StatsTenantTvaTask statsTenantTvaTask){
		this.statsHouseEvaTask = statsHouseEvaTask;
		this.statsTenantTvaTask = statsTenantTvaTask;
	};
	
	public StatsHouseEvaTask getStatsHouseEvaTask() {
		return statsHouseEvaTask;
	}
	public void setStatsHouseEvaTask(StatsHouseEvaTask statsHouseEvaTask) {
		this.statsHouseEvaTask = statsHouseEvaTask;
	}
	public StatsTenantTvaTask getStatsTenantTvaTask() {
		return statsTenantTvaTask;
	}
	public void setStatsTenantTvaTask(StatsTenantTvaTask statsTenantTvaTask) {
		this.statsTenantTvaTask = statsTenantTvaTask;
	}
	/**
	 * 
	 * 保存或者更新 统计房源信息
	 * @author yd
	 * @created 2016年4月9日 下午12:21:39
	 *
	 */
	public  void saveOrUpdateStatsHouseEva(){
		if(!Check.NuNObj(statsHouseEvaTask)){
			threadPoolExecutor.execute(statsHouseEvaTask);
		}
	}
	/**
	 * 
	 * 保存或者更新 统计房东对房客统计信息
	 *
	 * @author yd
	 * @created 2016年4月9日 下午12:28:39
	 *
	 */
	public  void saveOrUpdateStatsTenantEva(){
		if(!Check.NuNObj(statsTenantTvaTask)){
			threadPoolExecutor.execute(statsTenantTvaTask);
		}
	}
	
	

}
