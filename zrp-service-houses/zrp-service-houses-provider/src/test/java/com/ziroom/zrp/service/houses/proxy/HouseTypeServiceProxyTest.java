package com.ziroom.zrp.service.houses.proxy;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.SOAResParseUtil;
import com.google.common.collect.Sets;
import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import com.ziroom.zrp.service.houses.base.BaseTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HouseTypeServiceProxyTest extends BaseTest{
	
	@Resource(name="houses.houseTypeServiceProxy")
	private HouseTypeServiceProxy houseTypeProxy;
	
	@Test	
	public void testfindHouseTypeById(){
		String fid = "2c908d174facc5fa014facd0d7ef000a";
		String houseType = houseTypeProxy.findHouseTypeById(fid);
		System.err.println(houseType);
	}

	@Test
	public void testFindHouseTypeListByIds() {
		List<String> list = new ArrayList<>();
		list.add("8a90a3ab576ba74701576fc33d730032");
		list.add("8a90a3ab57e1c0760157e69a910800cd");
		String houseTypeIds = list.stream().collect(Collectors.joining(","));
		System.out.println("houseTypeIds:" +houseTypeIds);
		String result = houseTypeProxy.findHouseTypeListByIds(houseTypeIds);
		System.out.println("result:" + result);
	}

	/**
	 * 户型查询By projectIds
	 */
	@Test
	public void findHouseTypeByProjectIdsTests() throws SOAParseException {

		String jsonStr = houseTypeProxy.findHouseTypeByProjectIds(
				Sets.newHashSet("17")
		);

		log.info(jsonStr);

		List<HouseTypeEntity> houseTypeEntities = SOAResParseUtil.getListValueFromDataByKey(jsonStr, "data", HouseTypeEntity.class);

		Assert.notEmpty(houseTypeEntities, "获取户型失败");

		log.info(JSON.toJSONString(houseTypeEntities));
	}
}
