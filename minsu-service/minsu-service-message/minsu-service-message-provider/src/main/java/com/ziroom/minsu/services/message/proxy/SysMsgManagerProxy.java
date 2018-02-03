package com.ziroom.minsu.services.message.proxy;

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
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.SysMsgManagerService;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;
import com.ziroom.minsu.services.message.service.SysMsgManagerServiceImpl;

/**
 * @author jixd on 2016年4月18日
 * @version 1.0
 * @since 1.0
 */
@Component("message.sysMsgManagerProxy")
public class SysMsgManagerProxy implements SysMsgManagerService {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SysMsgManagerProxy.class);

	@Resource(name = "message.messageSource")
	private MessageSource messageSource;

	@Resource(name = "message.sysMsgManagerServiceImpl")
	private SysMsgManagerServiceImpl sysMsgManagerServiceImpl;
	
	@Override
	public String saveSysMsgManager(String paramJson) {
		// 返回对象
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		SysMsgManagerEntity sysMsgManagerEntity = JsonEntityTransform.json2Object(paramJson,SysMsgManagerEntity.class);
		try {
			dto.putValue("result", sysMsgManagerServiceImpl.saveSysMsgManager(sysMsgManagerEntity));
		} catch (Exception e) {
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,
					MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,
					MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	@Override
	public String releaseMsg(String paramJson) {
		// 返回对象
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		SysMsgManagerEntity sysMsgManagerEntity = JsonEntityTransform.json2Object(paramJson,SysMsgManagerEntity.class);
		try {
			dto.putValue("result", sysMsgManagerServiceImpl.updateReleaseStatus(sysMsgManagerEntity));
		} catch (Exception e) {
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,
					MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,
					MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String deleteMsg(String paramJson) {
		// 返回对象
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		SysMsgManagerEntity sysMsgManagerEntity = JsonEntityTransform.json2Object(paramJson,SysMsgManagerEntity.class);
		try {
			dto.putValue("result", sysMsgManagerServiceImpl.deleteSysMsgManager(sysMsgManagerEntity));
		} catch (Exception e) {
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,
					MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,
					MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String updateMsg(String paramJson) {
		// 返回对象
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		SysMsgManagerEntity sysMsgManagerEntity = JsonEntityTransform.json2Object(paramJson,SysMsgManagerEntity.class);
		try {
			dto.putValue("result", sysMsgManagerServiceImpl.updateSysMsgManager(sysMsgManagerEntity));
		} catch (Exception e) {
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,
					MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,
					MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	@Override
	public String queryMsgPage(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		SysMsgManagerRequest sysMsgManagerRequest = JsonEntityTransform.json2Object(paramJson,SysMsgManagerRequest.class);
		try {
			PagingResult<SysMsgManagerEntity> pageResult = sysMsgManagerServiceImpl.querySysMsgManagerHasPage(sysMsgManagerRequest);
			dto.putValue("listSysMsg", pageResult.getRows());
			dto.putValue("total", pageResult.getTotal());
		} catch (Exception e) {
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,
					MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,
					MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	@Override
	public String findSysMsgManagerByFid(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource,MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,"paramJson is null"));
			return dto.toJsonString();
		}
		dto.putValue("sysMsgManagerEntity", sysMsgManagerServiceImpl.findSysMsgManagerByFid(fid));
		return dto.toJsonString();
	}
	
}
