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


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgCustomizationEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgCustomizationService;
import com.ziroom.minsu.services.message.entity.MsgCustomizationVo;
import com.ziroom.minsu.services.message.service.MsgCustomizationimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>自定义回复信息代理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Component("message.msgCustomizationProxy")
public class MsgCustomizationProxy implements MsgCustomizationService {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgCustomizationProxy.class);

	@Resource(name = "message.msgCustomizationimpl")
	private MsgCustomizationimpl msgCustomizationimpl;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;


	@Override
	public String queryMsgCustomizationByUid(String uid) {
		LogUtil.info(logger, "请求参数:{}", uid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		List<MsgCustomizationVo> msgCustomizationList = msgCustomizationimpl.queryMsgCustomizationByUid(uid);
		dto.putValue("list",msgCustomizationList);
		return dto.toJsonString();
	}

	@Override
	public String addMsgCustomization(String paramJson) {
		LogUtil.info(logger, "请求参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		MsgCustomizationEntity msgCustom = JsonEntityTransform.json2Object(paramJson, MsgCustomizationEntity.class);
		if(Check.NuNObj(msgCustom)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNStr(msgCustom.getContent())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("要添加的回复消息内容不可以为空");
			return dto.toJsonString();
		}
		int result = msgCustomizationimpl.addMsgCustomization(msgCustom);
		dto.putValue("result",result);
		return dto.toJsonString();
	}

	@Override
	public String updateMsgCustomization(String paramJson) {
		LogUtil.info(logger, "请求参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		MsgCustomizationEntity msgCustom = JsonEntityTransform.json2Object(paramJson, MsgCustomizationEntity.class);
		if(Check.NuNObj(msgCustom)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if( Check.NuNStr(msgCustom.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		int result = msgCustomizationimpl.updateMsgCustomization(msgCustom);
		dto.putValue("result",result);
		return dto.toJsonString();
	}
}
