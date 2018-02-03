/**
 * @FileName: CustomerBankCardMsgDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author bushujie
 * @created 2016年4月25日 下午3:32:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBankCardMsgDao;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>TODO</p>
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
public class CustomerBankCardMsgDaoTest extends BaseTest{
	
	@Resource(name="customer.customerBankCardMsgDao")
	private CustomerBankCardMsgDao customerBankCardMsgDao;
	
	@Test
	public void insertCustomerBankCardTest(){
		CustomerBankCardMsgEntity customerBankCardMsgEntity=new CustomerBankCardMsgEntity();
		customerBankCardMsgEntity.setBankcardHolder("王二小");
		customerBankCardMsgEntity.setBankcardNo("6221880469651234");
		customerBankCardMsgEntity.setUid("8a9e9a9e543d23f901543d23f9e90000");
		customerBankCardMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerBankCardMsgEntity.setBankName("北京银行");
		customerBankCardMsgEntity.setBranchName("劲松分行");
		customerBankCardMsgDao.insertCustomerBankCard(customerBankCardMsgEntity);
	}
	
	@Test
	public void getCustomerBankCardByUid(){
		CustomerBankCardMsgEntity customerBankCardMsgEntity=customerBankCardMsgDao.getCustomerBankCardByUid("8a9e9a9e543d23f901543d23f9e90000");
		System.err.println(JsonEntityTransform.Object2Json(customerBankCardMsgEntity));
	}
		
}
