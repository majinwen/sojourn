package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dto.WeatherDayRequest;
import com.ziroom.minsu.services.basedata.entity.WeatherDayInfoVo;
import com.ziroom.minsu.services.basedata.proxy.WeatherDayProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author jixd on 2016年4月15日
 * @version 1.0
 * @since 1.0
 */
public class WeatherDayProxyTest extends BaseTest {
	@Resource(name="basedata.weatherDayProxy")
	private WeatherDayProxy weatherDayProxy;
	
	@Test
	public void testselectByCityAndDate(){
		WeatherDayRequest request = new WeatherDayRequest();
		request.setCityName("北京");
		request.setDate(DateUtil.getDayAfterCurrentDate());
		String json = weatherDayProxy.selectByCityAndDate(JsonEntityTransform.Object2Json(request));
		
		DataTransferObject weatherDto = JsonEntityTransform.json2DataTransferObject(json);
		//当天天气信息
		WeatherDayInfoVo vo = weatherDto.parseData("weatherdayvo", new TypeReference<WeatherDayInfoVo>(){});
		System.out.println(json);
		System.out.println(vo.getCityName());
	}
}
