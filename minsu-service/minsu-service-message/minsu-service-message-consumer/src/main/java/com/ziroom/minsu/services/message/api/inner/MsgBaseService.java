/**
 * @FileName: MsgBaseService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2016年4月18日 下午4:16:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

/**
 * <p>留言基本信息接口</p>
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
public interface MsgBaseService {
	
	
	/**
	 * 
	 * 后台分页条件查询房客 房东的留言列表
	 *
	 * @author yd
	 * @created 2016年4月18日 下午4:45:53
	 *
	 * @param msgBaseRequest
	 * @return
	 */
	public String queryAllMsgByCondition(String msgBaseRequest);
	
	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年4月19日 下午8:28:42
	 *
	 * @param msgBaseRequest
	 * @return
	 */
	public String queryByCondition(String msgBaseRequest);
	
	/**
	 * 
	 * 查询对话中的消息未读数量
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:52:12
	 *
	 * @param msgBaseRequest
	 * @return
	 */
	public String queryMsgCountByItem(String msgBaseRequest);
	
	/**
	 * 
	 * 查询房客或者房东消息未读数量
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:52:32
	 *
	 * @param request
	 * @return
	 */
	public String queryMsgCountByUid(String request);
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午4:49:29
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public String save(String msgBaseEntity);
	
	/**
	 * 
	 * update is_del  by msgHouseFid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午4:50:12
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public String updateByMsgHouseFid(String msgHouseFid);
	
	/**
	 * 
	 * 更新消息为已读状态
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午9:50:38
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public String updateByMsgHouseReadFid(String msgBaseRequest);
	
	
	/**
	 * 统计 2小时内  房东回复在30min内回复的新增会话人数
	 * @author liyingjie
	 * @created 2016年7月5日 下午2:50:32
	 * @param request
	 * @return
	 */
	public String staticsCountLanImReplyNum(String request);
	
	/**
	 * 统计 2小时内  房东回复在30min内的时间和
	 * @author liyingjie
	 * @created 2016年7月5日 下午2:50:32
	 * @param request
	 * @return
	 */
	public String staticsCountLanImReplySumTime(String request);

	/**
	 * 定时任务统计相关
	 * @author liyingjie
	 * @created 2016年7月5日 下午2:50:32
	 * @param request
	 * @return
	 */
	public String taskStaticsCountLanImReplyInfo(String param);
	
	
	
	/**
	 * troy查询房东未回复的IM记录（1小时以内）
	 * @author lishaochuan
	 * @create 2016年8月4日下午2:30:07
	 * @param param
	 * @return
	 */
	public String getNotReplyList(String param);
	
	/**
	 * 条件更新 消息类型 供修改 系统消息类容
	 * @param msgBaseRequest
	 * @return
	 */
	public String updateByCondition(String msgBaseEntity);
	
	/**
	 * 根据主记录fid 查询当前最近的咨询信息
	 * @param msgHouseFid
	 * @return
	 */
	public String  queryCurrMsgBook(String msgHouseFid);
	/**
	 * 
	 * 房东或房客 条件查询IM聊天历史数据（ 数据是当前俩人的聊天数据）
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryTwoChatRecord(String msgHouseRequest);
	/**
	 * 
	 * 老聊天记录查询：分页 查询当前一个用户 所有的聊天记录
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryOneChatRecord(String msgHouseRequest);
	
	/**
	 * 
	 * IM记录查询：分页 查询当前一个用户 所有的聊天记录
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String findIMChatRecord(String msgHouseRequest);
	
	
	/**
	 * 
	 *  查询当前一个用户 所有的聊天记录
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public String queryOneChatAllRecord(String msgHouseRequest);
	
	/**
	 * 统计房东消息回复数据
	 * @author zl
	 * @param request
	 * @return
	 */
	public String staticsLanReplyData(String request);
	
	/**
	 * 
	 * 保存错误消息实体
	 *
	 * @author yd
	 * @created 2016年11月11日 上午11:49:36
	 *
	 * @param msgBaseError
	 * @return
	 */
	public String saveMsgBaseError(String msgBaseError);
	

	/**
	 * 
	 *  条件分页查询留言房源关系实体 （房源留言fid必须要有）
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param imMsgListDto
	 * @return
	 */
	public String queryImMsgList(String imMsgListDto);

	/**
	 * 保存实体
	 * @author jixd
	 * @created 2017年04月07日 09:59:27
	 * @param
	 * @return
	 */
	public String saveMsgLog(String msgLog);

	/**
	 * 保存离线消息
	 * @param msgOffline
	 * @return
	 */
	public String saveMsgOffline(String msgOffline);

	/**
	 * 同步消息记录
	 * @author jixd
	 * @created 2017年04月07日 14:57:52
	 * @param
	 * @return
	 */
	public String listImMsgSyncList(String msgSyncRequest);

	/**
	 * 查询消息的聊天列表
	 * @author jixd
	 * @created 2017年04月12日 11:01:24
	 * @param
	 * @return
	 */
	public String listChatBoth(String fid);

	/**
	  * house_fid,msg_advisory_fid 维度的消息聊天列表
	  * @author wangwentao
	  * @created 2017/5/27 15:49
	  *
	  * @param
	  * @return
	  */
	String listChatOnAdvisory(String msgBaseFid);

	/**
	 * 获取用户24小时内聊天记录（t_huanxin_im_record表）
	 *
	 * @author loushuai
	 * @created 2017年9月5日 下午2:20:46
	 *
	 * @param object2Json
	 * @return
	 */
	public String queryUserChatInTwentyFour(String msgSyncRequest);

	/**
	 * 查询用户活跃度
	 *
	 * @author loushuai
	 * @created 2017年9月14日 下午1:50:15
	 *
	 * @param object2Json
	 * @return
	 */
	public String queryLiveness(String object2Json);
}
