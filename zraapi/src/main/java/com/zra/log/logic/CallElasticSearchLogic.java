package com.zra.log.logic;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.common.dto.log.LogRecordDto;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.ZraApiConst;
/**
 * 调用基础平台搜索类
 * @author tianxf9
 *
 */
@Component
public class CallElasticSearchLogic {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CallElasticSearchLogic.class);
	
	/**
	 * 创建索引
	 * @author tianxf9
	 * @return
	 */
	public static boolean createIndex(LogRecordDto logRecordDto) {
		
		String url = PropUtils.getString(ZraApiConst.ELASTICSEARCH_CREATEINDEX_URL);
		String paramJson = JSON.toJSONString(logRecordDto);
		LOGGER.info("url:"+url);
		LOGGER.info("params:"+paramJson);
		String result = null;
		try {
			result = NetUtil.sendPostReqByJsonParamForCompetency2(paramJson, url);
			
		} catch (Exception e) {
			LOGGER.error("调用"+url+"出错！", e);
		}
		LOGGER.info("url="+url+";params="+paramJson+";result:"+result);
		Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
		if("200".equals((String)resultMap.get("code"))&&"success".equals((String)resultMap.get("status"))) {
			return true;
		}
		
		return false;
	}

}
