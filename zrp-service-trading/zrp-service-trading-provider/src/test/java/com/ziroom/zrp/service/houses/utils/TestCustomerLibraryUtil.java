package com.ziroom.zrp.service.houses.utils;

import org.junit.Test;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;

public class TestCustomerLibraryUtil extends BaseTest{
	
	@Test
	public void testfindCustomer(){
		PersonalInfoDto dto = CustomerLibraryUtil.findAuthInfoFromCustomer("53213bf7-4d23-edb4-8e5a-52ce7ae1bcf7");
		System.err.println(dto);
	}

}
