/**
 * @FileName: HotRegionServiceProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.test.proxy
 * 
 * @author yd
 * @created 2017年2月22日 下午4:10:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.proxy.HotRegionServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.common.utils.JsonTransform;

/**
 * <p>测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HotRegionServiceProxyTest extends BaseTest {

	
	@Resource(name = "basedata.hotRegionServiceProxy")
	private HotRegionServiceProxy hotRegionServiceProxy;

	@Test
	public void testSaveHotRegion() {
		HotRegionEntity hotRegion = new HotRegionEntity();
		
		hotRegion.setFid(UUIDGenerator.hexUUID());
		hotRegion.setNationCode("100000");
		hotRegion.setProvinceCode("440000");
		hotRegion.setCityCode("440100");
		hotRegion.setRegionName("大连民族大学");
		hotRegion.setHot(50);
		hotRegion.setRadii(5);
		hotRegion.setRegionStatus(1);
		hotRegion.setRegionType(3);
		hotRegion.setGoogleLatitude(39.0442695);
		hotRegion.setGoogleLongitude(121.77395030000002);
		
		Gps gps = CoordinateTransforUtils.wgs84_To_bd09(hotRegion.getGoogleLatitude(), hotRegion.getGoogleLongitude());
		if(!Check.NuNObj(gps)){
			hotRegion.setLatitude(gps.getWgLat());
			hotRegion.setLongitude(gps.getWgLon());
		}

		DataTransferObject dto = JsonTransform.json2DataTransferObject(this.hotRegionServiceProxy.saveHotRegion(JsonTransform.Object2Json(hotRegion)));
		
		System.out.println(dto);
	}

}
