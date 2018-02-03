package com.ziroom.minsu.services.house.api.inner;


/**
 * 
 * <p>房源发布pc端相关接口</p>
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
public interface HouseIssuePcService {
	
	/**
	 * 
	 * 保存或者更新房源位置信息
	 *
	 * @author bushujie
	 * @created 2016年8月6日 下午3:45:20
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertOrUpdateHouseLocation(String paramJson);
	
	/**
	 * 
	 * 根据配置父类code模糊查询房源对应配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月9日 下午4:10:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseConfigByPcode(String paramJson);
	
	/**
	 * 
	 * 查询房源以及房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月11日 下午5:08:26
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseRoomList(String paramJson);
	
	/**
	 * 
	 * 查询房源信息和房间信息（带床位信息）
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午2:39:39
	 *
	 * @param param
	 * @return
	 */
	String findHouseRoomWithBedsList(String houseFid);
	
	/**
	 * 
	 * 保存或更新房间信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 上午10:26:57
	 *
	 * @param param
	 * @return
	 */
	String saveOrUpHouseFRooms(String param);
	
	/**
	 * 
	 * 删除房间信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午12:09:31
	 *
	 * @param param
	 * @return
	 */
	String delFRoomByFid(String roomFid);
	
	/**
	 * 
	 * 删除床铺信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午12:10:04
	 *
	 * @param param
	 * @return
	 */
	String delBedByFid(String bedFid);
	
	/**
	 * 
	 * 保存房源描述信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午9:20:21
	 *
	 * @param param
	 * @return
	 */
	String saveOrUpdateHouseDesc(String param);
	/**
	 * 
	 * 整租删除房间
	 *
	 * @author bushujie
	 * @created 2016年8月16日 下午7:42:42
	 *
	 * @param roomFid
	 * @return
	 */
	String delZRoomByFid(String roomFid);	
	/**
	 * 
	 * 获取房源基本信息和描述信息
	 *
	 * @author jixd
	 * @created 2016年8月16日 上午10:48:05
	 *
	 * @param param
	 * @return
	 */
	String findHouseBaseAndDesc(String houseFid);
	
	/**
	 * 
	 * 查询房源图片根据房源fid和房源图片
	 *
	 * @author jixd
	 * @created 2016年8月17日 上午11:35:34
	 *
	 * @param param
	 * @return
	 */
	String findHousePicByTypeAndFid(String param);
	
	 /** 保存或者更新整租房源房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月16日 下午9:37:47
	 *
	 * @param param
	 * @return
	 */
	String saveOrUpdateZroom(String param);
	
	/**
	 * 
	 * 获取房源默认图片集合（整租或者分租）
	 *
	 * @author jixd
	 * @created 2016年8月17日 下午9:26:48
	 *
	 * @param param
	 * @return
	 */
	String getHouseDefaultPicList(String houseFid);
	
	/**
	 * 
	 * 保存房源图片
	 *
	 * @author jixd
	 * @created 2016年8月18日 上午10:55:58
	 *
	 * @param param
	 * @return
	 */
	String saveHousePicByType(String param);
	/**
	 * 
	 * 删除房源图片 根据图片的fid
	 *
	 * @author jixd
	 * @created 2016年9月8日 下午5:18:09
	 *
	 * @param paramJson
	 * @return
	 */
	String deleteHousePicMsgByFid(String paramJson);
	
	/**
	 * 
	 * 
	 *
	 * @author jixd
	 * @created 2016年8月18日 下午7:20:09
	 *
	 * @param param
	 * @return
	 */
	String issueHouse(String param);
	
	/**
	 * 
	 * 发布合租房间列表
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:29:26
	 *
	 * @param param
	 * @return
	 */
	String issueRooms(String param);
	
	/**
	 * 
	 * 查询房源房间基本信息
	 *
	 * @author jixd
	 * @created 2016年8月18日 下午10:05:44
	 *
	 * @param param
	 * @return
	 */
	String getHouseRoomBaseMsgList(String houseFid);
	
	/**
	 * 
	 * 设置房间的默认图片（图片为客厅和公共区域部分）
	 *
	 * @author jixd
	 * @created 2016年8月24日 下午8:02:45
	 *
	 * @param param
	 * @return
	 */
	String setRoomDefaultPic(String param);
	
	/**
	 * 
	 * 更新房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月30日 上午10:25:49
	 *
	 * @param param
	 * @return
	 */
	String updateHouseConf(String param);
	
	/**
	 * 
	 * getHouseDefaultPicList新接口
	 *
	 * @author bushujie
	 * @created 2017年12月21日 下午4:32:53
	 *
	 * @param paramJson
	 * @return
	 */
	public String getHouseDefaultPicListNew(String paramJson);
}
