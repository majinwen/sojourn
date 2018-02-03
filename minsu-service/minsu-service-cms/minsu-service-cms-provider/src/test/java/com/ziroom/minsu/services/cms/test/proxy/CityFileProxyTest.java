/**
 * @FileName: CityFileProxyTest.java
 * @Package com.ziroom.minsu.services.cms.test.proxy
 * 
 * @author bushujie
 * @created 2016年11月8日 上午10:28:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.proxy.CityFileProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * <p>TODO</p>
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
public class CityFileProxyTest extends BaseTest{
	
	@Resource(name="cms.CityFileProxy")
	private CityFileProxy cityFileProxy;
	
	
	@Test
	public void columnTemplateListTest(){
		ColumnTemplateRequest request=new ColumnTemplateRequest();
		String resultJson=cityFileProxy.columnTemplateList(JsonEntityTransform.Object2Json(request));
		System.err.println(resultJson);
	}
	
	@Test
	public void insertColumnTemplateTest(){
		ColumnTemplateEntity columnTemplateEntity=new ColumnTemplateEntity();
		columnTemplateEntity.setFid(UUIDGenerator.hexUUID());
		columnTemplateEntity.setTempName("测试模板");
		columnTemplateEntity.setTempUrl("http://www.baidu.com");
		String resultJson=cityFileProxy.insertColumnTemplate(JsonEntityTransform.Object2Json(columnTemplateEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void columnCityListTest(){
		ColumnCityRequest columnCityRequest=new ColumnCityRequest();
		String resultJson=cityFileProxy.columnCityList(JsonEntityTransform.Object2Json(columnCityRequest));
		System.err.println(resultJson);
	}
	
	@Test
	public void findAllRegTemplateTest(){
		String resultJson=cityFileProxy.findAllRegTemplate();
		System.err.println(resultJson);
	}
	
	@Test
	public void columnRegionListTest(){
		ColumnRegionRequest columnRegionRequest=new ColumnRegionRequest();
		columnRegionRequest.setColumnCityFid("8a9e98985847fdfc015847fdfcbf0000");
		String resultJson=cityFileProxy.columnRegionList(JsonEntityTransform.Object2Json(columnRegionRequest));
		System.err.println(resultJson);
	}
	@Test
	public void getRegionFile(){
		String resultJson = cityFileProxy.getCityRegionsByCityCode("110100");
		System.err.println(resultJson);
	}
}
