package com.ziroom.minsu.services.basedata.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;
import com.ziroom.minsu.services.basedata.dao.SubwayOutDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;

public class SubwayOutDaoTest extends BaseTest {

	@Resource(name = "basedata.subwayOutDao")
	private SubwayOutDao subwayOutDao;
	
	
	@Test
	public void TestSaveSubwayOut() {
		SubwayOutEntity subwayOut = new SubwayOutEntity();
		subwayOut.setFid(UUIDGenerator.hexUUID());
		subwayOut.setStationFid("8a9e9ab9540faf8201540faf82500000");
		subwayOut.setOutName("B口11111");
		subwayOut.setLatitude(39.876911 );
		subwayOut.setLongitude(116.349411);
		
		Gps gps = CoordinateTransforUtils.bd09_To_Gps84(39.876911 ,116.349411);
		subwayOut.setGoogleLatitude(Check.NuNObj(gps)?0:gps.getWgLat());
		subwayOut.setGoogleLongitude(Check.NuNObj(gps)?0:gps.getWgLon());
		subwayOut.setCreateFid(UUIDGenerator.hexUUID());
		
		int resultNum = subwayOutDao.saveSubwayOut(subwayOut);
		System.out.println("resultNum:" + resultNum);
	}

}
