/**
 * @FileName: ConfTagServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfTagEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfTagService;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;
import com.ziroom.minsu.services.basedata.service.ConfTagServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

import java.util.List;

/**
 * <p>
 * 标签
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.confTagServiceProxy")
public class ConfTagServiceProxy implements ConfTagService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfTagServiceProxy.class);

	@Resource(name = "basedata.confTagServiceImpl")
	private ConfTagServiceImpl confTagServiceImpl;
 
	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;
 

	@Override
	public String findByConfTagRequest(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			ConfTagRequest params = JsonEntityTransform.json2Entity(paramJson, ConfTagRequest.class);
			
			PagingResult<ConfTagVo> pagingResult = confTagServiceImpl.findByConfTagRequest(params);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询标签列表异常， error={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}

	@Override
	public String findByConfTagRequestList(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ConfTagRequest params = JsonEntityTransform.json2Entity(paramJson, ConfTagRequest.class);
			List<ConfTagVo> list = confTagServiceImpl.findByConfTagRequestList(params);
			dto.putValue("list",list);
		}catch (Exception e){
			LogUtil.error(LOGGER, "查询标签列表异常， error={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}


	@Override
	public String addConfTag(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			ConfTagEntity entity = JsonEntityTransform.json2Entity(paramJson, ConfTagEntity.class);
			
			Integer num  = confTagServiceImpl.addConfTag(entity);
			dto.putValue("result", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "添加标签异常 error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}


	@Override
	public String modifyTagName(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			ConfTagEntity entity = JsonEntityTransform.json2Entity(paramJson, ConfTagEntity.class);
			
			Integer num  = confTagServiceImpl.modifyTagName(entity);
			dto.putValue("result", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "修改标签名称异常 error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}


	@Override
	public String modifyTagStatus(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			ConfTagEntity entity = JsonEntityTransform.json2Entity(paramJson, ConfTagEntity.class);
			
			Integer num  = confTagServiceImpl.modifyTagStatus(entity);
			dto.putValue("result", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "修改标签有效状态异常 error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}
	
	
	 
}
