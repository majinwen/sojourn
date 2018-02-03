/**
 * @FileName: HousePriceConfDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月3日 下午11:37:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HousePriceConfEntity;
import com.ziroom.minsu.services.house.dao.HousePriceConfDao;
import com.ziroom.minsu.services.house.dto.HousePriceConfDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>特殊价格设置测试</p>
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
public class HousePriceConfDaoTest extends BaseTest{
	
	@Resource(name="house.housePriceConfDao")
	private HousePriceConfDao housePriceConfDao;


    @Test
    public void  findHousePriceConfByDate() throws Exception{
        HousePriceConfDto dto = new HousePriceConfDto();

        dto.setRentWay(1);
        dto.setRoomFid("8a9e9aae53e97a9e0153e97a9ecb0000");
        dto.setSetTime(DateUtil.parseDate("2016-05-26", "yyyy-MM-dd"));
        HousePriceConfEntity entity =
        housePriceConfDao.findHousePriceConfByDate(dto);
        System.out.println(JsonEntityTransform.Object2Json(entity));
    }


	@Test
	public void insertHousePriceConfTest() throws ParseException{
		HousePriceConfEntity housePriceConfEntity=new HousePriceConfEntity();
		housePriceConfEntity.setFid(UUIDGenerator.hexUUID());
		housePriceConfEntity.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		housePriceConfEntity.setSetTime(DateUtil.parseDate("2016-4-6", "yyyy-MM-dd"));
		housePriceConfEntity.setPriceVal(10000);
		housePriceConfEntity.setCreateUid("8a9e9a8b53d6089f0153d608a1f80002");
		housePriceConfDao.insertHousePriceConf(housePriceConfEntity);
	}
	
	@Test
	public void findSpecialPriceList() throws ParseException{
		LeaseCalendarDto leaseCalendarDto=new LeaseCalendarDto();
		leaseCalendarDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		leaseCalendarDto.setStartDate(DateUtil.parseDate("2016-4-22", "yyyy-MM-dd"));
		leaseCalendarDto.setEndDate(DateUtil.parseDate("2016-5-12", "yyyy-MM-dd"));
		List<SpecialPriceVo> list=housePriceConfDao.findSpecialPriceList(leaseCalendarDto);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void deleteHousePriceConfByFid() throws ParseException{
		String fidString="8a9e9aa0543903d201543903d28d0000";
		int n =housePriceConfDao.deleteHousePriceConfByFid(fidString);
		System.err.println(n);
	}
	
	@Test
	public void getLastModifyCalendarDate() throws ParseException{
		Date date =housePriceConfDao.getLastModifyCalendarDate("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		System.err.println(date);
	}

}
