/**
 * @FileName: TonightDiscountDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2017年5月10日 下午3:58:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.services.house.dao.TonightDiscountDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class TonightDiscountDaoTest extends BaseTest{
	
	@Resource(name="house.tonightDiscountDao")
	private TonightDiscountDao tonightDiscountDao;
	
	/**
	 * 
	 * 插入今夜特价测试
	 *
	 * @author bushujie
	 * @created 2017年5月10日 下午4:02:28
	 *
	 */
	@Test
	public void insertTonightDiscountTest(){
		TonightDiscountEntity tonightDiscountEntity=new TonightDiscountEntity();
		tonightDiscountEntity.setFid(UUIDGenerator.hexUUID());
		tonightDiscountEntity.setCreateUid(UUIDGenerator.hexUUID());
		tonightDiscountEntity.setDiscount(0.75);
//		Date date = new Date();
//		try {
//			date = DateUtil.parseDate("2017-05-10", "yyyy-MM-dd");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println("date " + date);
//		tonightDiscountEntity.setDiscountDate(date);
		tonightDiscountEntity.setDiscountSource(1);
//		tonightDiscountEntity.setHouseFid(UUIDGenerator.hexUUID());
		tonightDiscountEntity.setHouseFid("8a90a2d4549341c60154940bb37b00e1");
//		tonightDiscountEntity.setRoomFid(UUIDGenerator.hexUUID());
		tonightDiscountEntity.setRentWay(0);
		Date date = new Date();
		Calendar ca= Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR, 8);
		tonightDiscountEntity.setStartTime(date);
		tonightDiscountEntity.setEndTime(ca.getTime());
		tonightDiscountDao.insertTonightDiscount(tonightDiscountEntity);
	}

	@Test
	public void findTonightDiscountByCondition(){
		TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
		tonightDiscountEntity.setHouseFid("8a9e98b45bf22ca4015bf22ca4bd0002");
		tonightDiscountEntity.setRoomFid("8a9e98b45bf22ca4015bf22ca4bd0003");
		tonightDiscountEntity.setRentWay(1);
		Date date = new Date();
		try {
			date = DateUtil.parseDate("2017-05-10", "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tonightDiscountEntity.setDiscountDate(date);
		System.err.println(JsonEntityTransform.Object2Json(tonightDiscountEntity));
		List<TonightDiscountEntity> result = tonightDiscountDao.findTonightDiscountByCondition(tonightDiscountEntity);
		Assert.assertTrue(result.size() == 1);
	}
}
