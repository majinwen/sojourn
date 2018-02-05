package test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.CreateOrderApiRequest;
import com.ziroom.minsu.api.order.dto.NeedPayFeeApiRequest;
import com.ziroom.minsu.services.order.dto.OrderDetailRequest;
import com.ziroom.minsu.valenum.order.OrderSourceEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>订单api测试</p>
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
public class TenantOrderControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantOrderControllerTest.class);

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	/**
	 * 
	 * 点击结束日期获取 金额详情
	 *
	 * @author yd
	 * @created 2016年5月2日 下午3:00:22
	 *
	 */
	public static void getNeedPayFeeTest() {
		NeedPayFeeApiRequest needPayFeeRequest  = new NeedPayFeeApiRequest();


		String startTime = "2017-06-12";
		String endTime = "2017-06-15";

		needPayFeeRequest.setRentWay(1);
		needPayFeeRequest.setFid("8a9084df556cd72c01556d0eaf1b000d");
		needPayFeeRequest.setUid("9c6cd1e2-f0f3-863d-c892-8d5028906563");
		try {
			needPayFeeRequest.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			needPayFeeRequest.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
		} catch (ParseException e) {
            LogUtil.error(LOGGER,"e:{}",e);
		}


		// {\"globalConfigEntity\":{\"maxNoPayNumber\":null},\"userUid\":null,\"couponSn\":null,\"fid\":\"8a90a2d4549ac7990154a01b4c19016f\",\"rentWay\":0,\"startTime\":1462982400000,\"endTime\":1463932800000}"]}

		System.err.println(JsonEntityTransform.Object2Json(needPayFeeRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(needPayFeeRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(needPayFeeRequest).toString(),"UTF-8"));
	}

	/**
	 * 
	 * 创建订单
	 *
	 * @author yd
	 * @throws ParseException 
	 * @created 2016年5月2日 下午3:10:59
	 *
	 */
	public static void creatOrderTest(){
		// {\"globalConfigEntity\":{\"maxNoPayNumber\":null},\"userUid\":\"f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6\",\"couponSn\":null,\"fid\":\"8a90a2d4549ac7990154a01b4c19016f\",\"rentWay\":0,\"startTime\":1463011200000,\"endTime\":1464652800000,\"userTel\":\"15998685217\",\"userName\":\"李央\",\"orderSource\":3,\"tenantFids\":[\"00000000549ee89101549f67c0d30006\"]}"]}

		String startTime = "2017-06-12";
		String endTime = "2017-06-15";
		
		
		CreateOrderApiRequest creatOrderApiRequest = new CreateOrderApiRequest();
		creatOrderApiRequest.setUid("9c6cd1e2-f0f3-863d-c892-8d5028906563");
		// creatOrderApiRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		creatOrderApiRequest.setRentWay(1);
		creatOrderApiRequest.setFid("8a9084df556cd72c01556d0eaf1b000d");
		creatOrderApiRequest.setSourceType(OrderSourceEnum.ANDROID.getCode());
		List<String> tenantFids = new ArrayList<String>();
		tenantFids.add("00000000549ee89101549f67c0d30006");
		creatOrderApiRequest.setTenantFids(tenantFids);
		try {
			creatOrderApiRequest.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			creatOrderApiRequest.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
		} catch (ParseException e) {
            LogUtil.error(LOGGER, "e:{}", e);
		}
		creatOrderApiRequest.setCouponSn("FDQX01YCYNCD");
		creatOrderApiRequest.setUserName("杨东测试首单立减");
		creatOrderApiRequest.setUserTel("15998685217");
		System.err.println(JsonEntityTransform.Object2Json(creatOrderApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(creatOrderApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(creatOrderApiRequest).toString(),"UTF-8"));
	}
	
	
	/**
	 * 
	 * 查询订单详情
	 *
	 * @author yd
	 * @created 2016年6月25日 下午3:56:22
	 *
	 */
	public static void showOrderDetailTest(){
		OrderDetailRequest request = new OrderDetailRequest();
        request.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");;
		request.setRequestType(1);
		request.setOrderSn("1605117Y6VG0CF213442");
		
		System.err.println(JsonEntityTransform.Object2Json(request));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
	}
	public static void main(String[] args) {
		//getNeedPayFeeTest();

		creatOrderTest();
		
		//showOrderDetailTest();

	}

}
