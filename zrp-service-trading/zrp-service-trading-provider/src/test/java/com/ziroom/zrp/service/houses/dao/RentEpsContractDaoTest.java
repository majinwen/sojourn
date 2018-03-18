package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentEpsCustomerDao;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;
/**
 * <p>测试企业签约人信息</p>
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
public class RentEpsContractDaoTest extends BaseTest{
	
	@Resource(name="trading.rentEpsCustomerDao")
	private RentEpsCustomerDao rentEpsCustomerDao;

	@Test
	public void testsaveRentEpsCustomer(){
		RentEpsCustomerEntity rentEpsCustomerEntity = new RentEpsCustomerEntity();
		rentEpsCustomerEntity.setCode("101102");
		rentEpsCustomerEntity.setContacter("101102");
		rentEpsCustomerEntity.setIsDeleted(0);
		System.err.println(this.rentEpsCustomerDao.saveRentEpsCustomer(rentEpsCustomerEntity));
	}

}
