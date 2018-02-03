/**
 * @FileName: TenantHouseService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2016年4月30日 下午4:35:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>房源房客端接口</p>
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
public interface TenantHouseService {
	
	/**
	 * 
	 * 房源详情(会查询房源或者房间某天或者当天的最终价格)
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午4:38:00
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseDetail(String paramJson);
	
	/**
	 * 
	 *房源列表详情
	 *
	 * @author bushujie
	 * @created 2016年5月1日 下午3:36:57
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseListDetail(String paramJson);
	
	/**
	 * 
	 *  统计浏览量
	 *
	 * @author bushujie
	 * @created 2016年5月15日 上午2:06:33
	 *
	 * @param paramJson
	 * @return
	 */
	public String statisticalPv(String paramJson);
	
	/**
	 * 
	 * 查询房源房间浏览量
	 *
	 * @author bushujie
	 * @created 2016年5月15日 下午3:04:40
	 *
	 * @param paramJson
	 * @return
	 */
	public String findStatisticalPv(String paramJson);
	/**
	 * 
	 * 根据 房源 或房间 fid 查询房源或房间信息（暂不支持 床位）
	 *
	 * @author yd
	 * @created 2016年9月26日 上午11:39:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseDetail(String paramJson);


	/**
	 * 获取房源描述信息
	 * @author lishaochuan
	 * @create 2016/12/7 14:43
	 * @param 
	 * @return 
	 */
	public String findHoseDesc(String paramJson);
}
