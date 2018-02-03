/**
 * @FileName: HouseFollowService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2017年2月24日 上午10:41:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface HouseFollowService {
	
	/**
	 * 
	 * 分页查询客服跟进未审核通过房源列表
	 *
	 * @author bushujie
	 * @created 2017年2月24日 上午10:48:29
	 *
	 * @param paramJson
	 * @return
	 */
	public String findServicerFollowHouseList(String paramJson);
	
	/**
	 * 
	 * 客服锁并且没有主跟进记录表添加记录
	 *
	 * @author bushujie
	 * @created 2017年2月25日 下午2:52:44
	 *
	 * @param paramJson
	 * @return
	 */
	public String lockAndSaveHouseFollow(String paramJson);
	
	/**
	 * 
	 * 房源跟进详情
	 *
	 * @author bushujie
	 * @created 2017年2月27日 上午11:39:13
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseFollowDetail(String paramJson);
	
	/**
	 * 
	 * 插入跟进记录明细
	 *
	 * @author bushujie
	 * @created 2017年2月27日 下午8:00:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertFollowLog(String paramJson);
	
	/**
	 * 
	 * 分页查询专员房源跟进列表
	 *
	 * @author bushujie
	 * @created 2017年2月28日 下午2:12:33
	 *
	 * @param paramJson
	 * @return
	 */
	public String findAttacheFollowHouseList(String paramJson);
	
	/**
	 * 
	 * 更新客服跟进状态
	 * 1. 更新客服跟进表
	 * 2. 记录跟进日志
	 *
	 * @author yd
	 * @created 2017年4月19日 上午11:55:38
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseFollowByFid(String paramJson);
	
	
	
}
