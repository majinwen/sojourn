/**
 * @FileName: MsgHouseService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年4月18日 下午5:25:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>房源消息接口</p>
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
public interface MsgHouseService {

	/**
	 * 
	 * 分页查询所有消息记录(后台使用)
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:26:45
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public  String queryAllByPage(String msgHouseRequest);
	
	/**
	 * 
	 * 分页查询所有消息记录(房东端)
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:26:45
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public  String queryLandlordByPage(String msgHouseRequest);
	
	/**
	 * 
	 * 分页查询所有消息记录(房客端)
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:26:45
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public  String queryTenantByPage(String msgHouseRequest);
	
	/**
	 * 
	 * 查询房东端列表页（只显示房客记录）
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:10:21
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryLandlordList(String msgHouseRequest);
	/**
	 * 
	 * 查询房客端列表页（只显示最后房东记录）
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:10:40
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryTenantList(String msgHouseRequest);
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:29:02
	 *
	 * @param msgHouseEntity
	 * @return
	 */
	public String save(String msgHouseEntity);
	
	/**
	 * 
	 * delete entity by fid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:29:52
	 *
	 * @param fid
	 * @return
	 */
	public String deleteByFid(String fid);
	/**
	 *
	 * query MsgHouseEntity by fid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午1:33:43
	 *
	 * @param fid
	 * @return
	 */
	public String queryByFid(String fid);
	
	/**
	 * 
	 * 查询好友（根据房东好友uid或查询房客好友uid）
	 * 
	 * 只能单向查询(不能同时包含两个uid或同时为null)
	 *
	 * @author yd
	 * @created 2016年9月14日 下午4:13:29
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryFriendsUid(String msgHouseRequest);
	
	/**
	 * 
	 * 保存环信IM的消息
	 *
	 * @author yd
	 * @created 2016年9月21日 下午5:47:13
	 *
	 * @param appChatRecordsDto
	 * @return
	 */
	public String saveImMsg(String appChatRecordsDto);

	/**
	 * 查询单位时间内房源(房间)咨询量
	 * 单位时间内相同房客,相同房东,相同房源(房间)的所有IM消息算一次咨询
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param paramJson
	 * @return
	 */
	public String queryConsultNumByHouseFid(String paramJson);

}
