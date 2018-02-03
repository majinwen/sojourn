package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.cms.proxy.ActCouponProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActCouponProxyTest extends BaseTest {
	
	@Resource(name = "cms.actCouponProxy")
	private ActCouponProxy actCouponProxy;

    public static void main(String[] args) {
        for (int i = 0; i < 163; i++) {
            System.out.println(UUIDGenerator.hexUUID());
        }
    }

	@Test
	public void checkActivityByMobile(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setGroupSn("456465456456465");
		request.setMobile("189111235451");
		request.setSourceType(1);
		String result = actCouponProxy.checkActivityByMobile(JsonEntityTransform.Object2Json(request));
		System.err.println(result);
	}





	@Test
	public void pullActivityByMobile(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setGroupSn("456465456456465");
		request.setMobile("189111235451");
		request.setSourceType(1);
		String result = actCouponProxy.pullActivityByMobile(JsonEntityTransform.Object2Json(request));
		System.err.println(result);
	}



	@Test
	public void getDefaultCouponByUid(){
		CheckCouponRequest request = new CheckCouponRequest();
		request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		request.setCityCode("110100");
		request.setPrice(109);
		request.setStartTime("2016-11-17 14:00:00");
		request.setEndTime("2016-11-19 14:00:00");
		Map<String,Integer>  priceMap = new HashMap<>();
		priceMap.put("2016-11-17",10000);
		priceMap.put("2016-11-18",10000);
		request.setAllPriceMap(priceMap);
		request.setRentCut(0.5);

		String result = actCouponProxy.getDefaultCouponByUid(JsonEntityTransform.Object2Json(request));
		System.err.println(result);
	}


    @Test
    public void getCouponListCheckByUid(){
        CheckCouponRequest request = new CheckCouponRequest();
        request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
        request.setCityCode("110100");
        request.setPrice(109);
        String result = actCouponProxy.getCouponListCheckByUid(JsonEntityTransform.Object2Json(request));
        System.err.println(result);
    }


    @Test
    public void getCouponListByUid(){
        OutCouponRequest request = new OutCouponRequest();
        request.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");

        String aa = "{\"limit\":10,\"status\":\"1\",\"page\":1,\"uid\":\"22d6ef61-4ec8-9a83-99ee-91528e36b928\"}";
        String result = actCouponProxy.getCouponListByUid(aa);
        System.err.println(result);
    }

	
	@Test
	public void getCouponBySnTest(){
		
		String result = actCouponProxy.getCouponBySn("123");
		System.err.println(result);
	}
	
	@Test
	public void getCouponListByActSnTest(){
		
		ActCouponRequest request = new ActCouponRequest();
		request.setActSn("ziroom");
		String result = actCouponProxy.getCouponListByActSn(JsonEntityTransform.Object2Json(request));
		System.err.println(result);
	}
	
	@Test
	public void getActCouponOrderVoByCouponSnTest(){
		String str = actCouponProxy.getActCouponUserVoByCouponSn("123");
		System.err.println("----:"+str);
	}
	
	
	@Test
	public void bindCouponTest(){
		/*BindCouponRequest request = new BindCouponRequest();
		request.setCouponSn("123");
		request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
		String str = actCouponProxy.bindCoupon(JsonEntityTransform.Object2Json(request));
		System.err.println(str);*/
		
		
		String req = "{\"uid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"couponSn\":\"afi111OAPKDO\"}";
		String str = actCouponProxy.bindCoupon(req);
		System.err.println(str);
	}
	
	@Test
	public void syncCouponStatusTest(){
		String paramJson = "[{\"id\":null,\"actSn\":null,\"couponSn\":\"124\",\"couponName\":null,\"couponStatus\":null,\"couponSource\":null,\"cityCode\":null,\"actUser\":null,\"actType\":null,\"actLimit\":null,\"actCut\":null,\"couponStartTime\":null,\"couponEndTime\":null,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":null,\"createTime\":null,\"lastModifyDate\":null,\"isDel\":null,\"oldStatus\":null},{\"id\":null,\"actSn\":null,\"couponSn\":\"124\",\"couponName\":null,\"couponStatus\":null,\"couponSource\":null,\"cityCode\":null,\"actUser\":null,\"actType\":null,\"actLimit\":null,\"actCut\":null,\"couponStartTime\":null,\"couponEndTime\":null,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":null,\"createTime\":null,\"lastModifyDate\":null,\"isDel\":null,\"oldStatus\":null},{\"id\":null,\"actSn\":null,\"couponSn\":\"124\",\"couponName\":null,\"couponStatus\":null,\"couponSource\":null,\"cityCode\":null,\"actUser\":null,\"actType\":null,\"actLimit\":null,\"actCut\":null,\"couponStartTime\":null,\"couponEndTime\":null,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":null,\"createTime\":null,\"lastModifyDate\":null,\"isDel\":null,\"oldStatus\":null}]";
		String result = actCouponProxy.syncCouponStatus(paramJson);
		System.err.println(result);
	}

	@Test
	public void bindActCouponTest(){
		BindCouponRequest request = new BindCouponRequest();
		request.setCouponSn("afi111");
		request.setUid("a06f8111111");
		String str = actCouponProxy.exchangeCode(JsonEntityTransform.Object2Json(request));
		System.err.println(str);
		System.err.println(str);
		System.err.println(str);
		
	}




	@Test
	public void exchangeGroupTest(){
		BindCouponRequest request = new BindCouponRequest();
		request.setGroupSn("111");
		request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190111");
		String str = actCouponProxy.exchangeGroup(JsonEntityTransform.Object2Json(request));
		System.err.println(str);
		System.err.println(str);
		System.err.println(str);

	}

	@Test
	public void testbindCouponByPhoneNums(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setActSn("cellsd");
		request.setMobile("18811366591");
		String s = actCouponProxy.bindCouponByPhoneNums(JsonEntityTransform.Object2Json(request));
		System.err.println(s);
	}

	@Test
	public void testbindCouponByPhoneAndOrder(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setActSn("cellsd");
		request.setMobile("18811366591");
		request.setOrderSn("sdfsdfdsfdfsdf");
		String s = actCouponProxy.bindCouponByPhoneAndOrder(JsonEntityTransform.Object2Json(request));
		System.err.println(s);
	}
	@Test
	public void testcountMobileGroupSns(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setMobile("18811366591");
		List<String> list = new ArrayList<>();
		list.add("51LIBAO1");
		list.add("51LIBAO2");
		list.add("51LIBAO3");
		request.setGroupSns(list);
		String s = actCouponProxy.countMobileGroupSns(JsonEntityTransform.Object2Json(request));
		System.err.println(s);
	}


	@Test
	public void testhasChanceToGetCoupon(){
		MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
		mobileCouponRequest.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		mobileCouponRequest.setActSn("FYLJ10");
		String s = actCouponProxy.hasChanceToGetCoupon(JsonEntityTransform.Object2Json(mobileCouponRequest));
		System.err.println(s);
	}


	@Test
	public void testCancelCoupon() {
		CancelCouponDto cancelCouponDto = new CancelCouponDto();
		cancelCouponDto.setEmpCode("3333");
		cancelCouponDto.setEmpName("yanb");
		cancelCouponDto.setCouponSn("test2.2");
		cancelCouponDto.setRemark("NewJunitTestProxy1.2");
		actCouponProxy.cancelCoupon(JsonEntityTransform.Object2Json(cancelCouponDto));

	}

}
