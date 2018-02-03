package com.ziroom.minsu.services.order.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.services.order.dao.AccountFillLogDao;
import com.ziroom.minsu.services.order.dto.AccountFillLogRequest;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FillTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * 
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年5月4日
 * @since 1.0
 * @version 1.0
 */
public class AccountFillLogDaoTest extends BaseTest {

	@Resource(name = "order.accountFillLogDao")
	private AccountFillLogDao accountFillLogDao;
	
	@Test
	public void insertAccountFillLogResTest(){
		AccountFillLogEntity afe = new AccountFillLogEntity();
		afe.setFillSn(OrderSnUtil.getFillSn());
		afe.setOrderSn(UUIDGenerator.hexUUID());
		afe.setBussinessType(1);
		afe.setPayTime(new Date());
		afe.setPayType("zfb_pay");
		afe.setResultMsg("tttttttt");
		afe.setFillMoneyType(com.ziroom.minsu.valenum.account.ConsumeTypeEnum.filling_freeze.getCode());
		afe.setCityCode("BJ");
		afe.setFillType(FillTypeEnum.cash.getCode());
		afe.setTradeNo(UUIDGenerator.hexUUID());
		afe.setTargetUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		afe.setTargetType(CustomerTypeEnum.roomer.getStatusCode());
		afe.setTotalFee(2000);
		afe.setFillStatus(YesOrNoEnum.NO.getCode());
		accountFillLogDao.insertAccountFillLogRes(afe);
	}

	@Test
	public void taskGetFillFailListTest() {
		AccountFillLogRequest taskRequest = new AccountFillLogRequest();
        taskRequest.setLimit(100);

		// List<String> list = accountFillLogDao.taskGetFillFailList(taskRequest);
		// System.out.println(list);
	}

}
