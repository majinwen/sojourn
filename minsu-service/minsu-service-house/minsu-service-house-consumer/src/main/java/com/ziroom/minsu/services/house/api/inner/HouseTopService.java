/**
 * @FileName: HouseTopService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2017年3月17日 下午4:24:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>top房源对外接口</p>
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
public interface HouseTopService {
	
	/**
	 * 
	 * 分页查询top房源数据
	 *
	 * @author bushujie
	 * @created 2017年3月17日 下午4:26:22
	 *
	 * @param paramJson
	 * @return
	 */
	public String topHouseListData(String paramJson);
	
	/**
	 * 
	 * 保存top房源数据
	 *
	 * @author bushujie
	 * @created 2017年3月21日 下午3:27:00
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertTopHouse(String paramJson);
	
	/**
	 * 
	 * top房源详情
	 *
	 * @author bushujie
	 * @created 2017年3月22日 上午10:55:33
	 *
	 * @param fid
	 * @return
	 */
	public String houseTopDetail(String fid);

	/**
	 *
	 * 交换两个序号
	 *
	 * @created 2017年3月22日
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseTopSortExchange(String paramJson);

	/**
	 *
	 * 上浮或者下沉
	 *
	 * @created 2017年3月22日
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseTopSortFloatOrSink(String paramJson);

	/**
	 *
	 * 上线下线
	 *
	 * @created 2017年3月22日
	 *
	 * @param fid
	 * @return
	 */
	public String houseTopOnlineOrDownline(String paramJson);
	
	/**
	 * 
	 * 更新top房源内容
	 *
	 * @author bushujie
	 * @created 2017年3月22日 下午6:02:20
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseTop(String paramJson);
	
	/**
	 * 
	 * 更新top房源条目
	 *
	 * @author bushujie
	 * @created 2017年3月23日 下午8:53:07
	 *
	 * @param fid
	 * @return
	 */
	public String updateHouseTopColumn(String paramJson);
}
