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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.services.house.dao.HouseOperateLogDao;
import com.ziroom.minsu.services.house.dto.HouseOpLogDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;

/**
 * <p>房源操作日志dao测试类</p>
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

public class HouseOperateLogDaoTest extends BaseTest{
	
	@Resource(name = "house.houseOperateLogDao")
    private HouseOperateLogDao houseOperateLogDao;
	
	@Test
	public void findHouseOperateLogListTest(){
		List<Integer> fromList = new ArrayList<Integer>();
		fromList.add(HouseStatusEnum.YFB.getCode());
		fromList.add(HouseStatusEnum.XXSHWTG.getCode());
		fromList.add(HouseStatusEnum.XXSHTG.getCode());
		fromList.add(HouseStatusEnum.ZPSHWTG.getCode());
		fromList.add(HouseStatusEnum.XJ.getCode());
		fromList.add(HouseStatusEnum.SJ.getCode());
		
		HouseOpLogDto houseOpLogDto = new HouseOpLogDto();
		houseOpLogDto.setFromList(fromList);
		houseOpLogDto.setHouseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseOpLogDto.setRentWay(0);
		
		PagingResult<HouseOperateLogEntity> list = houseOperateLogDao.findHouseOperateLogList(houseOpLogDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findRoomOperateLogListTest(){
		List<Integer> fromList = new ArrayList<Integer>();
		fromList.add(HouseStatusEnum.YFB.getCode());
		fromList.add(HouseStatusEnum.XXSHWTG.getCode());
		fromList.add(HouseStatusEnum.XXSHTG.getCode());
		fromList.add(HouseStatusEnum.ZPSHWTG.getCode());
		fromList.add(HouseStatusEnum.XJ.getCode());
		fromList.add(HouseStatusEnum.SJ.getCode());
		
		HouseOpLogDto houseOpLogDto = new HouseOpLogDto();
		houseOpLogDto.setFromList(fromList);
		houseOpLogDto.setHouseFid("8a9e9a8b53d62d740153d62d76730002");
		houseOpLogDto.setRentWay(1);
		
		PagingResult<HouseOperateLogEntity> list = houseOperateLogDao.findRoomOperateLogList(houseOpLogDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findFirstChangeStautsTest(){
		Map<String, Object> paMap=new HashMap<String, Object>();
		paMap.put("fromStatus", HouseStatusEnum.DFB.getCode());
		paMap.put("toStatus", HouseStatusEnum.YFB.getCode());
		paMap.put("houseFid", "8a90a2d45aa77596015aa77eb951000f");
		paMap.put("roomFid", "8a90a2d35aa77eb8015aa780879b0001");
		HouseOperateLogEntity houseOperateLogEntity=houseOperateLogDao.findFirstChangeStauts(paMap);
		System.err.println(JsonEntityTransform.Object2Json(houseOperateLogEntity));
	}
	
	@Test
	public void findToStatusNumTest(){
		int num=houseOperateLogDao.findToStatusNum("8a9e9a8b53d6089f0153d608a1f80002", HouseStatusEnum.YFB.getCode());
		System.err.println(num);
	}
	
}
