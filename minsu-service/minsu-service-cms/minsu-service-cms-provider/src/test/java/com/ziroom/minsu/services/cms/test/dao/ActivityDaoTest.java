package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.cms.dao.ActivityDao;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.ActKindEnum;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityDaoTest extends BaseTest {

	@Resource(name = "cms.activityDao")
	private ActivityDao activityDao;

	@Test
	public void getSeedActivityLast(){
		ActivityEntity page = activityDao.getSeedActivityLast();
		System.err.println(JsonEntityTransform.Object2Json(page));
	}


    @Test
    public void getActivityVoListByCondictionByCity(){
        ActivityInfoRequest request = new ActivityInfoRequest();
        request.setCityCode("0");
        PagingResult<ActivityVo> page = activityDao.getActivityVoListByCondictionByCity(request);
        System.err.println(JsonEntityTransform.Object2Json(page));
    }



	@Test
	public void getActivityVoListByCondiction(){
		ActivityInfoRequest request = new ActivityInfoRequest();
		PagingResult<ActivityVo> page = activityDao.getActivityVoListByCondiction(request);
		System.err.println(JsonEntityTransform.Object2Json(page));
	}
	
	
	@Test
	public void saveActivityInfoTest() {

		ActivityEntity activityInfoEntity = new ActivityEntity();
		activityInfoEntity.setActSn("lisc001");
		activityInfoEntity.setActName("lisc免佣金活动");
		activityInfoEntity.setActStatus(1);
		activityInfoEntity.setActStartTime(new Date());
		activityInfoEntity.setActEndTime(new Date());
		activityInfoEntity.setGroupSn(UUIDGenerator.hexUUID());
		activityInfoEntity.setActKind(ActKindEnum.COUPON.getCode());
		activityInfoEntity.setActType(ActTypeEnum.FREE.getCode());
		activityInfoEntity.setTimes(10);
		activityInfoEntity.setActStartTime(new Date());
		activityInfoEntity.setActEndTime(new Date());

		int num = activityDao.saveActivity(activityInfoEntity);
		System.err.println(num);
	}

	@Test
	public void getSDLJActivityInfo() throws Exception {
		ActivityEntity page = activityDao.getSDLJActivityInfo();
		System.err.println(JsonEntityTransform.Object2Json(page));
	}


	@Test
	public void listSuitActForZrp() {
		ZrpActRequest zrpActRequest = new ZrpActRequest();
		zrpActRequest.setCustomerType(1);
		zrpActRequest.setSignType(1);
		zrpActRequest.setRentDay(50);
		zrpActRequest.setProjectId("1111");
		zrpActRequest.setLayoutId("222");
		zrpActRequest.setRoomId("2333");
		activityDao.listSuitActForZrp(zrpActRequest);
	}

	/*@Test
	public void updateEnableStatusTest(){
		int num = activityDao.updateEnableStatus("lisc001");
		System.err.println(num);
	}*/
	
	/*@Test
	public void getNormalToUnderwayCount(){
		Long l = activityDao.getNormalToUnderwayCount();
		System.err.println(l);
	}
	
	@Test
	public void updateNormalToUnderwayStatus(){
		int num = activityDao.updateNormalToUnderwayStatus(10);
		System.err.println(num);
	}
	
	@Test
	public void getCouponToUnderwayCount(){
		Long l = activityDao.getCouponToUnderwayCount();
		System.err.println(l);
	}
	
	@Test
	public void updateCouponToUnderwayStatus(){
		int num = activityDao.updateCouponToUnderwayStatus(10);
		System.err.println(num);
	}
	
	@Test
	public void getToEndCount(){
		Long num = activityDao.getToEndCount();
		System.err.println(num);
	}
	
	@Test
	public void updateToEndStatus(){
		int num = activityDao.updateToEndStatus(10);
		System.err.println(num);
	}*/

	@Test
	public void updateByActSn() throws ParseException {
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setActSn("8a9084df55e7fbc60155e7fbc6df0000");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse("2018-01-23");
		activityEntity.setActEndTime(date);
		int num = activityDao.updateByActSn(activityEntity);
		System.err.println(num);
	}
}
