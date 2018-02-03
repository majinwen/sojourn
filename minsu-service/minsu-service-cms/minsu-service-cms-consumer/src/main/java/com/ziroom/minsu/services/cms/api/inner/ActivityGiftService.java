/**
 * @FileName: ActivityGiftService.java
 * @Package com.ziroom.minsu.services.cms.api.inner
 * 
 * @author yd
 * @created 2016年10月9日 下午3:38:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.api.inner;

import com.ziroom.minsu.entity.cms.ActivityGiftEntity;

/**
 * <p>TODO</p>
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
public interface ActivityGiftService {
	
	/**
	 * 
	 * 分页查询活动 礼物
	 *
	 * @author yd
	 * @created 2016年10月9日 下午3:39:39
	 *
	 * @param activityGift
	 * @return
	 */
	public String queryActivityGifyByPage(String activityGift);

	/**
	 * 获取当前房东的免佣金的活动
	 * @author afi
	 * @created 2016年10月9日
	 * @param landUid
	 * @return
	 */
	public String getLandFreeComm(String landUid);
	
	/**
	 * 
	 * 新增活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertActivityGiftEntity(String paramJson);

	/**
	 * 
	 * 根据逻辑id查询活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftFid
	 * @return
	 */
	public String queryActivityGifyByFid(String actGiftFid);

	/**
	 * 修改活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateActivityGiftEntity(String paramJson);

	/**
	 * 取消天使房东免佣金特权
	 *
	 * @author loushuai
	 * @created 2017年5月17日 下午8:09:27
	 *
	 * @param uid
	 * @return
	 */
	public String cancelFreeCommission(String uid);

}
