/**
 * @FileName: HouseUpdateFieldAuditManagerDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年6月30日 下午4:36:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;

import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>审核字段管理 测试</p>
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
public class HouseUpdateFieldAuditManagerDaoTest extends BaseTest {

	
	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;
	@Test
	public void saveHouseUpdateFieldAuditManager() {
//		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager = new HouseUpdateFieldAuditManagerEntity();
//		houseUpdateFieldAuditManager.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldName()));
//		houseUpdateFieldAuditManager.setFid(MD5Util.MD5Encode(houseUpdateFieldAuditManager.getFieldPath(), "UTF-8"));
//		houseUpdateFieldAuditManager.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldDesc());
//
//		houseUpdateFieldAuditManager.setCreaterFid("001");
//		int i = houseUpdateFieldAuditManagerDao.saveHouseUpdateFieldAuditManager(houseUpdateFieldAuditManager);
//		System.out.println(i);


//		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager = new HouseUpdateFieldAuditManagerEntity();
//		houseUpdateFieldAuditManager.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldName()));
//		houseUpdateFieldAuditManager.setFid(MD5Util.MD5Encode(houseUpdateFieldAuditManager.getFieldPath(), "UTF-8"));
//		houseUpdateFieldAuditManager.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldDesc());
//
//		houseUpdateFieldAuditManager.setCreaterFid("001");
//		int i = houseUpdateFieldAuditManagerDao.saveHouseUpdateFieldAuditManager(houseUpdateFieldAuditManager);
//		System.out.println(i);

//		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager = new HouseUpdateFieldAuditManagerEntity();
//		houseUpdateFieldAuditManager.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseExtEntity.class,HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldName()));
//		houseUpdateFieldAuditManager.setFid(MD5Util.MD5Encode(houseUpdateFieldAuditManager.getFieldPath(), "UTF-8"));
//		houseUpdateFieldAuditManager.setFieldDesc(HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldDesc());
//
//		houseUpdateFieldAuditManager.setCreaterFid("001");
//		int i = houseUpdateFieldAuditManagerDao.saveHouseUpdateFieldAuditManager(houseUpdateFieldAuditManager);
//		System.out.println(i);

		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager = new HouseUpdateFieldAuditManagerEntity();
		System.err.println(HouseUpdateLogEnum.House_Base_Msg_House_Type.getFieldPath());
		houseUpdateFieldAuditManager.setFieldPath(HouseUpdateLogEnum.House_Base_Msg_House_Type.getFieldPath());
		houseUpdateFieldAuditManager.setFid(MD5Util.MD5Encode(houseUpdateFieldAuditManager.getFieldPath(), "UTF-8"));
		houseUpdateFieldAuditManager.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Type.getFieldDesc());

		houseUpdateFieldAuditManager.setCreaterFid("001");
		int i = houseUpdateFieldAuditManagerDao.saveHouseUpdateFieldAuditManager(houseUpdateFieldAuditManager);
		
		System.out.println(i);


	}

	@Test
	public void findHouseUpdateFieldAuditManagerByType(){
		List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
		System.out.print(houseUpdateFieldAuditManagerEntities.toString());
	}

}
