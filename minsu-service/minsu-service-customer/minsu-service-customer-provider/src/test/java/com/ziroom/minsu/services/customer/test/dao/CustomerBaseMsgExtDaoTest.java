
package com.ziroom.minsu.services.customer.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBaseMsgExtDao;
import com.ziroom.minsu.services.customer.test.BaseTest;

/**
 * <p>dao层 测试</p>
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
public class CustomerBaseMsgExtDaoTest extends BaseTest{



	@Resource(name = "customer.customerBaseMsgExtDao")
	private CustomerBaseMsgExtDao customerBaseMsgExtDao;

	@Test
	public void selectByUidTest() {
		CustomerBaseMsgExtEntity customerBaseMsgExt =customerBaseMsgExtDao.selectByUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448");
		System.out.println(customerBaseMsgExt);
	}

	@Test
	public void insertSelectiveTest(){

		CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
		//customerBaseMsgExt.setFid(UUIDGenerator.hexUUID());
		customerBaseMsgExt.setUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448");
		customerBaseMsgExt.setCustomerIntroduce("自我介绍");
		customerBaseMsgExtDao.insertSelective(customerBaseMsgExt);

	}

	@Test
	public void updateByUidSelectiveTest(){

		CustomerBaseMsgExtEntity customerBaseMsgExt = new CustomerBaseMsgExtEntity();
		customerBaseMsgExt.setUid("50cc2cc3-49a1-79cc-fa23-fd0c4d881448");
		customerBaseMsgExt.setCustomerIntroduce("自我介绍123456");
		customerBaseMsgExtDao.updateByUidSelective(customerBaseMsgExt);
	}

}
