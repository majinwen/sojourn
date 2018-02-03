/**
 * @FileName: HuanxinImRecordServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * @author yd
 * @created 2016年9月10日 下午3:17:07
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;
import com.ziroom.minsu.services.message.dto.HuanxinImRecordDto;
import com.ziroom.minsu.services.message.dto.MsgFirstDdvisoryRequest;
import com.ziroom.minsu.services.message.entity.SysMsgVo;
import com.ziroom.minsu.services.message.service.HuanxinImRecordServiceImpl;
import com.ziroom.minsu.services.message.service.MsgFirstAdvisoryServiceImpl;
import com.ziroom.minsu.services.message.utils.DealHuanxinPicUtils;
import com.ziroom.minsu.services.message.utils.HuanxinUtils;
import com.ziroom.minsu.services.message.utils.SendHuanxinForChangzuThread;
import com.ziroom.minsu.services.message.utils.SendImMesToHuanxinThread;
import com.ziroom.minsu.services.message.utils.SysHuanxinImThread;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;
import com.ziroom.minsu.services.message.utils.base.SendImMsgRequest;

/**
 * <p>环信接口实现</p>
 *
 * 环信接口扩展信息：
 * 1.自如标识  ziroomFlag     ZIROOM_MINSU_IM 代表 民宿
 * 2.fid  房源或房间fid  
 * 3. rentWay 出租方式  0=整租 1=分租
 * 4. 房源或房间名称  houseName 
 * 5. 房源图片url  housePicUrl
 * 6. msgSenderType  消息发送人类型（1=房东 2=房客）
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
@Component("message.huanxinImRecordServiceProxy")
public class HuanxinImRecordServiceProxy implements HuanxinImRecordService {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HuanxinImRecordServiceProxy.class);


	@Resource(name = "message.huanxinImRecordServiceImpl")
	private HuanxinImRecordServiceImpl huanxinImRecordServiceImpl;

	@Autowired
	private RedisOperations redisOperations;

	@Value("#{'${HUANXIN_TOKEN_URL}'.trim()}")
	private String HUANXIN_TOKEN_URL;

	@Value("#{'${HUANXIN_CHATMESSAGE_URL}'.trim()}")
	private String HUANXIN_CHATMESSAGE_URL;

	@Value("#{'${HUANXIN_CLIENT_ID}'.trim()}")
	private String HUANXIN_CLIENT_ID;

	@Value("#{'${HUANXIN_CLIENT_SECRET}'.trim()}")
	private String HUANXIN_CLIENT_SECRET;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;

	@Value("#{'${IM_MINSU_FLAG}'.trim()}")
	private String IM_MINSU_FLAG;
	
	@Value("#{'${IM_CHANGZU_FLAG}'.trim()}")
	private String IM_CHANGZU_FLAG;

	@Value("#{'${HUANXIN_APP_KEY}'.trim()}")
	private String HUANXIN_APP_KEY;

	@Value("#{'${HUANXIN_DOMAIN}'.trim()}")
	private String HUANXIN_DOMAIN;

	@Value("#{'${IM_DOMAIN_FLAG}'.trim()}")
	private String IM_DOMAIN_FLAG;

	@Value("#{'${HUANXIN_SEND_IM_MSG_URL}'.trim()}")
	private String HUANXIN_SEND_IM_MSG_URL;

	@Value("#{'${HUANXIN_SEND_TIME}'.trim()}")
	private String HUANXIN_SEND_TIME;

	@Resource(name = "message.msgFirstAdvisoryServiceImpl")
	private MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl;
	
	@Value("#{'${DEACTIVATE_IM_USER_URL}'.trim()}")
	private String DEACTIVATE_IM_USER_URL;
	
	@Value("#{'${ACTIVATE_IM_USER_URL}'.trim()}")
	private String ACTIVATE_IM_USER_URL;


	/**
	 *
	 * 定时任务同步环信IM消息  每天晚上12点，同步前一天的数据，即：同步前一天晚上到今天晚上12点的数据（例如：2016/09/09 24:00:00   到 2016/09/10 24:00:00）
	 * 环信地址：http://docs.easemob.com/im/100serverintegration/30chatlog
	 * 接口限流说明：同一个 APP 每分钟最多可调用10次，超过的部分会返回429或503错误。所以在调用程序中，如果碰到了这样的错误，需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。（一次最多返回1000条）
	 * 算法：
	 * 1. 获取环信token （保存当前redis中，失效时间6天，redis失效后去环信获取）
	 * 2. 由于 msg_id 在环信返回中是唯一的，故为主键，并且入库时，以此值，校验重复，重复插入直接忽略
	 * 3. 以当前时间往前推hours小时（25小时 这个时间做成可配置的，比定时任务时间长1个小时即可  例如：定时任务24小时，hours就是25，影响：理论上多取1个小时数据，好处：能保证数据不丢失）
	 * 4. 对于接口调用次数限制问题处理：让接口去调用，出现429或503，让当前线程睡30s
	 * 5. 接口循环去调用，直到接口获取完所以数据停止
	 *
	 * @author yd
	 *
	 * @created 2016年9月10日 下午2:55:21
	 *
	 */
	@Override
	public String sysHuanxinImMes(String hours, String sleepScends) {

		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(hours)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("定时同步hours为null或者空值");
			return dto.toJsonString();
		}

		LogUtil.info(logger, "IM同步环信聊天记录开始");
		try {

			HuanxinConfig huanxinConfig = new HuanxinConfig();
			huanxinConfig.setHuanxinAppKey(HUANXIN_APP_KEY);
			huanxinConfig.setHuanxinChatMessageUrl(HUANXIN_CHATMESSAGE_URL);
			huanxinConfig.setHuanxinClientId(HUANXIN_CLIENT_ID);
			huanxinConfig.setHuanxinClientSecret(HUANXIN_CLIENT_SECRET);
			huanxinConfig.setHuanxinDomain(HUANXIN_DOMAIN);
			huanxinConfig.setHuanxinTokenUrl(HUANXIN_TOKEN_URL);
			huanxinConfig.setImMinsuFlag(IM_MINSU_FLAG);
			huanxinConfig.setDomainFlag(IM_DOMAIN_FLAG);
			
			
			
			SendThreadPool.execute(new SysHuanxinImThread(huanxinConfig, redisOperations, huanxinImRecordServiceImpl, Integer.valueOf(hours), Check.NuNStr(sleepScends) ? 10 : Integer.valueOf(sleepScends)));
		} catch (Exception e) {
			LogUtil.error(logger, "同步环信聊天记录异常e={}", e);
		}
		LogUtil.info(logger, "IM同步环信聊天记录结束");
		return dto.toJsonString();
	}


	/**
	 *
	 * 发送IM消息到环信 异步
	 *
	 * @author yd
	 * @created 2017年4月8日 下午2:50:04
	 *
	 * @return
	 */
	@Override
	public String sendImMesToHuanxin(String paramJson) {

		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(HUANXIN_SEND_TIME)) {
			HUANXIN_SEND_TIME = "5";
		}

		LogUtil.info(logger, "IM同步环信聊天记录开始");
		try {
			HuanxinConfig huanxinConfig = getHuanxinConfig();
			List<SendImMsgRequest> list = JsonEntityTransform.json2List(paramJson, SendImMsgRequest.class);
			if (!Check.NuNCollection(list)) {
				SendThreadPool.execute(new SendImMesToHuanxinThread(huanxinConfig, redisOperations, list, msgFirstAdvisoryServiceImpl));
			}
		} catch (Exception e) {
			LogUtil.error(logger, " 发送IM消息到环信 异常e={}", e);
		}
		LogUtil.info(logger, "发送IM消息到环信 结束");
		return dto.toJsonString();
	}

	private HuanxinConfig getHuanxinConfig() {
		HuanxinConfig huanxinConfig = new HuanxinConfig();
		huanxinConfig.setHuanxinAppKey(HUANXIN_APP_KEY);
		huanxinConfig.setHuanxinClientId(HUANXIN_CLIENT_ID);
		huanxinConfig.setHuanxinClientSecret(HUANXIN_CLIENT_SECRET);
		huanxinConfig.setHuanxinDomain(HUANXIN_DOMAIN);
		huanxinConfig.setHuanxinTokenUrl(HUANXIN_TOKEN_URL);
		huanxinConfig.setImMinsuFlag(IM_MINSU_FLAG);
		huanxinConfig.setDomainFlag(IM_DOMAIN_FLAG);
		huanxinConfig.setHuanxinSendImMsgUrl(HUANXIN_SEND_IM_MSG_URL);
		return huanxinConfig;
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
	@Override
	public String queryByPage(String msgFirstDdvisoryRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgFirstDdvisoryRequest msgFirstDdvisoryR = JsonEntityTransform.json2Object(msgFirstDdvisoryRequest, MsgFirstDdvisoryRequest.class);
		PagingResult<SysMsgVo> PagingResult = msgFirstAdvisoryServiceImpl.queryByPage(msgFirstDdvisoryR);
		dto.putValue("pagingResult", PagingResult);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 根据用户uid  禁用IM聊天
	 *  命令行 例如：curl -X POST -i -H "Authorization: Bearer YWMtqRUrqlBBEeeWjsFfPbdUoQAAAAAAAAAAAAAAAAAAAAHl73NgrQ4R5ImtyyIs_e6VAgMAAAFcocgHEABPGgCb6khrvN6mPqjYDt_UVVJETUNgzAgN8NBr8pYzv_-Weg" "https://a1.easemob.com/ziroom/ziroom/users/app_86d27d81-1775-4a1f-b890-78a97c7588f3/deactivate"
	 * @author yd
	 * @created 2017年6月16日 上午10:46:55
	 *
	 * @param uid
	 */
	@Override
	public void deactivateImUser(String uid){

		if(!Check.NuNStrStrict(uid)){
			String token = null;
			String shellCommand = "curl -X POST -i -H \"Authorization: Bearer ${token}\" \"https://a1.easemob.com/ziroom/ziroom/app_${uid}/deactivate\"";
			try {
				HuanxinConfig huanxinConfig = getHuanxinConfig();
				token = HuanxinUtils.getHuanxinToken(redisOperations, huanxinConfig);
				shellCommand = shellCommand.replace("${token}", token).replace("${uid}", uid);
				
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Content-Type", "application/json");
				headerMap.put("Authorization", "Bearer "+token);
				String postUrl = DEACTIVATE_IM_USER_URL.replace("${uid}", uid);
				LogUtil.info(logger, "【向环信发消息】url={},headerMap={}", postUrl,headerMap);
				
				String result = CloseableHttpsUtil.sendPost(postUrl, "", headerMap);
				LogUtil.info(logger,"【环信禁用聊天】uid:{},token:{},命令行shellCommand：{},result:{}", uid,token,shellCommand,result);
				
			} catch (Exception e) {
				LogUtil.error(logger,"【环信禁用聊天】uid:{},token:{},命令行shellCommand：{}", uid,token,shellCommand);
			}

		}
	}
	
	/**
	 * 
	 * 根据用户uid  禁用IM聊天
	 *  命令行 例如：curl -X POST -i -H "Authorization: Bearer YWMtqRUrqlBBEeeWjsFfPbdUoQAAAAAAAAAAAAAAAAAAAAHl73NgrQ4R5ImtyyIs_e6VAgMAAAFcocgHEABPGgCb6khrvN6mPqjYDt_UVVJETUNgzAgN8NBr8pYzv_-Weg" "https://a1.easemob.com/ziroom/ziroom/users/app_86d27d81-1775-4a1f-b890-78a97c7588f3/activate"
	 * @author yd
	 * @created 2017年6月16日 上午10:46:55
	 *
	 * @param uid
	 */
	@Override
	public void activateImUser(String uid){

		if(!Check.NuNStrStrict(uid)){
			String token = null;
			String shellCommand = "curl -X POST -i -H \"Authorization: Bearer ${token}\" \"https://a1.easemob.com/ziroom/ziroom/app_${uid}/activate\"";
			try {
				HuanxinConfig huanxinConfig = getHuanxinConfig();
				token = HuanxinUtils.getHuanxinToken(redisOperations, huanxinConfig);
				shellCommand = shellCommand.replace("${token}", token).replace("${uid}", uid);
				
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Content-Type", "application/json");
				headerMap.put("Authorization", "Bearer "+token);
				String postUrl = ACTIVATE_IM_USER_URL.replace("${uid}", uid);
				LogUtil.info(logger, "【向环信发消息】url={},headerMap={}", postUrl,headerMap);
				
				String result = CloseableHttpsUtil.sendPost(postUrl, "", headerMap);
				LogUtil.info(logger,"【环信禁用聊天解除】uid:{},token:{},命令行shellCommand：{},result:{}", uid,token,shellCommand,result);
				
			} catch (Exception e) {
				LogUtil.error(logger,"【环信禁用聊天解除】uid:{},token:{},命令行shellCommand：{}", uid,token,shellCommand);
			}

		}
	}
	
	/**
	 * 
	 * 保存记录
	 *
	 * @author yd
	 * @created 2016年9月10日 下午2:23:55
	 *
	 * @param huanxinIm
	 * @return
	 */
	public String saveHuanxinImRecord(String jsonParam){
		
		DataTransferObject dto = new DataTransferObject();
		HuanxinImRecordDto huanxinImRecordDto  = JsonEntityTransform.json2Object(jsonParam, HuanxinImRecordDto.class);
		HuanxinImRecordEntity imRecord = huanxinImRecordDto.getHuanxinImRecord();
		if(Check.NuNObj(imRecord)||Check.NuNStr(imRecord.getFromUid())
				||Check.NuNStr(imRecord.getToUid())||Check.NuNStr(imRecord.getMsgId())
				||Check.NuNStr(imRecord.getChatType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("保存参数错误");
			LogUtil.error(logger, "【消息保存失败】imRecord={}", JsonEntityTransform.Object2Json(imRecord));
			return dto.toJsonString();
		}
		this.huanxinImRecordServiceImpl.saveHuanxinImRecordAndOffline(imRecord);
		return dto.toJsonString();
	}


	/**
	 * 获取两个聊天用户发送消息的条数
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午3:04:09
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getCountMsgEach(String jsonParam) {
		DataTransferObject dto = new DataTransferObject();
		HuanxinImRecordEntity huanxinImRecord = JsonEntityTransform.json2Entity(jsonParam, HuanxinImRecordEntity.class);
		if(Check.NuNObj(huanxinImRecord) || Check.NuNStr(huanxinImRecord.getFromUid()) || Check.NuNStr(huanxinImRecord.getToUid())
				|| Check.NuNStr(huanxinImRecord.getZiroomFlag())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromUid", huanxinImRecord.getFromUid());
		params.put("toUid", huanxinImRecord.getToUid());
		params.put("ziroomFlag", huanxinImRecord.getZiroomFlag());
		long countEach = huanxinImRecordServiceImpl.getCountMsgEach(params);
		dto.putValue("countEach", countEach);
		return dto.toJsonString();
	}
	
	/**
	 *
	 * 发送IM消息到环信 异步
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午5:50:04
	 *
	 * @return
	 */
	@Override
	public String sendHuanxinForChangzu(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(logger, "发送IM消息到环信 异步(长租)");
		try {
			HuanxinConfig huanxinConfig = getHuanxinForChangzuConfig();
			List<SendImMsgRequest> list = JsonEntityTransform.json2List(paramJson, SendImMsgRequest.class);
			if (!Check.NuNCollection(list)) {
				SendThreadPool.execute(new SendHuanxinForChangzuThread(huanxinConfig, redisOperations, list, msgFirstAdvisoryServiceImpl));
			}
		} catch (Exception e) {
			LogUtil.error(logger, " 发送IM消息到环信 异常e={}", e);
		}
		LogUtil.info(logger, "发送IM消息到环信 结束");
		return dto.toJsonString();
	}

	private HuanxinConfig getHuanxinForChangzuConfig() {
		HuanxinConfig huanxinConfig = new HuanxinConfig();
		huanxinConfig.setHuanxinAppKey(HUANXIN_APP_KEY);
		huanxinConfig.setHuanxinClientId(HUANXIN_CLIENT_ID);
		huanxinConfig.setHuanxinClientSecret(HUANXIN_CLIENT_SECRET);
		huanxinConfig.setHuanxinDomain(HUANXIN_DOMAIN);
		huanxinConfig.setHuanxinTokenUrl(HUANXIN_TOKEN_URL);
		huanxinConfig.setImMinsuFlag(IM_CHANGZU_FLAG);
		huanxinConfig.setDomainFlag(IM_DOMAIN_FLAG);
		huanxinConfig.setHuanxinSendImMsgUrl(HUANXIN_SEND_IM_MSG_URL);
		return huanxinConfig;
	}


	/**
	 * 保存环信离线消息
	 *
	 * @author loushuai
	 * @created 2017年9月19日 上午11:37:59
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String saveHuanxinOffline(String object2Json) {
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(logger, "保存环信离线消息  object2Json={}", object2Json);
		HuanxinImOfflineEntity json2Entity = JsonEntityTransform.json2Entity(object2Json, HuanxinImOfflineEntity.class);
		if(Check.NuNObj(json2Entity)|| Check.NuNStr(json2Entity.getMsgId()) || Check.NuNStr(json2Entity.getFromUid()) || Check.NuNStr(json2Entity.getToUid())
				|| Check.NuNStr(json2Entity.getZiroomFlag())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		try {
			 huanxinImRecordServiceImpl.saveHuanxinOffline(json2Entity);
		} catch (Exception e) {
			LogUtil.error(logger, " 发送IM消息到环信 异常e={}", e);
		}
		LogUtil.info(logger, "保存环信离线消息");
		return dto.toJsonString();
	}
}
