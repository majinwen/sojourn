/**
 * @FileName: HouseManageServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 下午1:10:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.service;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>房东端房源管理实现测试类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liujun
 * @version 1.0
 * @since 1.0
 */
public class TenantHouseServiceImplTest extends BaseTest {

	@Resource(name = "house.tenantHouseServiceImpl")
	private com.ziroom.minsu.services.house.service.TenantHouseServiceImpl tenantHouseServiceImpl;



	@Test
	public void getHouseRoomDetail() {
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		houseDetailDto.setFid("8a9084df5ffb8ad4015ffcc1ce59045d");
		houseDetailDto.setRentWay(1);
		TenantHouseDetailVo tenantHouseDetailVo=tenantHouseServiceImpl.getHouseRoomDetail(houseDetailDto);
		System.err.println(JsonEntityTransform.Object2Json(tenantHouseDetailVo));

	}
}
