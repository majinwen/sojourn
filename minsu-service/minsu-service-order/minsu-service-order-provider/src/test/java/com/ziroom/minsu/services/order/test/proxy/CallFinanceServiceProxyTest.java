package com.ziroom.minsu.services.order.test.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinancePayVouchersDetailEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.common.utils.HttpUtil;
import com.ziroom.minsu.services.common.utils.ResponseCallback;
import com.ziroom.minsu.services.finance.dto.PayVouchersCallBackRequest;
import com.ziroom.minsu.services.finance.dto.PayVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.order.api.inner.FinanceCallBackServiceService;
import com.ziroom.minsu.services.order.proxy.CallFinanceServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.finance.PaymentTypeEnum;
import com.ziroom.minsu.valenum.order.AuditStatusEnum;
import com.ziroom.minsu.valenum.order.OrderPayTypeChannelEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;

public class CallFinanceServiceProxyTest extends BaseTest{
	
	@Resource(name = "order.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;
	
	@Resource(name = "order.financeCallBackServiceProxy")
	private FinanceCallBackServiceService financeCallBackServiceImpl;

	//同步收入
	@Test 
	public void TestSyncIncomeDataData() throws Exception {
		FinanceIncomeVo fivo = new FinanceIncomeVo();
		fivo.setOrderSn("16070155H9V7B1220304");
		fivo.setIncomeSn("SR16070174QMP3H4223000");
	
		fivo.setIncomeSourceType(1);
		fivo.setIncomeType(1);
		fivo.setOrderSn("1604288RU0R5W5181535");
		fivo.setPayUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		fivo.setTotalFee(100);
		fivo.setIncomeStatus(2);
		fivo.setOrderStatus(1);
		fivo.setUserName("李英杰");
		fivo.setLandlordName("丁方文");
		fivo.setStartTime(new Date());
		fivo.setEndTime(new Date());
		fivo.setRealEndTime(new Date());
		fivo.setGenerateFeeTime(new Date());
		Map<String,String> rest = callFinanceServiceProxy.syncIncomeData(fivo);
		System.err.println("------------------------"+rest);
	}
		
	
	//同步业务账
	@Test 
	public void TestSyncPaymentData() throws Exception {
		FinancePaymentVouchersEntity ope = new FinancePaymentVouchersEntity();
		ope.setOrderSn("16041974RRJ8SX172841");
		ope.setTradeNo("16041976RRJ8SX172841");
		ope.setPaymentSn("asdfdasdfasdf");
		ope.setNeedMoney(1);
		ope.setTotalFee(1);
		ope.setSourceType(1);
		ope.setPaymentType(PaymentTypeEnum.order.getCode());
		ope.setPayType(OrderPayTypeChannelEnum.zfb_ad_pay.getPayType());//支付宝
		ope.setPaymentUid("937d573a-4f25-638b-db9b-f97339e3e-dz-test1");
		Map<String,String> rest = callFinanceServiceProxy.syncPaymentData(ope);
		System.err.println(rest);
		System.out.println("------------------------"+rest.get("result"));
		System.out.println("------------------------"+rest.get("result"));
		System.out.println("------------------------"+rest.get("result"));
		System.out.println("------------------------"+rest.get("result"));
		
	}




	//传财务 付款单
	@Test
	public void TestSendPayVouchersLocal() throws Exception {
		PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
		payVouchersRequest.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
		payVouchersRequest.setPvSn(UUIDGenerator.hexUUID());
		payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		payVouchersRequest.setCreator("system");// 实际的创建人  001 即为系统创建  这个时候传system
		payVouchersRequest.setOrderSn(UUIDGenerator.hexUUID());
		payVouchersRequest.setCustomerBankName("zgjsyh");
		payVouchersRequest.setCustomerName("liyj");
		payVouchersRequest.setCustomerAccountName("liyj");
		payVouchersRequest.setCustomerPhone("18301315875");
		payVouchersRequest.setCustomerAccountNo("12345678912345678");
		payVouchersRequest.setPayTime(new Date());
		payVouchersRequest.setReceiveUid("937d573a-4f25-638b-db9b-f97339e3e-dz-test");
		payVouchersRequest.setCustomerPhone("18301315875");
		payVouchersRequest.setPaymentTypeCode(OrderPaymentTypeEnum.YHFK.getCode());
		FinancePayVouchersDetailEntity fpvd = new FinancePayVouchersDetailEntity();
		fpvd.setFeeItemCode(1);
		fpvd.setItemMoney(3004);
		List<FinancePayVouchersDetailEntity> list = new ArrayList<FinancePayVouchersDetailEntity>(1);
		list.add(fpvd);
		payVouchersRequest.setDetailList(list);
		Map<String,String> res = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
		System.out.println("--------------"+JsonEntityTransform.Object2Json(res));
		System.out.println("--------------"+JsonEntityTransform.Object2Json(res));
		System.out.println("--------------"+JsonEntityTransform.Object2Json(res));
	}
	
	
	
	/**
	 * 并发2个线程调财务提现
	 *
	 * @author lishaochuan
	 * @create 2016/12/14 9:16
	 * @param 
	 * @return 
	 */
	@Test 
	public void TestSendPayVouchersThread() throws Exception {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("-------1111-------");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("-------1111-------kaishi");
				PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
				payVouchersRequest.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
				payVouchersRequest.setPvSn(UUIDGenerator.hexUUID());
				payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
				payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
				payVouchersRequest.setCreator("system");// 实际的创建人  001 即为系统创建  这个时候传system
				payVouchersRequest.setOrderSn(UUIDGenerator.hexUUID());
				payVouchersRequest.setCustomerBankName("zgjsyh");
				payVouchersRequest.setCustomerName("liyj");
				payVouchersRequest.setCustomerAccountName("liyj");
				payVouchersRequest.setCustomerPhone("18301315875");
				payVouchersRequest.setCustomerAccountNo("12345678912345678");
				payVouchersRequest.setPayTime(new Date());
				payVouchersRequest.setReceiveUid("937d573a-4f25-638b-db9b-f97339e3e-dz-test");
				payVouchersRequest.setCustomerPhone("18301315875");
				payVouchersRequest.setPaymentTypeCode(OrderPaymentTypeEnum.YHFK.getCode());
				FinancePayVouchersDetailEntity fpvd = new FinancePayVouchersDetailEntity();
				fpvd.setFeeItemCode(1);
				fpvd.setItemMoney(3004);
				List<FinancePayVouchersDetailEntity> list = new ArrayList<FinancePayVouchersDetailEntity>(1);
				list.add(fpvd);
				payVouchersRequest.setDetailList(list);
				Map<String,String> res = null;
				try {
					res = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("-------1111-------"+JsonEntityTransform.Object2Json(res));
			}
		};


		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("-------2222-------");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("-------2222-------kaishi");
				PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
				payVouchersRequest.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
				payVouchersRequest.setPvSn(UUIDGenerator.hexUUID());
				payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
				payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
				payVouchersRequest.setCreator("system");// 实际的创建人  001 即为系统创建  这个时候传system
				payVouchersRequest.setOrderSn(UUIDGenerator.hexUUID());
				payVouchersRequest.setCustomerBankName("zgjsyh");
				payVouchersRequest.setCustomerName("liyj");
				payVouchersRequest.setCustomerAccountName("liyj");
				payVouchersRequest.setCustomerPhone("18301315875");
				payVouchersRequest.setCustomerAccountNo("12345678912345678");
				payVouchersRequest.setPayTime(new Date());
				payVouchersRequest.setReceiveUid("937d573a-4f25-638b-db9b-f97339e3e-dz-test");
				payVouchersRequest.setCustomerPhone("18301315875");
				payVouchersRequest.setPaymentTypeCode(OrderPaymentTypeEnum.YHFK.getCode());
				FinancePayVouchersDetailEntity fpvd = new FinancePayVouchersDetailEntity();
				fpvd.setFeeItemCode(1);
				fpvd.setItemMoney(3004);
				List<FinancePayVouchersDetailEntity> list = new ArrayList<FinancePayVouchersDetailEntity>(1);
				list.add(fpvd);
				payVouchersRequest.setDetailList(list);
				Map<String,String> res = null;
				try {
					res = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("------222222--------"+JsonEntityTransform.Object2Json(res));
			}
		};


