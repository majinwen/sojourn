package com.ziroom.minsu.services.order.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinancePayVouchersLogEntity;
import com.ziroom.minsu.entity.order.FinanceSyncLogEntity;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersLogDao;
import com.ziroom.minsu.services.order.dao.FinanceSyncLogDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

public class FinancePayVouchersLogDaoTest extends BaseTest {

	@Resource(name = "order.financePayVouchersLogDao")
	private FinancePayVouchersLogDao financePayVouchersLogDao;
	
	@Resource(name ="order.financeSyncLogDao")
	private FinanceSyncLogDao financeSyncLogDao;

	@Test
	public void insertFinancePayVouchersLog() {
		FinancePayVouchersLogEntity financePayVouchersLogEntity = new FinancePayVouchersLogEntity();
		financePayVouchersLogEntity.setFid(UUIDGenerator.hexUUID());
		financePayVouchersLogEntity.setPvSn(OrderSnUtil.getPvSn());
		financePayVouchersLogEntity.setOrderSn("123123");
		financePayVouchersLogEntity.setCallStatus(YesOrNoEnum.YES.getCode());
		financePayVouchersLogEntity.setResultCode("1234");
		financePayVouchersLogEntity.setResultMsg("成功");
		int num = financePayVouchersLogDao.insertFinancePayVouchersLog(financePayVouchersLogEntity);
		System.out.println("num:" + num);
	}
	
	@Test
	public void insertFinanceSyncLog() {
		FinanceSyncLogEntity fsle = new FinanceSyncLogEntity();
		fsle.setFid(UUIDGenerator.hexUUID());
		fsle.setSyncSn(OrderSnUtil.getPvSn());
		fsle.setOrderSn("123123");
		fsle.setCallStatus(YesOrNoEnum.YES.getCode());
		fsle.setResultCode("1234");
		fsle.setResultMsg("成功");
		int num = financeSyncLogDao.insertFinanceSyncLog(fsle);
		System.out.println("num:" + num);
	}
}
