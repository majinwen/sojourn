/**
 * @FileName: HousePCService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author jixd
 * @created 2016年7月30日 下午2:44:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>PC使用相关的接口</p>
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
public interface HousePCService {

	/**
	 * 
	 * 获取整租和分租房源的数量,根据房东Uid
	 *
	 * @author jixd
	 * @created 2016年7月30日 下午3:42:20
	 *
	 * @return
	 */
	String countHouseAndRoomNumByUid(String landLordUid);
	
	/**
	 * 
	 * 获取房东整租 或者合租列表，根据rentWay来区分
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午4:21:44
	 *
	 * @param param
	 * @return
	 */
	String getLandlordHouseOrRoomList(String param);
	
	/**
	 * 
	 * 获取房东已上架房源 和房间信息，并按照更新时间降序排序
	 *
	 * @author jixd
	 * @created 2016年8月2日 下午5:34:01
	 *
	 * @param param
	 * @return
	 */
	String getOnlineHouseRoomList(String param);
	
	/**
	 * 
	 * 获取房源或者房间，出租日历时间列表(从上架，到出租截止时间)
	 *
	 * @author jixd
	 * @created 2016年8月3日 上午11:18:19
	 *
	 * @return
	 */
	String getCalendarDate(String param);
	
	/**
	 * 
	 * 获取日历上要展示的房源列表
	 *
	 * @author bushujie
	 * @created 2017年10月28日 下午1:49:53
	 *
	 * @param param
	 * @return
	 */
	String getCalendarHouseList(String param);
	
}
