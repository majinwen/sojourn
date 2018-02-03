/**
 * @FileName: LandlordStaticsService.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author liyingjie
 * @created 2016年4月25日 上午10:15:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>房东行为统计相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public interface LandlordBehaviorService {
	
	/**
	 * 查询房东行为统计数据
	 * @author jixd
	 * @created 2016年11月04日 10:31:25
	 * @param
	 * @return
	 */
	String findLandlordBehavior(String uid);

	/**
	 * 新增房东行为统计
	 * @author jixd
	 * @created 2016年11月04日 10:31:29
	 * @param
	 * @return
	 */
	String saveLandlordBehavior(String paramJson);

	/**
	 * 更新房东行为统计
	 * @author jixd
	 * @created 2016年11月04日 10:31:33
	 * @param
	 * @return
	 */
	String updateLandlordBehaviorByUid(String paramJson);

	
}
