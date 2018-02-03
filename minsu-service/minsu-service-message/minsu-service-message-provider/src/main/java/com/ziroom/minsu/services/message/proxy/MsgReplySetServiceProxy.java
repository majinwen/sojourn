/**
 * @FileName: MsgReplySetServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午8:20:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

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
import com.ziroom.minsu.entity.message.MsgReplySetEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgReplySetService;
import com.ziroom.minsu.services.message.dto.MsgReplySetRequest;
import com.ziroom.minsu.services.message.service.MsgReplySetServiceImpl;

/**
 * <p>测试</p>
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
@Component("message.msgReplySetServiceProxy")
public class MsgReplySetServiceProxy implements MsgReplySetService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgReplySetServiceProxy.class);

	@Resource(name = "message.msgReplySetServiceImpl")
	private MsgReplySetServiceImpl msgReplySetServiceImpl;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;


	/**
	 * 查询当前房东设置实体
	 *
	 * @author yd
	 * @created 2016年4月18日 上午9:57:38
	 *
	 * @param msgReplySetRequest
	 * @return
	 */
	@Override
	public String queryByLanglordFid(String msgReplySetRequest) {

		DataTransferObject dto = new DataTransferObject();

		MsgReplySetRequest msSetRequest = JsonEntityTransform.json2Entity(msgReplySetRequest, MsgReplySetRequest.class);

		if(Check.NuNObj(msSetRequest)||Check.NuNStr(msSetRequest.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数出错");
			LogUtil.info(logger, "请求参数msgReplySetRequest={}", msgReplySetRequest);
			return dto.toJsonString();
		}

		MsgReplySetEntity msgReplySetEntity = this.msgReplySetServiceImpl.queryByLanglordFid(msSetRequest);

		dto.putValue("msgReplySetEntity", msgReplySetEntity);
		return dto.toJsonString();
	}

	/**
	 * 保存实体
	 * @author yd
	 * @created 2016年4月18日 下午8:19:56
	 *
	 * @param msgReplySet
	 * @return
	 */
	@Override
	public String save(String msgReplySetEntity) {

		DataTransferObject dto = new DataTransferObject();

		MsgReplySetEntity msgReplySet = JsonEntityTransform.json2Entity(msgReplySetEntity, MsgReplySetEntity.class);

		if(Check.NuNObj(msgReplySet)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在");
			LogUtil.info(logger, "请求实体msgReplySetEntity={}", msgReplySet);
			return dto.toJsonString();
		}
		
		MsgReplySetRequest msgReplySetRequest = new MsgReplySetRequest();
		
		msgReplySetRequest.setLandlordUid(msgReplySet.getLandlordUid());
		msgReplySetRequest.setHouseFid(msgReplySet.getHouseFid());;
		
		MsgReplySetEntity msEntity = this.msgReplySetServiceImpl.queryByLanglordFid(msgReplySetRequest);
		
	    if(Check.NuNObj(msEntity)){
	    	dto.putValue("result", this.msgReplySetServiceImpl.save(msgReplySet));
	    }else{
	    	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体已存在");
			LogUtil.info(logger, "当前实体MsgReplySetEntity={}", msEntity);
	    }
		return dto.toJsonString();
	}

	/**
	 * 
	 * 条件更新
	 *
	 * @author yd
	 * @created 2016年4月18日 下午8:20:28
	 *
	 * @param msgReplySetEntity
	 * @return
	 */
	@Override
	public String updateByCondition(String msgReplySetEntity) {
		DataTransferObject dto = new DataTransferObject();

		MsgReplySetEntity msgReplySet = JsonEntityTransform.json2Entity(msgReplySetEntity, MsgReplySetEntity.class);

		if(Check.NuNObj(msgReplySet)||Check.NuNStr(msgReplySet.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在或者房东fid不存在");
			LogUtil.info(logger, "请求实体msgReplySetEntity={}", msgReplySet);
			return dto.toJsonString();
		}
		LogUtil.info(logger, "待更新实体MsgReplySetEntity={}", msgReplySet.toString());
		dto.putValue("result", this.msgReplySetServiceImpl.updateByCondition(msgReplySet));
		return dto.toJsonString();
	}

}
