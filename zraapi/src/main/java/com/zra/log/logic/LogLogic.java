package com.zra.log.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.ziroom.platform.tesla.client.TeslaClientFactory;
import com.zra.common.dto.log.LogRecordDto;
import com.zra.common.dto.log.LogRecordParam;
import com.zra.common.dto.log.ResultLogRecordDto;
import com.zra.common.enums.BussSystemEnums;
import com.zra.common.utils.NetUtil;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.StrUtils;

@Component
public class LogLogic {
	
	//此处不可以切换成LoggerFactoryProxy -cuiyuhui,因为logstash收集的日志是固定格式的
	public static final Logger LOGSTASH = LoggerFactory.getLogger("LOGSTASH");
	
	public static final Logger LOG = LoggerFactoryProxy.getLogger(LogLogic.class);
	
	
	/**
	 * 根据查询条件，返回日志信息，可直接调用
	 * @return
	 */
	public ResultLogRecordDto getLogs(LogRecordParam logRecordParam){
		ResultLogRecordDto resultLogRecordDto  = new ResultLogRecordDto();
		try {
			//验证参数
			if(StrUtils.isNullOrBlank(logRecordParam.getSystemId()) || 
					StrUtils.isNullOrBlank(logRecordParam.getOperModId()) ||
					StrUtils.isNullOrBlank(logRecordParam.getOperObjId()) ){
				return null;
			}
			
			//设置默认参数
			if(StrUtils.isNullOrBlank(logRecordParam.getPage()) ){
				logRecordParam.setPage(1);
			}
			if(StrUtils.isNullOrBlank(logRecordParam.getSize()) ){
				logRecordParam.setSize(10);
			}
			
			int from = (logRecordParam.getPage() -1 ) * logRecordParam.getSize();
			
			
			//设计查询的索引和类型
			String indexName = getIndexName(logRecordParam.getSystemId());
			String typeName = getTypeName(logRecordParam.getSystemId());
			
			Map<String,String> pathMap = new HashMap<String,String>();
			pathMap.put("index", indexName);
			pathMap.put("type", typeName);
			
			
			WebTarget webTarget =  TeslaClientFactory.newDynamicClient("elsearch").target("");
			WebTarget target = webTarget.path("/{index}/{type}/_search").
					resolveTemplate("index", pathMap.get("index")).
					resolveTemplate("type", pathMap.get("type")).
					queryParam("q", "(operModId:"+logRecordParam.getOperModId()+") AND (operObjId:"+logRecordParam.getOperObjId()+")").
					queryParam("sort", "operTime:desc").
					queryParam("from", from).
					queryParam("size", logRecordParam.getSize());
			
/*	WebTarget target = webTarget.path("/{index}/{type}/_search").
					resolveTemplate("index", pathMap.get("index")).
					resolveTemplate("type", pathMap.get("type")).
					queryParam("q", "(operModId:1) AND (operObjId:11)").
					queryParam("sort", "operTime:desc").
					queryParam("from", "0").
					queryParam("size", "30");*/
			//Response response = target.request().get();
			//String str = response.readEntity(String.class);
			String str = NetUtil.getDataByHttpGet(target.getUri());
			//解析 elastic search返回结果
			Map<String, Object> firstMap = JSON.parseObject(str, new TypeReference<Map<String,Object>>(){});
			Map<String, Object> hitsMap = (Map<String, Object>)firstMap.get("hits");
			Integer total = (Integer)hitsMap.get("total");
			resultLogRecordDto.setTotal(total);
			//无查询结果，返回null
			if(total == 0){
				return resultLogRecordDto;
			}
			JSONArray hitsArray = (JSONArray)hitsMap.get("hits");
			List<LogRecordDto> logRecordDtoList = new ArrayList<LogRecordDto>();
			for(int i = 0 ;i<hitsArray.size();i++){
				Map<String, Object> hitsSubMap = (Map<String, Object>)hitsArray.get(i);
				Map<String, Object> sourceMap = (Map<String, Object>)hitsSubMap.get("_source");
				String systemId = (String)sourceMap.get("systemId");
				String operModId = (String)sourceMap.get("operModId");
				String operObjId = (String)sourceMap.get("operObjId");
				String operator = (String)sourceMap.get("operator");
				String operTime = (String)sourceMap.get("operTime");
				String loginfo = (String)sourceMap.get("loginfo");
				LogRecordDto dto = new LogRecordDto(systemId,operModId,operObjId,operator,operTime,loginfo);
				logRecordDtoList.add(dto);
			}
			resultLogRecordDto.setLogRecordList(logRecordDtoList);
		} catch (Exception e) {
			LOG.error("商机查询操作日志出错！",e);
		}
		return resultLogRecordDto;
	}
	
	/**
	 * 保存日志
	 * @param logRecordDto
	 * @return true 成功,false 失败
	 */
	public boolean saveLog(LogRecordDto logRecordDto){

		//验证必要条件
		if(StrUtils.isNullOrBlank(logRecordDto.getSystemId())
				|| StrUtils.isNullOrBlank(logRecordDto.getOperModId())
				|| StrUtils.isNullOrBlank(logRecordDto.getOperObjId())
				|| StrUtils.isNullOrBlank(logRecordDto.getOperator())
				|| StrUtils.isNullOrBlank(logRecordDto.getLoginfo())){
			return false;
		}
		//拼接打印日志
		String systemId = logRecordDto.getSystemId();
		StringBuffer sb = new StringBuffer();
		sb.append(getIndexName(systemId));
		sb.append(" ");
		sb.append(getTypeName(systemId));
		sb.append(" ");
		sb.append(logRecordDto.toLogString());
		LOG.info(sb.toString());
//		//记录日志。该方法只记录日志，然后通过logstash收集日志，发送到elastic search中
//		LOGSTASH.info(sb.toString());
		//调用基础平台的创建索引
		return CallElasticSearchLogic.createIndex(logRecordDto);
	}
	
	/**
	 * 获取indexName
	 * @param systemId
	 * @return
	 */
	private String getIndexName(String systemId){
		return PropUtils.getString("ZRA_PLATFORM");
	}
	
	
	/**
	 * @param systemId
	 * @return
	 */
	private String getTypeName(String systemId){
		BussSystemEnums systemEnum = BussSystemEnums.getByKey(systemId);
		return systemEnum.getAliasName();
	}
	
	public static void main(String[] args) {
		String jsonString = "{\"aa\":{\"bb\":[{\"cc\":\"dd\"},{\"ee\":\"dd\"}]}}";
		Map<String, Object> listMap = JSON.parseObject(jsonString, new TypeReference<Map<String,Object>>(){});
		Map<String,Object> test2 = (Map<String,Object>)(listMap.get("aa"));
		JSONArray test3 = (JSONArray)(test2.get("bb"));
	}
}
