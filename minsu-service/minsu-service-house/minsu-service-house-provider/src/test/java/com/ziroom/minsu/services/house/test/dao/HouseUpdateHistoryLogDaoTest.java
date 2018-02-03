/**
 * @FileName: HouseUpdateHistoryLogDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年7月3日 下午3:40:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryLogDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;

/**
 * <p房源基本信息修改记录  测试</p>
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
public class HouseUpdateHistoryLogDaoTest extends BaseTest{
	
	@Resource(name = "house.houseUpdateHistoryLogDao")
	private  HouseUpdateHistoryLogDao houseUpdateHistoryLogDao;

	@Test
	public void saveHouseUpdateHistoryLog() {
		
		
		
		String houseFid = "8a9e989c5d077c10015d077c10c90000";
		String roomFid ="8a9e989c5d077c10015d077c10c90001";
		int rentWay = 1;
		HouseUpdateHistoryLogEntity houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
		
		houseUpdateHistoryLog.setCreaterFid("0000233355");
		houseUpdateHistoryLog.setCreaterType(CreaterTypeEnum.OTHER.getCode());
		houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
		houseUpdateHistoryLog.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldDesc());
		houseUpdateHistoryLog.setHouseFid(houseFid);
		houseUpdateHistoryLog.setRoomFid(roomFid);
		houseUpdateHistoryLog.setRentWay(rentWay);
		houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldName()));
		String fieldPathKey = MD5Util.MD5Encode(houseFid+roomFid+rentWay+houseUpdateHistoryLog.getFieldPath(), "UTF-8");
		houseUpdateHistoryLog.setFieldPathKey(fieldPathKey);
		houseUpdateHistoryLog.setIsText(IsTextEnum.IS_NOT_TEST.getCode());
		houseUpdateHistoryLog.setNewValue("杨东的测试房源55555");
		houseUpdateHistoryLog.setOldValue("大连民族的测试房源666666");
		houseUpdateHistoryLog.setSourceType(HouseSourceEnum.ANDROID.getCode());
		int i = houseUpdateHistoryLogDao.saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
		
		System.out.println(i);
	}

	
	@Test
	public void findListHouseUpdateHistoryLogByKey(){
		
		List<HouseUpdateHistoryLogEntity> list = houseUpdateHistoryLogDao.findListHouseUpdateHistoryLogByKey("335b5dac39e8361fbea2bf3392f302f4");
		System.out.println(list);
		
	}
	
	@Test
	public void findLastOneByKey(){
		HouseUpdateHistoryLogEntity log = houseUpdateHistoryLogDao.findLastOneByKey("335b5dac39e8361fbea2bf3392f302f4");
		System.out.println(log);
		
	}
}
