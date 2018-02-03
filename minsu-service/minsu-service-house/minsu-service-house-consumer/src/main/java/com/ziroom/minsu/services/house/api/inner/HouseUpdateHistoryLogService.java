/**
 * @FileName: HouseUpdateHistoryLogService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author yd
 * @created 2017年7月4日 下午4:52:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房源 跟新记录 相关接口</p>
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
public interface HouseUpdateHistoryLogService {

	
	/**
	 * 
	 * 保存 用户更新记录
	 *
	 * @author yd
	 * @created 2017年7月4日 下午4:54:31
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveHistoryLog(String paramJson);
	

	/**
	 * 
	 * 查询 所有房源 待修改实体
	 *
	 * @author yd
	 * @created 2017年7月7日 上午11:05:12
	 *
	 * @param waitUpdateHouseInfoDto
	 * @return
	 */
	public String findWaitUpdateHouseInfo(String  waitUpdateHouseInfoDto);
}
