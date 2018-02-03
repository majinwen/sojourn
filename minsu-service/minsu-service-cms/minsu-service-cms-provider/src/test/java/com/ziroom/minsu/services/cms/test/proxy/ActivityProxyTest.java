package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.entity.cms.ActivityInfoEntity;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.cms.proxy.ActivityProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.ActKindEnum;
import com.ziroom.minsu.valenum.cms.ActTypeEnum;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityProxyTest extends BaseTest{

	@Resource(name = "cms.activityProxy")
	private ActivityProxy activityProxy;
	
	@Test
	public void getActivityBySnTest(){
		String str = activityProxy.getActivityBySn("8a9e9ab35581c38a0155822e4824000a");
		System.err.println(str);
	}



    @Test
    public void getRealUnderwayActivityList(){
        String str = activityProxy.getRealUnderwayActivityList();
        System.err.println(str);
    }

	@Test
	public void getCashbackList_YES(){
		String str = activityProxy.getCashbackList(YesOrNoEnum.YES.getCode());
		System.err.println(str);
	}

	@Test
	public void getCashbackList_NO(){
		String str = activityProxy.getCashbackList(YesOrNoEnum.NO.getCode());
		System.err.println(str);
	}


	@Test
	public void getCashbackList_NULL(){
		String str = activityProxy.getCashbackList(null);
		System.err.println(str);
	}


	
	@Test
	public void getUnderwayActivityListTest(){
		String str = activityProxy.getUnderwayActivityList();
		System.err.println(str);
	}
	
	@Test
	public void getActivityVoListByCondiction(){
		ActivityInfoRequest request = new ActivityInfoRequest();

		String str = activityProxy.getActivityVoListByCondiction(JsonEntityTransform.Object2Json(request));
		System.err.println(str);
	}
	
	@Test
	public void saveActivityInfoTest(){
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		activityInfoEntity.setActivitySn("lisc005");
		activityInfoEntity.setActivityName("lisc免佣金活动5");
		activityInfoEntity.setActivityType(4);
		activityInfoEntity.setActivityStatus(1);
		activityInfoEntity.setStartTime(new Date());
		activityInfoEntity.setEndTime(new Date());

		/* String saveActivity = activityProxy.saveActivity(JsonEntityTransform.Object2Json(activityInfoEntity),"0");
		System.err.println(saveActivity);*/
		
		
		/*String s = "{\"activityName\":\"lisc免佣金活动12\",\"cityCode\":\"0\",\"startTime\":\"2016-06-26 00:00:00\",\"endTime\":\"2016-06-26 16:59:27\",\"activityType\":\"1\",\"userRole\":\"0\"}";
		String str = activityProxy.saveActivity(s, "0");*/
		//System.err.println(str);
	}




    @Test
    public void saveActCoupon(){
        ActivityFullEntity activityInfoEntity = new ActivityFullEntity();
		activityInfoEntity.setActSn("129891");
		activityInfoEntity.setActName("lisc免佣金活动5");
        //sa
        activityInfoEntity.setActKind(ActKindEnum.COUPON.getCode());
        activityInfoEntity.setActType(ActTypeEnum.FREE.getCode());
		activityInfoEntity.setActStatus(1);
		activityInfoEntity.setActStartTime(new Date());
		activityInfoEntity.setActEndTime(new Date());
        activityInfoEntity.setCouponName("1111");
        activityInfoEntity.setCouponNum(11);
        activityInfoEntity.setCouponStartTime(new Date());
        activityInfoEntity.setCouponEndTime(new Date());
        activityInfoEntity.setCreateId("111");
        //activityInfoEntity.setCityCode("0");
		//String str = activityProxy.saveActCoupon(JsonEntityTransform.Object2Json(activityInfoEntity), "0");
		//System.err.println(str);

    }


	
	@Test
	public void updateByActivitySnTest(){
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		activityInfoEntity.setActivitySn("lisc005");
		activityInfoEntity.setActivityName("lisc免佣金活动5修改一下名");
		activityInfoEntity.setActivityStatus(1);
		activityInfoEntity.setStartTime(new Date());
		activityInfoEntity.setEndTime(new Date());
		
		System.err.println(JsonEntityTransform.Object2Json(activityInfoEntity));
		
		String str = activityProxy.updateByActivity(JsonEntityTransform.Object2Json(activityInfoEntity),"");
		System.err.println(str);
		
		
		/*String s = "{\"activitySn\":\"8a9e9ab3558aa9fa01558aa9fa560000\",\"activityName\":\"lisc免佣金活动8\",\"cityCode\":\"0\",\"startTime\":\"2016-06-21 22:00:00\",\"endTime\":\"2016-07-09 22:00:00\",\"activityType\":\"1\",\"userRole\":\"1\"}";
		String str = activityProxy.updateByActivity(s);
		System.err.println(str);*/
		
	}
	
	
	
	@Test
	public void enableActivityTest(){
		
		String res = activityProxy.enableActivity("8a9e988d57a9b3e10157a9b3e11f0000");
		System.err.println(res);
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getActivityByGroupSn(){
		String activityByGroupSn = activityProxy.getActivityByGroupSn("456465456456465");
		System.err.println(activityByGroupSn);
	}
	

	@Test
	public void updateGiftAcByActivityTest(){
		
		ActivityGiftInfoRequest activityGiftInfoRe = new ActivityGiftInfoRequest();
		
		ActivityEntity ac = new ActivityEntity();
		
		ac.setActSn("8a9e988d57aef9320157aefdc6c20006");
		ac.setActName("杨东活动22");
		ac.setActType(30);
		
		List<ActivityGiftItemEntity> listAcGiftItems = new ArrayList<ActivityGiftItemEntity>();
		
		ActivityGiftItemEntity gi = new ActivityGiftItemEntity();
		
		gi.setActSn("8a9e988d57aef9320157aefdc6c20006");
		gi.setFid("8a9e988d57aef9320157aefdc6c20007");
		gi.setGiftCount(1000);
		gi.setIsDel(IsDelEnum.NOT_DEL.getCode());
		listAcGiftItems.add(gi);
		
		activityGiftInfoRe.setAc(ac);
		activityGiftInfoRe.setListAcGiftItems(listAcGiftItems);
	
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityProxy.updateGiftAcByActivity(JsonEntityTransform.Object2Json(activityGiftInfoRe), "13333"));
		
		System.out.println(dto);
	}

	@Test
	public void testlistActFeeConditionForZrp() {
		ZrpActRequest zrpActRequest = new ZrpActRequest();
		zrpActRequest.setProjectId("8a9099cb576ba5c101576ea29c8a0027");
		zrpActRequest.setRoomId("8a9099cb57e703ca0157fba682264426");
		zrpActRequest.setLayoutId("8a90a3ab576ba74701576fc33d730032");
		zrpActRequest.setRentDay(365);
		zrpActRequest.setSignType(1);
		zrpActRequest.setCustomerType(2);
		zrpActRequest.setCommonFee(BigDecimal.valueOf(1511));
		zrpActRequest.setUid("2ffbae0f-661e-46bd-c305-5ac995efd609");
		String s = activityProxy.listActFeeConditionForZrp("{\"customerType\":2,\"signType\":1,\"uid\":\"2ffbae0f-661e-46bd-c305-5ac995efd609\",\"projectId\":\"8a9099cb576ba5c101576ea29c8a0027\",\"layoutId\":\"8a90a3ab576ba74701576fc33d730032\",\"roomId\":\"8a90a3ab57e7054a0157ecfe1f741f55\",\"rentDay\":365,\"originCommonFee\":null,\"commonFee\":1199}");
		System.err.println(s);
	}

	@Test
	public void checkUserInviteStateByUid() {
		InviteCmsRequest inviteCmsRequest =new InviteCmsRequest();
		inviteCmsRequest.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
		inviteCmsRequest.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");

		String result = activityProxy.checkUserInviteStateByUid(JsonEntityTransform.Object2Json(inviteCmsRequest));
		System.err.println(result);
	}
	@Test
	public void checkUserInviteStateByListTest(){
		InviteOrderRequest a = new InviteOrderRequest("loushuai2","AABBCC",1,new Date());
		InviteOrderRequest b = new InviteOrderRequest("loushuai3","AABBCC",1,new Date());
		InviteOrderRequest c = new InviteOrderRequest("loushuai4","AABBCC",1,new Date());
		InviteOrderRequest d = new InviteOrderRequest("loushuai5","AABBCC",1,new Date());
		InviteOrderRequest e = new InviteOrderRequest("loushuai6","AABBCC",1,new Date());
		InviteOrderRequest f = new InviteOrderRequest("loushuai7","AABBCC",1,new Date());
		List<InviteOrderRequest> orderList = new ArrayList<>();
		orderList.add(a);
		orderList.add(b);
		orderList.add(c);
		orderList.add(d);
		orderList.add(e);
		orderList.add(f);

		String result = activityProxy.checkUserInviteStateByList(JsonEntityTransform.Object2Json(orderList));
		System.err.println(result);
	}

	@Test
	public void updateActivityByActSn() throws ParseException {
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setActSn("8a9084df55e7fbc60155e7fbc6df0000");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse("2018-01-23");
		activityEntity.setActEndTime(date);
		String result = activityProxy.updateActivityByActSn(activityEntity.toJsonStr());
		System.err.println(result);
	}
}
