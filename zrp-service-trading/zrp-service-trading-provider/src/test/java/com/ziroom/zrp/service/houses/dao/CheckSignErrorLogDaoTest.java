package com.ziroom.zrp.service.houses.dao;


import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.CheckSignErrorLogDao;
import com.ziroom.zrp.service.trading.dao.RentCheckinPersonDao;
import com.ziroom.zrp.trading.entity.CheckSignErrorLogEntity;
import org.junit.Test;

import javax.annotation.Resource;


public class CheckSignErrorLogDaoTest extends BaseTest{



	@Resource(name = "trading.checkSignErrorLogDao")
	private CheckSignErrorLogDao checkSignErrorLogDao;

	@Test
	public void testSave(){
		CheckSignErrorLogEntity checkSignErrorLogEntity = new CheckSignErrorLogEntity();
		checkSignErrorLogEntity.setContractId("jfkdsjlfjal545f65d");
		checkSignErrorLogEntity.setErrMsg("错误日志");
		int result = checkSignErrorLogDao.saveCheckSignErrorLog(checkSignErrorLogEntity);
		System.err.println(result);
	}

	@Test
	public void test(){
		String paramJson = null;
		CheckSignErrorLogEntity checkSignErrorLogEntity = JsonEntityTransform.json2Entity(paramJson,CheckSignErrorLogEntity.class);
		System.err.println(checkSignErrorLogEntity);
	}

	@Test
	public void testSave1()throws Exception{
		CheckSignErrorLogEntity checkSignErrorLogEntity = new CheckSignErrorLogEntity();
		checkSignErrorLogEntity.setContractId("jfkdsjlfjal545f65d");
		checkSignErrorLogEntity.setErrMsg("河南省郑州bbbbbcccccddddd");
		System.err.println("string 长度： "+ "河南省郑州bbbbbcccccddddd".length());
		System.err.println("长度为："+"河南省郑州bbbbbcccccddddd".getBytes("utf-8").length);
		int result = checkSignErrorLogDao.saveCheckSignErrorLog(checkSignErrorLogEntity);
		System.err.println(result);
	}

}
