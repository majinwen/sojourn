/**
 * 
 */
package com.ziroom.minsu.services.order.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinanceCashbackLogEntity;
import com.ziroom.minsu.services.order.dao.FinanceCashbackLogDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月9日
 * @since 1.0
 * @version 1.0
 */
public class FinanceCashbackLogDaoTest extends BaseTest {
	
	@Resource(name = "order.financeCashbackLogDao")
	private FinanceCashbackLogDao financeCashbackLogDao;
	
	
	@Test
	public void testSaveCashbackLog(){
		FinanceCashbackLogEntity cashbackLog = new FinanceCashbackLogEntity();
		cashbackLog.setFid(UUIDGenerator.hexUUID());
		cashbackLog.setCashbackSn("123");
		int saveCashbackLog = financeCashbackLogDao.saveCashbackLog(cashbackLog);
		System.err.println(saveCashbackLog);
	}
	
	@Test
	public void queryByCashbackSn(){
		List<FinanceCashbackLogEntity> queryByCashbackSn = financeCashbackLogDao.queryByCashbackSn("1606305EMS41D81630111");
		System.err.println(queryByCashbackSn);
	}
	
}
