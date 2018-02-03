package com.ziroom.minsu.services.cms.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ShortChainMapEntity;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.cms.service.ShortChainMapServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.valenum.common.JumpOpenAppEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

/**
 * 
 * <p>短链proxy</p>
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
@Service("cms.shortChainMapProxy")
public class ShortChainMapProxy implements ShortChainMapService {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortChainMapProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.shortChainMapImpl")
	private ShortChainMapServiceImpl shortChainMapImpl ;

    @Value("#{'${SHORT_CHAIN_SERVICE_API}'.trim()}")
    private String API_URL;
    
	private String smsAppJumpUrl = "common/ee5f86/goToApp?param=";
	
	@Value(value = "${JPUSH_APP_M_HOUSE}")
	private String m_house;

	/**
	 * 根据给定长链接返回短链
	 */
	@Override
	public String generateShortLink(String originalLink, String createId) {
		LogUtil.info(LOGGER, "参数:originalLink={}", originalLink);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(originalLink)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			String checkCode = MD5Util.MD5Encode(new StringBuilder(originalLink.toLowerCase()).append(";").append(API_URL).toString());
			List<ShortChainMapEntity> list = shortChainMapImpl.findShortChainMapByCheckCode(checkCode);
			if (!Check.NuNCollection(list)) {
				for (ShortChainMapEntity shortChainMapEntity : list) {
					if (originalLink.equalsIgnoreCase(shortChainMapEntity.getOriginalLink())) {
						dto.putValue("shortLink", shortChainMapEntity.getShortLink());
						LogUtil.info(LOGGER, "结果:{}", JsonEntityTransform.Object2Json(shortChainMapEntity));
						return dto.toJsonString();
					}
				}
			}
			
			ShortChainMapEntity shortChainMapEntity = shortChainMapImpl.assembleEntity(originalLink, createId, checkCode);
			String shortLink = shortChainMapImpl.generateShortLink(shortChainMapEntity, API_URL);
			dto.putValue("shortLink", shortLink);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "generateShortLink error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据短链编号查询短链信息
	 */
	@Override
	public String findShortChainMapByUniqueCode(String uniqueCode) {
		LogUtil.info(LOGGER, "参数:uniqueCode={}", uniqueCode);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uniqueCode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			ShortChainMapEntity shortChainMapEntity = shortChainMapImpl.findShortChainMapByUniqueCode(uniqueCode);
			dto.putValue("obj", shortChainMapEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findShortChainMapByUniqueCode error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 
	 * 获取短信跳转到房源列表的短连接
	 *
	 * @author loushuai
	 * @created 2017年12月22日 下午3:34:19
	 *
	 * @param type
	 * @return
	 */
	public String getMinsuHomeJump() {
		DataTransferObject dto = new DataTransferObject();
		JSONObject object = new JSONObject();
		object.put("type",UserTypeEnum.LANDLORD.getUserCode());
		object.put("jumpType", JumpOpenAppEnum.LAND_HOUSE_LIST.getCode());
		String param = "";
		try {
				param = URLEncoder.encode(object.toString(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.error(LOGGER, "获取短信跳转到房源列表的短连接错误  e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		String url = m_house + smsAppJumpUrl + param;
		String shortLink = generateShortLink(url, "001");
		LogUtil.info(LOGGER, "m_house={},smsAppJumpUrl={},url={},shortLink={}", m_house,smsAppJumpUrl,url,shortLink);
		return shortLink;
	}

}
