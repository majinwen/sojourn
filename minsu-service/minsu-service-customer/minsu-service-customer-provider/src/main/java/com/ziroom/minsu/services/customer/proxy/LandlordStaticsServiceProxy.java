/**
 * @FileName: CustomerInfoServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年4月25日 下午12:06:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.LandlordStaticsService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.service.LandlordStaticsServiceImpl;

/**
 * <p>客户中心相关业务接口实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Component("customer.landlordStaticsServiceProxy")
public class LandlordStaticsServiceProxy implements LandlordStaticsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordStaticsServiceProxy.class);

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;

    @Resource(name = "customer.landlordStaticsServiceImpl")
    private LandlordStaticsServiceImpl landlordStaticsServiceImpl;



	@Override
	public String staticsUpdateLanActAssociationImp(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			LandlordStatisticsEntity landlordStatistics=JsonEntityTransform.json2Object(paramJson, LandlordStatisticsEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(landlordStatistics.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			landlordStaticsServiceImpl.staticsUpdateLanActAssociationImp(landlordStatistics);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	@Override
	public String staticsInsertLanActAssociationImp(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			LandlordStatisticsEntity landlordStatistics=JsonEntityTransform.json2Object(paramJson, LandlordStatisticsEntity.class);
			//判断是否存在uid
			if(Check.NuNStr(landlordStatistics.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			landlordStaticsServiceImpl.staticsInsertLanActAssociationImp(landlordStatistics);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	
	@Override
	public String findLandlordStatisticsByUid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//判断是否存在uid
			if(Check.NuNStr(paramJson)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
				return dto.toJsonString();
			}
			LandlordStatisticsEntity resultEntity = landlordStaticsServiceImpl.findLandlordStatisticsByUid(paramJson);
			dto.putValue("result", resultEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
}
