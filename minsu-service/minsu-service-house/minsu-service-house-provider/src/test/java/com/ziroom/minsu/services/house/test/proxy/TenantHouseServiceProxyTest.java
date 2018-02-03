/**
 * @FileName: TenantHouseServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月30日 下午10:50:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseStatisticsMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.proxy.TenantHouseServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户房源测试</p>
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
public class TenantHouseServiceProxyTest extends BaseTest{
	
	@Resource(name="house.tenantHouseServiceProxy")
	private TenantHouseServiceProxy tenantHouseServiceProxy;
	
	/**
	 * 
	 * 
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午10:51:55
	 *
	 */
	@Test
	public void houseDetailTest(){
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		houseDetailDto.setFid("8a9084df550d9bdd01550db2ec340107");
		houseDetailDto.setRentWay(1);
		houseDetailDto.setUid("eaaf194b-067e-4289-bcd7-63a9433d3ef4");
//		houseDetailDto.setFid("8a9e9a9a54b646e60154b646e6d50001");
//		houseDetailDto.setRentWay(0);
		/*System.out.println(JsonEntityTransform.Object2Json(houseDetailDto));*/
		System.err.println(tenantHouseServiceProxy.houseDetail(JsonEntityTransform.Object2Json(houseDetailDto)));
		//System.out.println(tenantHouseServiceProxy.houseDetail(JsonEntityTransform.Object2Json(houseDetailDto)));
//		System.err.println(tenantHouseServiceProxy.houseDetail("{\"fid\":\"8a90a2d4549ac7990154a3ee6eee0237\",\"rentWay\":0,\"startTime\":null,\"uid\":\"0f163457-ad6a-09ce-d5de-de452a251cf6\"}"));
	}
	

	
	@Test
	public void statisticalPvTest(){
		for(int i=0;i<100;i++){
			HouseStatisticsMsgEntity houseStatisticsMsgEntity=new HouseStatisticsMsgEntity();
			houseStatisticsMsgEntity.setHouseBaseFid("8a9e9a9a54afbc950154afbc95290001");
			houseStatisticsMsgEntity.setRentWay(0);
			HouseDetailDto houseDetailDto=new HouseDetailDto();
			houseDetailDto.setFid("8a9e9a9a54afbc950154afbc95290001");
			houseDetailDto.setRentWay(0);
			tenantHouseServiceProxy.statisticalPv(JsonEntityTransform.Object2Json(houseDetailDto));
		}
	}
	
	@Test
	public void findStatisticalPvTest(){
		HouseStatisticsMsgEntity houseStatisticsMsgEntity=new HouseStatisticsMsgEntity();
		houseStatisticsMsgEntity.setHouseBaseFid("8a9e9a9a54afbc950154afbc95290001");
		houseStatisticsMsgEntity.setRentWay(0);
		String resultJson=tenantHouseServiceProxy.findStatisticalPv(JsonEntityTransform.Object2Json(houseStatisticsMsgEntity));
		System.err.println(resultJson);
	}


	@Test
	public void findHoseDesc(){
		String str = "{\"fid\":\"8a9084df5b9da32e015b9da3a31d0008\",\"rentWay\":0}";
		// String str = "{\"fid\":\"8a90a2d458be6b220158be77ac270008\",\"rentWay\":0}";
		String rst = tenantHouseServiceProxy.findHoseDesc(str);
		System.out.println(rst);
		System.err.println(rst);

	}

	@Test
	public void houseListDetailTest(){
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		houseDetailDto.setFid("8a908e9d601f774e01601f7ccb9f008a");
		houseDetailDto.setRentWay(0);
		houseDetailDto.setUid("499bb47a-a9de-460e-b2a1-2feebf92def9");
//		houseDetailDto.setFid("8a9e9aae5419cc22015419cc250a0002");
//		houseDetailDto.setRentWay(0);
		System.err.println(tenantHouseServiceProxy.houseDetail(JsonEntityTransform.Object2Json(houseDetailDto)));
	}
}
