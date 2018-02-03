/**
 * @FileName: CustomerInfoServiceImplProxyTest.java
 * @Package com.ziroom.minsu.services.customer.test.api.inner
 * 
 * @author bushujie
 * @created 2016年4月25日 下午5:51:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.api.inner;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerInvoiceTitleEntity;
import com.ziroom.minsu.services.customer.proxy.CustomerInfoServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>客户相关业务代理层测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CustomerInfoServiceProxyTest extends BaseTest{
	
	@Resource(name="customer.customerInfoServiceProxy")
	private CustomerInfoServiceProxy customerInfoServiceProxy;
	
	
	@Test
	public void insertCustomerInfoTest(){
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid(UUIDGenerator.hexUUID());
		customerBaseMsgEntity.setNickName("ziroom001fdsfdsfdsf");
		String resultJson=customerInfoServiceProxy.insertCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateCustomerInfoTest(){
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid("8a9e9a9e544ce07501544ce075d00000");
		customerBaseMsgEntity.setNickName("ziroom修改");
		String resultJson=customerInfoServiceProxy.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void insertCustomerInvoiceTest(){
		CustomerInvoiceTitleEntity customerInvoiceTitleEntity=new CustomerInvoiceTitleEntity();
		customerInvoiceTitleEntity.setUid("8a9e9a9e544ce07501544ce075d00000");
		customerInvoiceTitleEntity.setFid(UUIDGenerator.hexUUID());
		customerInvoiceTitleEntity.setInvoiceTitle("民生电子商务有限公司");
		String resultJson=customerInfoServiceProxy.insertCustomerInvoice(JsonEntityTransform.Object2Json(customerInvoiceTitleEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void insertCustomerBankcardTest(){
		CustomerBankCardMsgEntity customerBankCardMsgEntity=new CustomerBankCardMsgEntity();
		customerBankCardMsgEntity.setBankcardHolder("网吧");
		customerBankCardMsgEntity.setBankcardNo("256545545498745544");
		customerBankCardMsgEntity.setBankName("浙江银行");
		customerBankCardMsgEntity.setBranchName("北京分行");
		customerBankCardMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerBankCardMsgEntity.setUid("8a9e9a9e544ce07501544ce075d00000");
		String resultJson=customerInfoServiceProxy.insertCustomerBankcard(JsonEntityTransform.Object2Json(customerBankCardMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void getCustomerBankcardTest(){
		String resultJson=customerInfoServiceProxy.getCustomerBankcard("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		System.out.println(resultJson);
	}
	
	@Test
	public void updateCustomerBankcardTest(){
		CustomerBankCardMsgEntity customerBankCardMsgEntity=new CustomerBankCardMsgEntity();
		customerBankCardMsgEntity.setUid("8a9e9a9e544ce07501544ce075d00000");
		customerBankCardMsgEntity.setBankcardNo("123456789123456");
		String resultJson=customerInfoServiceProxy.updateCustomerBankcard(JsonEntityTransform.Object2Json(customerBankCardMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void getCustomerInfoByUidTest(){
		String resultJson=customerInfoServiceProxy.getCustomerInfoByUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		System.err.println(resultJson);
	}


    @Test
    public void getCustomerAndRoleInfoByUid(){
        String resultJson=customerInfoServiceProxy.getCustomerAndRoleInfoByUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
        System.err.println(resultJson);
    }
    
    @Test
    public void getCustomerListByMobile(){
    	String resultJson = customerInfoServiceProxy.getCustomerListByMobile("15010386533");
    	System.err.println(resultJson);
    }


}
