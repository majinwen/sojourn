package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ziroom.minsu.entity.weather.WeatherDayEntity;
import com.ziroom.minsu.services.basedata.dao.WeatherDayDao;

/**
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
@Service("basedata.weatherDayServiceImpl")
public class WeatherDayServiceImpl {
	
	 @Resource(name = "basedata.weatherDayDao")
	 private WeatherDayDao weatherDayDao;
	 
 	/**
	 * 插入天气信息
	 */
	public void insertWeatherDay(WeatherDayEntity weatherDayEntity){
		weatherDayDao.insertWeatherDay(weatherDayEntity);
	}
	/**
	 * 根据城市和日期选择
	 * @param weatherDayEntity
	 */
	public WeatherDayEntity selectByCityAndDate(WeatherDayEntity weatherDayEntity){
		return weatherDayDao.selectByCityAndDate(weatherDayEntity);
	}
	/**
	 * 根据日期删除记录
	 */
	public void deleteByDate(String date){
		weatherDayDao.deleteByDate(date);
	}
}
