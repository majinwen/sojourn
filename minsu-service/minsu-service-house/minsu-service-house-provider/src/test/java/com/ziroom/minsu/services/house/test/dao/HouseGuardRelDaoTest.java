package com.ziroom.minsu.services.house.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.services.house.dao.HouseGuardRelDao;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.entity.HouseGuardVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源维护管家日志dao测试类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseGuardRelDaoTest extends BaseTest{
	
	@Resource(name = "house.houseGuardRelDao")
    private HouseGuardRelDao houseGuardRelDao;
	
	@Test
	public void insertHouseGuardRelTest(){
		HouseGuardRelEntity entity = new HouseGuardRelEntity();
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setHouseFid(UUIDGenerator.hexUUID());
		entity.setEmpGuardCode("20080808");
		entity.setEmpGuardName("管家");
		entity.setCreateFid(UUIDGenerator.hexUUID());
		entity.setCreateDate(new Date());
		entity.setLastModifyDate(new Date());
		entity.setIsDel(0);
		houseGuardRelDao.insertHouseGuardRel(entity);
	}
	
	@Test
	public void updateHouseGuardRelByFidTest(){
		HouseGuardRelEntity houseGuardRel = new HouseGuardRelEntity();
		houseGuardRel.setFid("8a9e9aa855c084680155c084688c0000");
		houseGuardRel.setEmpGuardCode("测试1");
		int line = houseGuardRelDao.updateHouseGuardRelByFid(houseGuardRel);
		System.err.println(line);
	}
	
	@Test
	public void updateHouseGuardRelByHouseFidTest(){
		HouseGuardRelEntity houseGuardRel = new HouseGuardRelEntity();
		houseGuardRel.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		houseGuardRel.setEmpGuardCode("测试2");
		int line = houseGuardRelDao.updateHouseGuardRelByHouseFid(houseGuardRel);
		System.err.println(line);
	}
	
	@Test
	public void findHouseGuardRelByFidTest(){
		String fid = "8a9e9aa855c084680155c084688c0000";
		HouseGuardRelEntity entity = houseGuardRelDao.findHouseGuardRelByFid(fid);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findHouseGuardRelByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		HouseGuardRelEntity entity = houseGuardRelDao.findHouseGuardRelByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(entity));
	}
	
	@Test
	public void findHouseGuardVoForPageTest(){
		HouseGuardDto houseGuardDto = new HouseGuardDto();
		houseGuardDto.setRentWay(1);
//		houseGuardDto.setRoomSn("110100031211F");
		houseGuardDto.setHouseStatus(40);
		
		PagingResult<HouseGuardVo> list = houseGuardRelDao.findHouseGuardVoForPage(houseGuardDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseGuardVoByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		HouseGuardVo vo = houseGuardRelDao.findHouseGuardVoByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(vo));
	}


	@Test
	public void findHouseGuardRelByCondition(){
		HouseGuardRelEntity houseGuard = new HouseGuardRelEntity();
		houseGuard.setEmpPushName("张继炜");

		List<HouseGuardRelEntity> houseGuardRelByCondition = houseGuardRelDao.findHouseGuardRelByCondition(houseGuard);
		System.err.println(JsonEntityTransform.Object2Json(houseGuardRelByCondition));
	}
	
	@Test
	public void findSpecialHouseGuardVoForPage(){
		HouseGuardDto houseGuardDto = new HouseGuardDto();
		houseGuardDto.setRoleType(2);
		CurrentuserCityEntity userCityEntity1=new CurrentuserCityEntity();
		userCityEntity1.setNationCode("100000");
		userCityEntity1.setProvinceCode("310000");
		houseGuardDto.getUserCityList().add(userCityEntity1);
		PagingResult<HouseGuardVo> list =houseGuardRelDao.findSpecialHouseGuardVoForPage(houseGuardDto);
		System.err.println(JsonEntityTransform.Object2Json(list.getRows()));
		System.err.println(list.getTotal());
	}
}
