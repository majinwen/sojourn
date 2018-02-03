package com.ziroom.minsu.services.basedata.proxy;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.TipMsgHasSubTitleVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.basedata.service.StaticResourceServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>静态资源proxy</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.staticResourceServiceProxy")
public class StaticResourceServiceProxy implements StaticResourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceServiceProxy.class);

	@Resource(name = "basedata.staticResourceServiceImpl")
	private StaticResourceServiceImpl staticResourceServiceImpl;
	
	@Resource(name="basedata.messageSource")
	private MessageSource messageSource;
	
	@Autowired
	private RedisOperations redisOperations;

	@Override
	public String findStaticResourceListByPage(String paramJson) {
		LogUtil.info(LOGGER, "findStaticResourceListByPage paramJson:{}", paramJson);
		StaticResourceRequest paramRequest = JsonEntityTransform.json2Object(paramJson, StaticResourceRequest.class);
		DataTransferObject dto = new DataTransferObject();
		try {
			PagingResult<StaticResourceEntity> pagingResult = staticResourceServiceImpl.findStaticResourceListByPage(paramRequest);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findStaticResourceListByPage error:{}, paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String findStaticResListByResCode(String resCode) {
		LogUtil.info(LOGGER, "findStaticResListByResCode paramJson:{}", resCode);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(resCode)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			}else{
				List<StaticResourceVo> resList = staticResourceServiceImpl.findStaticResListByResCode(resCode);
				dto.putValue("staticResList",resList);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findStaticResListByResCode error:{}, paramJson:{}", e, resCode);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.StaticResourceService#saveStaticEntity(java.lang.String)
	 */
	@Override
	public String saveStaticEntity(String paramJson) {
		LogUtil.info(LOGGER, "saveStaticEntity paramJson:{}", paramJson);
		StaticResourceVo staticResourceVo = JsonEntityTransform.json2Object(paramJson, StaticResourceVo.class);
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(staticResourceVo)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			this.staticResourceServiceImpl.saveStaticEntity(staticResourceVo);
		}catch(Exception e){
			LogUtil.error(LOGGER, "saveStaticEntity error:{}, paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String findStaticResourceByCode(String resCode) {
		LogUtil.info(LOGGER, "findStaticResourceByCode resCode:{}", resCode);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(resCode)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("code为空");
			return dto.toJsonString();
		}
		StaticResourceEntity staticResourceByCode = staticResourceServiceImpl.findStaticResourceByCode(resCode);
		dto.putValue("StaticResourceEntity",staticResourceByCode);
		return dto.toJsonString();
	}

	@Override
	public String getTipsMsgHasSubTitleByCode(String code) {
		LogUtil.info(LOGGER, "getTipsMsgHasSubTitleByCode code:{}", code);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(code)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("code为空");
			return dto.toJsonString();
		}
		StaticResourceEntity staticEntity = staticResourceServiceImpl.findStaticResourceByCode(code);
		if (Check.NuNObj(staticEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("资源不存在");
			return dto.toJsonString();
		}
		List<StaticResourceEntity> staticList = staticResourceServiceImpl.listStaticResourceByParentCode(code);
		if (Check.NuNCollection(staticList)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("资源不存在");
			return dto.toJsonString();
		}

		TipMsgHasSubTitleVo tipMsgHasSubTitleVo = new TipMsgHasSubTitleVo();
		tipMsgHasSubTitleVo.setTitle(staticEntity.getResTitle());
		List<TipMsgHasSubTitleVo.SubTitleContent> list = new ArrayList<>();
		for (StaticResourceEntity staticResourceEntity : staticList){
			TipMsgHasSubTitleVo.SubTitleContent subTitleContent = new TipMsgHasSubTitleVo.SubTitleContent();
			subTitleContent.setSubTitle(staticResourceEntity.getResTitle());
			subTitleContent.setSubContent(staticResourceEntity.getResContent());
			list.add(subTitleContent);
		}
		tipMsgHasSubTitleVo.setSubTitleContents(list);

		dto.putValue("tipMsg",tipMsgHasSubTitleVo);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.StaticResourceService#findStaticResByResCode(java.lang.String)
	 */
	@Override
	public String findStaticResByResCode(String resCode) {
		DataTransferObject dto = new DataTransferObject();
		StringBuffer key=new StringBuffer();
		key.append(RedisKeyConst.CONF_KEY_STATIC);
		if(!Check.NuNStr(resCode)){
			key.append(resCode);
		}
		String resJson= null;
		try {
			resJson=redisOperations.get(key.toString());
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		if(StringUtils.isBlank(resJson)){
			StaticResourceVo vo=staticResourceServiceImpl.findStaticResByResCode(resCode);
			if(!Check.NuNObj(vo)){
				resJson=JsonEntityTransform.Object2Json(vo);
				try {
					redisOperations.setex(key.toString(), RedisKeyConst.HOUSE_STATISTICAL_CACHE_TIME, resJson);
				} catch (Exception e) {
                    LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}
				dto.putValue("res", vo);
			}			
		} else {
			dto.putValue("res", JsonEntityTransform.json2Object(resJson, StaticResourceVo.class));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.StaticResourceService#updateStaticResource(java.lang.String)
	 */
	@Override
	public String updateStaticResource(String paramJson) {
		LogUtil.info(LOGGER, "updateStaticResource paramJson:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(paramJson)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		StaticResourceVo staticResourceVo = JsonEntityTransform.json2Object(paramJson, StaticResourceVo.class);
		try{
			staticResourceServiceImpl.updateStaticResource(staticResourceVo);
			//更新缓存
			StringBuffer key=new StringBuffer();
			key.append(RedisKeyConst.CONF_KEY_STATIC);
			if(!Check.NuNStr(staticResourceVo.getResCode())){
				key.append(staticResourceVo.getResCode());
			}
			StaticResourceVo vo=staticResourceServiceImpl.findStaticResByResCode(staticResourceVo.getResCode());
			redisOperations.setex(key.toString(), RedisKeyConst.HOUSE_STATISTICAL_CACHE_TIME, JsonEntityTransform.Object2Json(vo));
		}catch(Exception e){
			LogUtil.error(LOGGER, "saveStaticEntity error:{}, paramJson:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
