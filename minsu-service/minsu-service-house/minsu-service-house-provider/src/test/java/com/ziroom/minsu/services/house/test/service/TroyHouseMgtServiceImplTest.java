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
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.house.entity.HouseCityVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;


public class TroyHouseMgtServiceImplTest extends BaseTest{
	
	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;
	
	
	@Test
	public void getHouseCityVoByFidsTest(){
		List<String> houseFidList = new ArrayList<String>();
		houseFidList.add("8a9e9a8d55d9595a0155da35e6cd000c");
		houseFidList.add("8a9e9aa255da410d0155da410d750001");
		List<HouseCityVo> houseCityVoByFids = troyHouseMgtServiceImpl.getHouseCityVoByFids(houseFidList);
		System.err.println(JsonEntityTransform.Object2Json(houseCityVoByFids));
	}

	@Test
	public void findHouseUpdateFieldAuditNewlogByCondition(){
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid("8a9084df5d9ba997015d9bac79dc0006");
		houseUpdateFieldAuditNewlogEntity.setRentWay(0);
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);

		List<HouseFieldAuditLogVo> list = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
		System.out.print(list.toString());
	}

	@Test
	public void approveRoomPic()throws Exception{
		HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
		houseRoomMsgEntity.setHouseBaseFid("8a9084df5dc60fa5015dc614bd67001d");
		houseRoomMsgEntity.setFid("8a9084df5dc60fa5015dc616c9460022");
		int i = troyHouseMgtServiceImpl.approveRoomPic(houseRoomMsgEntity,null);
	}

	@Test
	public void findNoAuditAddHousePicList(){
		List<HousePicMsgEntity> list=troyHouseMgtServiceImpl.findNoAuditAddHousePicList("8a9084df5d11ad44015d11c1b55a0089", "8a9084df5d11ad44015d11c2659a0090");
		System.out.println(JsonEntityTransform.Object2Json(list));
	}

}
