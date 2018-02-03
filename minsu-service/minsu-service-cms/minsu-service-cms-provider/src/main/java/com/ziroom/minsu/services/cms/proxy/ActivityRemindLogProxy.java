package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityRemindLogService;
import com.ziroom.minsu.services.cms.service.ActivityRemindLogServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on on 17/6/6.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityRemindLogProxy")
public class ActivityRemindLogProxy implements ActivityRemindLogService {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRemindLogProxy.class);

	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;

	@Resource(name="cms.activityRemindLogServiceImpl")
	private ActivityRemindLogServiceImpl activityRemindLogServiceImpl;



	@Override
	public String insertActivityRemindLogIgnore(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityRemindLogEntity activityRemindLogEntity = JsonEntityTransform.json2Entity(paramJson,ActivityRemindLogEntity.class);
			if(Check.NuNObj(activityRemindLogEntity)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
			if(Check.NuNStr(activityRemindLogEntity.getUid())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("uid 为空");
                return dto.toJsonString();
            }
			if(Check.NuNStr(activityRemindLogEntity.getActSn())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("actSn 为空");
                return dto.toJsonString();
            }
			if(Check.NuNObj(activityRemindLogEntity.getRunTime())){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("runTime 为空");
                return dto.toJsonString();
            }
			if(Check.NuNObj(activityRemindLogEntity.getSendTimes())){
                activityRemindLogEntity.setSendTimes(0);
            }
			int upNum = activityRemindLogServiceImpl.insertActivityRemindLogIgnore(activityRemindLogEntity);
			dto.putValue("upNum", upNum);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "insertActivityRemindLogIgnore error:{},paramJson={}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
			return dto.toString();
		}

		return dto.toJsonString();
	}

	@Override
	public String deleteActivityRemindLogByUid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityRemindLogEntity activityRemindLogEntity = JsonEntityTransform.json2Entity(paramJson,ActivityRemindLogEntity.class);
			if(Check.NuNStr(activityRemindLogEntity.getUid())){
				LogUtil.error(LOGGER, "参数异常,uid为空,  paramJson:{}", paramJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("用户uid 为空");
				return dto.toJsonString();
			}
			activityRemindLogServiceImpl.deleteActivityRemindLogByUid(activityRemindLogEntity);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String queryRemindUidInfoByPage(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
			PagingResult<ActivityRemindLogEntity> result=activityRemindLogServiceImpl.queryRemindUidInfoByPage(paramMap);
			dto.putValue("count", result.getTotal());
			dto.putValue("list", result.getRows());
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String updateSendTimesRunTimeByUid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityRemindLogEntity activityRemindLogEntity = JsonEntityTransform.json2Entity(paramJson,ActivityRemindLogEntity.class);
			if(Check.NuNStr(activityRemindLogEntity.getUid())){
				LogUtil.error(LOGGER, "参数异常,uid为空,  paramJson:{}", paramJson);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("用户uid 为空");
				return dto.toJsonString();
			}
			activityRemindLogServiceImpl.updateSendTimesRunTimeByUid(activityRemindLogEntity);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
