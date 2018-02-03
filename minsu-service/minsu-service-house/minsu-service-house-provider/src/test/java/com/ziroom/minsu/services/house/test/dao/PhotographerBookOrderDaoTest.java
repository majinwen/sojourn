/**
 * @FileName: PhotographerBookOrderDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午7:21:10
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.house.dao.PhotographerBookOrderDao;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.photographer.BookOrderSourceEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.photographer.BusinessTypeEnum;

/**
 * <p>预约订单测试</p>
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
public class PhotographerBookOrderDaoTest extends BaseTest{

	
	@Resource(name = "photographer.photographerBookOrderDao")
	private PhotographerBookOrderDao photographerBookOrderDao;
	@Test
	public void savePhotographerBookOrderTest() throws ParseException {
		
		PhotographerBookOrderEntity photographerBookOrder = new PhotographerBookOrderEntity();
		
		photographerBookOrder.setBookEndTime(DateUtil.parseDate("2016-11-08 23:00:00", "yyyy-MM-dd HH:mm:ss"));
		photographerBookOrder.setBookerMobile("18701482472");
		photographerBookOrder.setBookerName("杨东");
		photographerBookOrder.setBookerUid("00300CB2213DDACBE05010AC69062479");
		photographerBookOrder.setBookOrderRemark("刘军预约");
		photographerBookOrder.setBookOrderSn(SnUtil.getBookOrderSn());
		photographerBookOrder.setBookOrderSource(BookOrderSourceEnum.MINSU_TROY.getCode());
		photographerBookOrder.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOKING.getCode());
		photographerBookOrder.setBookStartTime(DateUtil.parseDate("2016-11-06 23:00:00", "yyyy-MM-dd HH:mm:ss"));
		photographerBookOrder.setBusinessType(BusinessTypeEnum.BUSINESS_MINSU.getCode());
		photographerBookOrder.setCustomerMobile("18701482473");
		photographerBookOrder.setCustomerName("叫号机1");
		photographerBookOrder.setCustomerUid("00300CB2213DDACBE05010AC69062479");
		photographerBookOrder.setFid(UUIDGenerator.hexUUID());
		photographerBookOrder.setHouseSn("110100835738Z");
		photographerBookOrder.setPhotographerMobile("18701482474");
		photographerBookOrder.setPhotographerName("摄影师A");
		photographerBookOrder.setPhotographerUid("8a9e9aaf5456a3aa015456a3aacf0000");
		photographerBookOrder.setShotAddr("北京市东城区将台路5号院 1号楼4单元1304号");
		photographerBookOrder.setAppointPhotogDate(new Date());
		
		int i = this.photographerBookOrderDao.savePhotographerBookOrder(photographerBookOrder);
		
		System.out.println(i);
	}

	
	@Test
	public void queryPhotographerBookOrderByHouseFidTest(){
		
		List<PhotographerBookOrderEntity> list = this.photographerBookOrderDao.queryPhotographerBookOrderByHouseFid("8a9e9cd955dd0db90155dd0dba760001");
		System.out.println();
	}
	
	@Test
	public void findPhotographerBookOrder(){
		BookHousePhotogDto bookOrderDto = new BookHousePhotogDto();
//		bookOrderDto.setHouseFid("8a9e98945726bd03015726bd03720001");
//		bookOrderDto.setBookStartTimeStr("2016-10-06");
//		bookOrderDto.setBookEndTimeStr("2016-11-09");
		
		
		PagingResult<PhotographerBookOrderEntity> list =photographerBookOrderDao.findPhotographerBookOrder(bookOrderDto);
		
		System.err.println(JSON.toJSONString(list));
	}
}
