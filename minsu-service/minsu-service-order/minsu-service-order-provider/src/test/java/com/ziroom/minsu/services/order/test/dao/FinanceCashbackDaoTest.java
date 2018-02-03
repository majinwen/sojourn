package com.ziroom.minsu.services.order.test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.services.order.dao.FinanceCashbackDao;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.entity.AuditCashbackVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;

public class FinanceCashbackDaoTest extends BaseTest {
	
	
	@Resource(name = "order.financeCashbackDao")
	private FinanceCashbackDao financeCashbackDao;
	
	
	@Test
	public void queryByCashbackSns(){
		List<String> cashbackSns = new ArrayList<>();
		cashbackSns.add("1606305EMS41D8163011_3");
		cashbackSns.add("1606305EMS41D8163011_4");
		List<FinanceCashbackEntity> queryByOrderSn = financeCashbackDao.queryByCashbackSns(cashbackSns);
		System.err.println(JsonEntityTransform.Object2Json(queryByOrderSn));
		
	}
	
	
	@Test
	public void queryByCondition(){
		AuditCashbackQueryRequest request = new AuditCashbackQueryRequest();
		request.setOperTimeStart("2017-05-19 12:00:00");
		request.setRoleType(RoleTypeEnum.ADMIN.getCode());
		PagingResult<AuditCashbackVo> queryByCondition = financeCashbackDao.queryByCondition(request);
		System.err.println(JsonEntityTransform.Object2Json(queryByCondition));
	}

	
	@Test
	public void testCountByOrderSn(){
		long countByOrderSn = financeCashbackDao.countByOrderSn("123");
		System.err.println(countByOrderSn);
	}
	
	@Test
	public void testSaveCashback(){
		FinanceCashbackEntity cashback = new FinanceCashbackEntity();
		cashback.setCashbackSn("2");
		cashback.setCashbackStatus(10);
		cashback.setOrderSn("123");
		cashback.setActSn("huodongma");
		cashback.setReceiveType(1);
		cashback.setReceiveUid("awsdfqwerqwerqwe");
		cashback.setTotalFee(10000);
		int saveCashback = financeCashbackDao.saveCashback(cashback);
		System.err.println(saveCashback);
	}
	
	
	@Test
	public void auditCashbacks(){
		int num = financeCashbackDao.auditCashback("160630MU4JORXK1505311", "123", "321");
		System.err.println(num);
	}
	
}
