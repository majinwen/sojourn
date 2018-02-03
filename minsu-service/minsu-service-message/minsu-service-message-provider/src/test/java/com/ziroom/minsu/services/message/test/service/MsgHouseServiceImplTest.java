/**
 * @FileName: MsgHouseServiceImplTest.java
 * @Package com.ziroom.minsu.services.message.test.service
 * 
 * @author yd
 * @created 2016年4月18日 下午2:36:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.service.MsgHouseServiceImpl;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>房源消息服务层的测试service</p>
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
public class MsgHouseServiceImplTest extends BaseTest {

	@Resource(name = "message.msgHouseServiceImpl")
	private MsgHouseServiceImpl msgHouseServiceImpl;
	@Test
	public void queryByPageTest() {

		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		PagingResult<MsgHouseEntity> listPagingResult = this.msgHouseServiceImpl.queryByPage(msgHouseRequest);
		System.out.println(listPagingResult);
	}

	@Test
	public void saveTest() {
		
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setBedFid("F45fdsfdsf6DS4F");
		msgHouseEntity.setCreateTime(new Date());
		msgHouseEntity.setHouseFid("fds45f6");
		msgHouseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgHouseEntity.setLandlordUid("f45fdsfdsfds64f");
		msgHouseEntity.setRentWay(RentWayEnum.HOUSE.getCode());
		msgHouseEntity.setTenantUid("4f5ds6f45d6sf");
		int index = this.msgHouseServiceImpl.save(msgHouseEntity);
		
		System.out.println(index);
	}
	@Test
	public void updateByFidTest(){
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgHouseEntity.setFid("8a9e9c8b541e32c001541e32c0150000");
		
		int index  = this.msgHouseServiceImpl.updateByFid(msgHouseEntity);
		
		System.out.println(index);
	}
	@Test
	public void deleteByFidTest(){
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setFid("8a9e9c8b541e32c001541e32c0150000");
		DataTransferObject dto = this.msgHouseServiceImpl.deleteByFid(msgHouseEntity);
		
		System.out.println(dto);
	}
}
