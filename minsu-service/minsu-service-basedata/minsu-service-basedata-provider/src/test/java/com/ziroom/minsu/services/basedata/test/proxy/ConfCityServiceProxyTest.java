package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.proxy.ConfCityServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyingjie on 2016年5月8日
 * @version 1.0
 * @since 1.0
 */
public class ConfCityServiceProxyTest extends BaseTest {
	@Resource(name = "basedata.confCityServiceProxy")
	private ConfCityServiceProxy confCityServiceProxy;



	@Test
	public void getOpenCityAndHot() {
		String resString = confCityServiceProxy.getOpenCityAndHot();
		System.out.print("------------------------" + resString);
	}


    @Test
    public void getAllCityList() {
        String resString = confCityServiceProxy.getAllCityList();
        System.out.print("------------------------" + resString);
    }

	@Test
	public void testGetOpenCity() {
		String resString = confCityServiceProxy.getOpenCity();
		System.out.print("------------------------" + resString);
	}
	
	@Test
	public void getOpenCityMapTest(){
		String res = confCityServiceProxy.getOpenCityMap();
		System.err.println(res);
	}

	@Test
	public void testConfCityTreeVo() {
		String resString = confCityServiceProxy.confCityTreeVo();
		System.err.print(resString);
	}

	@Test
	public void testConfCityOnlyTreeVo() {
		String resString = confCityServiceProxy.confCityOnlyTreeVo();
		System.err.print(resString);
	}

	@Test
	public void testConfCitySelect() {
		String resString = confCityServiceProxy.getConfCitySelect();
		System.err.print(resString);
	}
	
	@Test
	public void getConfCitySelectForLandlord() {
		String resString = confCityServiceProxy.getConfCitySelectForLandlord();
		System.err.print(resString);
	}

	@Test
	public void getOpenCityLandlord4Nation() {
		String resString = confCityServiceProxy.getOpenCityLandlord4Nation("386");
		System.err.print(resString);
	}

	@Test
	public void getOpenCityLandlord() {
		String resString = confCityServiceProxy.getOpenCityLandlord();
		System.err.print(resString);
	}
	
	@Test
	public void getConfCitySelectForTenant() {
		String resString = confCityServiceProxy.getConfCitySelectForTenant();
		System.err.print(resString);
	}
	
	@Test
	public void getConfCitySelectForAll() {
		String resString = confCityServiceProxy.getConfCitySelectForAll();
		System.err.print(resString);
	}
	
	@Test
	public void testGetCityNameByCodeList(){
		List<String> codeList = new ArrayList<String>();
		codeList.add("110112");
		codeList.add("120000");
		codeList.add("130434");
		codeList.add("130600");
		
		String resString = confCityServiceProxy.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList));
		System.err.println(resString);
	}
	
	@Test
	public void testUpdateConfCityByFid(){
		
		ConfCityEntity resourceEntity = new ConfCityEntity();
		resourceEntity.setFid("8a9e9a945486425d015486425df70001");
		resourceEntity.setCode("110100");
		resourceEntity.setCityStatus(1);
		resourceEntity.setLevel(3);
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(confCityServiceProxy.updateConfCityByFid(JsonEntityTransform.Object2Json(resourceEntity)));
		
		System.out.println(dto);
	}
	
	@Test
	public void getOpenCityWithFileTest() {
		String resString = confCityServiceProxy.getOpenCityWithFile();
		System.err.print(resString);
	}
}
