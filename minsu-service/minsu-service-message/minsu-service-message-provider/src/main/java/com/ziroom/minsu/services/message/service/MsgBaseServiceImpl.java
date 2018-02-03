/**
 * @FileName: MsgBaseServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年4月18日 下午2:28:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgBaseErrorEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordDao;
import com.ziroom.minsu.services.message.dao.MsgBaseDao;
import com.ziroom.minsu.services.message.dao.MsgBaseErrorDao;
import com.ziroom.minsu.services.message.dto.ImMsgListDto;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgCountRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsData;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;
import com.ziroom.minsu.services.message.entity.AppMsgBaseVo;
import com.ziroom.minsu.services.message.entity.ImMsgListVo;
import com.ziroom.minsu.services.message.entity.ImMsgSyncVo;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryChatVo;
import com.ziroom.minsu.services.message.entity.MsgChatVo;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * <p>基本消息的服务层</p>
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
@Service("message.msgBaseServiceImpl")
public class MsgBaseServiceImpl {

	/**
	 *  日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseServiceImpl.class);

	@Resource(name = "message.msgBaseDao")
	private MsgBaseDao msgBaseDao;
	
	@Resource(name = "message.msgBaseErrorDao")
	private MsgBaseErrorDao msgBaseErrorDao;
	
	@Resource(name = "message.huanxinImRecordDao")
	private HuanxinImRecordDao huanxinImRecordDao;

	/**
	 * 
	 * 条件分页查询留言房源关系实体 （房源留言fid必须要有）
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgBaseEntity> queryByPage(MsgBaseRequest msgBaseRequest){

		if(Check.NuNObj(msgBaseRequest)||Check.NuNStr(msgBaseRequest.getMsgHouseFid())){
			LogUtil.error(logger, "msgBaseRequest or msgHouseFid is null");
		}
		LogUtil.info(logger, "当前查询条件msgBaseRequest={}", msgBaseRequest);

		return msgBaseDao.queryByPage(msgBaseRequest);
	}
	
	/**
	 * 
	 * 条件查询
	 *
	 * @author yd
	 * @created 2016年4月19日 下午8:26:18
	 *
	 * @param msgBaseRequest
	 * @return
	 */
	public List<MsgBaseEntity> queryByCondition(MsgBaseRequest msgBaseRequest){
		return msgBaseDao.queryByCondition(msgBaseRequest);
	}

	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午4:49:05
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public int save(MsgBaseEntity msgBaseEntity){
		return this.msgBaseDao.save(msgBaseEntity);
	}
	
	/**
	 * 
	 * 根据msgHouseFid 修改留言状态is_del
	 *
	 * @author yd
	 * @created 2016年4月18日 下午1:51:40
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public int updateByMsgHouseFid(String msgHouseFid){
		return this.msgBaseDao.updateByMsgHouseFid(msgHouseFid);
	}
	
	
	/**
	 * 
	 * 更新消息为已读状态
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午9:48:22
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public int updateByMsgHouseReadFid(MsgBaseRequest request){
		return this.msgBaseDao.updateByMsgHouseReadFid(request);
	}
	
	/**
	 * 
	 * 查询一个对话的未读数量
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:50:11
	 *
	 * @param msgBaseRequest
	 * @return
	 */
	public long queryMsgCountByItem(MsgBaseRequest msgBaseRequest){
		return msgBaseDao.queryMsgCountByItem(msgBaseRequest);
	}
	
	/**
	 * 
	 * 查询一个房客或者房东消息未读数量
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:50:32
	 *
	 * @param request
	 * @return
	 */
	public long queryMsgCountByUid(MsgCountRequest request){
		return msgBaseDao.queryMsgCountByUid(request);
	}
	
	/**
	 * 统计 2小时内  房东回复在30min内回复的新增会话人数
	 * @author liyingjie
	 * @created 2016年7月5日 下午2:50:32
	 * @param request
	 * @return
	 */
	public Long staticsCountLanImReplyNum(MsgStaticsRequest request){
		Long result = msgBaseDao.staticsCountLanImReplyNum(request);
		return result;
	}
	
	/**
	 * 统计 2小时内  房东回复在30min内的时间和
	 * @author liyingjie
	 * @created 2016年7月5日 下午2:50:32
	 * @param request
	 * @return
	 */
	public Long staticsCountLanImReplySumTime(MsgStaticsRequest request){
		Long result = msgBaseDao.staticsCountLanImReplySumTime(request);
		return result;
	}
	
	
	/**
	 * troy查询房东未回复的IM记录（1小时以内）
	 * @author lishaochuan
	 * @create 2016年8月4日下午2:28:03
	 * @param pageRequest
	 * @return
	 */
	public PagingResult<MsgNotReplyVo> getNotReplyList(PageRequest pageRequest){
		return msgBaseDao.getNotReplyList(pageRequest);
	}
	
	/**
	 * 条件更新
	 * @param msgBaseRequest
	 * @return
	 */
	public int updateByCondition(MsgBaseEntity msgBaseEntity){
		return this.msgBaseDao.updateByCondition(msgBaseEntity);
	}
	
	/**
	 * 根据主记录fid 查询当前最近的咨询信息
	 * @param msgHouseFid
	 * @return
	 */
	public MsgBaseEntity  queryCurrMsgBook(String msgHouseFid){
		
		if(Check.NuNStr(msgHouseFid)){
			return null;
		}
		return msgBaseDao.queryCurrMsgBook(msgHouseFid);
				
	}
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
	public PagingResult<MsgBaseEntity> queryTwoChatRecord(MsgHouseRequest msgHouseRequest){
		return this.msgBaseDao.queryTwoChatRecord(msgHouseRequest);
	}
	
	/**
	 * 
	 * 分页 查询当前一个用户 所有的聊天记录
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<AppMsgBaseVo> queryOneChatRecord(MsgHouseRequest msgHouseRequest){
		return this.msgBaseDao.queryOneChatRecord(msgHouseRequest);
	}
	

	/**
	 * 
	 *  IM记录查询 ： 分页 查询当前一个用户 所有的聊天记录  
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<AppMsgBaseVo> findIMChatRecord(MsgHouseRequest msgHouseRequest){
		return this.msgBaseDao.findIMChatRecord(msgHouseRequest);
	}
	
	/**
	 * 
	 * 分页 查询当前一个用户 所有的聊天记录
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<AppMsgBaseVo> queryOneChatAllRecord(MsgHouseRequest msgHouseRequest){
		return this.msgBaseDao.queryOneChatAllRecord(msgHouseRequest);
	}
	
	/**
	 * 查询房东回复时长
	 * @param request
	 * @return
	 */
	public MsgReplyStaticsData queryLanReplyTime(MsgReplyStaticsRequest request){
		return msgBaseDao.queryLanReplyTime(request);
	}
	

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
	public int saveMsgBaseError(MsgBaseErrorEntity msgBaseError){
		return msgBaseErrorDao.saveMsgBaseError(msgBaseError);
	}
	
	/**
	 * 
	 * 查询im聊天列表
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<ImMsgListVo> queryImMsgList(ImMsgListDto imMsgListDto){
		return msgBaseDao.queryImMsgList(imMsgListDto);
	}

	/**
	 * 同步消息记录
	 * @author jixd
	 * @created 2017年04月07日 14:56:35
	 * @param
	 * @return
	 */
	public PagingResult<ImMsgSyncVo> listImMsgSyncList(MsgSyncRequest msgSyncRequest){
		return this.msgBaseDao.listImMsgSyncList(msgSyncRequest);
	}

	/**
	 * 查询双方的聊天内容
	 * @author jixd
	 * @created 2017年04月12日 11:00:23
	 * @param msgFid 消息主表fid
	 * @return
	 */
	public List<MsgChatVo> listChatBoth(String msgFid){
		return this.msgBaseDao.listChatBoth(msgFid);
	}

	/**
	  *	house_fid,msg_advisory_fid 维度的消息聊天列表
	  * @author wangwentao
	  * @created 2017/5/27 15:50
	  *
	  * @param
	  * @return
	  */
	public List<MsgAdvisoryChatVo> listChatOnAdvisory(String msgBaseFid, String fisrtAdvisoryFollowStartTime){
		return this.msgBaseDao.listChatOnAdvisory(msgBaseFid, fisrtAdvisoryFollowStartTime);
	}
	
	/**
	 * 
	 * 分页查询 聊天信息
	 *
	 * @author yd
	 * @created 2017年8月2日 下午2:13:30
	 *
	 * @param msgSyncRequest
	 * @return
	 */
	public PagingResult<HuanxinImRecordEntity> queryHuanxinImRecordByPage(MsgSyncRequest msgSyncRequest){
		return huanxinImRecordDao.queryHuanxinImRecordByPage(msgSyncRequest);
	}

	/**
	 * 获取用户24小时内聊天记录（t_huanxin_im_record表）
	 *
	 * @author loushuai
	 * @created 2017年9月5日 下午2:20:46
	 *
	 * @param object2Json
	 * @return
	 */
	public PagingResult<HuanxinImRecordEntity> queryUserChatInTwentyFour(PeriodHuanxinRecordRequest msgSyncRequest) {
		return huanxinImRecordDao.queryUserChatInTwentyFour(msgSyncRequest);
	}

	/**
	 * t_msg_house查询所有记录
	 *
	 * @author loushuai
	 * @created 2017年9月26日 下午4:34:38
	 *
	 * @param msgHouseRe
	 * @return
	 */
	public List<MsgHouseEntity> getAllMsgHouseFid(MsgHouseRequest msgHouseRe) {
		return msgBaseDao.getAllMsgHouseFid(msgHouseRe);
	}

	/**
	 * 查询t_msg_base表，填充所有需要的字段
	 *
	 * @author loushuai
	 * @created 2017年9月26日 下午4:55:30
	 *
	 * @param fid
	 * @return
	 */
	public List<AppMsgBaseVo> fillMsgBaseByMsgHouse(String msgHouseFid) {
		return msgBaseDao.fillMsgBaseByMsgHouse(msgHouseFid);
	}

	/**
	 * t_msg_house查询所有记录(im聊天)
	 *
	 * @author loushuai
	 * @created 2017年10月24日 下午3:56:55
	 *
	 * @param msgHouseRe
	 * @return
	 */
	public List<MsgHouseEntity> getAllMsgHouseFidByIMChatRecord(MsgHouseRequest msgHouseRe) {
		return msgBaseDao.getAllMsgHouseFidByIMChatRecord(msgHouseRe);
	}
	
	/**
	 * 查询t_msg_base表，填充所有需要的字段
	 *
	 * @author loushuai
	 * @created 2017年10月24日 下午3:42:38
	 *
	 * @param fid
	 * @return
	 */
	public List<AppMsgBaseVo> getMsgBaseByMsgHouse(String msgHouseFid) {
		return msgBaseDao.getMsgBaseByMsgHouse(msgHouseFid);
	}

	/**
	 * 根据msgBaseFid获取对象
	 *
	 * @author loushuai
	 * @created 2017年11月6日 下午4:35:31
	 *
	 * @param msgBaseFid
	 * @return
	 */
	public MsgBaseEntity findMsgBaseByFid(String msgBaseFid) {
		return msgBaseDao.findMsgBaseByFid(msgBaseFid);
	}
	
	/**
	 * 根据param获取两个人所有回话的集合
	 *
	 * @author loushuai
	 * @created 2017年11月6日 下午4:35:31
	 *
	 * @param msgBaseFid
	 * @return
	 */
	public List<MsgBaseEntity> findAllImByParam(Map<String, Object> param) {
		return msgBaseDao.findAllImByParam(param);
	}

}
