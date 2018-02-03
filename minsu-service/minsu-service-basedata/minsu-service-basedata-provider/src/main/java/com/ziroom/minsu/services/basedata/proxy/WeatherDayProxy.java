package com.ziroom.minsu.services.basedata.proxy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.weather.WeatherDayEntity;
import com.ziroom.minsu.services.basedata.api.inner.WeatherDayService;
import com.ziroom.minsu.services.basedata.dto.WeatherDayRequest;
import com.ziroom.minsu.services.basedata.entity.WeatherDayInfoVo;
import com.ziroom.minsu.services.basedata.entity.WeatherDayInfoVo.IntervalDay;
import com.ziroom.minsu.services.basedata.service.WeatherDayServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;

/**
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
@Component("basedata.weatherDayProxy")
public class WeatherDayProxy implements WeatherDayService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDayProxy.class);
	
	@Resource(name = "basedata.weatherDayServiceImpl")
	WeatherDayServiceImpl weatherDayServiceImpl;
	
	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;
	
	@Override
	public String selectByCityAndDate(String paramJson) {
		//转换json
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(paramJson)){
			 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
             dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
             return dto.toJsonString();
		}
		try{
			WeatherDayRequest weatherDayRequest = JsonEntityTransform.json2Object(paramJson,WeatherDayRequest.class);
			WeatherDayEntity entity = new WeatherDayEntity();
			entity.setCityName(weatherDayRequest.getCityName());
			entity.setDate(DateUtil.parseDate(weatherDayRequest.getDate(), "yyyy-MM-dd"));
			WeatherDayEntity resultEntity = weatherDayServiceImpl.selectByCityAndDate(entity);
			//如果为空从接口中获取
			if(Check.NuNObj(resultEntity)){
				String url = SystemGlobalsUtils.getValue("basedata.weatherday.api.url").replace("CITYNAME", weatherDayRequest.getCityName());
				LogUtil.info(LOGGER, "天气api:"+url);
				//post发送不用编码cityName
				String weatherJSON = CloseableHttpUtil.sendPost(url);
				List<WeatherDayEntity> list = parseJSONToWeatherEntity(weatherJSON);
				if(list != null && list.size()>0){
					for(WeatherDayEntity weatherDayEntity: list){
						//直接从接口数据中获取需要数据
						if(DateUtil.dateFormat(weatherDayEntity.getDate()).equals(weatherDayRequest.getDate())){
							resultEntity = weatherDayEntity;
						}
						//不录取当天天气信息
						if(DateUtil.dateFormat(new Date(), "yyyy-MM-dd").equals(DateUtil.dateFormat(weatherDayEntity.getDate()))){
							continue;
						}
						//对接口数据做持久化处理
						weatherDayServiceImpl.insertWeatherDay(weatherDayEntity);
					}
				}
			}
			WeatherDayInfoVo vo = transEntityToVo(resultEntity);
			dto.putValue("weatherdayvo", vo);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		//转换vo
		return dto.toJsonString();
		
	}
	
	
	/**
	 * 接口返回json数据解析为实体对象
	 * @param json
	 * @return
	 */
	private List<WeatherDayEntity> parseJSONToWeatherEntity(String json){
		JSONObject obj = JSONObject.parseObject(json);
		//判断接口是否调用成功
		if(obj.getIntValue("error_code") != 0){
			return null;
		}
		JSONObject jsondata = obj.getJSONObject("result").getJSONObject("data");
		String cityName = jsondata.getJSONObject("realtime").getString("city_name");
		JSONArray jsonArr = jsondata.getJSONArray("weather");
		List<WeatherDayEntity> list = new ArrayList<>();
		for(int i =0;i<jsonArr.size();i++){
			JSONObject dayjson = jsonArr.getJSONObject(i);
			
			WeatherDayEntity tempEntity = new WeatherDayEntity();	
			tempEntity.setCityName(cityName);
			try {
				tempEntity.setDate(DateUtil.parseDate(dayjson.getString("date"), "yyyy-MM-dd"));
			} catch (ParseException e) {
				LogUtil.error(LOGGER, "error:{}", e);
			}
			tempEntity.setFid(UUIDGenerator.hexUUID());
			tempEntity.setNongli(dayjson.getString("nongli"));
			tempEntity.setWeek(dayjson.getString("week"));
			tempEntity.setWeatherInfo(dayjson.getJSONObject("info").toString());
			list.add(tempEntity);
		}
		return list;
	}
	
	/**
	 * 实体类装换成查询vo
	 * @param entity
	 * @return
	 */
	private WeatherDayInfoVo transEntityToVo(WeatherDayEntity entity){
		WeatherDayInfoVo vo = new WeatherDayInfoVo();
		vo.setCityName(entity.getCityName());
		vo.setDate(entity.getDate());
		vo.setWeek(entity.getWeek());
		vo.setNongli(entity.getNongli());
		String info = entity.getWeatherInfo();
		JSONObject jsonObj = JSONObject.parseObject(info);
		//可能没有早上数据
		if(jsonObj.containsKey("dawn")){
			JSONArray dawnObj = jsonObj.getJSONArray("dawn");
			vo.setDawn(new WeatherDayInfoVo.IntervalDay());
			transfValue(vo.getDawn(),dawnObj);
		}
		JSONArray dayObj = jsonObj.getJSONArray("day");
		JSONArray nightObj = jsonObj.getJSONArray("night");
		vo.setDay(new WeatherDayInfoVo.IntervalDay());
		vo.setNight(new WeatherDayInfoVo.IntervalDay());
		
		transfValue(vo.getDay(),dayObj);
		transfValue(vo.getNight(),nightObj);
		return vo;
	}
	
	private void transfValue(IntervalDay intervalDay,JSONArray json){
		intervalDay.setLowTemperature(json.getString(0));
		intervalDay.setWeather(json.getString(1));
		intervalDay.setHighTemperature(json.getString(2));
		intervalDay.setWind(json.getString(3));
		intervalDay.setWindStrong(json.getString(4));
	}
	
	public static void main(String[] args) {
		//System.out.println(UUIDGenerator.hexUUID().length());
		System.out.println(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
	}

}
