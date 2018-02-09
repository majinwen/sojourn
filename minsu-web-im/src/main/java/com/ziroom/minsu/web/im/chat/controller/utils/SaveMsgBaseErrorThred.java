/**
 * @FileName: SaveMsgBaseErrorThred.java
 * @Package com.ziroom.minsu.web.im.chat.controller.utils
 * 
 * @author yd
 * @created 2016年11月11日 下午2:19:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller.utils;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseErrorEntity;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;

/**
 * <p>异步保存环信 错误消息</p>
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
public class SaveMsgBaseErrorThred implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(SaveMsgBaseErrorThred.class);

	
	/**
	 * 环信 im 消息参数
	 */
	private AppChatRecordsDto appChatRecordsDto;
	
	/**
	 * 消息 服务
	 */
	private MsgBaseService msgBaseService;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	public	SaveMsgBaseErrorThred(AppChatRecordsDto appChatRecordsDto,MsgBaseService msgBaseService){
		
		this.appChatRecordsDto = appChatRecordsDto;
		this.msgBaseService = msgBaseService;
	}
	@Override
	public void run() {
		
		
		AppChatRecordsDto appChatRecordsDto = this.getAppChatRecordsDto();
		
		if(!Check.NuNObj(appChatRecordsDto)
				&&!Check.NuNStrStrict(appChatRecordsDto.getLandlordUid())
				&&!Check.NuNStrStrict(appChatRecordsDto.getTenantUid())){
			
			AppChatRecordsExt appChatRecordsExt = appChatRecordsDto.getAppChatRecordsExt();
			MsgBaseErrorEntity msgBaseError = new MsgBaseErrorEntity();
			
			msgBaseError.setFid(UUIDGenerator.hexUUID());
			msgBaseError.setLandlordUid(appChatRecordsDto.getLandlordUid());
			msgBaseError.setMsgContent(appChatRecordsDto.getMsgContent());
			msgBaseError.setMsgSentType(appChatRecordsDto.getMsgSentType());
			msgBaseError.setTenantUid(appChatRecordsDto.getTenantUid());
			
			if(!Check.NuNObj(appChatRecordsExt)){
				msgBaseError.setPersonNum(Check.NuNStrStrict(appChatRecordsExt.getPersonNum())?null:Integer.valueOf(appChatRecordsExt.getPersonNum()));
				msgBaseError.setMsgSource(appChatRecordsExt.getSource());
				msgBaseError.setHouseFid(appChatRecordsExt.getFid());
				msgBaseError.setHouseName(appChatRecordsExt.getHouseName());
				msgBaseError.setHousePicUrl(appChatRecordsExt.getHousePicUrl());
				msgBaseError.setRentWay(appChatRecordsExt.getRentWay());
				try {
					msgBaseError.setEndDate(Check.NuNStrStrict(appChatRecordsExt.getEndDate())?null:DateUtil.parseDate(appChatRecordsExt.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
					msgBaseError.setStartDate(Check.NuNStrStrict(appChatRecordsExt.getStartDate())?null:DateUtil.parseDate(appChatRecordsExt.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					LogUtil.info(logger, "时间格式解析异常e={}", e);
				}
			}
			
			//填充图片相关字段值
			msgBaseError.setType(appChatRecordsDto.getType());
			msgBaseError.setUrl(appChatRecordsDto.getUrl());
			msgBaseError.setFilename(appChatRecordsDto.getFilename());
			msgBaseError.setFileLength(appChatRecordsDto.getFileLength());
			msgBaseError.setSecret(appChatRecordsDto.getSecret());
			msgBaseError.setSize(appChatRecordsDto.getSize());
	           
			if(!Check.NuNObj(getMsgBaseService()))
			getMsgBaseService().saveMsgBaseError(JsonEntityTransform.Object2Json(msgBaseError));
		}
		
	}
	/**
	 * @return the appChatRecordsDto
	 */
	public AppChatRecordsDto getAppChatRecordsDto() {
		return appChatRecordsDto;
	}
	/**
	 * @param appChatRecordsDto the appChatRecordsDto to set
	 */
	public void setAppChatRecordsDto(AppChatRecordsDto appChatRecordsDto) {
		this.appChatRecordsDto = appChatRecordsDto;
	}
	/**
	 * @return the msgBaseService
	 */
	public MsgBaseService getMsgBaseService() {
		return msgBaseService;
	}
	/**
	 * @param msgBaseService the msgBaseService to set
	 */
	public void setMsgBaseService(MsgBaseService msgBaseService) {
		this.msgBaseService = msgBaseService;
	}
	
	

}
