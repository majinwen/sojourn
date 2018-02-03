/**
 * @FileName: MsgHouseServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午5:24:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.*;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.message.api.inner.MsgHouseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.services.message.service.MsgFirstAdvisoryServiceImpl;
import com.ziroom.minsu.services.message.service.MsgHouseServiceImpl;
import com.ziroom.minsu.services.message.service.StatusConfServiceImpl;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.IsReadEnum;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源消息管理代理接口</p>
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
@Component("message.msgHouseServiceProxy")
public class MsgHouseServiceProxy implements MsgHouseService {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgHouseServiceProxy.class);


	@Resource(name = "message.msgFirstAdvisoryServiceImpl")
	private MsgFirstAdvisoryServiceImpl msgFirstAdvisoryService;

	@Resource(name = "message.msgHouseServiceImpl")
	private MsgHouseServiceImpl msgHouseServiceImpl;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "message.statusConfServiceImpl")
	private StatusConfServiceImpl statusConfServiceImpl;
	
	@Autowired
	private RedisOperations redisOperations;
	
	private final static String IM_OPEN_FLAG = "IM_OPEN_FLAG";

	@Value("${HUANXIN_SEND_TIME}")
	private String HUANXIN_SEND_TIME;

	

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
	@Override
	public String queryAllByPage(String msgHouseRequest) {

		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);
		PagingResult<MsgHouseEntity> listPagingResult = msgHouseServiceImpl.queryByPage(msRequest);

		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		dto.putValue("listMsgHouse", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

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
	@Override
	public String queryLandlordByPage(String msgHouseRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);

		if(Check.NuNObj(msRequest)||Check.NuNStr(msRequest.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msRequest is null or LandlordUid is null");

			return dto.toJsonString();
		}
		PagingResult<MsgHouseEntity> listPagingResult = msgHouseServiceImpl.queryByPage(msRequest);

		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		dto.putValue("listMsgHouse", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

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
	@Override
	public String queryTenantByPage(String msgHouseRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);

		if(Check.NuNObj(msRequest)||Check.NuNStr(msRequest.getTenantUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msRequest is null or TenantUid is null");
			return dto.toJsonString();
		}
		PagingResult<MsgHouseEntity> listPagingResult = msgHouseServiceImpl.queryByPage(msRequest);

		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		dto.putValue("listMsgHouse", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

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
	@Override
	public String save(String msgHouseEntity) {

		DataTransferObject dto = new DataTransferObject();

		MsgHouseEntity msgHouse = JsonEntityTransform.json2Entity(msgHouseEntity, MsgHouseEntity.class);

		if(Check.NuNObj(msgHouse)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msgHouse is null");
			return dto.toJsonString();
		}

		dto.putValue("result", this.msgHouseServiceImpl.save(msgHouse));
		return dto.toJsonString();
	}

	/**
	 * 
	 * delete entity by fid 
	 * only to update `is_del`
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:29:52
	 *
	 * @param msgHouseEntity
	 * @return
	 */
	@Override
	public String deleteByFid(String msgHouseEntity) {
		DataTransferObject dto = new DataTransferObject();

		MsgHouseEntity msgHouse = JsonEntityTransform.json2Entity(msgHouseEntity, MsgHouseEntity.class);

		if(Check.NuNObj(msgHouse)||Check.NuNStr(msgHouse.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("MsgHouseEntity or fid is null or empty");
			return dto.toJsonString();
		}
		dto = this.msgHouseServiceImpl.deleteByFid(msgHouse);
		return dto.toJsonString();
	}


	/**
	 * 
	 * 查询检验
	 *
	 * @author yd
	 * @created 2016年4月18日 下午5:08:05
	 *
	 * @param msgHouseRequest
	 * @param dto
	 * @return
	 */
	private MsgHouseRequest getMsgBaseRequest(String msgHouseRequest,DataTransferObject dto){

		if(Check.NuNObj(dto)) dto = new DataTransferObject();
		MsgHouseRequest msgHouseRe = JsonEntityTransform.json2Entity(msgHouseRequest, MsgHouseRequest.class);
		return msgHouseRe;

	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgHouseService#queryLandlordList(java.lang.String)
	 */
	@Override
	public String queryLandlordList(String msgHouseRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);
		
		if(Check.NuNObj(msRequest)||Check.NuNStr(msRequest.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msRequest is null or LandlordUid is null");
			return dto.toJsonString();
		}
		PagingResult<MsgHouseListVo> listPagingResult = msgHouseServiceImpl.queryLandlordMsgList(msRequest);
		
		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		dto.putValue("listMsg", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgHouseService#queryTenantList(java.lang.String)
	 */
	@Override
	public String queryTenantList(String msgHouseRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);
		
		if(Check.NuNObj(msRequest)||Check.NuNStr(msRequest.getTenantUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("msRequest is null or tenantUid is null");
			return dto.toJsonString();
		}
		PagingResult<MsgHouseListVo> listPagingResult = msgHouseServiceImpl.queryTenantMsgList(msRequest);
		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		dto.putValue("listMsg", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}
	
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
	@Override
	public String queryByFid(String fid){
		
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStr(fid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("fid is null");
			return dto.toJsonString();
		}
		
		MsgHouseEntity msgHouseEntity = msgHouseServiceImpl.queryByFid(fid);
		dto.putValue("msgHouse", msgHouseEntity);
		return dto.toJsonString();
	}
	
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
	@Override
	public String queryFriendsUid(String msgHouseRequest){
		
		DataTransferObject dto = new DataTransferObject();
		MsgHouseRequest msRequest = this.getMsgBaseRequest(msgHouseRequest, dto);
		
		
		if(Check.NuNObj(msRequest)||(Check.NuNObj(msRequest.getLandlordUid())&&Check.NuNObj(msRequest.getTenantUid()))||
				(!Check.NuNObj(msRequest.getLandlordUid())&&!Check.NuNObj(msRequest.getTenantUid()))){
			
			LogUtil.error(logger, "查询好友列表参数错误msRequest={}", msRequest==null?"":msRequest.toJsonStr());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		List<String> listUid = this.msgHouseServiceImpl.queryFriendsUid(msRequest);
		
		dto.putValue("frendsUid", listUid);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 保存环信IM的消息
	 *  A. 保存IM信息
	 *  B. 保存房东的回复时长
	 * 
	 * @author yd
	 * @created 2016年9月21日 下午5:47:13
	 *
	 * @param appChatRecordsDto
	 * @return
	 */
	@Override
	public String saveImMsg(String appChatRecordsDto) {
		LogUtil.info(logger, "saveImMsg 参数appChatRecordsDto={}", appChatRecordsDto);
		DataTransferObject dto = new DataTransferObject();
		AppChatRecordsDto appChatRecords= JsonEntityTransform.json2Object(appChatRecordsDto, AppChatRecordsDto.class);
		
		
		if(!checkAppChatRecordsDto(appChatRecords)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setTenantUid(appChatRecords.getTenantUid());
		msgHouseRequest.setLandlordUid(appChatRecords.getLandlordUid());;
		MsgHouseEntity msgHouseEntity = this.msgHouseServiceImpl.queryOneMsgHouse(msgHouseRequest);

		AppChatRecordsExt appChatRecordsExt = appChatRecords.getAppChatRecordsExt();

		if(Check.NuNObj(msgHouseEntity)){
			msgHouseEntity = new MsgHouseEntity();
			msgHouseEntity.setImLastmodifyTime(appChatRecords.getMsgSendTime());
			msgHouseEntity.setCreateTime(new Date());
			msgHouseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			msgHouseEntity.setLandlordUid(appChatRecords.getLandlordUid());
			msgHouseEntity.setTenantUid(appChatRecords.getTenantUid());
			msgHouseEntity.setHouseFid("0");
			msgHouseEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		}

		MsgBaseEntity  msgBaseEntity = new MsgBaseEntity();
		Map<String, Object> msgContentMap = new HashMap<String, Object>();
		String msgContent = appChatRecords.getMsgContent();
		
		if(!Check.NuNObj(appChatRecordsExt)){
			if(Check.NuNStr(appChatRecordsExt.getHuanxinMsgId())){
				appChatRecordsExt.setHuanxinMsgId(UUIDGenerator.hexUUID());
			}
			msgContentMap.put("appChatRecordsExt", appChatRecordsExt);
			msgBaseEntity.setHouseFid(appChatRecordsExt.getFid());
			msgBaseEntity.setRentWay(appChatRecordsExt.getRentWay());
			
			if("#error#".equals(msgContent)){
				 msgContent = "";
                 if(!Check.NuNStrStrict(appChatRecordsExt.getPersonNum())){
                	 msgContent = msgContent+"入住人数："+appChatRecordsExt.getPersonNum()+",";
                 }
                 if(!Check.NuNStrStrict(appChatRecordsExt.getStartDate())
                		 &&!Check.NuNStrStrict(appChatRecordsExt.getEndDate())){
                	 msgContent = msgContent+"入住起始时间："+appChatRecordsExt.getStartDate()+",入住结束时间:"+appChatRecordsExt.getEndDate();
                 }
			}

			//增加系统消息表识 @Author:lusp @Date:2017/9/5
			if(!Check.NuNStrStrict(appChatRecordsExt.getMsgType())){
				if("3".equals(appChatRecordsExt.getMsgType())){
					msgBaseEntity.setIsSystemSend(1);
				}
			}
		}
		String msgContentJson = "";
		if(!Check.NuNStr(msgContent) && msgContent.length()>500){
			msgContentJson = msgContent.substring(0, 500);
	    }else{
	    	msgContentJson = msgContent;
	    }
		msgContentMap.put("msgContent", msgContentJson);
		
		
		
		
		msgBaseEntity.setMsgContent(JsonEntityTransform.Object2Json(msgContentMap));
		msgBaseEntity.setFid(UUIDGenerator.hexUUID());
		msgBaseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgBaseEntity.setIsRead(IsReadEnum.UNREAD.getCode());
		msgBaseEntity.setMsgSenderType(appChatRecords.getMsgSentType());
		msgBaseEntity.setMsgSendTime(appChatRecords.getMsgSendTime());
		//截取聊天消息开始，2017-01-29 ，线上有用户发送的msg超过了数据VARCHAR(1024)存贮范围，（民宿，驿站，长租的在线离线都是这样处理的
		if(!Check.NuNStr(msgContent)&& msgContent.length()>1024){
				msgContent = msgContent.substring(0, 1024);
				LogUtil.info(logger,"民宿聊天消息conent>1024,截取到1024长度，截取后msgContent={}",msgContent);
		}
		msgBaseEntity.setMsgRealContent(msgContent);
		if (!Check.NuNStr(appChatRecordsExt.getHouseCard())){
			msgBaseEntity.setHouseCard(Integer.parseInt(appChatRecordsExt.getHouseCard()));
		}
		
		//填充图片相关字段值
		msgBaseEntity.setType(appChatRecords.getType());
		msgBaseEntity.setUrl(appChatRecords.getUrl());
		msgBaseEntity.setFilename(appChatRecords.getFilename());
		msgBaseEntity.setFileLength(appChatRecords.getFileLength());
		msgBaseEntity.setSecret(appChatRecords.getSecret());
		msgBaseEntity.setSize(appChatRecords.getSize());
		
		int retult =  this.msgHouseServiceImpl.saveMsgHouseAndMsgBase(msgHouseEntity, msgBaseEntity,dto);
		MsgHouseEntity msgHouse = dto.parseData("msgHouse", new TypeReference<MsgHouseEntity>() {});

		handlerFirstAdvisory(appChatRecords,msgHouse,msgBaseEntity);

		if(retult>0){
			//处理房东IM回复时长
			this.msgHouseServiceImpl.updateImReplayTime(msgBaseEntity);
		}
		dto.putValue("retult", retult);
		return dto.toJsonString();
	}

	/**
	 * 处理首次咨询
	 * @author jixd
	 * @created 2017年04月10日 20:26:07
	 * @param
	 * @param msgHouse
	 *@param msgBaseEntity @return
	 */
	private void handlerFirstAdvisory(AppChatRecordsDto appChatRecordsDto, MsgHouseEntity msgHouse, MsgBaseEntity msgBaseEntity){
		AppChatRecordsExt appChatRecordsExt = appChatRecordsDto.getAppChatRecordsExt();
		Integer msgSentType = appChatRecordsDto.getMsgSentType();
		MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = new MsgFirstAdvisoryEntity();


		if ("1".equals(appChatRecordsExt.getHouseCard()) && appChatRecordsDto.getMsgSentType() == UserTypeEnum.TENANT_HUANXIN.getUserType()){
			msgFirstAdvisoryEntity.setFromUid(appChatRecordsDto.getTenantUid());
			msgFirstAdvisoryEntity.setToUid(appChatRecordsDto.getLandlordUid());

			LogUtil.info(logger,"第一次咨询保存咨询记录,msgBaseFid={}",msgBaseEntity.getFid());
			msgFirstAdvisoryEntity.setMsgSendTime(appChatRecordsDto.getMsgSendTime());
			msgFirstAdvisoryEntity.setHouseFid(appChatRecordsExt.getFid());
			msgFirstAdvisoryEntity.setMsgContent(appChatRecordsDto.getMsgContent());
			msgFirstAdvisoryEntity.setMsgContentExt(JsonEntityTransform.Object2Json(appChatRecordsExt));
			msgFirstAdvisoryEntity.setRentWay(appChatRecordsExt.getRentWay());
			msgFirstAdvisoryEntity.setStatus(RunStatusEnum.NOT_RUN.getValue());
			int sendMinute = Integer.parseInt(HUANXIN_SEND_TIME);
			try {
				msgFirstAdvisoryEntity.setRuntime(DateUtil.parseDate(DateUtil.timestampFormat(appChatRecordsDto.getMsgSendTime().getTime()+sendMinute*60*1000),"yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			msgFirstAdvisoryEntity.setMsgHouseFid(msgHouse.getFid());
			msgFirstAdvisoryEntity.setMsgBaseFid(msgBaseEntity.getFid());
			msgFirstAdvisoryService.saveMsgFirstAdvisory(msgFirstAdvisoryEntity);
		}

		//如果是房东回复  更新咨询表状态
		if (appChatRecordsDto.getMsgSentType() == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
			msgFirstAdvisoryEntity.setFromUid(appChatRecordsDto.getTenantUid());
			msgFirstAdvisoryEntity.setToUid(appChatRecordsDto.getLandlordUid());
			msgFirstAdvisoryEntity.setOldStatus(RunStatusEnum.NOT_RUN.getValue());
			msgFirstAdvisoryEntity.setStatus(RunStatusEnum.SYS_DELETE.getValue());

			LogUtil.info(logger,"房东回复更新咨询记录为系统删除upMsg={},msgBaseEntity={}",JsonEntityTransform.Object2Json(msgFirstAdvisoryEntity),JsonEntityTransform.Object2Json(msgBaseEntity));
			msgFirstAdvisoryService.updateByUid(msgFirstAdvisoryEntity);
		}

	}
	
	/**
	 * 
	 * 校验 扩展信息以及消息体信息
	 *
	 * @author yd
	 * @created 2016年9月21日 下午4:33:53
	 *
	 * @param appChatRecordsDto
	 * @return
	 */
	private boolean checkAppChatRecordsDto(AppChatRecordsDto appChatRecordsDto){
		
		
		if(Check.NuNObj(appChatRecordsDto)||Check.NuNStr(appChatRecordsDto.getLandlordUid())
				||Check.NuNObj(appChatRecordsDto.getMsgSentType())
				||Check.NuNStr(appChatRecordsDto.getTenantUid())){
			
			LogUtil.info(logger, "来自于IM聊天参数错误", Check.NuNObj(appChatRecordsDto)?"":appChatRecordsDto.toJsonStr());
			return false;
		}
		return true;
	}

	/**
	 * 查询单位时间内房源(房间)咨询量
	 * 单位时间内相同房客,相同房东,相同房源(房间)的所有IM消息算一次咨询
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String queryConsultNumByHouseFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			Map<String, Object> paramMap = JsonEntityTransform.json2Object(paramJson, Map.class);
			List<HouseStatsVo> list = msgHouseServiceImpl.queryConsultNumByHouseFid(paramMap);
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(logger, "queryConsultNumByHouseFid error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
