/**
 * @FileName: ActivityRecordProxyTest.java
 * @Package com.ziroom.minsu.services.cms.test.proxy
 * 
 * @author yd
 * @created 2016年10月9日 下午4:44:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.proxy;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.services.cms.dto.BindActivityRequest;
import com.ziroom.minsu.valenum.cms.FreeTypeEnum;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.proxy.ActivityRecordProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.IsPickUpEnum;

/**
 * <p>TODO</p>
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
public class ActivityRecordProxyTest extends BaseTest{

	
	@Resource(name = "cms.activityRecordProxy")
	private ActivityRecordProxy activityRecordProxy;




	@Test
	public void saveFreeComm(){
		ActivityFreeEntity request = new ActivityFreeEntity();
		request.setActCode("cdde");
		request.setActName("mame");
		request.setUid("uid");
		request.setCreateId("1231");
		request.setFreeType(FreeTypeEnum.SEED_FREE.getCode());
		String aa = activityRecordProxy.saveFreeComm(JsonEntityTransform.Object2Json(request),29);
		System.out.println(aa);
	}


	@Test
	public void updateAddress(){

		String aa = activityRecordProxy.updateAddress("8a9e988d57af87080157af8708710004","aaa","asdad");

		System.out.println(aa);
	}


	@Test
	public void exchangeActivity4Record(){

		BindActivityRequest activityRecord = new BindActivityRequest();
		activityRecord.setGroupSn("8a9e988d57a8acd60157a8acd6b10002");
		activityRecord.setUid("2342342");
        activityRecord.setMobile("1891112354");
		String aa = activityRecordProxy.exchangeActivity4Record(JsonEntityTransform.Object2Json(activityRecord));

		System.out.println(aa);
	}



	@Test
	public void checkActivity4RecordTest(){

		BindActivityRequest activityRecord = new BindActivityRequest();
		activityRecord.setGroupSn("8a9e988d57a82ae20157a82ae2970002");
		activityRecord.setMobile("18701482472");
		String aa = activityRecordProxy.checkActivity4Record(JsonEntityTransform.Object2Json(activityRecord));

		System.out.println(aa);
	}


	@Test
	public void saveActivityRecordTest(){
		
		ActivityRecordEntity activityRecord = new ActivityRecordEntity();

		activityRecord.setActSn(UUIDGenerator.hexUUID());
		activityRecord.setFid(UUIDGenerator.hexUUID());
		activityRecord.setGroupSn(UUIDGenerator.hexUUID());
		activityRecord.setIsPickUp(IsPickUpEnum.NO_PICK_UP.getCode());
		activityRecord.setUserAdress("北京 朝阳 芳华园 113号");
		activityRecord.setGiftFid(UUIDGenerator.hexUUID());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityRecordProxy.saveActivityRecord(JsonEntityTransform.Object2Json(activityRecord)));
		
		System.out.println(dto);
	}
}
