package com.ziroom.zrp.service.houses.proxy;

import javax.annotation.Resource;

import com.ziroom.zrp.service.trading.dto.EnterpriseCustomerDto;
import org.junit.Test;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.RentCustomerServiceProxy;
/**
 * <p>测试企业签约人</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class RentCustomerServiceProxyTest extends BaseTest{
	@Resource(name="trading.rentCustomerServiceProxy")
	private RentCustomerServiceProxy rentEpsCustomerServiceProxy;
	@Test
	public void saveEpsCustomer(){
		String epsCustomerJson = "{\"contacter\":\"101102\",\"code\":\"101102\"}";
		String resJson = rentEpsCustomerServiceProxy.saveRentEpsCustomerService(epsCustomerJson);
		System.err.println(resJson);
	}

	@Test
	public void testFindTempRentEpsCustomerInfo() {
		EnterpriseCustomerDto enterpriseCustomerDto = new EnterpriseCustomerDto();
		enterpriseCustomerDto.setSurParentRentId("8a9e98b75f95bb67015f95bb67010000");
		// 合同中存在customerId情况
		String result = rentEpsCustomerServiceProxy.findTempRentEpsCustomerInfo(enterpriseCustomerDto.toJsonStr());
		System.out.println("testFindTempRentEpsCustomerInfo:" + result);

		// 合同中不存在customerId情况
//		enterpriseCustomerDto.setSurParentRentId("8a9eae535f28fd8c015f28fd8cc00000");
//		enterpriseCustomerDto.setCustomerUid("8fdc471a-536c-6ab6-a334-1f8f16fc690f");
//		String result1 = rentEpsCustomerServiceProxy.findTempRentEpsCustomerInfo(enterpriseCustomerDto.toJsonStr());
//		System.out.println("result1:" + result1);

	}

	@Test
	public void testFindRentEpsCustomerById() {
		String result = rentEpsCustomerServiceProxy.findRentEpsCustomerById("8a9e989060254cb80160255ea88a00a7");
		System.out.println("result:" + result);
	}
}
