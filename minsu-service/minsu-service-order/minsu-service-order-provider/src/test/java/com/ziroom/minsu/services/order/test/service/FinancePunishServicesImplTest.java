package com.ziroom.minsu.services.order.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.order.FinancePunishEntity;
import com.ziroom.minsu.services.finance.dto.PunishListRequest;
import com.ziroom.minsu.services.order.entity.FinancePunishVo;
import com.ziroom.minsu.services.order.service.FinancePunishServicesImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

public class FinancePunishServicesImplTest extends BaseTest {

	@Resource(name = "order.financePunishServicesImpl")
	private FinancePunishServicesImpl financePunishServices;



	@Test
	public void TestGetPunishListByCondition() {
		PunishListRequest punishRequest = new PunishListRequest();
		punishRequest.setLimit(10);
		punishRequest.setPage(1);
		punishRequest.setPunishUid("1111");

		PagingResult<FinancePunishEntity> punishList = financePunishServices.getPunishListByCondition(punishRequest);
		System.out.println("punishList:" + punishList);
	}
	
	@Test
	public void TestUpdateByPunishSn(){
		FinancePunishEntity financePunishEntity = new FinancePunishEntity();
		financePunishEntity.setPunishSn("KK160425N77JFQMI115341");
		financePunishEntity.setPunishDescribe("房东取消罚款修改1");
		int num = financePunishServices.updateByPunishSn(financePunishEntity);
		System.out.println("num:"+num);
	}
	
	@Test
	public void TestGetPunishByPunishSn(){
		FinancePunishEntity financePunishEntity = financePunishServices.getPunishByPunishSn("KK160425N77JFQMI115341");
		System.out.println("financePunishEntity:"+financePunishEntity);
	}
}
