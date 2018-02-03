/**
 * @FileName: MsgBaseDao.java
 * @Package com.ziroom.minsu.services.message.dao.map
 * 
 * @author yd
 * @created 2016年4月16日 下午3:18:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.message.dto.ImMsgListDto;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgCountRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsData;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.entity.AppMsgBaseVo;
import com.ziroom.minsu.services.message.entity.ImMsgBaseVo;
import com.ziroom.minsu.services.message.entity.ImMsgListVo;
import com.ziroom.minsu.services.message.entity.ImMsgSyncVo;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryChatVo;
import com.ziroom.minsu.services.message.entity.MsgChatVo;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>基本留言持久化层</p>
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
@Repository("message.msgBaseDao")
public class MsgBaseDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseDao.class);

	private String SQLID = "message.msgBaseDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
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
	
		if(Check.NuNObj(msgBaseRequest)){
			LogUtil.error(logger, "msgBaseRequest  is null");
		}
		LogUtil.info(logger, "当前查询条件msgBaseRequest={}", msgBaseRequest);
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgBaseRequest.getLimit());
		pageBounds.setPage(msgBaseRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryByPage", MsgBaseEntity.class, msgBaseRequest, pageBounds);
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
		if(Check.NuNObj(msgBaseRequest)){
			LogUtil.error(logger, "msgBaseRequest  is null");
		}
		LogUtil.info(logger, "当前查询条件msgBaseRequest={}", msgBaseRequest);
		
		return mybatisDaoContext.findAll(SQLID+"queryByPage", MsgBaseEntity.class, msgBaseRequest);
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
		
		if(Check.NuNObj(msgBaseEntity) ){
			LogUtil.info(logger, "msgBaseEntity is null");
			return 0;
		}
		if(Check.NuNStr(msgBaseEntity.getFid())) msgBaseEntity.setFid(UUIDGenerator.hexUUID());
		return this.mybatisDaoContext.save(SQLID+"insertSelective", msgBaseEntity);
	}
	
	/**
	 * 
	 * 根据fid 更新实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午4:54:11
	 *
	 * @param msgBaseEntity
	 * @return
	 */
	public int updateByFid(MsgBaseEntity msgBaseEntity){
		
		if(Check.NuNObj(msgBaseEntity)||Check.NuNStr(msgBaseEntity.getFid())){
			LogUtil.info(logger, "msgBaseEntity or fid is null or empty");
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByFid", msgBaseEntity);
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
		
		if(Check.NuNStr(msgHouseFid)){
			LogUtil.info(logger,"msgHouseFid is null,return 0");
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgHouseFid", msgHouseFid);
		return this.mybatisDaoContext.update(SQLID+"updateByMsgHouseFid", params);
	}
	
	
	/**
	 * 
	 * 根据fid更新消息为已读
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午9:47:03
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public int updateByMsgHouseReadFid(MsgBaseRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("msgHouseFid", request.getMsgHouseFid());
		params.put("msgSenderType", request.getMsgSenderType());
		return this.mybatisDaoContext.update(SQLID+"updateByMsgHouseReadFid", params);
	}
	/**
	 * 
	 * 查询每个消息列表的（未读）消息个数
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:23:58
	 *
	 * @return
	 */
	public long queryMsgCountByItem(MsgBaseRequest msgBaseRequest){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("msgHouseFid", msgBaseRequest.getMsgHouseFid());
		paramMap.put("msgSenderType", msgBaseRequest.getMsgSenderType());
		paramMap.put("startTime", msgBaseRequest.getStartTime());
		paramMap.put("endTime", msgBaseRequest.getEndTime());
		return mybatisDaoContext.count(SQLID +"queryMsgCountByItem", paramMap);
	}
	
	/**
	 * 
	 * 查询房东或房客未读消息个数
	 *
	 * @author jixd
	 * @created 2016年7月5日 下午2:24:54
	 *
	 * @return
	 */
	public long queryMsgCountByUid(MsgCountRequest request){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("landlordUid", request.getLandlordUid());
		paramMap.put("tenantUid", request.getTenantUid());
		paramMap.put("msgSenderType", request.getMsgSenderType());
		return mybatisDaoContext.count(SQLID +"queryMsgCountByUid", paramMap);
	}
	
	
	
	/**
     * 统计 2小时内  房东回复在30min内回复的新增会话人数
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanImReplyNum(MsgStaticsRequest request){
    	
    	if(Check.NuNObj(request)){
    		LogUtil.info(logger, "staticsCountLanImReplyNum 参数对象为空");
			throw new BusinessException("staticsCountLanImReplyNum 参数对象为空");
    	}
    	if(Check.NuNStr(request.getLandlordUid())){
    		LogUtil.info(logger, "staticsCountLanImReplyNum landlordUid为空");
			throw new BusinessException("staticsCountLanImReplyNum landlordUid为空");
    	}
    	
    	if(Check.NuNObj(request.getLimitTime())){
    		LogUtil.info(logger, "staticsCountLanImReplyNum limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanImReplyNum limitTime为空");
    	}
    	
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("landlordUid", request.getLandlordUid());
    	params.put("limitTime", request.getLimitTime());
    	params.put("sumTime", request.getSumTime());
    	
		return mybatisDaoContext.count(SQLID +"staticsCountLanImReplyNum", params);
    }
    
    /**
     * 
     * 统计 2小时内  房东回复在30min内的时间和
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public Long staticsCountLanImReplySumTime(MsgStaticsRequest request){
    	
    	if(Check.NuNObj(request)){
    		LogUtil.info(logger, "staticsCountLanImReplySumTime 参数对象为空");
			throw new BusinessException("staticsCountLanImReplySumTime 参数对象为空");
    	}
    	if(Check.NuNStr(request.getLandlordUid())){
    		LogUtil.info(logger, "staticsCountLanImReplySumTime landlordUid为空");
			throw new BusinessException("staticsCountLanImReplySumTime landlordUid为空");
    	}
    	
    	if(Check.NuNObj(request.getLimitTime())){
    		LogUtil.info(logger, "staticsCountLanImReplySumTime limitTime:{}",request.getLimitTime());
			throw new BusinessException("staticsCountLanImReplySumTime limitTime为空");
    	}
    	
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("landlordUid", request.getLandlordUid());
    	params.put("limitTime", request.getLimitTime());
    	params.put("sumTime", request.getSumTime());
    	
		return mybatisDaoContext.count(SQLID +"staticsCountLanImReplySumTime", params);
    }
	
	
	/**
	 * troy查询房东未回复的IM记录（1小时以内）
	 * @author lishaochuan
	 * @create 2016年8月4日下午2:23:09
	 * @param pageRequest
	 * @return
	 */
	public PagingResult<MsgNotReplyVo> getNotReplyList(PageRequest pageRequest) {
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(pageRequest.getLimit());
		pageBounds.setPage(pageRequest.getPage());
    	
    	
		Map<String, Object> par = new HashMap<>();
		par.put("todayStart", DateSplitUtil.getFirstSecondOfDay(new Date()));
		par.put("todayEnd", DateSplitUtil.getLastSecondOfDay(new Date()));
		par.put("limitStart", DateSplitUtil.jumpHours(new Date(), -1));
		return mybatisDaoContext.findForPage(SQLID + "getNotReplyList", MsgNotReplyVo.class, par, pageBounds);
	}
	
	/**
	 * 条件更新 记录内容
	 * @param msgBaseRequest
	 * @return
	 */
	public int updateByCondition(MsgBaseEntity msgBaseEntity){
		
		//如果当前主记录id没有 不让更新
		if(Check.NuNStr(msgBaseEntity.getMsgHouseFid())){
			return 0;
		}
		return this.mybatisDaoContext.update(SQLID+"updateByCondition", msgBaseEntity);
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
		return mybatisDaoContext.findOne(SQLID+"queryCurrMsgBook", MsgBaseEntity.class, msgHouseFid);
				
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
		
		if(Check.NuNObj(msgHouseRequest)||Check.NuNStr(msgHouseRequest.getTenantUid())||Check.NuNStr(msgHouseRequest.getLandlordUid())){
			return null;
		}
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"queryTwoChatRecord", MsgBaseEntity.class, msgHouseRequest,pageBounds);
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
		
		if(Check.NuNObj(msgHouseRequest)||Check.NuNStr(msgHouseRequest.getTenantUid())||Check.NuNStr(msgHouseRequest.getLandlordUid())){
			return null;
		}
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"queryOneChatRecord", AppMsgBaseVo.class, msgHouseRequest,pageBounds);
	}
	
	
	/**
	 * 
	 * IM记录查询 ： 分页 查询当前一个用户 所有的聊天记录  
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<AppMsgBaseVo> findIMChatRecord(MsgHouseRequest msgHouseRequest){
		
		if(Check.NuNObj(msgHouseRequest)||Check.NuNStr(msgHouseRequest.getTenantUid())||Check.NuNStr(msgHouseRequest.getLandlordUid())){
			return null;
		}
		
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgHouseRequest.getLimit());
		pageBounds.setPage(msgHouseRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"findIMChatRecord", AppMsgBaseVo.class, msgHouseRequest,pageBounds);
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
		
		if(Check.NuNObj(msgHouseRequest)||Check.NuNStr(msgHouseRequest.getTenantUid())||Check.NuNStr(msgHouseRequest.getLandlordUid())){
			return null;
		}
		return this.mybatisDaoContext.findAll(SQLID+"queryOneChatRecord", AppMsgBaseVo.class, msgHouseRequest);
	}
	
	/**
	 * 查询房东回复时长
	 * @param request
	 * @return
	 */
	public MsgReplyStaticsData queryLanReplyTime(MsgReplyStaticsRequest request){
		MsgReplyStaticsData data = new MsgReplyStaticsData();
		
		if (request==null || Check.NuNStr(request.getLandlordUid())) {
			return null;
		}
		
		List<String> msgHouseIds = mybatisDaoContext.findAll(SQLID+"queryLandAllMsgHouseByDays", String.class, request);
		if (Check.NuNCollection(msgHouseIds) || msgHouseIds.size()<request.getMinLimit()) {
			request.setDays(request.getDays2());
			msgHouseIds = mybatisDaoContext.findAll(SQLID+"queryLandAllMsgHouseByDays", String.class, request);
		}
		
		if (!Check.NuNCollection(msgHouseIds) && msgHouseIds.size()>0) {
			request.setMsgHouseFidList(msgHouseIds);
			
			List<Integer> list = mybatisDaoContext.findAll(SQLID+"staticsLandReplyTimeByDays", Integer.class, request);
			if (!Check.NuNCollection(list)&& list.size()>0) {
				int sum = 0;
				int t30Num=0;
				for (Integer integer : list) {
					sum+=integer;
					if (integer<=30*60) {
						t30Num+=1;
					}
				}
				Integer repliedNum = mybatisDaoContext.findOne(SQLID+"staticsLandReplyNumByDays", Integer.class, request);
				repliedNum = repliedNum==null?0:repliedNum;
				data.setAvgRepliedTime(Double.valueOf(sum/list.size()).intValue());
				data.setMaxRepliedTime(list.get(list.size()-1));
				data.setMinRepliedTime(list.get(0));
				data.setRepliedMsgNum(repliedNum);
				data.setTotalMsgNum(msgHouseIds.size());
				data.setReplied30MsgNum(t30Num);
			}
		}
		
		return data;
	}
	
	/**
	 * 
	 * 根据当前 房东的聊天记录 查询上一条房东的聊天记录  没有的话返回null
	 *
	 * @author yd
	 * @created 2016年11月15日 下午7:48:22
	 *
	 * @param curMsgBase
	 * @return
	 */
	public MsgBaseEntity findPreLanRecord(MsgBaseEntity curMsgBase){
		
		if(!Check.NuNObj(curMsgBase)
				&&!Check.NuNStrStrict(curMsgBase.getFid())
				&&!Check.NuNStrStrict(curMsgBase.getMsgHouseFid())
				&&!Check.NuNObj(curMsgBase.getMsgSenderType())){
			
			return this.mybatisDaoContext.findOne(SQLID+"findPreLanRecord", MsgBaseEntity.class, curMsgBase);
		}
		
		return null;
	}
	
	/**
	 * 
	 * 根据 fid 查询实体
	 *
	 * @author yd
	 * @created 2016年11月15日 下午8:12:21
	 *
	 * @param fid
	 * @return
	 */
	public MsgBaseEntity findMsgBaseByFid(String fid){
		
		return this.mybatisDaoContext.findOne(SQLID+"findMsgBaseByFid", MsgBaseEntity.class, fid);
	}
	
	
	/**
	 * 
	 * 查询IM聊天列表
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<ImMsgListVo> queryImMsgList(ImMsgListDto imMsgListDto){
		return mybatisDaoContext.findAll(SQLID+"queryImMsgList", ImMsgListVo.class, imMsgListDto);
	}
	
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
	public List<ImMsgBaseVo> queryImMsgBaseVo(String msgHouseFid){
		return mybatisDaoContext.findAll(SQLID+"queryImMsgBaseVo", ImMsgBaseVo.class, msgHouseFid);
	}

	/**
	 * 同步消息列表
	 * @author jixd
	 * @created 2017年04月07日 14:40:38
	 * @param
	 * @return
	 */
	public PagingResult<ImMsgSyncVo> listImMsgSyncList(MsgSyncRequest msgSyncRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgSyncRequest.getLimit());
		pageBounds.setPage(msgSyncRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"listImMsgSyncList", ImMsgSyncVo.class, msgSyncRequest,pageBounds);
	}

	/**
	 * 消息内容列表
	 * @author jixd
	 * @created 2017年04月12日 10:55:48
	 * @param
	 * @return
	 */
	public List<MsgChatVo> listChatBoth(String msgFid){
		return mybatisDaoContext.findAll(SQLID + "listChatBoth",MsgChatVo.class,msgFid);
	}

	/**
	  *	house_fid,msg_advisory_fid 维度的消息聊天列表
	  * @author wangwentao
	  * @created 2017/5/27 15:48
	  *
	  * @param
	  * @param baseFid
     * @return
	  */
	public List<MsgAdvisoryChatVo> listChatOnAdvisory(String msgBaseFid, String fisrtAdvisoryFollowStartTime){
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("msgBaseFid", msgBaseFid);
		paramMap.put("fisrtAdvisoryFollowStartTime", fisrtAdvisoryFollowStartTime);
		return mybatisDaoContext.findAllByMaster(SQLID + "listChatOnAdvisory", MsgAdvisoryChatVo.class, paramMap);
	}


	/**
	 * t_msg_house查询所有记录
	 *
	 * @author loushuai
	 * @created 2017年9月26日 下午4:34:57
	 *
	 * @param msgHouseRe
	 * @return
	 */
	public List<MsgHouseEntity> getAllMsgHouseFid(MsgHouseRequest msgHouseRe) {
		return mybatisDaoContext.findAll(SQLID+"getAllMsgHouseFid", MsgHouseEntity.class, msgHouseRe);
	}


	/**
	 * 查询t_msg_base表，填充所有需要的字段
	 *
	 * @author loushuai
	 * @created 2017年9月26日 下午4:55:45
	 *
	 * @param fid
	 * @return
	 */
	public List<AppMsgBaseVo> fillMsgBaseByMsgHouse(String msgHouseFid) {
		return mybatisDaoContext.findAll(SQLID+"fillMsgBaseByMsgHouse", AppMsgBaseVo.class, msgHouseFid);
	}

	/**
	 * t_msg_house查询所有记录（im聊天）
	 *
	 * @author loushuai
	 * @created 2017年10月24日 下午3:57:47
	 *
	 * @param msgHouseRe
	 * @return
	 */
	public List<MsgHouseEntity> getAllMsgHouseFidByIMChatRecord(MsgHouseRequest msgHouseRe) {
		return mybatisDaoContext.findAll(SQLID+"getAllMsgHouseFidByIMChatRecord", MsgHouseEntity.class, msgHouseRe);
	} 


	/**
	 * 查询t_msg_base表，填充所有需要的字段
	 *
	 * @author loushuai
	 * @created 2017年10月24日 下午3:44:53
	 *
	 * @param msgHouseFid
	 * @return
	 */
	public List<AppMsgBaseVo> getMsgBaseByMsgHouse(String msgHouseFid) {
		return mybatisDaoContext.findAll(SQLID+"getMsgBaseByMsgHouse", AppMsgBaseVo.class, msgHouseFid);
	}


	/**
	 * 根据param获取两个人所有回话的集合
	 *
	 * @author loushuai
	 * @created 2017年11月22日 上午11:18:01
	 *
	 * @param param
	 * @return
	 */
	public List<MsgBaseEntity> findAllImByParam(Map<String, Object> param) {
		return mybatisDaoContext.findAll(SQLID+"findAllImByParam", MsgBaseEntity.class, param);
	}
	
}
