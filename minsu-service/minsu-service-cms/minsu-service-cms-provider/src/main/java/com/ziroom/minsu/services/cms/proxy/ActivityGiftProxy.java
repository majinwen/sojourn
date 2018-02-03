/**
 * @FileName: ActivityGiftProxy.java
 * @Package com.ziroom.minsu.services.cms.proxy
 * 
 * @author yd
 * @created 2016年10月9日 下午3:40:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;
import com.ziroom.minsu.services.cms.service.ActivityGiftServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>TODO</p>
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
@Service("cms.activityGiftProxy")
public class ActivityGiftProxy implements ActivityGiftService {

	
	 /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityGiftProxy.class);


    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;
    
    @Resource(name = "cms.activityGiftServiceImpl")
    private ActivityGiftServiceImpl activityGiftServiceImpl;
	/**
	 * 
	 * 分页查询活动 礼物
	 *
	 * @author yd
	 * @created 2016年10月9日 下午3:39:39
	 *
	 * @param activityGift
	 * @return
	 */
	@Override
	public String queryActivityGifyByPage(String activityGift) {
		
		DataTransferObject dto = new DataTransferObject();
		
		ActivityGiftRequest activityGiftRe = JsonEntityTransform.json2Object(activityGift, ActivityGiftRequest.class); 
		
		if(Check.NuNObj(activityGiftRe)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数不存在");
		}
		LogUtil.info(LOGGER, "查询参数activityGiftRe={}", activityGiftRe.toJsonStr());
		PagingResult<ActivityGiftEntity> pagintRe = this.activityGiftServiceImpl.queryActivityGifyByPage(activityGiftRe);
		
		dto.putValue("count", pagintRe.getTotal());
		dto.putValue("listActivityGift", pagintRe.getRows());
		return dto.toJsonString();
	}


	/**
	 * 获取当前房东的免佣金的活动
	 * @author afi
	 * @param landUid
	 * @return
	 */
	@Override
	public String getLandFreeComm(String landUid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(landUid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		ActivityGiftEntity obj = activityGiftServiceImpl.getLanFreeCommAc(landUid);
		dto.putValue("obj", obj);
		return dto.toJsonString();
	}
	
	/**
	 * 新增活动礼品信息
	 */
	@Override
	public String insertActivityGiftEntity(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		ActivityGiftEntity activityGiftEntity = JsonEntityTransform.json2Object(paramJson, ActivityGiftEntity.class);
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNObj(activityGiftEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (!Check.NuNObj(activityGiftEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("逻辑fid非空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = activityGiftServiceImpl.insertActivityGiftEntity(activityGiftEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "insertActivityGiftEntity error:{},paramJson={}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}

	/**
	 * 根据逻辑id查询活动礼品信息
	 */
	@Override
	public String queryActivityGifyByFid(String actGiftFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(actGiftFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		
		try {
			ActivityGiftEntity activityGiftEntity = activityGiftServiceImpl.getGiftByFid(actGiftFid);
			dto.putValue("obj", activityGiftEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "queryActivityGifyByFid error:{},actGiftFid={}", e, actGiftFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		
		return dto.toJsonString();
	}

	/**
	 * 根据逻辑id修改活动礼品信息
	 */
	@Override
	public String updateActivityGiftEntity(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		ActivityGiftEntity activityGiftEntity = JsonEntityTransform.json2Object(paramJson, ActivityGiftEntity.class);
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNObj(activityGiftEntity)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (Check.NuNObj(activityGiftEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("逻辑fid为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = activityGiftServiceImpl.updateActivityGiftEntityByFid(activityGiftEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "insertActivityGiftEntity error:{},paramJson={}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		return dto.toJsonString();
	}


	@Override
	public String cancelFreeCommission(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		ActivityFreeEntity activityFreeEntity = JsonEntityTransform.json2Object(paramJson, ActivityFreeEntity.class);
		if(Check.NuNObj(activityFreeEntity) ||  Check.NuNStr(activityFreeEntity.getUid()) || Check.NuNObj(activityFreeEntity.getIsDel())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		//先查在修改
		ActivityFreeEntity activityFree = activityGiftServiceImpl.getFreeCommissionByUid(activityFreeEntity);
		if(Check.NuNObj(activityFree)){
			dto.setMsg("该房东没有免佣金权益");
			return  dto.toJsonString();
		}
		int a = activityGiftServiceImpl.cancelFreeCommission(activityFreeEntity);
		if(a==0){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("取消用户免佣金实体——修改数量为0");
			return dto.toJsonString();
		}
		dto.setMsg("该房东的免佣金权益已被逻辑删除");
		return dto.toJsonString();
	}

}
