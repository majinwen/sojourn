package com.ziroom.minsu.services.basedata.api.inner;

/**
 * 获取天气接口
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
public interface WeatherDayService {
	/**
	 * 根据城市和日期查询天气
	 * @param weatherDayEntity
	 * @return
	 */
	
	String selectByCityAndDate(String paramJson);
}
