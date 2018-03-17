package com.ziroom.minsu.activity.controller;


import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ShortChainMapEntity;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;

/**
 * <p>短链服务控制层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/")
@Controller
public class ShortChainContoller {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortChainContoller.class);

	@Resource(name = "cms.shortChainMapService")
	private ShortChainMapService shortChainMapService;

	/**
	 * 
	 * 根据短链映射重定向原链接
	 *
	 * @author liujun
	 * @created 2016年10月28日
	 *
	 * @param uniqueCode
	 * @return
	 */
	@RequestMapping("/uniqueCode/{uniqueCode}")
	public String redirect(@PathVariable("uniqueCode") String uniqueCode) {
		try {
			String resultJson = shortChainMapService.findShortChainMapByUniqueCode(uniqueCode);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "shortChainMapService.findShortChainMapByUniqueCode error", dto.toJsonString());
				return "/error";
			}
			
			ShortChainMapEntity shortChainMapEntity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", ShortChainMapEntity.class);
			if (Check.NuNObj(shortChainMapEntity)) {
				LogUtil.error(LOGGER, "shortChainMapEntity is null");
				return "/error";
			}
			
			if (Check.NuNStr(shortChainMapEntity.getOriginalLink())) {
				LogUtil.error(LOGGER, "originalLink is null");
				return "/error";
			}
			
			return "redirect:" + shortChainMapEntity.getOriginalLink();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redirect error:{}", e);
			return "/error";
		} 
	}
}
