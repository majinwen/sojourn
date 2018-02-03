/**
 * @FileName: MsgHouseDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2016年4月16日 下午4:16:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.message.dao.MsgHouseDao;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房源留言持久化测试</p>
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
public class MsgHouseDaoTest extends BaseTest{

	
	@Resource(name = "message.msgHouseDao")
	private MsgHouseDao msgHouseDao;
	@Test
	public void saveTest() {
		
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setBedFid("F456DS4F");
		msgHouseEntity.setCreateTime(new Date());
		msgHouseEntity.setHouseFid("fds45f6");
		msgHouseEntity.setIsDel(0);
		msgHouseEntity.setLandlordUid("f45ds64f");
		msgHouseEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		msgHouseEntity.setTenantUid("4f5ds6f45d6sf");
		int index = this.msgHouseDao.save(msgHouseEntity);
		
		System.out.println(index);
	}

	
	@Test
	public void updateByFidTest(){
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setBedFid("F456DS4F");
		msgHouseEntity.setCreateTime(new Date());
		msgHouseEntity.setHouseFid("fds45f6");
		msgHouseEntity.setIsDel(1);
		msgHouseEntity.setLandlordUid("f45ds64f");
		msgHouseEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		msgHouseEntity.setTenantUid("4f5ds6f45d6sf");
		msgHouseEntity.setFid("8a9e9c8b541e32c001541e32c0150000");
		
		int index  = this.msgHouseDao.updateByFid(msgHouseEntity);
		
		System.out.println(index);
	}
	
	@Test
	public void queryByPageTest(){
		
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		PagingResult<MsgHouseEntity> lisPagingResult = this.msgHouseDao.queryByPage(msgHouseRequest);
		
		System.out.println(lisPagingResult);
	}
	
	@Test
	public void testTenantMsgList(){
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setTenantUid("4f5ds6f45d6sf");
		PagingResult<MsgHouseListVo> queryTenantMsgList = msgHouseDao.queryTenantMsgList(msgHouseRequest);
		System.out.println(queryTenantMsgList);
	}
	
	@Test
	public void queryFriendsUidTest(){
	
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		//msgHouseRequest.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		msgHouseRequest.setTenantUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		List<String> list = this.msgHouseDao.queryFriendsUid(msgHouseRequest);
		
		System.out.println(list);
	}
	
	@Test
	public void queryConsultNumByHouseFidTest(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", DateUtil.dateFormat(new Date()));
		paramMap.put("endTime", DateUtil.getDayAfterCurrentDate());
		List<HouseStatsVo> list = this.msgHouseDao.queryConsultNumByHouseFid(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
}