		new Thread(runnable).start();
		new Thread(runnable1).start();

	}
	
	
	
	
	
	//传财务 付款单
	@Test 
	public void TestSendPayVouchers() throws Exception {
		PayVouchersRequest payVouchersRequest = new PayVouchersRequest();
		payVouchersRequest.setReceiveType(UserTypeEnum.LANDLORD.getUserType());
		payVouchersRequest.setPvSn(UUIDGenerator.hexUUID());
		payVouchersRequest.setAuditStatus(AuditStatusEnum.COMPLETE.getCode());
		payVouchersRequest.setPaymentStatus(PaymentStatusEnum.UN_PAY.getCode());
		payVouchersRequest.setCreator("system");// 实际的创建人  001 即为系统创建  这个时候传system
		payVouchersRequest.setOrderSn(UUIDGenerator.hexUUID());
		payVouchersRequest.setCustomerBankName("中国建设银行");
		payVouchersRequest.setCustomerName("李英杰");
		payVouchersRequest.setCustomerAccountName("李英杰");
		payVouchersRequest.setCustomerPhone("18301315875");
		payVouchersRequest.setCustomerAccountNo("12345678912345678");
		payVouchersRequest.setPayTime(new Date());
		payVouchersRequest.setReceiveUid("937d573a-4f25-638b-db9b-f97339e3e-dz-test");
		payVouchersRequest.setCustomerPhone("18301315875");
		FinancePayVouchersDetailEntity fpvd = new FinancePayVouchersDetailEntity();
		fpvd.setFeeItemCode(1);
		fpvd.setItemMoney(300);
		
		List<FinancePayVouchersDetailEntity> list = new ArrayList<FinancePayVouchersDetailEntity>(1);
		list.add(fpvd);
		payVouchersRequest.setDetailList(list);
		Map<String,String> res = callFinanceServiceProxy.sendPayVouchers(payVouchersRequest);
		System.out.println("--------------"+JsonEntityTransform.Object2Json(res));
	}	
	  @Test 
	  public void testCreate(){
	        String param="{\"businessNum\":\"16041974RRJ8SX172841\",\"contract\":\"16041974RRJ8SX172841\",\"dataSources\":\"dz\",\"drawee\":\"937d573a-4f25-638b-db9b-f97339e3e-dz-test\",\"isContract\":1,\"payNum\":\"16041976RRJ8SX172841\",\"realAmount\":1000,\"receiptMethod\":9,\"receiptPath\":\"线上\",\"receiptStatus\":2,\"receiptType\":\"短租\",\"receivableAmount\":1000,\"remark\":\"同步业务\"}";
	        //String url="http://localhost:8080/receivable/create";
	        String url="http://10.30.92.51:8080/receivable/create";
//	        String aa = CloseableHttpUtil.sendPost(url, param);
          System.out.println("=============================");
          System.out.println("=============================");
          System.out.println("=============================");
          System.out.println("=============================");
          System.out.println("=============================");
          System.out.println("=============================");
          System.out.println("=============================");
//          System.out.println(aa);

          HttpUtil.post(url, param, new ResponseCallback<Object>() {

              @Override
              public Object onResponse(int resultCode, String resultJson) {
                  System.out.println("=============================");
                  System.out.println("=============================");
                  System.out.println("=============================");
                  System.out.println("---------------->"+resultJson);
                  return resultJson;
              }
          });
	    }
	
	  
	  @Test 
	  public void TestCallBack(){
		  PayVouchersCallBackRequest pc = new PayVouchersCallBackRequest();
		  pc.setBusId("8a9e9cc954a4ea780154a4ea78de0000");
		  pc.setPayFlag(2);
		  String resultJson = financeCallBackServiceImpl.sendPayVouchersCallBack(JsonEntityTransform.Object2Json(pc));
	      System.out.print("------------------"+resultJson);
	      System.out.print("------------------"+resultJson);
	      System.out.print("------------------"+resultJson);
	  }
	
}
