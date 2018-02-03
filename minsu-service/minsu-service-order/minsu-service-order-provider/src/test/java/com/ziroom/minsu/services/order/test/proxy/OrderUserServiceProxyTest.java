package com.ziroom.minsu.services.order.test.proxy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.services.order.dto.CanclOrderRequest;
import com.ziroom.minsu.services.order.dto.CheckOutOrderRequest;
import com.ziroom.minsu.services.order.dto.ConfirmOtherMoneyRequest;
import com.ziroom.minsu.services.order.dto.CreateOrderRequest;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseCountRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.services.order.dto.NeedPayFeeRequest;
import com.ziroom.minsu.services.order.dto.OrderDetailRequest;
import com.ziroom.minsu.services.order.proxy.CancelOrderServiceProxy;
import com.ziroom.minsu.services.order.proxy.OrderUserServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.ActivityTypeEnum;

/**
 * <p>用户大代理层的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class OrderUserServiceProxyTest extends BaseTest {

    @Resource(name = "order.orderUserServiceProxy")
    private OrderUserServiceProxy orderUserServiceProxy;
    @Resource(name = "order.cancelOrderServiceProxy")
    private CancelOrderServiceProxy cancelOrderServiceProxy;





    @Test
    public void TestfindHouseSnapshotByOrderSn(){

        String orderHouseSnapshotEntity = orderUserServiceProxy.findHouseSnapshotByOrderSn("16051176ZD432U160931");

        System.out.println(orderHouseSnapshotEntity);

    }
    


    @Test
    public void getNeedPayFeeTest(){
    	// {"code":0,"msg":"","data":{"depositMoney":300,"discountMoney":200,"userCommission":18,"couponMoney":0,"needPay":2118,"cost":2000,"payTime":null}}
    	/*NeedPayFeeRequest request = new NeedPayFeeRequest();
    	String startTime = "2016-08-14";
		String endTime = "2016-08-24";
		
		request.setRentWay(0);
		request.setUserUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		request.setFid("8a9e9a9a548a061301548a67e6300029");
		request.setActivitys(new ArrayList<ActivityEntity>());
		try {
			request.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			request.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	System.err.println(JsonEntityTransform.Object2Json(request));
    	
    	System.err.println(this.orderUserServiceProxy.getNeedPayFee(JsonEntityTransform.Object2Json(request)));*/
    	
    	
    	// String json = "{\"endTime\":\"2016-05-27\",\"fid\":\"8a9e9a9a548a061301548a0613760001\",\"rentWay\":0,\"startTime\":\"2016-05-09\"}";
    	//String json = "{\"globalConfigEntity\":{\"maxNoPayNumber\":null},\"userUid\":\"9afeae98-56ff-0c35-77cf-8624b2e1efae\",\"couponSn\":null,\"fid\":\"8a9084df54bacbd90154bb2f1cad008b\",\"rentWay\":0,\"startTime\":1464307200000,\"endTime\":1464739200000}";
    	String json = "{\"globalConfigEntity\":{\"maxNoPayNumber\":null},\"userUid\":\"f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6\",\"couponSn\":\"146\",\"fid\":\"8a9e9a9a548a061301548a67e6300029\",\"rentWay\":0,\"startTime\":1471104000000,\"endTime\":1471968000000,\"sourceType\":null,\"tripPurpose\":null,\"actCouponUserEntity\":{\"id\":26,\"actSn\":\"免天不限制入住和离开时间的活动\",\"couponSn\":\"146\",\"couponName\":\"优惠券146\",\"couponStatus\":2,\"couponSource\":1,\"cityCode\":\"110100\",\"actUser\":0,\"actType\":4,\"actLimit\":50000,\"actMax\":0,\"actCut\":4,\"couponStartTime\":1466228023000,\"couponEndTime\":1469943223000,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":\"1\",\"createTime\":1466059485000,\"lastModifyDate\":1468388349000,\"isDel\":0,\"uid\":\"f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6\",\"customerMobile\":null,\"usedTime\":null,\"oldStatus\":null},\"activitys\":[{\"id\":1,\"actSn\":\"ziroom\",\"actName\":\"ziroom\",\"actSource\":1,\"cityCode\":\"0\",\"actStatus\":3,\"roleCode\":\"0\",\"actType\":1,\"actLimit\":0,\"actMax\":0,\"actCut\":null,\"actStartTime\":1468326163000,\"actEndTime\":1469881367000,\"isCheckTime\":0,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":\"1\",\"createTime\":1468326045000,\"lastModifyDate\":1468388613000,\"isDel\":0},{\"id\":2,\"actSn\":\"lisc001\",\"actName\":\"lisc免佣金活动\",\"actSource\":1,\"cityCode\":\"0\",\"actStatus\":2,\"roleCode\":null,\"actType\":1,\"actLimit\":0,\"actMax\":0,\"actCut\":null,\"actStartTime\":1468380830000,\"actEndTime\":1469763230000,\"isCheckTime\":0,\"checkInTime\":null,\"checkOutTime\":null,\"createId\":\"1\",\"createTime\":1468380704000,\"lastModifyDate\":1468388610000,\"isDel\":0}]}";





        json ="{\"globalConfigEntity\":{\"maxNoPayNumber\":null},\"userUid\":\"e0a0f779-9117-6283-84e1-43e0be20ecf4\",\"couponSn\":\"\",\"fid\":\"8a9084df550d9bdd01550db2ec340107\",\"rentWay\":1,\"startTime\":1483488000000,\"endTime\":1483660800000,\"sourceType\":null,\"tripPurpose\":null,\"actCouponUserEntity\":null,\"lanFree\":{\"freeFlag\":0,\"freeCode\":null,\"freeName\":null},\"activitys\":null,\"versionCode\":null}";
    	String result = this.orderUserServiceProxy.getNeedPayFee(json);
    	System.err.println(result);
    }

    @Test
    public void needPay() throws ParseException {
        NeedPayFeeRequest request = new NeedPayFeeRequest();

        String uid = "0f163457-ad6a-09ce-d5de-de452a251cf6";
        request.setUserUid(uid);
        request.setSourceType(3);

        request.setRentWay(0);
        request.setFid("8a90a2d4549ac7990154a05d8fac01a9");
        request.setSourceType(1);
        request.setUserUid(uid);
        
        List<ActivityVo> activitys = new ArrayList<>();
        ActivityVo ac = new ActivityVo();

        ac.setActSn("0000000057e64d2a0157e64d2aed0000");
        ac.setActName("首单立减测试");
        //ac.setCityCode("0");
       // ac.setRoleCode("123");
        ac.setActType(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode());
        ac.setActStartTime(DateUtil.parseDate("2017-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        ac.setActEndTime(DateUtil.parseDate("2018-02-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        ac.setActCut(5000);
        activitys.add(ac);
        request.setActivitys(activitys);
        request.setVersionCode(100020);

        /*ActCouponUserEntity actCouponUserEntity = new ActCouponUserEntity();
        actCouponUserEntity.setActSn("GROUP2");
        actCouponUserEntity.setActType(1);
        actCouponUserEntity.setActCut(30000);
        actCouponUserEntity.setActLimit(2000);
        */


        request.setStartTime(DateUtil.parseDate("2017-08-14", "yyyy-MM-dd"));
        request.setEndTime(DateUtil.parseDate("2017-08-15", "yyyy-MM-dd"));

        String result = this.orderUserServiceProxy.needPay(JsonEntityTransform.Object2Json(request));
        System.err.println(result);
    }

    @Test
    public void  needPayForLan()throws ParseException {
    	NeedPayFeeRequest request = new NeedPayFeeRequest();
        request.setUserUid("e5fdd6a9-978c-4ab0-b519-8a18c3ce4d2b");
        request.setSourceType(3);

        request.setFid("8a90a2d458be6b220158be77ac270008");
        request.setRentWay(0);
        request.setStartTime(DateUtil.parseDate("2017-04-13", "yyyy-MM-dd"));
        request.setEndTime(DateUtil.parseDate("2017-04-29", "yyyy-MM-dd"));

        String result = this.orderUserServiceProxy.needPayForLan(JsonEntityTransform.Object2Json(request));
        System.err.println(result);
    }
    @Test
    public void TestcreatOrder4Ac1() throws ParseException {

        CreateOrderRequest request = new CreateOrderRequest();

        String uid = "a119dd2f-2e0b-4a9f-a116-9f2279eb5224";
        request.setRentWay(1);
        request.setFid("8a90a2d355bde4af0155c313f7620022");
        request.setSourceType(2);
        request.setUserUid(uid);
        request.setUserTel("13214725804");
        request.setUserName("皮卡丘");

        List<String> tenantFids = new ArrayList<>();
        tenantFids.add("8a90a2d45d16ef39015d17022aee00b4");
        request.setTenantFids(tenantFids);
        request.setStartTime(DateUtil.parseDate("2017-08-08", "yyyy-MM-dd"));
        request.setEndTime(DateUtil.parseDate("2017-09-10", "yyyy-MM-dd"));
        request.setVersionCode(100020);
        String rst = orderUserServiceProxy.createOrder(JsonEntityTransform.Object2Json(request));
        System.err.println(JsonEntityTransform.Object2Json(rst));
    }



    @Test
    public void TestcreatOrder4Ac() throws ParseException {

       CreateOrderRequest request = new CreateOrderRequest();

        String uid = "664524c5-6e75-ee98-4e0d-667d38b9eee1";

        List<ActivityVo> activitys = new ArrayList<>();
        ActivityVo ac = new ActivityVo();

        ac.setActSn("8a9e989e5c7b8b4f015c7b8b4fa00000");
        ac.setActName("首单立减测试");
        //ac.setCityCode("0");
       // ac.setRoleCode("123");
        ac.setActType(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode());
        ac.setActStartTime(DateUtil.parseDate("2017-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        ac.setActEndTime(DateUtil.parseDate("2018-02-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        ac.setActCut(50000);
        activitys.add(ac);
        request.setActivitys(activitys);

        request.setRentWay(1);
        request.setFid("8a9084df556cd72c01556d0eaf1b000d");
        request.setSourceType(1);
        request.setUserUid(uid);
        request.setUserTel("18911123545");
        request.setUserName("杨东测试首单");
        request.setVersionCode(100009);
        
        List<String> tenantFids = new ArrayList<>();
        tenantFids.add("0000000054a411ca0154a55c7d6d059a");
        tenantFids.add("0000000054a411ca0154a52f63fc0499");
        request.setTenantFids(tenantFids);
        request.setStartTime(DateUtil.parseDate("2017-06-12", "yyyy-MM-dd"));
        request.setEndTime(DateUtil.parseDate("2017-06-14", "yyyy-MM-dd"));

        String rst = orderUserServiceProxy.createOrder(JsonEntityTransform.Object2Json(request));
        System.err.println(JsonEntityTransform.Object2Json(rst));
    }



    @Test
    public void TestcreatOrder4Coupon() throws ParseException {

        //{"endTime":"2016-05-06","fid":"8a9e9aae5419cc22015419cc250a0002","orderSource":"3","rentWay":1,"startTime":"2016-05-04","tenantFids":[],"userName":"杨东","userTel":"18701482472","userUid":"8a9e9a9e543d23f901543d23f9e90000"}
        CreateOrderRequest request = new CreateOrderRequest();

        String uid = "52daaf35-0cd9-13be-a64d-88abef8fd973";
        ActCouponUserEntity ac = new ActCouponUserEntity();
        String sn  = "300";


        List<ActivityVo> activitys = new ArrayList<>();
        ActivityVo acVo = new ActivityVo();

        acVo.setActSn("8a9e989e5c7b8b4f015c7b8b4fa00000");
        acVo.setActName("首单立减测试");
      /*  ac.setCityCode("0");
        ac.setRoleCode("123");*/
        acVo.setActType(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode());
        acVo.setActStartTime(DateUtil.parseDate("2017-06-06 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        acVo.setActEndTime(DateUtil.parseDate("2018-02-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        acVo.setActCut(50000);
        activitys.add(acVo);
        request.setActivitys(activitys);
        
        
        ac.setActType(4);
        ac.setActSn("200");
        ac.setCouponSn(sn);
        ac.setCouponStartTime(DateUtil.parseDate("2017-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        ac.setCouponEndTime(DateUtil.parseDate("2017-08-15 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        //ac.setCityCode("110100");
        ac.setCouponStatus(2);
        ac.setActCut(30000+36500);
        ac.setActLimit(500);
        ac.setCouponName("优惠券141");
        ac.setUid(uid);
        
        ActivityCityEntity city = new ActivityCityEntity();
        city.setActSn("150");
        city.setCityCode("0");
        ac.getCityList().add(city);
        ac.setIsLimitHouse(0);
        request.setActCouponUserEntity(ac);
        request.setCouponSn(sn);

        request.setRentWay(1);
        request.setFid("8a9084df556cd72c01556d0eaf1b000d");
        request.setSourceType(3);
        request.setUserUid(uid);
        request.setUserTel("18911123545");
        request.setUserName("杨东测试首单");

        List<String> tenantFids = new ArrayList<>();
        tenantFids.add("0000000054a411ca0154a55c7d6d059a");
        tenantFids.add("0000000054a411ca0154a52f63fc0499");
        request.setTenantFids(tenantFids);
        request.setStartTime(DateUtil.parseDate("2017-06-12", "yyyy-MM-dd"));
        request.setEndTime(DateUtil.parseDate("2017-06-14", "yyyy-MM-dd"));


        String rst = orderUserServiceProxy.createOrder(JsonEntityTransform.Object2Json(request));
        System.err.println(JsonEntityTransform.Object2Json(rst));
    }


    @Test
    public void TestgetOrderPrices() {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderSn("8a9e9cd253d0b29d0153d0b29d460001");
        request.setUid("testxiaohua");
        String rst = orderUserServiceProxy.getOrderPrices(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestcancleOrderUid_null() {
        try {
            CanclOrderRequest request = new CanclOrderRequest();
            request.setOrderSn("1612200Q30Y61N103658");
            request.setUid("588e49b7-9ad9-43f8-98fc-b9f6974e25c3");
            String rst = orderUserServiceProxy.cancleOrder(JsonEntityTransform.Object2Json(request));
            System.out.println(JsonEntityTransform.Object2Json(rst));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void TestcancleOrderSn_null() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("16051838F3XV4W213126");
        request.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
        String rst = orderUserServiceProxy.cancleOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestcancleOrder() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("1706074KQQCOJ4114207");
        request.setUid("52daaf35-0cd9-13be-a64d-88abef8fd973");
        String rst = orderUserServiceProxy.cancleOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestinitCancleOrder() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("161212N5JKX8XC162509");
        request.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        String rst = orderUserServiceProxy.initCancleOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }
    
    @Test
    public void TestgetHouseLockInfo() throws ParseException {
    	HouseLockRequest lockRequest = new HouseLockRequest();
		lockRequest.setFid("8a9e9aae5419cc22015419cc250a0002");
		lockRequest.setRentWay(0);
		lockRequest.setStarTime(DateUtil.parseDate("2016-4-22", "yyyy-MM-dd"));
		lockRequest.setEndTime(DateUtil.parseDate("2016-5-21", "yyyy-MM-dd"));
    	String rst = orderUserServiceProxy.getHouseLockInfo(JsonEntityTransform.Object2Json(lockRequest));
    	System.err.println(JsonEntityTransform.Object2Json(rst));
    }
    
    @Test
    public void TestlockHouse() {
        LockHouseRequest paramDto = new LockHouseRequest();
		paramDto.setHouseFid("8a9e9aae5419cc22015419cc250a00021");
        paramDto.setRoomFid("asdasdas");
		paramDto.setRentWay(0);
		paramDto.setLockType(2);
        List<Date> aa = new ArrayList<>();
        aa.add(new Date());
		paramDto.setLockDayList(aa);
    	String rst = orderUserServiceProxy.lockHouse(JsonEntityTransform.Object2Json(paramDto));
    	System.err.println(JsonEntityTransform.Object2Json(rst));
    }


    @Test
    public void TestInitCheckOutOrderT(){
        // 初始化退房
//    	CheckOutOrderRequest checkOutRequest = new CheckOutOrderRequest();
//		checkOutRequest.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
//		checkOutRequest.setOrderSn("160419V6621S1J172304");
//		checkOutRequest.setUid("uid");
//		checkOutRequest.setOrderSn("1605042NL8KYP8111215");
//		String rst = orderUserServiceProxy.initCheckOutOrder(JsonEntityTransform.Object2Json(checkOutRequest));
//      System.err.println(JsonEntityTransform.Object2Json(rst));

        String json = "{\"uid\":\"1914e6a6-bcaa-4cc7-bbb0-0f96e42c2bb9\",\"orderSn\":\"161219Q88X1Y98174826\",\"landlordUid\":null}";
        String rst = orderUserServiceProxy.initCheckOutOrder(json);
        System.err.println(JsonEntityTransform.Object2Json(rst));
    }


    @Test
    public void TestInitCheckOutOrder(){
    	// 初始化退房
//    	CheckOutOrderRequest checkOutRequest = new CheckOutOrderRequest();
//		checkOutRequest.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
//		checkOutRequest.setOrderSn("160419V6621S1J172304");
//		checkOutRequest.setUid("uid");
//		checkOutRequest.setOrderSn("1605042NL8KYP8111215");
//		String rst = orderUserServiceProxy.initCheckOutOrder(JsonEntityTransform.Object2Json(checkOutRequest));
//      System.err.println(JsonEntityTransform.Object2Json(rst));
    	
    	String json = "{\"uid\":\"89483a8f-1369-480f-bedb-ccf9e73d6a69\",\"orderSn\":\"161221L34L6MHF193336\",\"landlordUid\":null}";
    	String rst = orderUserServiceProxy.initCheckOutOrder(json);
    	System.err.println(JsonEntityTransform.Object2Json(rst));
    }
    
    @Test
    public void TestinitCheckOutOrder(){
    	// 退房
    	CheckOutOrderRequest checkOutRequest = new CheckOutOrderRequest();
		checkOutRequest.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		checkOutRequest.setOrderSn("160512MK5UPOL5113905");
		String rst = orderUserServiceProxy.initCheckOutOrder(JsonEntityTransform.Object2Json(checkOutRequest));
        System.err.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestcheckOutOrderUid_ERROR() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("160427W688N4L0220417");
        request.setUid("uidaa");
        String rst = orderUserServiceProxy.checkOutOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestcheckOutOrderOrderSn_ERROR() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("160427W688N4L0220417sss");
        request.setUid("uidaa");
        String rst = orderUserServiceProxy.checkOutOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }

    @Test
    public void TestcheckOutOrder() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("170309PH50VUP7110839");
        request.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
        String rst = orderUserServiceProxy.checkOutOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }


    @Test
    public void TestcheckOutOrderInit() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("160512U76U1LPW151135");
        request.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
        String rst = orderUserServiceProxy.initCheckOutOrder(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }



    @Test
    public void TestconfirmOtherMoney() {
        CanclOrderRequest request = new CanclOrderRequest();
        request.setOrderSn("160517Y24LK88S000405");
        request.setUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
        String rst = orderUserServiceProxy.confirmOtherMoney(JsonEntityTransform.Object2Json(request));
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }
    
    @Test
    public void TestunlockHouse() throws ParseException {
    	LockHouseRequest request = new LockHouseRequest();
    	request.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
    	request.setRentWay(0);
    	
    	List<Date> lockDayList = new ArrayList<Date>();
    	lockDayList.add(DateUtil.parseDate("2016-05-12", "yyyy-MM-dd"));
    	
    	request.setLockDayList(lockDayList);
    	String rst = orderUserServiceProxy.unlockHouse(JsonEntityTransform.Object2Json(request));
    	System.err.println(JsonEntityTransform.Object2Json(rst));
    }
    
    
    @Test
    public void testgetBookDaysByFid(){
    	LockHouseCountRequest request = new LockHouseCountRequest();
    	request.setStartTime(new Date());
    	request.setEndTime(DateUtils.addDays(new Date(), 30));
    	request.setHouseFid("8a9084df556cef6e01556d0ded1e0014");
    	request.setRoomFid("8a9084df556cd72c01556d1081070010");
    	request.setRentWay(1);
    	String str = orderUserServiceProxy.getBookDaysByFid(JsonEntityTransform.Object2Json(request));
    	System.err.println(str);
    }
    
    @Test
    public void testConfigOtherMoney(){
    	ConfirmOtherMoneyRequest request = new ConfirmOtherMoneyRequest();
    	request.setUid("c6150dae-ab21-b356-afac-471d9318993e");
    	request.setOrderSn("160511252A9834163856");
    	String str = orderUserServiceProxy.confirmOtherMoney(JsonEntityTransform.Object2Json(request));
    	System.err.println(str);
    }

    
    @Test
    public void getCountInTimes(){
    	String updateOrderConfValue = cancelOrderServiceProxy.getCountInTimes("160527IV96ZHR2140806", null,null);
    }

}
