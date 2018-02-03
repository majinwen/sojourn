/**
 * @FileName: EvaluateOrderServiceProxy.java
 * @Package com.ziroom.minsu.services.evaluate.proxy
 * 
 * @author yd
 * @created 2016年4月7日 下午9:03:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

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
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.SysMsgService;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;
import com.ziroom.minsu.services.message.service.SysMsgServiceImpl;

/**
 * <p>评价管理接口实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Component("message.sysMsgProxy")
public class SysMsgProxy implements SysMsgService{

	/**
	 * 日志对象
	 */
	private static Logger logger =LoggerFactory.getLogger(SysMsgProxy.class);

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;

	@Resource(name = "message.sysMsgServiceImpl")
	private SysMsgServiceImpl sysMsgServiceImpl;
	
	/*@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;*/
	
	/**
	 * 增加系统消息
	 * @author jixd on 2016年4月18日
	 */
	@Override
	public String saveSysMsg(String sysMsgJson) {
		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(sysMsgJson)){
			dto.setErrCode(1);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"sysMsgJson is null"));
			return dto.toJsonString();
		}
		SysMsgEntity sysMsgEntity = JsonEntityTransform.json2Object(sysMsgJson, SysMsgEntity.class);
        LogUtil.info(logger,"evaluateRequest to toStirng {}",sysMsgEntity.toString());
        try{
        	dto.putValue("result", sysMsgServiceImpl.saveSysMsg(sysMsgEntity));
        }catch(Exception e){
        	LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg(MessageConst.UNKNOWN_ERROR);
			return dto.toJsonString();
        }
		return dto.toJsonString();
	}
	
	/**
	 * 查询系统消息
	 * @author jixd on 2016年4月18日
	 */
	@Override
	public String querySysMsg(String sysMsgRequest) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(sysMsgRequest)){
			dto.setErrCode(1);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"sysMsgRequest is null"));
			return dto.toJsonString();
		}
		SysMsgRequest rquest = JsonEntityTransform.json2Object(sysMsgRequest, SysMsgRequest.class);
		try{
			PagingResult<SysMsgEntity> pageResult = sysMsgServiceImpl.querySysMsg(rquest);
			dto.putValue("sysMsgList", pageResult.getRows());
			dto.putValue("total", pageResult.getTotal());
		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg(MessageConst.UNKNOWN_ERROR);
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	/**
	 * 系统消息
	 * @author jixd on 2016年4月18日
	 */
	@Override
	public String  saveSysMsgBatch(String sysMsgList) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(sysMsgList)){
			dto.setErrCode(1);
			dto.setMsg("sysMsgList is null");
			return dto.toJsonString();
		}
		List<SysMsgEntity> list = JsonEntityTransform.json2List(sysMsgList, SysMsgEntity.class);
		if(Check.NuNCollection(list)){
			dto.setErrCode(1);
			dto.setMsg("list is empty");
			return dto.toJsonString();
		}
		try{
			dto.putValue("result", sysMsgServiceImpl.saveSysMsgList(list));
		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg(MessageConst.UNKNOWN_ERROR);
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	/**
	 * 删除系统消息
	 * @author jixd on 2016年4月18日
	 * 
	 */
	@Override
	public String deleteSysMsg(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(1);
			dto.setMsg("fid is null");
			return dto.toJsonString();
		}
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setFid(fid);
		dto.putValue("result", sysMsgServiceImpl.deleteSysMsg(sysMsgEntity));
		return dto.toJsonString();
	}

	/**
	 * 需要传map,包含smsCode,msgTargetUid
	 */
	/*public String sendSysMsg(String paramMap) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(paramMap)){
			dto.setErrCode(1);
			dto.setMsg("paramMap is null");
			return dto.toJsonString();
		}
		Map<String, String> map = (Map<String, String>) JsonEntityTransform.json2Map(paramMap);
		String smsCode = (String) map.get("smsCode");
		if(Check.NuNStr(smsCode)){
			dto.setErrCode(1);
			dto.setMsg("smsCode is null");
			return dto.toJsonString();
		}
		String msgJson = smsTemplateService.findEntityBySmsCode(smsCode);
		DataTransferObject smsDto = JsonEntityTransform.json2DataTransferObject(msgJson);
		if(smsDto.getCode() != 0){
			dto.setErrCode(1);
			dto.setMsg("没有模板");
			return dto.toJsonString();
		}
		SmsTemplateEntity smsEntity = smsDto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>(){});
		String smsContent = smsEntity.getSmsComment();
		String smsName = smsEntity.getSmsName();
		for (Map.Entry<String, String> entry:map.entrySet()) {
			if("smsCode".equals(entry.getKey())){
				continue;
			}
			smsContent = smsContent.replace("{"+entry.getKey()+"}", entry.getValue());
		}
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setCreateTime(new Date());
		sysMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		sysMsgEntity.setIsRead(IsReadEnum.UNREAD.getCode());
		//单个用户
		sysMsgEntity.setMsgTargetType(TargetTypeEnum.SINGLE_USER.getCode());
		sysMsgEntity.setMsgTargetUid(map.get("msgTargetUid"));
		sysMsgEntity.setMsgTmpType(Integer.parseInt(smsCode));
		sysMsgEntity.setMsgTitle(smsName);
		sysMsgEntity.setMsgContent(smsContent);
		int count = sysMsgServiceImpl.saveSysMsg(sysMsgEntity);
		dto.putValue("result",count);
		return dto.toJsonString();
	}
*/
	/**
	 * 更新已读
	 */
	@Override
	public String updateSysMsgRead(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(1);
			dto.setMsg("fid is null");
			return dto.toJsonString();
		}
		SysMsgEntity sysMsgEntity = new SysMsgEntity();
		sysMsgEntity.setFid(fid);
		dto.putValue("result", sysMsgServiceImpl.updateSysMsgRead(sysMsgEntity));
		return dto.toJsonString();
	}

}
