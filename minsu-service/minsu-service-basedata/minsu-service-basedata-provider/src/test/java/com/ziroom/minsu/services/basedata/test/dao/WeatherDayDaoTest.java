package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.weather.WeatherDayEntity;
import com.ziroom.minsu.services.basedata.dao.WeatherDayDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
public class WeatherDayDaoTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDayDaoTest.class);
	
	@Resource(name = "basedata.weatherDayDao")
    private WeatherDayDao weatherDayDao;

    @Test
    public void insertTest() {
    	WeatherDayEntity entity = new WeatherDayEntity();
    	entity.setCityName("朝阳");
    	try {
			entity.setDate(DateUtil.parseDate(DateUtil.dateFormat(new Date()), "yyyy-MM-dd"));
		} catch (ParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
    	entity.setFid(UUIDGenerator.hexUUID());
    	entity.setNongli("三月初七");
    	entity.setWeek("3");
    	entity.setWeatherInfo("");
    	weatherDayDao.insertWeatherDay(entity);
    }
    
    @Test
    public void selectByCityAndDateTest(){
    	WeatherDayEntity entity = new WeatherDayEntity();
    	entity.setCityName("朝阳");
    	try {
			entity.setDate(DateUtil.parseDate("2016-04-13","yyyy-MM-dd"));
		} catch (ParseException e) {
			
			LogUtil.error(LOGGER, "error:{}", e);
		}
    	
    	WeatherDayEntity selectByCityAndDate = weatherDayDao.selectByCityAndDate(entity);    
    	System.out.println(selectByCityAndDate.getCityName());
    }
    
    @Test
    public void deleteByDateTest(){
    	weatherDayDao.deleteByDate("2016-4-14");
    }
}
