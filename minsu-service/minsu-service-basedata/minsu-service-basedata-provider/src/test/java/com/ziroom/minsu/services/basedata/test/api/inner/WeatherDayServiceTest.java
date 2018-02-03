package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.api.inner.WeatherDayService;
import com.ziroom.minsu.services.basedata.dto.WeatherDayRequest;
import com.ziroom.minsu.services.basedata.entity.WeatherDayInfoVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author jixd on 2016年4月13日
 * @version 1.0
 * @since 1.0
 */
public class WeatherDayServiceTest extends BaseTest {
	
	@Resource(name="basedata.weatherDayProxy")
    private WeatherDayService weatherDayService;
	@Test
	public void selectByCityAndDateTest(){
		//WeatherDayInfoVo vo = weatherDayService.selectByCityAndDate("郑州", "2016-04-18");
		WeatherDayRequest request = new WeatherDayRequest();
		request.setCityName("西安");
		request.setDate("2016-04-15");
		String jsonparam = JsonEntityTransform.Object2Json(request);
		String resultJson = weatherDayService.selectByCityAndDate(jsonparam);
		
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		//当天天气信息
		WeatherDayInfoVo vo = resultDto.parseData("weatherdayvo", new TypeReference<WeatherDayInfoVo>(){});
		System.out.println(vo.getCityName());
	}
	
	
}
