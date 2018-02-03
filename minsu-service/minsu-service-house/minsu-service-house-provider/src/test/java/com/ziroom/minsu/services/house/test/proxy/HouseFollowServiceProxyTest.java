/**
 * @FileName: HouseFollowServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2017年2月28日 下午3:34:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.dto.HouseFollowLogDto;
import com.ziroom.minsu.services.house.dto.HouseFollowUpdateDto;
import com.ziroom.minsu.services.house.proxy.HouseFollowServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.house.HouseLockEnum;

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
public class HouseFollowServiceProxyTest extends BaseTest{
	
	@Resource(name="house.houseFollowServiceProxy")
	private HouseFollowServiceProxy houseFollowServiceProxy;
	
	@Test
	public void findAttacheFollowHouseListTest(){
		HouseFollowDto houseFollowDto=new HouseFollowDto();
		houseFollowDto.setBeforeDate(DateUtil.dateFormat(DateUtils.addHours(new Date(), -24), "yyyy-MM-dd HH:mm:ss"));
		String resultJson=houseFollowServiceProxy.findAttacheFollowHouseList("{\"page\":1,\"limit\":50,\"startDate\":null,\"beforeDate\":null,\"attacheStartDate\":\"2017-04-19 11:43:32\",\"landlordUid\":null,\"houseSn\":\"110110361449Z\",\"landlordName\":null,\"landlordMobile\":null,\"empName\":null,\"followStatus\":null,\"nationCode\":null,\"provinceCode\":null,\"cityCode\":null,\"uidStr\":null,\"isNotLock\":null,\"operateFid\":null,\"operateDate\":null,\"auditCause\":null}");
		System.err.println(resultJson);
	}
	
	@Test
	public void updateHouseFollowByFid(){
		
		HouseFollowUpdateDto houseFollowUpdateDto = new HouseFollowUpdateDto();
		
		houseFollowUpdateDto.setFid("7508f3b9fcdd9e2e9c93bf0d7f8cc1a0");
		houseFollowUpdateDto.setFollowStatus(FollowStatusEnum.KFGJJS.getCode());
		houseFollowUpdateDto.getHouseFollowLogDto().setFollowDesc("杨东测试");
		HouseFollowLogDto houseFollowLogDto = houseFollowUpdateDto.getHouseFollowLogDto();
		houseFollowLogDto.setFollowUserFid("001");
		houseFollowLogDto.setFollowEmpCode("11111");
		houseFollowLogDto.setFollowEmpName("22222222");
		houseFollowLogDto.setFid(UUIDGenerator.hexUUID());
		houseFollowLogDto.setHouseLockCode(HouseLockEnum.KFGJWSHTGFY.getCode()+"");
		
		houseFollowUpdateDto.setHouseFollowLogDto(houseFollowLogDto);
		houseFollowUpdateDto.setFollowStatusOld(FollowStatusEnum.KFGJJS.getCode());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseFollowServiceProxy.updateHouseFollowByFid(JsonEntityTransform.Object2Json(houseFollowUpdateDto)));
		System.out.println(dto);
	}
}
