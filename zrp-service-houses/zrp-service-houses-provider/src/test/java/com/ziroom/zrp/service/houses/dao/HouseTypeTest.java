package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.google.common.collect.Sets;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>房型测试</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月17日
 * @since 1.0
 */
@Slf4j
public class HouseTypeTest extends BaseTest {

	@Resource(name="houses.houseTypeDao")
	private  HouseTypeDao houseTypeDao;
	
	@Test
	public void testFindTypeById(){
		String id = "2c908d194f6e5e5f014f83cc01ac01ad";
		HouseTypeEntity houseTypeEntity = houseTypeDao.findHouseTypeById(id);
		System.err.println(JSONObject.toJSON(houseTypeEntity));
	}

	@Test
	public void testfindLayoutListForPage(){
		AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
		addHouseGroupDto.setProjectid("8a9099cb576ba5c101576ea29c8a0027");
		PagingResult<AddHouseGroupVo> pagingResult = houseTypeDao.findLayoutListForPage(addHouseGroupDto);
		System.err.print(pagingResult.toString());
	}

	@Test
	public void findHouseTypeListByIds() {
//		Stream.of("aa", "bb");
//		List<String> list = Stream.of("8a90a3ab576ba74701576fc33d730032","8a90a3ab57e1c0760157e69a910800cd").collect(Collectors.toList());
		List<String> list = new ArrayList<>();
		list.add("8a90a3ab576ba74701576fc33d730032");
		list.add("8a90a3ab57e1c0760157e69a910800cd");
//		list.stream().collect(Collectors.toList());
		List<HouseTypeEntity>  houseTypeEntityList = houseTypeDao.findHouseTypeListByIds(list);
		System.out.println("houseTypeEntityList:" + houseTypeEntityList.size());
	}

	@Test
	public  void findHouseTypeByCondition(){

		AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
		List<AddHouseGroupVo> pagingResult = houseTypeDao.findHouseTypeByCondition(addHouseGroupDto);
		System.err.print(pagingResult.toString());
	}

	/**
	 * 户型查询By projectIds
	 */
	@Test
	public void findHouseTypeByProjectIdsTests(){

		List<HouseTypeEntity> houseTypeEntities = houseTypeDao.findHouseTypeByProjectIds(
				Sets.newHashSet("17")
		);
		Assert.notEmpty(houseTypeEntities, "获取户型失败");
		log.info(JSON.toJSONString(houseTypeEntities));
	}
}
