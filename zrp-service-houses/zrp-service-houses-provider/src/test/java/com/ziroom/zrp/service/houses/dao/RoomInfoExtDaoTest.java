/**
 * @FileName: RoomInfoExtDaoTest.java
 * @Package com.ziroom.zrp.service.houses.dao
 * 
 * @author bushujie
 * @created 2018年1月18日 下午5:11:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

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
public class RoomInfoExtDaoTest extends BaseTest{
	
	@Resource(name="houses.roomInfoExtDao")
	private RoomInfoExtDao roomInfoExtDao;
	
	@Test
	public void insertRoomInfoExtTest(){
		RoomInfoExtEntity roomInfoExtEntity=new RoomInfoExtEntity();
		roomInfoExtEntity.setFid(UUIDGenerator.hexUUID());
		roomInfoExtEntity.setRoomFid("8a9099cb57e703ca0157f9ffba1a2e34");
		roomInfoExtEntity.setIsBindAmmeter(1);
		roomInfoExtDao.insertRoomInfoExt(roomInfoExtEntity);
	}
	
	@Test
	public void getRoomInfoExtByRoomFid(){
		RoomInfoExtEntity roomInfoExtEntity=roomInfoExtDao.getRoomInfoExtByRoomFid("8a9099cb57e703ca0157f9ffba1a2e34");
		System.err.println(JsonEntityTransform.Object2Json(roomInfoExtEntity));
	}

	@Test
	public void getAllRoomOfBindingWaterMeter() {
		List<RoomInfoEntity> allRoomOfBindingWaterMeter = roomInfoExtDao.getAllRoomOfBindingWaterMeter();
		System.err.println(JsonEntityTransform.Object2Json(allRoomOfBindingWaterMeter));
	}
}
