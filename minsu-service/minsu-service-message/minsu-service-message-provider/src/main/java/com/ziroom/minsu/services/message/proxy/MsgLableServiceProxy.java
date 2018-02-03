/**
 * @FileName: MsgLableServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午6:09:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgLableEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgLableService;
import com.ziroom.minsu.services.message.dto.MsgLableRequest;
import com.ziroom.minsu.services.message.entity.MsgKeyVo;
import com.ziroom.minsu.services.message.service.MsgLableServiceImpl;

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
@Component("message.msgLableServiceProxy")
public class MsgLableServiceProxy implements MsgLableService{

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgLableServiceProxy.class);

	@Resource(name = "message.msgLableServiceImpl")
	private MsgLableServiceImpl msgLableServiceImpl;

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;

	/**
	 * 
	 * 条件分页查询 （小心使用无校验）
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:07:20
	 *
	 * @param msgLableRequest
	 * @return
	 */
	@Override
	public String queryByPage(String msgLableRequest) {

		DataTransferObject dto = new DataTransferObject();
		MsgLableRequest msRequest = JsonEntityTransform.json2Entity(msgLableRequest, MsgLableRequest.class);

		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		
		PagingResult<MsgLableEntity> listPagingResult = this.msgLableServiceImpl.queryByPage(msRequest);
		dto.putValue("listMsgLable", listPagingResult.getRows());
		dto.putValue("total", listPagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:08:35
	 *
	 * @param msLableEntity
	 * @return
	 */
	@Override
	public String save(String msgLableEntity) {
		DataTransferObject dto = new DataTransferObject();
		MsgLableEntity msLable = JsonEntityTransform.json2Entity(msgLableEntity, MsgLableEntity.class);
		
		if(Check.NuNObj(msLable)||Check.NuNStr(msLable.getMsgKey())||Check.NuNStr(msLable.getHouseFid())||Check.NuNStr(msLable.getLandlordFid())){
			LogUtil.info(logger,"MsgLableEntity is null");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体为null");
			return dto.toJsonString();
		}
		
		MsgLableRequest msgLableRequest = new MsgLableRequest();
		msgLableRequest.setHouseFid(msLable.getHouseFid());
		msgLableRequest.setLandlordFid(msLable.getLandlordFid());
		msgLableRequest.setMsgKey(msLable.getMsgKey());
		List<MsgKeyVo> lisKeyVos = this.msgLableServiceImpl.queryMsgKeyByCondition(msgLableRequest);
		
		if(!Check.NuNCollection(lisKeyVos)){
			LogUtil.info(logger,"此键msgKey={}已经存在",lisKeyVos);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(msLable.getMsgKey()+"已经存在");
			return dto.toJsonString();
		}
		dto.putValue("result", this.msgLableServiceImpl.save(msLable));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 根据fid修改实体
	 *
	 * @author yd
	 * @created 2016年4月18日 下午6:09:03
	 *
	 * @param msLableEntity
	 * @return
	 */
	@Override
	public String updateByFid(String msgLableEntity) {
		DataTransferObject dto = new DataTransferObject();
		MsgLableEntity msLable = JsonEntityTransform.json2Entity(msgLableEntity, MsgLableEntity.class);
		
		if(Check.NuNObj(msLable)||Check.NuNStr(msLable.getFid())){
			LogUtil.info(logger,"MsgLableEntity is null");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体为null或者fid为null");
			return dto.toJsonString();
		}
		dto.putValue("result", this.msgLableServiceImpl.updateByFid(msLable));
		return dto.toJsonString();
	}

	/**
	 * 
	 * 用户输入关键词 获取要回答的内容
	 *
	 * @author yd
	 * @created 2016年4月18日 下午8:44:25
	 *
	 * @param msgLableRequest
	 * @return
	 */
	@Override
	public String queryMsgContentByKey(String msgLableRequest) {
		DataTransferObject dto = new DataTransferObject();
		MsgLableRequest msRequest = JsonEntityTransform.json2Entity(msgLableRequest, MsgLableRequest.class);

		LogUtil.info(logger,"查询条件msRequest={}",msRequest);
		
		if(Check.NuNObj(msRequest)||Check.NuNStr(msRequest.getLandlordFid())||Check.NuNStr(msRequest.getMsgKey())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求参数错误,房东fid 或者 用户输入关键词");
			LogUtil.info(logger, "请求参数错误");
			return dto.toJsonString();
		}
		
		List<MsgKeyVo> listKeyVos = this.msgLableServiceImpl.queryMsgKeyByCondition(msRequest);
		
		List<String> listContent = new ArrayList<String>();
		if(!Check.NuNCollection(listKeyVos)){
			for (MsgKeyVo msgKeyVo : listKeyVos) {
				String key = msgKeyVo.getMsgKey();
				if(msRequest.getMsgKey().contains(key)){
					listContent.add(msgKeyVo.getMsgContent());
				}
			}
		}
		dto.putValue("listContent", listContent);
		return dto.toJsonString();
	}

}
