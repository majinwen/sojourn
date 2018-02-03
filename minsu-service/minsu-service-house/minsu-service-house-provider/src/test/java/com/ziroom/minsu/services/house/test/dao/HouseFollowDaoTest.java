/**
 * @FileName: HouseFollowDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2017年2月23日 下午5:50:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.house.dto.HouseFollowListDto;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dao.HouseFollowDao;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

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
public class HouseFollowDaoTest extends BaseTest{

	@Resource(name="house.houseFollowDao")
	private HouseFollowDao houseFollowDao;

	@Test
	public void findServicerFollowLandlordListTest(){
		HouseFollowDto houseFollowDto=new HouseFollowDto();
		houseFollowDto.setBeforeDate("2017-03-03 08:59:30");
		houseFollowDto.setAttacheStartDate("2017-03-02 20:59:30");
		houseFollowDto.setStartDate(HouseConstant.HOUSE_FOLLOW_START_TIME);
		//houseFollowDto.setUidStr("'664524c5-6e75-ee98-4e0d-667d38b9eee1','d185f535-2b4c-4dc3-8d9a-2eafab152ef4'");
		PagingResult<HouseFollowVo> result=houseFollowDao.findServicerFollowLandlordList(houseFollowDto);
		System.err.println(JsonEntityTransform.Object2Json(result.getRows()));
	}

	@Test
	public void findServicerFollowHouseListByLandlordTest(){
		HouseFollowDto houseFollowDto=new HouseFollowDto();
		houseFollowDto.setBeforeDate(DateUtil.dateFormat(DateUtils.addHours(new Date(), -12), "yyyy-MM-dd HH:mm:ss"));
		houseFollowDto.setStartDate(HouseConstant.HOUSE_FOLLOW_START_TIME);
		houseFollowDto.setLandlordUid("3a59968c-1eb9-4612-e325-5cc0a856ac34");
		houseFollowDto.setFollowStatus(101);
		List<HouseFollowVo> list=houseFollowDao.findServicerFollowHouseListByLandlord(houseFollowDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

	@Test
	public void testlistHouseFollowAll(){
		HouseFollowListDto houseFollowListDto = new HouseFollowListDto();
		houseFollowListDto.setHouseBaseFid("8a9084df574702e701574706878f0017");
		//houseFollowListDto.setRoomFid("");
		houseFollowListDto.setRentWay(0);
		List<Integer> list = new ArrayList<>();
		list.add(FollowStatusEnum.KFDGJ.getCode());
		list.add(FollowStatusEnum.KFGJZ.getCode());
		list.add(FollowStatusEnum.KFWLXSFD.getCode());
		list.add(FollowStatusEnum.KFDGJ.getCode());
		list.add(FollowStatusEnum.ZYDGJ.getCode());
		list.add(FollowStatusEnum.ZYGJZ.getCode());
		houseFollowListDto.setStatusList(list);
		houseFollowDao.listHouseFollowAll(houseFollowListDto);
	}

	@Test
	public void findAttacheFollowLandlordList(){

		HouseFollowDto houseFollowDto = new HouseFollowDto();
		houseFollowDto.setOperateDate("2017-04-17 23:59:59");
		houseFollowDto.setAuditCause("11");
		PagingResult<HouseFollowVo> list = 	houseFollowDao.findAttacheFollowLandlordList(houseFollowDto);

		System.out.println(list);
	}
}
