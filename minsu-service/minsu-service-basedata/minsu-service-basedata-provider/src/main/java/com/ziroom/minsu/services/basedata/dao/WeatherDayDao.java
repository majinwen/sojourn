package com.ziroom.minsu.services.basedata.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.weather.WeatherDayEntity;

/**
 *
 * @author jixd on 2016/4/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.weatherDayDao")
public class WeatherDayDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDayDao.class);

	private String SQLID = "basedata.weatherDayDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	
	/**
	 * 插入天气信息
	 */
	public void insertWeatherDay(WeatherDayEntity weatherDayEntity){
		if(weatherDayEntity == null){
			return;
		}
		mybatisDaoContext.save(SQLID + "insertSelective", weatherDayEntity);
	}
	/**
	 * 根据城市和日期选择
	 * @param weatherDayEntity
	 */
	public WeatherDayEntity selectByCityAndDate(WeatherDayEntity weatherDayEntity){
		//检测是否为空
		if(Check.NuNObjs(weatherDayEntity.getCityName(),weatherDayEntity.getDate())){
			 throw  new BusinessException("the CityName or Date is null on select the WeatherDay");
		}
		List<WeatherDayEntity> list = mybatisDaoContext.findAll(SQLID + "selectByCityAndDate", WeatherDayEntity.class, weatherDayEntity);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据日期删除记录
	 */
	public void deleteByDate(String date){
		if(Check.NuNStr(date)){
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		Map<String,Object> delPar = new HashMap<>();
        try {
			delPar.put("date",sdf.parse(date));
		} catch (ParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		mybatisDaoContext.delete(SQLID + "deleteByDate", delPar);
	}
}
