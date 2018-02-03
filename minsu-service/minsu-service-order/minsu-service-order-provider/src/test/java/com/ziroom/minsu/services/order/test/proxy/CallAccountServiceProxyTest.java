package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.account.dto.AccountDetailRequest;
import com.ziroom.minsu.services.account.dto.BalanceThawRequest;
import com.ziroom.minsu.services.account.dto.BalanceToFreezeRequest;
import com.ziroom.minsu.services.account.dto.FillMoneyRequest;
import com.ziroom.minsu.services.account.dto.FreezeConsumeRequest;
import com.ziroom.minsu.services.account.dto.TransfersAccountRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.proxy.CallAccountServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FillBussinessTypeEnum;

public class CallAccountServiceProxyTest extends BaseTest{

	@Resource(name = "order.callAccountServiceProxy")
    private CallAccountServiceProxy callAccountServiceProxy;


	//获取账户 余额、冻结金  成功
	@Test 
	public void TestAccountDetails() throws Exception {
		//7bbbf57f-6228-5e92-91dc-c9688d4398ce   937d573a-4f25-638b-db9b-f97339e3e5ming-2
		AccountDetailRequest adr = new AccountDetailRequest();
		//adr.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
		adr.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		adr.setDzCityCode("BJS");
		Map<String,String> res = callAccountServiceProxy.accountDetails(adr);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
		
	//充值冻结 成功
	@Test
	public void TestFillFreezeAmount() throws Exception {
		FillMoneyRequest btfr = new FillMoneyRequest();
		btfr.setOrderSn("111111");

		btfr.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
		btfr.setTradeNo("dz_tsgdfsgt_000000011111111111112");
        btfr.setTotalFee(1000);
		//btfr.setBizCommon("dz_fillFreezeAmount.1"); //充值优惠券，虚拟充值
		btfr.setBizCommon("dz_fillFreezeAmount");//正常充值
		btfr.setDzCityCode("BJ");
		btfr.setUidType(CustomerTypeEnum.roomer.getCode());
		//btfr.setBusiness_type(1);//正常充值时传1，虚拟充值的时候不传
		btfr.setPayType("zfb_pay");
		btfr.setPayTime(new Date());
		Map<String,String> res = callAccountServiceProxy.fillFreezeAmount(btfr);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
		
   //充值余额 成功
	@Test
	public void TestFillBalanceFreezeAmount() throws Exception {
		FillMoneyRequest btfr = new FillMoneyRequest();
		btfr.setOrderSn("222222");
		btfr.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
		
		btfr.setTradeNo("testchenyu100002");
        btfr.setTotalFee(1);
		btfr.setBizCommon("dz_fillFreezeAmount");
		btfr.setDzCityCode("BJS");
		btfr.setUidType(CustomerTypeEnum.roomer.getCode());
		btfr.setBusiness_type(FillBussinessTypeEnum.receive_fill.getCode());
		btfr.setPayType("yl_ios_pay");
		btfr.setPayTime(new Date());
		Map<String,String> res = callAccountServiceProxy.fillBalanceAmount(btfr);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
	
	
	//消费冻结   成功
	@Test 
	public void TestConsumeRfrozenAccount() throws Exception {
		FreezeConsumeRequest fsr = new FreezeConsumeRequest();
		fsr.setOrderSn("111111");

		fsr.setUnique_num("testlishaochuan000012");
		fsr.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
		//fsr.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		fsr.setTotalFee(1220);
		//fsr.setBiz_common_type("consumeFreeze.-1");//虚拟的，为了平账
		fsr.setBiz_common_type("consumeFreeze");//正常流程 给公司服务费 客户自动提现
		fsr.setDzCityCode("BJ");
		fsr.setUidType(CustomerTypeEnum.roomer.getCode());//yz
		//fsr.setBussiness_type("");//虚拟的，为了平账 ，bussiness_type为空，不传值
		fsr.setBussiness_type(String.valueOf(3));//正常流程 给公司服务费 客户自动提现  传相应的码
		
		Map<String,String> res = callAccountServiceProxy.consumeRfrozenAccount(fsr);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
	
	
	//冻结 转 余额  成功  
	@Test
	public void TestAccountBalanceThaw() throws Exception {
		BalanceThawRequest btr = new BalanceThawRequest();
		btr.setOrderSn("111111");
		btr.setUnique_num("testlishaochuan1000001");
		btr.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");

		btr.setTrade_no(""); // 传付款单表 pv_sn
		btr.setTotalFee(3);
		btr.setBizCommon("dz_accountBalanceThaw");
		btr.setDzCityCode("BJ");
		Map<String,String> res = callAccountServiceProxy.accountBalanceThaw( btr);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
	
	

	// 转账
	// 冻结 转  冻结  
	// 冻结 转 余额  成功    转账 流水号重复  则返回上次转账成功的结果
	@Test
	public void TestTransfersAccount() throws Exception {
		TransfersAccountRequest tar = new TransfersAccountRequest();
        tar.setOrderSn("170724L0G5788U093655");
        tar.setOrigUid("8ef91082-1bb7-4c97-9a1b-f58b076a0af7");
        tar.setUidType(CustomerTypeEnum.landlord.getCode());
		tar.setOrigBusinessType("");
		tar.setReduceType(ConsumeTypeEnum.freeze.getCode());
        tar.setTargetUid("a3f9cc95-fdf1-c3e2-efeb-db14daba6bef");
        tar.setTargetCityCode("BJ");
		tar.setTargetUidType(CustomerTypeEnum.roomer.getCode());
		tar.setAddType(ConsumeTypeEnum.filling_freeze.getCode());
        tar.setTradeNo("FK170728A9BUGVDE093049"); //传付款单表 pv_sn
        tar.setTotalFee(20000);
        Map<String,String> res = callAccountServiceProxy.transfersAccount(tar);
		System.err.println(JsonEntityTransform.Object2Json(res));
	}
	
	
	
	//=====================================================================
	//余额 转 冻结 成功
	@Test
	public void TestBalanceToFreezeAccount() throws Exception {
		BalanceToFreezeRequest btfr = new BalanceToFreezeRequest();
		btfr.setUnique_num("testtttttttttttttttttt");
		btfr.setUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		btfr.setTotalFee(20);
		btfr.setBizCommon("dz_balanceToFreezeAccount");
		btfr.setDzCityCode("BJ");
		btfr.setUidType(CustomerTypeEnum.roomer.getCode());
		Map<String,String> res = callAccountServiceProxy.balanceToFreezeAccount(btfr);
		System.out.println("--------------"+JsonEntityTransform.Object2Json(res));
	}
	
	
}
