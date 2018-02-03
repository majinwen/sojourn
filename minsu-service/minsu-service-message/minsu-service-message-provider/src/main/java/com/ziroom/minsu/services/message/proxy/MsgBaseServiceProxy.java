/**
 * @FileName: MsgBaseServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午4:51:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgBaseErrorEntity;
import com.ziroom.minsu.entity.message.MsgBaseLogEntity;
import com.ziroom.minsu.entity.message.MsgBaseOfflineEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.entity.message.MsgUserLivenessEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.ImMsgListDto;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgCountRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsData;
import com.ziroom.minsu.services.message.dto.MsgReplyStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgStaticsRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;
import com.ziroom.minsu.services.message.dto.UserLivenessReqeust;
import com.ziroom.minsu.services.message.entity.AppMsgBaseVo;
import com.ziroom.minsu.services.message.entity.ImMsgListVo;
import com.ziroom.minsu.services.message.entity.ImMsgSyncVo;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryChatVo;
import com.ziroom.minsu.services.message.entity.MsgChatVo;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;
import com.ziroom.minsu.services.message.service.HuanxinImManagerServiceImpl;
import com.ziroom.minsu.services.message.service.MsgBaseLogServiceImpl;
import com.ziroom.minsu.services.message.service.MsgBaseOfflineServiceImpl;
import com.ziroom.minsu.services.message.service.MsgBaseServiceImpl;
import com.ziroom.minsu.services.message.service.MsgUserLivenessImpl;
import com.ziroom.minsu.valenum.base.AuthIdentifyEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.HouseCardEnum;

import org.apache.cxf.ws.addressing.MAPAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>留言基本信息的代理</p>
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
@Component("message.msgBaseServiceProxy")
public class MsgBaseServiceProxy implements MsgBaseService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgBaseServiceProxy.class);

	@Resource(name = "message.msgBaseServiceImpl")
	private MsgBaseServiceImpl msgBaseServiceImpl;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;

	@Resource(name="message.msgBaseLogServiceImpl")
	private MsgBaseLogServiceImpl msgBaseLogServiceImpl;

	@Resource(name="message.msgBaseOfflineServiceImpl")
	private MsgBaseOfflineServiceImpl msgBaseOfflineServiceImpl;

	@Value(value = "${FIRST_ADVISORY_FOLLOW_START_TIME}")
	private String fisrtAdvisoryFollowStartTime;


	@Resource(name = "message.huanxinImManagerServiceImpl")
	private HuanxinImManagerServiceImpl huanxinImManagerServiceImpl;
	
	@Resource(name="message.msgUserLivenessImpl")
	private MsgUserLivenessImpl msgUserLivenessImpl;
	
	/**
	 * 
	 * 查询检验
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:08:05
	 *
	 * @param msgBaseRequest
	 * @param dto
	 * @return
	 */
	private MsgBaseRequest getMsgBaseRequest(String msgBaseRequest,DataTransferObject dto){

		if(Check.NuNObj(dto)) dto = new DataTransferObject();
		MsgBaseRequest msgBaseRe = JsonEntityTransform.json2Entity(msgBaseRequest, MsgBaseRequest.class);
		return msgBaseRe;

	}

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
	@Override
	public String queryAllMsgByCondition(String msgBaseRequest) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		MsgBaseRequest msgBaseRe = getMsgBaseRequest(msgBaseRequest, dto);

		if(Check.NuNObj(msgBaseRe)||Check.NuNStr(msgBaseRe.getMsgHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgHouseFid is null or  msgBaseRequest is null");
			LogUtil.info(logger, "msgHouseFid is null or  msgBaseRequest is null");
			return dto.toJsonString();
		}
		PagingResult<MsgBaseEntity> listPagingResult = this.msgBaseServiceImpl.queryByPage(msgBaseRe); 

		dto.putValue("listMsgBase", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

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
	@Override
	public String save(String msgBaseEntity) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		MsgBaseEntity msgBase = JsonEntityTransform.json2Entity(msgBaseEntity, MsgBaseEntity.class);

		if(Check.NuNObj(msgBase)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgBaseEntity is null");

			return dto.toJsonString();
		}

		dto.putValue("result", msgBaseServiceImpl.save(msgBase));
		return dto.toJsonString();
	}

	/**
	 * 
	 * update is_del ,is_read  by msgHouseFid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午4:50:12
	 *
	 * @param msgHouseFid
	 * @return
	 */
	@Override
	public String updateByMsgHouseFid(String msgHouseFid) {

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		String fid = JsonEntityTransform.json2String(msgHouseFid);
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgHouseFid is null or empty");
		}
		dto.putValue("result", this.msgBaseServiceImpl.updateByMsgHouseFid(msgHouseFid));
		return dto.toJsonString();
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
	@Override
	public String queryByCondition(String msgBaseRequest){
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		MsgBaseRequest msgBaseRe = getMsgBaseRequest(msgBaseRequest, dto);

		if(Check.NuNObj(msgBaseRe)||Check.NuNStr(msgBaseRe.getMsgHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgHouseFid is null or  msgBaseRequest is null");
			LogUtil.info(logger, "msgHouseFid is null or  msgBaseRequest is null");
			return dto.toJsonString();
		}
		List<MsgBaseEntity> listBaseEntities = this.msgBaseServiceImpl.queryByCondition(msgBaseRe); 

		dto.putValue("listBaseEntities", listBaseEntities);

		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#updateByMsgHouseReadFid(java.lang.String)
	 */
	@Override
	public String updateByMsgHouseReadFid(String paramJson) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(paramJson)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		MsgBaseRequest request = JsonEntityTransform.json2Entity(paramJson, MsgBaseRequest.class);
		if(Check.NuNObj(request)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgHouseFid is null or empty");
		}
		dto.putValue("result", this.msgBaseServiceImpl.updateByMsgHouseReadFid(request));
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#queryMsgCountByItem(java.lang.String)
	 */
	@Override
	public String queryMsgCountByItem(String request) {
		DataTransferObject dto = new DataTransferObject();
		MsgBaseRequest msgRequest = JsonEntityTransform.json2Entity(request, MsgBaseRequest.class);
		Integer sendType = msgRequest.getMsgSenderType();
		if(Check.NuNObj(sendType)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("发送人类型不能为空");
			return dto.toJsonString();
		}
		if(Check.NuNStr(msgRequest.getMsgHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("消息会话ID不能为空");
			return dto.toJsonString();
		}
		long count = msgBaseServiceImpl.queryMsgCountByItem(msgRequest);
		dto.putValue("count", count);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#queryMsgCountByUid(java.lang.String)
	 */
	@Override
	public String queryMsgCountByUid(String request) {
		DataTransferObject dto = new DataTransferObject();
		MsgCountRequest msgCountRequest = JsonEntityTransform.json2Object(request, MsgCountRequest.class);
		if(Check.NuNObj(msgCountRequest.getMsgSenderType())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("发送人类型不能为空");
			return dto.toJsonString();
		}
		if(msgCountRequest.getMsgSenderType() == UserTypeEnum.TENANT.getUserType()){
			if(Check.NuNStr(msgCountRequest.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房东uid不能为空");
				return dto.toJsonString();
			}
		}
		if(msgCountRequest.getMsgSenderType() == UserTypeEnum.LANDLORD.getUserType()){
			if(Check.NuNStr(msgCountRequest.getTenantUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房客uid不能为空");
				return dto.toJsonString();
			}
		}
		long count = msgBaseServiceImpl.queryMsgCountByUid(msgCountRequest);
		dto.putValue("count", count);
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#staticsCountLanImReplyNum(java.lang.String)
	 */
	@Override
	public String staticsCountLanImReplyNum(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			MsgStaticsRequest request = JsonEntityTransform.json2Object(param, MsgStaticsRequest.class);

			if(Check.NuNObj(request)){
				LogUtil.info(logger, "staticsCountLanImReplyNum 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplyNum 参数为空");
				return dto.toJsonString();
			}

			if(Check.NuNStr(request.getLandlordUid())){
				LogUtil.info(logger, "staticsCountLanImReplyNum landlordUid:{}", request.getLandlordUid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplyNum landlordUid为空");
				return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				LogUtil.info(logger, "staticsCountLanImReplyNum limitTime:{}", request.getLimitTime());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplyNum limitTime为空");
				return dto.toJsonString();
			}

			Long result = msgBaseServiceImpl.staticsCountLanImReplyNum(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#staticsCountLanImReplySumTime(java.lang.String)
	 */
	@Override
	public String staticsCountLanImReplySumTime(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			MsgStaticsRequest request = JsonEntityTransform.json2Object(param, MsgStaticsRequest.class);

			if(Check.NuNObj(request)){
				LogUtil.info(logger, "staticsCountLanImReplySumTime 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime 参数为空");
				return dto.toJsonString();
			}

			if(Check.NuNStr(request.getLandlordUid())){
				LogUtil.info(logger, "staticsCountLanImReplySumTime landlordUid:{}", request.getLandlordUid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime landlordUid为空");
				return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				LogUtil.info(logger, "staticsCountLanImReplySumTime limitTime:{}", request.getLimitTime());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime limitTime为空");
				return dto.toJsonString();
			}

			Long result = msgBaseServiceImpl.staticsCountLanImReplySumTime(request);
			dto.putValue("result", result);
		}catch (Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgBaseService#taskStaticsCountLanImReplyInfo(java.lang.String)
	 */
	@Override
	public String taskStaticsCountLanImReplyInfo(String param) {
		DataTransferObject dto = new DataTransferObject();
		try{
			MsgStaticsRequest request = JsonEntityTransform.json2Object(param, MsgStaticsRequest.class);

			if(Check.NuNObj(request)){
				LogUtil.info(logger, "staticsCountLanImReplySumTime 参数为空 param:{}", JsonEntityTransform.Object2Json(request));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime 参数为空");
				return dto.toJsonString();
			}

			if(Check.NuNStr(request.getLandlordUid())){
				LogUtil.info(logger, "staticsCountLanImReplySumTime landlordUid:{}", request.getLandlordUid());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime landlordUid为空");
				return dto.toJsonString();
			}
			if(Check.NuNObj(request.getLimitTime())){
				LogUtil.info(logger, "staticsCountLanImReplySumTime limitTime:{}", request.getLimitTime());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("staticsCountLanImReplySumTime limitTime为空");
				return dto.toJsonString();
			}
			Long replyPeopleNum = msgBaseServiceImpl.staticsCountLanImReplyNum(request);
			Long replySumTime = msgBaseServiceImpl.staticsCountLanImReplySumTime(request);
			dto.putValue("replyPeopleNum", replyPeopleNum);
			dto.putValue("replySumTime", replySumTime);

		}catch (Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}





	/**
	 * troy查询房东未回复的IM记录（1小时以内）
	 * @author lishaochuan
	 * @create 2016年8月4日下午2:30:07
	 * @param param
	 * @return
	 */
	@Override
	public String getNotReplyList(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			PageRequest pageRequest = JsonEntityTransform.json2Object(param, PageRequest.class);
			PagingResult<MsgNotReplyVo> pageResult = msgBaseServiceImpl.getNotReplyList(pageRequest);
			dto.putValue("total", pageResult.getTotal());
			dto.putValue("list", pageResult.getRows());
		} catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(logger, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}


	/**
	 * 条件更新 消息类型 供修改 系统消息类容
	 * @param msgBaseEntity
	 * @return
	 */
	@Override
	public String updateByCondition(String msgBaseEntity){

		DataTransferObject dto = new DataTransferObject();

		MsgBaseEntity msgBase = JsonEntityTransform.json2Object(msgBaseEntity, MsgBaseEntity.class);

		if(Check.NuNObj(msgBase)||Check.NuNStr(msgBase.getMsgHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		int  result = this.msgBaseServiceImpl.updateByCondition(msgBase);
		dto.putValue("result", result);
		return dto.toJsonString();
	}

	/**
	 * 根据主记录fid 查询当前最近的咨询信息
	 * @param msgHouseFid
	 * @return
	 */
	@Override
	public String  queryCurrMsgBook(String msgHouseFid){

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(msgHouseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		MsgBaseEntity msgBase  = this.msgBaseServiceImpl.queryCurrMsgBook(msgHouseFid);
		dto.putValue("msgBase", msgBase);
		return dto.toJsonString();

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
	@Override
	public String queryTwoChatRecord(String msgHouseRequest){

		DataTransferObject dto = new DataTransferObject();

		MsgHouseRequest msgHouseRe = JsonEntityTransform.json2Object(msgHouseRequest, MsgHouseRequest.class);

		if(Check.NuNObj(msgHouseRe)||Check.NuNStr(msgHouseRe.getTenantUid())||Check.NuNStr(msgHouseRe.getLandlordUid())){
			LogUtil.error(logger, "查询好友聊天记录参数错误msgHouseRe={}", msgHouseRe==null?"":msgHouseRe.toJsonStr());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		PagingResult<MsgBaseEntity> pageRe = this.msgBaseServiceImpl.queryTwoChatRecord(msgHouseRe);

		dto.putValue("listMsg", pageRe.getRows());
		dto.putValue("count", pageRe.getTotal());

		return dto.toJsonString();
	}

	/**
	 * 
	 * 分页 查询当前一个用户 所有的聊天记录
	 * 
	 * 消息类型处理：
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	@Override
	public String queryOneChatRecord(String msgHouseRequest){
		LogUtil.info(logger, "查询好友聊天记录参数msgHouseRequest={}", msgHouseRequest);
		DataTransferObject dto = new DataTransferObject();

		MsgHouseRequest msgHouseRe = JsonEntityTransform.json2Object(msgHouseRequest, MsgHouseRequest.class);
        long start =  System.currentTimeMillis();
        
		if(Check.NuNObj(msgHouseRe)||Check.NuNStr(msgHouseRe.getTenantUid())||Check.NuNStr(msgHouseRe.getLandlordUid())){
			LogUtil.error(logger, "查询好友聊天记录参数错误msgHouseRe={}", msgHouseRe==null?"":msgHouseRe.toJsonStr());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		//PagingResult<AppMsgBaseVo> pageRe = this.msgBaseServiceImpl.queryOneChatRecord(msgHouseRe);
		List<AppMsgBaseVo> resultList = new ArrayList<AppMsgBaseVo>();
		List<MsgHouseEntity> fidList = msgBaseServiceImpl.getAllMsgHouseFid(msgHouseRe);
		
		for (MsgHouseEntity msgHouse : fidList) {
			try {
				/*if("0".equals(msgHouse.getHouseFid())){
					continue;
				}*/
				List<AppMsgBaseVo> msgBaseVo = msgBaseServiceImpl.fillMsgBaseByMsgHouse(msgHouse.getFid());
				for (AppMsgBaseVo appMsgBase : msgBaseVo) {
					if(appMsgBase.getMsgSenderType()==1 || appMsgBase.getMsgSenderType()==10){
						appMsgBase.setFroms(msgHouse.getLandlordUid());
						appMsgBase.setTos(msgHouse.getTenantUid());
					}
					if (appMsgBase.getMsgSenderType()==3 || appMsgBase.getMsgSenderType()==20 || appMsgBase.getMsgSenderType()==2) {
						appMsgBase.setFroms(msgHouse.getTenantUid());
						appMsgBase.setTos(msgHouse.getLandlordUid());
					}
					appMsgBase.setRentWay(msgHouse.getRentWay());
					appMsgBase.setHouseFid(msgHouse.getHouseFid());
					resultList.add(appMsgBase);
				}
			} catch (Exception e) {
				LogUtil.info(logger, "查询好友聊天记录参数,异常信息{}", e);
				continue;
			}
			
		}
		dto.putValue("listMsg", resultList);
		dto.putValue("count", resultList.size());
		 
		return dto.toJsonString();
	}

	/**
	 * 
	 * 分页 查询当前一个用户 所有的聊天记录
	 * 
	 * 消息类型处理：
	 *
	 * @author yd
	 * @created 2016年9月14日 上午10:28:13
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	@Override
	public String findIMChatRecord(String msgHouseRequest){

		DataTransferObject dto = new DataTransferObject();

		MsgHouseRequest msgHouseRe = JsonEntityTransform.json2Object(msgHouseRequest, MsgHouseRequest.class);

		if(Check.NuNObj(msgHouseRe)||Check.NuNStr(msgHouseRe.getTenantUid())||Check.NuNStr(msgHouseRe.getLandlordUid())){
			LogUtil.error(logger, "查询好友聊天记录参数错误msgHouseRe={}", msgHouseRe==null?"":msgHouseRe.toJsonStr());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		//PagingResult<AppMsgBaseVo> pageRe = this.msgBaseServiceImpl.findIMChatRecord(msgHouseRe);
		List<AppMsgBaseVo> resultList = new ArrayList<AppMsgBaseVo>();
		List<MsgHouseEntity> fidList = msgBaseServiceImpl.getAllMsgHouseFidByIMChatRecord(msgHouseRe);
		
		for (MsgHouseEntity msgHouse : fidList) {
			try {
				/*if("0".equals(msgHouse.getHouseFid())){
					continue;
				}	*/
				List<AppMsgBaseVo> msgBaseVo = msgBaseServiceImpl.getMsgBaseByMsgHouse(msgHouse.getFid());
				for (AppMsgBaseVo appMsgBase : msgBaseVo) {
					if(appMsgBase.getMsgSenderType()==1 || appMsgBase.getMsgSenderType()==10){
						appMsgBase.setFroms(msgHouse.getLandlordUid());
						appMsgBase.setTos(msgHouse.getTenantUid());
					}
					if (appMsgBase.getMsgSenderType()==3 || appMsgBase.getMsgSenderType()==20 || appMsgBase.getMsgSenderType()==2) {
						appMsgBase.setFroms(msgHouse.getTenantUid());
						appMsgBase.setTos(msgHouse.getLandlordUid());
					}
					appMsgBase.setRentWay(msgHouse.getRentWay());
					appMsgBase.setHouseFid(msgHouse.getHouseFid());
					resultList.add(appMsgBase);
				}
			} catch (Exception e) {
				LogUtil.info(logger, "查询好友聊天记录参数,异常信息{}", e);
				continue;
			}
			
		}
		dto.putValue("listMsg", resultList);
		dto.putValue("count", resultList.size());
		 
		return dto.toJsonString();
	}

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
	@Override
	public String queryOneChatAllRecord(String msgHouseRequest){


		DataTransferObject dto = new DataTransferObject();

		MsgHouseRequest msgHouseRe = JsonEntityTransform.json2Object(msgHouseRequest, MsgHouseRequest.class);

		if(Check.NuNObj(msgHouseRe)||Check.NuNStr(msgHouseRe.getTenantUid())||Check.NuNStr(msgHouseRe.getLandlordUid())){
			LogUtil.error(logger, "查询好友聊天记录参数错误msgHouseRe={}", msgHouseRe==null?"":msgHouseRe.toJsonStr());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		List<AppMsgBaseVo> pageRe = this.msgBaseServiceImpl.queryOneChatAllRecord(msgHouseRe);
		dto.putValue("listMsg", pageRe);
		dto.putValue("count", Check.NuNCollection(pageRe)?0:pageRe.size());

		return dto.toJsonString();
	}

	@Override
	public String staticsLanReplyData(String request) {
		DataTransferObject dto = new DataTransferObject();

		MsgReplyStaticsRequest requestDto = JsonEntityTransform.json2Object(request, MsgReplyStaticsRequest.class);
		try {
			if(Check.NuNObj(requestDto)||Check.NuNStr(requestDto.getLandlordUid())){
				LogUtil.info(logger, "统计房东回复时间 参数错误 requestDto={}", JSON.toJSONString(requestDto));
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("参数错误");
				return dto.toJsonString();
			}

			MsgReplyStaticsData data =msgBaseServiceImpl.queryLanReplyTime(requestDto);
			dto.putValue("result", data);
		} catch (Exception e) {
			LogUtil.error(logger, "统计房东回复时间异常={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
			return dto.toJsonString();
		}

		return dto.toJsonString();
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
	@Override
	public String saveMsgBaseError(String msgBaseError){
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		MsgBaseErrorEntity msgBase = JsonEntityTransform.json2Entity(msgBaseError, MsgBaseErrorEntity.class);

		if(Check.NuNObj(msgBase)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgBaseError is null");

			return dto.toJsonString();
		}
		dto.putValue("result", msgBaseServiceImpl.saveMsgBaseError(msgBase));
		return dto.toJsonString();
	}

	/**
	 * 
	 *  查询im聊天列表
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param imMsgListDto
	 * @return
	 */
	@Override
	public String queryImMsgList(String imMsgListDto){

		DataTransferObject dto = new DataTransferObject();

		ImMsgListDto imMsgList = JsonEntityTransform.json2Object(imMsgListDto, ImMsgListDto.class);

		if(Check.NuNObj(imMsgList)
				||(Check.NuNStr(imMsgList.getTenantUid())
						&&Check.NuNStr(imMsgList.getLandlordUid()))){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		List<ImMsgListVo> listImMsgListVo = this.msgBaseServiceImpl.queryImMsgList(imMsgList);

		dto.putValue("listImMsgListVo", listImMsgListVo);
		return dto.toJsonString();
	}

	@Override
	public String saveMsgLog(String msgLog) {
		DataTransferObject dto = new DataTransferObject();
		MsgBaseLogEntity msgBaseLogEntity = JsonEntityTransform.json2Object(msgLog, MsgBaseLogEntity.class);
		if (Check.NuNObj(msgBaseLogEntity))	{
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int count = msgBaseLogServiceImpl.saveLog(msgBaseLogEntity);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String saveMsgOffline(String msgOffline) {
		DataTransferObject dto = new DataTransferObject();
		MsgBaseOfflineEntity msgBaseOfflineEntity = JsonEntityTransform.json2Object(msgOffline, MsgBaseOfflineEntity.class);
		if (Check.NuNObj(msgBaseOfflineEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int count = msgBaseOfflineServiceImpl.saveOffline(msgBaseOfflineEntity);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String listImMsgSyncList(String msgSyncRequest) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(msgSyncRequest)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		MsgSyncRequest request = JsonEntityTransform.json2Object(msgSyncRequest, MsgSyncRequest.class);
		if (Check.NuNStr(request.getUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("用户uid为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(request.getPage()) || Check.NuNObj(request.getLimit())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("分页参数为空");
			return dto.toJsonString();
		}

		List<ImMsgSyncVo> rows = null;
		List<String> toUidList = new ArrayList<String>();
		long total = 0;
		try {
			PagingResult<ImMsgSyncVo> pagingResult = msgBaseServiceImpl.listImMsgSyncList(request);
			total = pagingResult.getTotal();
			rows = pagingResult.getRows();
			//群聊 过滤掉 用户未入群之前的消息
			PagingResult<HuanxinImRecordEntity> huanxinImRecordPagingResult = 	msgBaseServiceImpl.queryHuanxinImRecordByPage(request);
			List<HuanxinImRecordEntity> listHuanxinImRecord = huanxinImRecordPagingResult.getRows();
			if(!Check.NuNCollection(huanxinImRecordPagingResult.getRows())){
				Map<String, Date> mapMem = this.huanxinImManagerServiceImpl.findIntoGroupTimeByMember(request.getUid());
				if(!Check.NuNMap(mapMem)){
					List<HuanxinImRecordEntity> removeList = new ArrayList<HuanxinImRecordEntity>(); 
					for (HuanxinImRecordEntity huanxinImRecordEntity : huanxinImRecordPagingResult.getRows()) {
						Date intoGroupTime = mapMem.get(huanxinImRecordEntity.getToUid());
						if(!Check.NuNObjs(intoGroupTime,huanxinImRecordEntity.getTimestampSend())
								&&huanxinImRecordEntity.getTimestampSend().before(intoGroupTime)){
							removeList.add(huanxinImRecordEntity);
						}
						
						
					}
					listHuanxinImRecord.removeAll(removeList);
				}
				
				//从t_huanxin_im_record_img获取所有is_certified=1和is_compliance=1的huanxin_fid集合
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("ziroomFlag", AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType());
				map.put("isCertified", YesOrNoEnum.YES.getCode());
				map.put("isCompliance",  YesOrNoEnum.YES.getCode());
				List<String> list = huanxinImManagerServiceImpl.getAllAbnormalPic(map);
				dto.putValue("allAbnormalPic",list);
			}
			total +=listHuanxinImRecord.size();
			dto.putValue("listHuanxinImRecord",listHuanxinImRecord);
		} catch (Exception e) {
			LogUtil.error(logger, "【同步环信聊天消息异常】msgSyncRequest={},e={}", msgSyncRequest,e);
		}
		dto.putValue("total",total);
		dto.putValue("list",rows);

		return dto.toJsonString();
	}
	
	/**
	 * 获取实际地址
	 * @param huanxinId
	 * @return
	 */
	private String getTrueUid(String huanxinId){
		if(Check.NuNStr(huanxinId)||!huanxinId.contains("app_")){
			return huanxinId;
		}
		return huanxinId.split("app_")[1];
	}

	@Override
	public String listChatBoth(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		List<MsgChatVo> msgChatVos = msgBaseServiceImpl.listChatBoth(fid);
		dto.putValue("list",msgChatVos);
		return dto.toJsonString();
	}

	@Override
	public String listChatOnAdvisory(String msgBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(msgBaseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		List<MsgAdvisoryChatVo> list = msgBaseServiceImpl.listChatOnAdvisory(msgBaseFid, fisrtAdvisoryFollowStartTime);
		List<MsgAdvisoryChatVo> result = new ArrayList<>();
		boolean isFindFisrtAdvisory = false;
		for (int i = 0; i < list.size(); i++) {
			MsgAdvisoryChatVo msgAdvisoryChatVo = list.get(i);
			if (Check.NuNObj(msgAdvisoryChatVo)) {
				LogUtil.error(logger, "msgAdvisoryChatVo is null");
				continue;
			}
			if (Check.NuNObj(msgAdvisoryChatVo.getHouseCard())) {
				msgAdvisoryChatVo.setHouseCard(0);
			}
			//第一次遇到首次咨询 变换状态
			if (msgAdvisoryChatVo.getHouseCard() == HouseCardEnum.HOUSE_CARD_FIRST_ADVISORY.getVal() && !isFindFisrtAdvisory) {
				isFindFisrtAdvisory = true;
				result.add(msgAdvisoryChatVo);
				continue;
			}
			//第二次遇到首次咨询 结束
			if (msgAdvisoryChatVo.getHouseCard() == HouseCardEnum.HOUSE_CARD_FIRST_ADVISORY.getVal() && isFindFisrtAdvisory) {
				break;
			}
			result.add(msgAdvisoryChatVo);

		}
		dto.putValue("list",result);
		LogUtil.info(logger, "获取环信聊天记录出参：{}", dto.toJsonString());
		return dto.toJsonString();
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
	@Override
	public String queryUserChatInTwentyFour(String msgSyncRequest) {
		DataTransferObject dto = new DataTransferObject();
		PeriodHuanxinRecordRequest request = JsonEntityTransform.json2Object(msgSyncRequest, PeriodHuanxinRecordRequest.class);
		if (Check.NuNObj(request)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(request.getPage()) || Check.NuNObj(request.getLimit())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("分页参数为空");
			return dto.toJsonString();
		}
		PagingResult<HuanxinImRecordEntity> huanxinImRecordPagingResult = 	msgBaseServiceImpl.queryUserChatInTwentyFour(request);
		List<HuanxinImRecordEntity> listHuanxinImRecord = huanxinImRecordPagingResult.getRows();
		dto.putValue("list", listHuanxinImRecord);
		return dto.toJsonString();
	}

	/**
	 * 查询用户活跃度
	 *
	 * @author loushuai
	 * @created 2017年9月14日 下午1:50:15
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String queryLiveness(String object2Json) {
		DataTransferObject dto = new DataTransferObject();
		UserLivenessReqeust msgUserLiveness = JsonEntityTransform.json2Object(object2Json, UserLivenessReqeust.class);
		if (Check.NuNObj(msgUserLiveness) || Check.NuNCollection(msgUserLiveness.getUidList())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		List<MsgUserLivenessEntity> list = msgUserLivenessImpl.getAllUidLiveness(msgUserLiveness.getUidList());
		dto.putValue("list", list);
		return dto.toJsonString();
	}
}
