/**
 * @FileName: HouseBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月1日 下午1:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;

/**
 * <p>房源基本信息dao测试类</p>
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

public class HouseBaseMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseBaseMsgDao")
    private HouseBaseMsgDao houseBaseMsgDao;
	
	/*@Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;*/


	
	@Test
	public void insertHouseBaseMsgTest() throws ParseException{
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
		houseBaseMsg.setFid(UUIDGenerator.hexUUID());
		houseBaseMsg.setPhyFid("8a9e9a8b53d6089f0153d608a1540001");
		houseBaseMsg.setHouseName("测试小屋");
		houseBaseMsg.setRentWay(0);//整租
		houseBaseMsg.setHouseStatus(10);//待发布
		houseBaseMsg.setLandlordUid(UUIDGenerator.hexUUID());
		houseBaseMsg.setLeasePrice(100);
		houseBaseMsg.setHouseArea(100.00);
		houseBaseMsg.setRoomNum(1);
		houseBaseMsg.setHallNum(1);
		houseBaseMsg.setToiletNum(1);
		houseBaseMsg.setKitchenNum(1);
		houseBaseMsg.setBalconyNum(1);
		houseBaseMsg.setCameramanName("测试摄影师333");
		houseBaseMsg.setCameramanMobile("13300000001");
		houseBaseMsg.setOperateSeq(1);
		houseBaseMsg.setRefreshDate(new Date());
		houseBaseMsg.setTillDate(DateUtil.parseDate(SysConst.House.TILL_DATE, "yyyy-MM-dd HH:mm:ss"));
		houseBaseMsg.setHouseCleaningFees(231);
		houseBaseMsgDao.insertHouseBaseMsg(houseBaseMsg);
	}

	@Test
	public void updateHouseBaseMsgTest(){
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
		houseBaseMsg.setFid("8a9e98c056b0d9ec0156b0d9ecd30000");
		houseBaseMsg.setPhyFid("8a9084df5526714801552a18750a0042");
		houseBaseMsg.setHouseName("测试小屋5648974564");
		houseBaseMsg.setRentWay(RentWayEnum.HOUSE.getCode());
		/*houseBaseMsg.setRentWay(0);//整租
		houseBaseMsg.setHouseStatus(10);//待发布
		houseBaseMsg.setLandlordUid(UUIDGenerator.hexUUID());
		houseBaseMsg.setLeasePrice(100);
		houseBaseMsg.setHouseArea(100.00);
		houseBaseMsg.setRoomNum(1);
		houseBaseMsg.setHallNum(1);
		houseBaseMsg.setToiletNum(1);
		houseBaseMsg.setKitchenNum(1);
		houseBaseMsg.setBalconyNum(1);
		houseBaseMsg.setCameramanName("测试摄影师");
		houseBaseMsg.setCameramanMobile("13300000001");
		houseBaseMsg.setOperateSeq(1);
		houseBaseMsg.setRefreshDate(new Date());
		houseBaseMsg.setTillDate(new Date());*/
		houseBaseMsg.setHouseCleaningFees(111);
		int line = houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		System.err.println(line);
	}
	

	/**
	 * 
	 * 房源列表查询（房东端）
	 *
	 * @author bushujie
	 * @created 2016年4月2日 下午2:13:42
	 *
	 */
	@Test
	public void findHouseBaseListTest(){
		HouseBaseListDto paramDto=new HouseBaseListDto();
		paramDto.setLandlordUid("8a9e9a8b53d6089f0153d608a1f80002");
		PagingResult<HouseBaseListVo> result=houseBaseMsgDao.findHouseBaseList(paramDto);
		System.out.println(JsonEntityTransform.Object2Json(result.getRows()));
	}
	
	@Test
	public void getHouseBaseMsgEntityByFidTest(){
		HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid("8a9e9a8b53d6089f0153d608a1f80002");
		System.err.println(JsonEntityTransform.Object2Json(houseBaseMsgEntity));
	}
	
	@Test
	public void findHouseBaseMsgAndHouseDescTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		HouseBaseMsgDto houseBaseMsg = houseBaseMsgDao.findHouseBaseMsgAndHouseDesc(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(houseBaseMsg));
	}
	
	@Test
	public void findHouseDetailTest(){
		HouseDetailVo houseDetailVo=houseBaseMsgDao.findHouseDetail("8a9e9a8b53d6089f0153d608a1f80002", "8a9e9a8b53d6089f0153d6089f710000");
		System.err.println(JsonEntityTransform.Object2Json(houseDetailVo));
	}
	
	@Test
	public void findOrderNeedHouseVo(){
		OrderNeedHouseVo orderNeedHouseVo=houseBaseMsgDao.findOrderNeedHouseVo("8a9e9a8b53d6089f0153d608a1f80002");
		System.err.println(JsonEntityTransform.Object2Json(orderNeedHouseVo));
	}
	
	@Test
	public void findHouseMsgListByHouseTest(){
		List<Integer> houseStatusList = new ArrayList<Integer>();
		houseStatusList.add(HouseStatusEnum.XXSHWTG.getCode());
		HouseRequestDto houseRequest = new HouseRequestDto();
		
		List<String> landlordUidList = new ArrayList<String>();
		//landlordUidList.add("8a9e9a8b53d6da8b0153d6da8bae0000");
		/*List<String> landlordUidList = new ArrayList<String>();
		landlordUidList.add("8a9e9a8b53d6089f0153d608a1f80002");
		
		/*houseRequest.setHouseStatusList(houseStatusList);
		houseRequest.setLandlordUidList(landlordUidList);
		houseRequest.setCameramanName("王大雷");
		houseRequest.setCameramanMobile("17400000000");
		houseRequest.setNationCode("CN");
		houseRequest.setProvinceCode("BJ");
		houseRequest.setCityCode("BJS");
		houseRequest.setHouseStatus(10);*/
		//houseRequest.setIsPic(0);
	      houseRequest.setRentWay(0);
	      houseRequest.setHouseOnlineTimeStart("2017-10-01 00:00:00");
	      houseRequest.setHouseOnlineTimeEnd("2017-11-01 00:00:00");
	      //houseRequest.setAuditCause("");
	    houseRequest.setHouseStatus(30);
	/*houseRequest.setIsPic(0);
		houseRequest.setRentWay(0);*/
		PagingResult<HouseResultVo> list = houseBaseMsgDao.findHouseMsgListByHouse(houseRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseMsgListByRoomTest(){
		List<Integer> houseStatusList = new ArrayList<Integer>();
		houseStatusList.add(HouseStatusEnum.XXSHWTG.getCode());
		
		List<String> landlordUidList = new ArrayList<String>();
		landlordUidList.add("8a9e9a8b53d6089f0153d608a1f80002");
		
		HouseRequestDto houseRequest = new HouseRequestDto();
		houseRequest.setHouseStatusList(houseStatusList);
		houseRequest.setLandlordUidList(landlordUidList);
		houseRequest.setCameramanName("王大雷");
		houseRequest.setCameramanMobile("17400000000");
		houseRequest.setNationCode("CN");
		houseRequest.setProvinceCode("BJ");
		houseRequest.setCityCode("BJS");
		houseRequest.setHouseStatus(10);
		houseRequest.setIsPic(0);
		houseRequest.setRentWay(1);
		PagingResult<HouseResultVo> list = houseBaseMsgDao.findHouseMsgListByRoom(houseRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseDetailByFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		HouseMsgVo houseMsg = houseBaseMsgDao.findHouseDetailByFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(houseMsg));
	}
	
	/**
	 * 
	 * 测试根据房源基础信息逻辑id查询房源基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:49:10
	 *
	 */
	@Test
	public void findHouseBaseExtDtoByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		HouseBaseExtDto houseBase = houseBaseMsgDao.findHouseBaseExtDtoByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(houseBase));
	}
	
	@Test
	public void findHousePicAuditVoTest(){
		HousePicAuditVo housePicAuditVo=houseBaseMsgDao.findHousePicAuditVo("8a9e9a9a556cece401556cece55f000f");
		System.out.println(JsonEntityTransform.Object2Json(housePicAuditVo));
	}
	
	@Test
	public void findPicUnapproveedHouseListTest(){
		HouseRequestDto houseRequest = new HouseRequestDto();
		PagingResult<HouseResultVo> list = houseBaseMsgDao.findPicUnapproveedHouseList(houseRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findPicUnapproveedRoomListTest(){
		HouseRequestDto houseRequest = new HouseRequestDto();

		List<String> landlordUidList = new ArrayList<String>(); 
		landlordUidList.add("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		houseRequest.setLandlordUidList(landlordUidList);
		PagingResult<HouseResultVo> list = houseBaseMsgDao.findPicUnapproveedRoomList(houseRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void getCommunityListByLandlordUidTest(){
		List<SearchTerm> list=houseBaseMsgDao.getCommunityListByLandlordUid("8a9e9a8b53d6da8b0153d6da8bae0000");
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void getHouseDetailTest(){
		HouseDetailDto houseDetailDto=new HouseDetailDto();
		houseDetailDto.setFid("8a9e9a9454801ac501548026fb610029");
		houseDetailDto.setRentWay(0);
		TenantHouseDetailVo tenantHouseDetailVo =houseBaseMsgDao.getHouseDetail(houseDetailDto);
		System.err.println(JsonEntityTransform.Object2Json(tenantHouseDetailVo));
	}
	
	@Test
	public void updateHousePicTest(){
		int upNum=houseBaseMsgDao.updateHousePic("8a9084df549ac80201549b2d0863057c");
		System.out.println(upNum);
	}
	
	@Test
	public void getHouseBaseDetailVoByFidTest(){
		HouseBaseDetailVo houseBaseDetailVo=houseBaseMsgDao.getHouseBaseDetailVoByFid("8a9e9a8b53d6089f0153d608a1f80002");
		System.out.println(JsonEntityTransform.Object2Json(houseBaseDetailVo));
	}
	
	@Test
	public void countByHouseSnTest(){
		
		String houseSn = HouseUtils.getHouseOrRoomSn("100000", randomUtil.getNumrOrChar(6, "num"),RentWayEnum.HOUSE.getCode(), null);
		if(!Check.NuNStr(houseSn)){
			int i = 0;
			while (i<3) {
			  Long count = 	this.houseBaseMsgDao.countByHouseSn(houseSn);
			  if(count>0){
				  i++;
				  houseSn = HouseUtils.getHouseOrRoomSn("100000",randomUtil.getNumrOrChar(6, "num"),RentWayEnum.HOUSE.getCode(), null);
				  continue;
			  }
			  break;
			}
		}
		
		Long i = houseBaseMsgDao.countByHouseSn("110100352003Z");
		
		System.out.println(i);
		
	}
	
	@Test
	public void findHouseCountByUidTest(){
		int count=houseBaseMsgDao.findHouseCountByUid("9afeae98-56ff-0c35-77cf-8624b2eefae");
		System.err.println(count);
	}

	@Test
	public void testgetHousePCList(){
		HouseBaseListDto dto = new HouseBaseListDto();
		dto.setRentWay(1);
		dto.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		dto.setPage(1);
		dto.setLimit(5);
		PagingResult<HouseRoomVo> housePCList = houseBaseMsgDao.getHousePCList(dto);
		System.out.println(1);
	}


	@Test
	public void getHouseCityVoByFidsTest(){
		List<String> houseFidList = new ArrayList<String>();
		houseFidList.add("8a9e9a8d55d9595a0155da35e6cd000c");
		houseFidList.add("8a9e9aa255da410d0155da410d750001");
		List<HouseCityVo> houseCityVoByFids = houseBaseMsgDao.getHouseCityVoByFids(houseFidList);
		System.err.println(JsonEntityTransform.Object2Json(houseCityVoByFids));
	}

	@Test
	public void findHouseRoomListTest(){
		HouseRoomListPcDto listPcDto=houseBaseMsgDao.findHouseRoomList("8a9e9aa255de7a570155de7a57950001");
		System.err.println(JsonEntityTransform.Object2Json(listPcDto));
	}
	@Test
	public void findHouseSubletByLandlordUidTest(){
		HouseBaseMsgEntity houseSublet = houseBaseMsgDao.findHouseSubletByLandlordUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		System.err.println(houseSublet);
	}
	
	@Test
	public void findSpecialHouseMsgListByHouseTest(){
		HouseRequestDto requestDto=new HouseRequestDto();
		requestDto.setRoleType(3);
		requestDto.setEmpCode("20137434");
		CurrentuserCityEntity userCityEntity=new CurrentuserCityEntity();
		userCityEntity.setNationCode("100000");
		userCityEntity.setProvinceCode("110000");
		requestDto.getUserCityList().add(userCityEntity);
		CurrentuserCityEntity userCityEntity1=new CurrentuserCityEntity();
		userCityEntity1.setNationCode("100000");
		userCityEntity1.setProvinceCode("310000");
		requestDto.getUserCityList().add(userCityEntity1);
		PagingResult<HouseResultVo> result=houseBaseMsgDao.findSpecialHouseMsgListByHouse(requestDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
		System.err.println(result.getTotal());
	}
	
	@Test
	public void findSpecialPicUnapproveedHouseListTest(){
		HouseRequestDto requestDto=new HouseRequestDto();
		PagingResult<HouseResultVo> result=houseBaseMsgDao.findSpecialPicUnapproveedHouseList(requestDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
		System.err.println(result.getTotal());
	}
	
	@Test
	public void findSpecialPicUnapproveedRoomListTest(){
		HouseRequestDto requestDto=new HouseRequestDto();
		requestDto.setRoleType(2);
		CurrentuserCityEntity userCityEntity1=new CurrentuserCityEntity();
		userCityEntity1.setNationCode("100000");
		userCityEntity1.setProvinceCode("310000");
		requestDto.getUserCityList().add(userCityEntity1);
		PagingResult<HouseResultVo> result=houseBaseMsgDao.findSpecialPicUnapproveedRoomList(requestDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
		System.err.println(result.getTotal());
	}
	
	@Test
	public void findHouseFidByAuthTest(){
		
		AuthMenuEntity authMenu = new AuthMenuEntity();
		authMenu.setEmpCode("20080808");
		authMenu.setRoleType(1);
		
		List<CurrentuserCityEntity> userCityList = new LinkedList<CurrentuserCityEntity>();
		
		CurrentuserCityEntity cur = new CurrentuserCityEntity();
		cur.setAreaCode("110102");
		cur.setCityCode("110100");
		cur.setProvinceCode("110000");
		cur.setNationCode("100000");
		userCityList.add(cur);
		authMenu.setUserCityList(userCityList);
	    List<String>  list = this.houseBaseMsgDao.findHouseFidByAuth(authMenu);
	    System.out.println(list);
	}
	
	@Test
	public void findNeedPhotographerHouseTest(){
		HouseRequestDto houseRequest = new HouseRequestDto();
		PagingResult<NeedPhotogHouseVo> list = this.houseBaseMsgDao.findNeedPhotographerHouse(houseRequest);
		System.out.println(list);
	}

	@Test
	public void getHouseListByHouseSnsTest(){
		List<String> houseSns = new ArrayList<String>();
		houseSns.add("110100352003Z");
		houseSns.add("110100952787Z");
		houseSns.add("110100363414Z");
		List<HouseBaseMsgEntity> result = houseBaseMsgDao.getHouseBaseListByHouseSns(houseSns);
		System.err.println(result.size());
	}
	
	@Test
	public void findNoticeLanDfbHouseMsg(){
		
		List<HouseDfbNoticeDto> list = this.houseBaseMsgDao.findNoticeLanDfbHouseMsg("2016-11-22 20:00:00",3);
		
		System.out.println(list);
	}

	@Test
	public void testfindHouseMsgListByHouseNew(){
		HouseRequestDto houseRequestDto = new HouseRequestDto();
		houseRequestDto.setPage(1);
		houseRequestDto.setLimit(10);
		houseRequestDto.setHouseSn("110118080935Z");
		houseRequestDto.setOrderType(0);
		//houseRequestDto.setDiscountEnum("ProductRulesEnum0019002");
		PagingResult<HouseResultNewVo> result=houseBaseMsgDao.findHouseMsgListByHouseNew(houseRequestDto);
		System.err.println(JsonEntityTransform.Object2Json(result));
	}

	@Test
	public void testFindHouseMsgListByRoomNew(){
		HouseRequestDto houseRequestDto = new HouseRequestDto();
		houseRequestDto.setPage(1);
		houseRequestDto.setLimit(10);
		houseRequestDto.setRoomSn("110186322893F");
		houseRequestDto.setOrderType(0);
//		houseRequestDto.setOrderType(0);
		//houseRequestDto.setDiscountEnum("ProductRulesEnum0019002");

		PagingResult<HouseResultNewVo> result=houseBaseMsgDao.findHouseMsgListByRoomNew(houseRequestDto);
		System.err.println(JsonEntityTransform.Object2Json(result));
	}
	
	@Test
	public void getLandlordHouseListTest(){
		HouseBaseListDto houseBaseListDto=new HouseBaseListDto();
		houseBaseListDto.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		houseBaseListDto.setHouseBaseFid("8a90a2d45a8497cb015a84c5cb26007a");
		PagingResult<HouseRoomVo> result=houseBaseMsgDao.getLandlordHouseList(houseBaseListDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
	}

	@Test
	public void getHouseOrRoomNameList(){
		HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
		houseBaseParamsDto.setHouseBaseFid("8a9e9a94547faae001547faae1660001");
		houseBaseParamsDto.setRentWay(1);
		houseBaseParamsDto.setRoomFid("8a9e9a94547fadb601547fadb77f0012");
		List<String> list = houseBaseMsgDao.getHouseOrRoomNameList(houseBaseParamsDto);
		System.out.print(houseBaseMsgDao.getHouseOrRoomNameList(houseBaseParamsDto));
	}

	@Test
	public void testFindSpecialHouseMsgListByHouseNew(){
		HouseRequestDto houseRequestDto = new HouseRequestDto();
		houseRequestDto.setPage(1);
		houseRequestDto.setLimit(10);
		houseRequestDto.setRoomSn("120115913343Z");
//		houseRequestDto.setOrderType(0);
		//houseRequestDto.setDiscountEnum("ProductRulesEnum0019002");

		PagingResult<HouseResultNewVo> result=houseBaseMsgDao.findSpecialHouseMsgListByHouseNew(houseRequestDto);
		System.err.println(JsonEntityTransform.Object2Json(result));
	}

	@Test
	public void testFindSpecialHouseMsgListByRoomNew(){
		HouseRequestDto houseRequestDto = new HouseRequestDto();
		houseRequestDto.setPage(1);
		houseRequestDto.setLimit(10);
		houseRequestDto.setRoomSn("110186322893F");
		houseRequestDto.setOrderType(1);
		houseRequestDto.setIsTogetherLandlord(0);
		//houseRequestDto.setDiscountEnum("ProductRulesEnum0019002");

		PagingResult<HouseResultNewVo> result=houseBaseMsgDao.findSpecialHouseMsgListByRoomNew(houseRequestDto);
		System.err.println(JsonEntityTransform.Object2Json(result));
	}
}
