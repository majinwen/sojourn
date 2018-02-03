/**
 * @FileName: HouseIssueAppService.java
 * @Package com.ziroom.minsu.services.house.api.inner
 * 
 * @author bushujie
 * @created 2017年6月19日 下午2:32:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>发布房源（app原生化使用）</p>
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
public interface HouseIssueAppService {
	
	/**
	 * 
	 * 查询房源基础信息、物理信息和扩展信息
	 *
	 * @author bushujie
	 * @created 2017年6月19日 下午2:34:20
	 *
	 * @param houseBaseFid
	 * @return
	 */
	String searchHousePhyAndExt(String houseBaseFid);
	
	/**
	 * 
	 * 保存或更新房源基础信息、物理信息和扩展信息
	 *
	 * @author bushujie
	 * @created 2017年6月20日 下午3:14:04
	 *
	 * @param paramJson
	 * @return
	 */
	String saveHousePhyAndExt(String paramJson);
	
	/**
	 * 
	 * 保存或更新房源描述信息
	 *
	 * @author bushujie
	 * @created 2017年6月21日 下午2:58:24
	 *
	 * @param paramJson
	 * @return
	 */
	String saveHouseDesc(String paramJson);
	
	/**
	 * 
	 * 查询房源配置和价格信息
	 *
	 * @author bushujie
	 * @created 2017年6月23日 上午11:49:07
	 *
	 * @param fid
	 * @return
	 */
	public String searchHouseConfAndPrice(String fid);
	
	/**
	 * 
	 * 保存房源价格信息（整租）
	 *
	 * @author bushujie
	 * @created 2017年6月27日 上午11:14:56
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveHousePrice(String paramJson);

	/**
	 * 获取房源或者房间的入住信息
	 * @author jixd
	 * @created 2017年06月27日 16:14:09
	 * @param
	 * @return
	 */
	String searchHouseCheckInMsg(String paramJson);

	/**
	 * 保存
	 * @author jixd
	 * @created 2017年06月27日 19:58:53
	 * @param
	 * @return
	 */
	String saveHouseCheckInMsg(String paramJson);

	/**
	 *
	 * 保存房源描述及基础信息（整租）
	 *
	 * @author lusp
	 * @created 2017年6月29日 上午11:14:56
	 * @param paramJson
	 * @return
	 */
	public String saveHouseDescAndBaseInfoEntire(String paramJson);

	/**
	 *
	 * 保存房源描述及基础信息（分租）
	 *
	 * @author lusp
	 * @created 2017年6月29日 上午11:24:56
	 * @param paramJson
	 * @return
	 */
	public String saveHouseDescAndBaseInfoSublet(String paramJson);


	/**
	 * 保存基础价格，清洁费，周末价格，长期折扣
	 * @author wangwt
	 * @created 2017年06月29日 17:31:39
	 * @param
	 * @return
	 */
	String saveHouseOrRoomPriceForModify(String paramJson);
	
	
	/**
	 * 
	 * 保存房间及相关信息
	 *
	 * @author zl
	 * @created 2017年7月3日 下午5:23:04
	 *
	 * @return
	 */
	String saveAssembleRoomMsg(String paramJson);
	
	
	/**
	 * 更新房间数量 并更新房间信息
	 * @author jixd
	 * @created 2017年07月03日 18:21:03
	 * @param
	 * @return
	 */
	String updateRoomNumAndRoomMsg(String paramJson);
	
	
	/**
	 * 
	 * 保存户型及配置
	 *
	 * @author zl
	 * @created 2017年7月4日 下午2:23:04
	 *
	 * @return
	 */
	String saveHouseMsgAndConf(String paramJson);
	
	
}
