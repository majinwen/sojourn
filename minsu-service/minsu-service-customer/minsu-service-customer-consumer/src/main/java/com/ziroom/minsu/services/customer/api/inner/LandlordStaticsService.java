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
 * <p>客户中心相关业务接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public interface LandlordStaticsService {
	
	/**
	 * 更新房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param param
	 * @return
	 */
	public String staticsUpdateLanActAssociationImp(String param);

	/**
	 * 新增房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param param
	 * @return
	 */
	public String staticsInsertLanActAssociationImp(String param);

	/**
	 * 查询房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param param
	 * @return
	 */
	public String findLandlordStatisticsByUid(String paramJson);

	
}
