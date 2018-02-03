/**
 * @FileName: HouseUpdateFieldAuditNewlogDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年7月3日 下午2:59:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.house.entity.HouseAuditVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>记录 当前需要审核 字段的最新审核信息 测试</p>
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
public class HouseUpdateFieldAuditNewlogDaoTest extends BaseTest{


	@Resource(name = "house.houseUpdateFieldAuditNewlogDao")
	private HouseUpdateFieldAuditNewlogDao houseUpdateFieldAuditNewlogDao;


	@Test
	public void saveHouseUpdateFieldAuditNewlog() {


		String houseFid = UUIDGenerator.hexUUID();
		String roomFid =UUIDGenerator.hexUUID();
		int rentWay = 1;
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = new HouseUpdateFieldAuditNewlogEntity();

		houseUpdateFieldAuditNewlog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldName()));
		houseUpdateFieldAuditNewlog.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldDesc());
		houseUpdateFieldAuditNewlog.setFid(MD5Util.MD5Encode(houseFid+roomFid+rentWay+houseUpdateFieldAuditNewlog.getFieldPath(), "UTF-8"));
		houseUpdateFieldAuditNewlog.setCreaterFid("001");
		houseUpdateFieldAuditNewlog.setCreaterType(CreaterTypeEnum.OTHER.getCode());
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
		houseUpdateFieldAuditNewlogDao.saveHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlog);
	}

	@Test
	public void findHouseUpdateFieldAuditNewlogByFid(){
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByFid("d345123c02760d03ec6da151dc16c035");

		if(!Check.NuNObj(houseUpdateFieldAuditNewlog)){
			System.out.println(houseUpdateFieldAuditNewlog.toJsonStr());
		}
	}


	@Test
	public void updateHouseUpdateFieldAuditNewlogByFid(){


		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = new HouseUpdateFieldAuditNewlogEntity();

		houseUpdateFieldAuditNewlog.setFid("d345123c02760d03ec6da151dc16c035");
		houseUpdateFieldAuditNewlog.setCreaterFid("002222221");
		houseUpdateFieldAuditNewlog.setCreaterType(CreaterTypeEnum.GUARD.getCode());
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.REJECTED.getCode());

		int i = houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByFid(houseUpdateFieldAuditNewlog);
		System.out.println(i);

	}

	@Test
	public void saveAuditFieldList() throws Exception{
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid("8a9084df5d9ba997015d9bac79dc0006");
		houseUpdateFieldAuditNewlogEntity.setRentWay(0);
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);

		HouseBaseMsgEntity houseBaseMsgEntity = new HouseBaseMsgEntity();
		HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
		HouseDescEntity houseDescEntity = new HouseDescEntity();
		HouseConfMsgEntity houseConfMsgEntity = new HouseConfMsgEntity();
		HousePhyMsgEntity housePhyMsgEntity = new HousePhyMsgEntity();
		HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
		HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();

		for (HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVoList){
			String fieldPath = houseFieldAuditLogVo.getFieldPath();
			String classFullName = fieldPath.substring(0,fieldPath.lastIndexOf(".")).trim();
			String fieldName = fieldPath.substring(fieldPath.lastIndexOf(".")+1);
			String methodName = "set"+ fieldName.substring(0,1).toUpperCase()
					+fieldName.substring(1,fieldName.length());
			Class clazz = Class.forName(classFullName);
			if(!Check.NuNObj(clazz)){
				Field field = clazz.getDeclaredField(fieldName);
				Class<?> type = field.getType();
				Method method = clazz.getMethod(methodName,type);
				if(clazz == HouseBaseMsgEntity.class){
					method.invoke(houseBaseMsgEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HouseBaseExtEntity.class){
					method.invoke(houseBaseExtEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HouseDescEntity.class){
					method.invoke(houseDescEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HouseConfMsgEntity.class){
					method.invoke(houseConfMsgEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HousePhyMsgEntity.class){
					method.invoke(housePhyMsgEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HouseRoomMsgEntity.class){
					method.invoke(houseRoomMsgEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}else if(clazz == HouseRoomExtEntity.class){
					method.invoke(houseRoomExtEntity,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
				}
			}
		}

		if(!Check.NuNObj(houseBaseMsgEntity)){
			System.out.print(houseBaseMsgEntity.toJsonStr());
		}


	}


	@Test
	public void test() throws Exception{
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlogEntity.setHouseFid("8a9084df5d9ba997015d9bac79dc0006");
		houseUpdateFieldAuditNewlogEntity.setRentWay(0);
		houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
		List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);

		HouseAuditVo houseAuditVo = new HouseAuditVo();
		if(Check.NuNObj(houseAuditVo.getHouseBaseMsgEntity())){
			System.out.print("new 出来的都是空对象，哈哈");
		}

		for (HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVoList){
			String fieldPath = houseFieldAuditLogVo.getFieldPath();
			String classFullName = fieldPath.substring(0,fieldPath.lastIndexOf(".")).trim();
			String className = classFullName.substring(classFullName.lastIndexOf(".")+1);
			String fieldName = fieldPath.substring(fieldPath.lastIndexOf(".")+1);
			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String houseAuditVoFieldName = className.substring(0,1).toLowerCase()+className.substring(1);
			String houseAuditVoMethodName = "get"+houseAuditVoFieldName.substring(0,1).toUpperCase()+houseAuditVoFieldName.substring(1);
			Class clazz = Class.forName(classFullName);
			Class houseAuditVoClazz = HouseAuditVo.class;

			if(!Check.NuNObj(clazz)){
				Field field = clazz.getDeclaredField(fieldName);
				Class<?> type = field.getType();
				Method method = clazz.getMethod(methodName,type);
				Method houseAuditVoMethod = houseAuditVoClazz.getMethod(houseAuditVoMethodName);
				Object obj = houseAuditVoMethod.invoke(houseAuditVo);
				method.invoke(obj,StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
			}
		}
		System.out.print(houseAuditVo.toJsonStr());
	}


}
