/**
 * @FileName: HouseCommonService.java
 * @Package com.ziroom.minsu.services.order.api.inner
 * 
 * @author yd
 * @created 2016年12月7日 上午10:31:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.api.inner;


/**
 * <p>房源接口</p>
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
 */
public interface HouseCommonService {

	/**
	 * 
	 * 获取房源 夹心价格的map
	 *
	 * @author yd
	 * @created 2016年12月7日 上午10:32:54
	 *
	 * @param paramJson
	 * @return
	 */
	public String findPriorityDate(String paramJson);

	/**
	 * 插入房源锁日志
	 * @author jixd
	 * @created 2017年04月11日 15:20:52
	 * @param
	 * @return
	 */
	String saveHouseLockLog(String paramJson);

	/**
	 * 同步airbnb房源锁
	 * @author jixd
	 * @created 2017年04月18日 14:38:57
	 * @param
	 * @return
	 */
	String syncAirHouseLock(String paramJson);
	
	/**
	 * 
	 * 获取当前房源的今夜特价是否生效以及生效的折扣
	 *
	 * @author lusp
	 * @created 2017年5月12日 下午4:01:32
	 *
	 * @param housePriorityDto
	 */
	String getEffectiveOfJYTJInfo(String paramJson);
	
	/**
	 * 
	 * 锁定时间条件查询被锁房源fid列表
	 *
	 * @author bushujie
	 * @created 2017年5月16日 下午3:33:59
	 *
	 * @param lockTime
	 * @return
	 */
	String getLockFidByLockTime(String lockTime);
	
	/**
	 * 
	 * 查询当前房源是否被锁定
	 *
	 * @author lusp
	 * @created 2017年5月17日 下午5:43:59
	 *
	 * @param tonightDiscountEntity
	 * @return
	 */
	String isHousePayLockCurrentDay(String paramJson);
	
	/**
	 * 
	 * 根据　用户uid 判断用户是否符合首单立减用户
	 * 1. 用户存在 有效订单且发生过入住  
	 * 2. 用户存在有效订单且未发生过入住且违约金大于0
	 * 3. 取 满足1且满足2  如果>0 不是新用户  ==0 是新用户
	 *
	 * @author yd
	 * @created 2017年6月5日 下午4:13:31
	 *
	 * @param uid
	 * @return
	 */
	public String isNewUserByOrder(String uid);
	
	
	
	   /**
     * 
     * 根据　用户uid 判断用户是否是新人  供首页使用
     * 
     * 说明： 只判断当前用户是否有订单
     *
     * @author yd
     * @created 2017年6月16日 下午12:05:05
     *
     * @param uid
     * @return
     */
	public String isNewUserByOrderForFirstPage(String uid);
	
	/**
	 * 
	 * 查询房源日历记录
	 * 
	 * @author zyl
	 * @created 2017年6月27日 下午3:10:09
	 * @param paramJson
	 * @return
	 */
	public String getHouseLockDayList(String paramJson);
}
