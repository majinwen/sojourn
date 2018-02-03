/**
 * @FileName: HouseFollowServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2017年2月24日 上午10:51:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.HouseFollowService;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.dto.HouseFollowLogDto;
import com.ziroom.minsu.services.house.dto.HouseFollowSaveDto;
import com.ziroom.minsu.services.house.dto.HouseFollowUpdateDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;
import com.ziroom.minsu.services.house.service.HouseFollowServiceImpl;
import com.ziroom.minsu.services.house.service.PhotographerBookOrderServiceImpl;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;

/**
 * <p>房源跟进业务代理层</p>
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
@Component("house.houseFollowServiceProxy")
public class HouseFollowServiceProxy implements HouseFollowService{
	/**
	 * 日志工具
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseFollowServiceProxy.class);

	@Resource(name="house.messageSource")
	private MessageSource messageSource;

	@Resource(name="house.houseFollowServiceImpl")
	private HouseFollowServiceImpl houseFollowServiceImpl;

	@Resource(name="photographer.photographerBookOrderServiceImpl")
	private PhotographerBookOrderServiceImpl photographerBookOrderServiceImpl;

	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Value("#{'${MAPP_URL}'.trim()}")
	private String MAPP_URL;

	@Value("#{'${OPEN_MINSU_APP_MYHOUSE}'.trim()}")
	private String OPEN_MINSU_APP_MYHOUSE;

	@SuppressWarnings("unchecked")
	@Override
	public String findServicerFollowHouseList(String paramJson) {
		LogUtil.info(LOGGER, "findServicerFollowHouseList参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseFollowDto houseFollowDto=JsonEntityTransform.json2Object(paramJson, HouseFollowDto.class);
			Map<String, Object> resultMap=houseFollowServiceImpl.findServicerFollowHouseList(houseFollowDto);
			List<HouseFollowVo> dataList=(List<HouseFollowVo>) resultMap.get("dataList");
			dto.putValue("dataList", dataList);
			dto.putValue("count", resultMap.get("count"));
		} catch(Exception e) {
			LogUtil.error(LOGGER, "findServicerFollowHouseList error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#lockAndSaveHouseFollow(java.lang.String)
	 */
	@Override
	public String lockAndSaveHouseFollow(String paramJson) {
		LogUtil.info(LOGGER, "lockAndSaveHouseFollow参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseFollowSaveDto followSaveDto=null;
		HouseFollowEntity houseFollowEntity=null;
		try {
			followSaveDto=JsonEntityTransform.json2Object(paramJson, HouseFollowSaveDto.class);
			if(Check.NuNObj(followSaveDto.getRentWay())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("rentWay不能为空！");
				return dto.toJsonString();
			}
			houseFollowEntity=houseFollowServiceImpl.lockAndSaveHouseFollow(followSaveDto,dto);
		} catch(Exception e) {
			LogUtil.error(LOGGER, "lockAndSaveHouseFollow error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		//未插入跟进表记录发短信和通知
		if (Check.NuNStr(followSaveDto.getFollowFid())||followSaveDto.getFollowFid().equals("undefined")) {
			try {
				//推送消息
				Map<String, String> jpushMap=new HashMap<>();
				JpushRequest jpushRequest = new JpushRequest();
				jpushRequest.setParamsMap(jpushMap);
				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_MSG.getCode()));
				jpushRequest.setTitle("房源审核通知");
				jpushRequest.setUid(followSaveDto.getLandlordUid());
				//自定义消息
				Map<String, String> extrasMap = new HashMap<>();
				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				/*String url = MAPP_URL+String.format(JpushConst.HOUSE_ROOM_DETAIL_URL,houseFollowEntity.getHouseBaseFid(),houseFollowEntity.getRoomFid(),houseFollowEntity.getRentWay());
				extrasMap.put("url",url);*/
				jpushRequest.setExtrasMap(extrasMap);
				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
			} catch (Exception e) {
				LogUtil.error(LOGGER, "lockAndSaveHouseFollow 消息推送异常:{}", e);
			}
			try{
				//发短信
				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_OVERTIME_LANDLORD_SMS.getCode());
				SmsRequest smsRequest  = new SmsRequest();
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("{1}", OPEN_MINSU_APP_MYHOUSE); 
				smsRequest.setParamsMap(paramsMap);
				smsRequest.setMobile(followSaveDto.getCustomerMobile());
				smsRequest.setSmsCode(msgCode);
				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			} catch (Exception e){
				LogUtil.error(LOGGER, "lockAndSaveHouseFollow 发短信异常:{}", e);
			}
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#houseFollowDetail(java.lang.String)
	 */
	@Override
	public String houseFollowDetail(String paramJson) {
		LogUtil.info(LOGGER, "houseFollowDetail参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseFollowSaveDto followSaveDto=JsonEntityTransform.json2Object(paramJson, HouseFollowSaveDto.class);
			HouseFollowVo houseFollowVo=houseFollowServiceImpl.houseFollowDetail(followSaveDto);
			PhotographerBookOrderEntity photoOrder=photographerBookOrderServiceImpl.findBookOrderByHouseFid(houseFollowVo.getHouseBaseFid());
			if(Check.NuNObj(photoOrder)){
				houseFollowVo.setPhotoOrderStatus("无");
			} else {
				houseFollowVo.setPhotoOrderStatus(BookOrderStatuEnum.getEnummap().get(photoOrder.getBookOrderStatu()));
			}
			dto.putValue("followDetail", houseFollowVo);
		} catch(Exception e) {
			LogUtil.error(LOGGER, "houseFollowDetail error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#insertFollowLog(java.lang.String)
	 */
	@Override
	public String insertFollowLog(String paramJson) {
		LogUtil.info(LOGGER, "insertFollowLog参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		HouseFollowLogDto houseFollowLogDto=null;
		try {
			houseFollowLogDto=JsonEntityTransform.json2Entity(paramJson, HouseFollowLogDto.class);
			houseFollowServiceImpl.insertHouseFollowLog(houseFollowLogDto);
		} catch(Exception e) {
			LogUtil.error(LOGGER, "insertFollowLog error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		//判断是否发通知(客服选择未联系上房东)
		if(!Check.NuNObj(houseFollowLogDto)&&houseFollowLogDto.getToStatus()==FollowStatusEnum.KFWLXSFD.getCode()){
			try {
				//推送消息
				Map<String, String> jpushMap=new HashMap<>();
				JpushRequest jpushRequest = new JpushRequest();
				jpushRequest.setParamsMap(jpushMap);
				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_NOT_CONTACT_LANDLORD_SMS.getCode()));
				jpushRequest.setTitle("房源审核通知");
				jpushRequest.setUid(houseFollowLogDto.getLandlordUid());
				//自定义消息
				Map<String, String> extrasMap = new HashMap<>();
				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				/*String url = MAPP_URL+String.format(JpushConst.HOUSE_ROOM_DETAIL_URL,houseFollowLogDto.getHouseBaseFid(),houseFollowLogDto.getRoomFid(),houseFollowLogDto.getRentWay());
				extrasMap.put("url",url);*/
				jpushRequest.setExtrasMap(extrasMap);
				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
			} catch (Exception e) {
				LogUtil.error(LOGGER, "insertFollowLog 推送消息 error:{}", e);
			}
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseFollowService#findAttacheFollowHouseList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findAttacheFollowHouseList(String paramJson) {
		LogUtil.info(LOGGER, "findAttacheFollowHouseList参数:{}", paramJson);
		DataTransferObject dto=new DataTransferObject();
		try {
			HouseFollowDto houseFollowDto=JsonEntityTransform.json2Object(paramJson, HouseFollowDto.class);
			Map<String, Object> resultMap=houseFollowServiceImpl.findAttacheFollowHouseList(houseFollowDto);
			List<HouseFollowVo> dataList=(List<HouseFollowVo>) resultMap.get("dataList");
			dto.putValue("dataList", dataList);
			dto.putValue("count", resultMap.get("count"));
		} catch(Exception e) {
			LogUtil.error(LOGGER, "findAttacheFollowHouseList error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 更新客服跟进状态
	 * 1. 更新客服跟进表
	 * 2. 记录跟进日志
	 *
	 * @author yd
	 * @created 2017年4月19日 上午11:55:38
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateHouseFollowByFid(String paramJson) {

		DataTransferObject dto=new DataTransferObject();
		HouseFollowUpdateDto houseFollowUpdateDto = JsonEntityTransform.json2Object(paramJson, HouseFollowUpdateDto.class);

		if(Check.NuNObjs(houseFollowUpdateDto,houseFollowUpdateDto.getFollowStatus())
				||Check.NuNStr(houseFollowUpdateDto.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			LogUtil.info(LOGGER, "updateHouseFollowByFid参数:{}", paramJson);
			return dto.toJsonString();
		}
		
		HouseFollowEntity houseFollow = this.houseFollowServiceImpl.getHouseFollowEntityByFid(houseFollowUpdateDto.getFid());
		
		if(Check.NuNObj(houseFollow)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前跟进记录不存在");
			LogUtil.info(LOGGER, "updateHouseFollowByFid参数:{}", paramJson);
			return dto.toJsonString();
		}
		
		FollowStatusEnum followStatu = FollowStatusEnum.getFollowStatusEnumByCode(houseFollow.getFollowStatus());
		if(Check.NuNObj(followStatu)
				||!followStatu.isServiceFollowEnd()){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前跟进记录状态错误");
			LogUtil.info(LOGGER, "updateHouseFollowByFid参数:{},followStatu={}", paramJson,houseFollow.getFollowStatus());
			return dto.toJsonString();
		}
		houseFollowUpdateDto.setFollowStatusOld(followStatu.getCode());
		this.houseFollowServiceImpl.updateHouseFollow(houseFollowUpdateDto);
		return dto.toJsonString();
	}
}
