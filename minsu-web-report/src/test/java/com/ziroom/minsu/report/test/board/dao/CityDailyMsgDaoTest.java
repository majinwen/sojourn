package com.ziroom.minsu.report.test.board.dao;

import javax.annotation.Resource;

import com.ziroom.minsu.report.board.dao.CityDailyMsgDao;
import org.junit.Test;

import base.BaseTest;

import com.asura.framework.base.util.JsonEntityTransform;

/**
 * 
 * <p>目标看板-大区数据</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CityDailyMsgDaoTest extends BaseTest {

	@Resource(name = "report.cityDailyMsgDao")
	private CityDailyMsgDao cityDailyMsgDao;
	
	@Test
	public void getTotalUpNumByCityCodeListTest() {
		String cityCode = "110100";
		long num = cityDailyMsgDao.getTotalUpNumByCityCode(cityCode);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getLockNumByCityCodeAndLockDateListTest() {
		String cityCode = "110100";
		String lockDate = "2017-01-01";
		long num = cityDailyMsgDao.getLockNumByCityCodeAndLockDate(cityCode, lockDate );
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getIssueNumByCityCodeAndStatDateListTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getIssueNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getInitPushUpNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getInitPushUpNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getUpNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getUpNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getDownNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getDownNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getFinalUpNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getFinalUpNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getFinalDownNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getFinalDownNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getBookOrderNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getBookOrderNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getPayOrderNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getPayOrderNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getRoomNightNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getRoomNightNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getConsultNumByCityCodeAndStatDateTest() {
		String cityCode = "110100";
		String statDate = "2017-01-01";
		long num = cityDailyMsgDao.getConsultNumByCityCodeAndStatDate(cityCode, statDate);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getExpLandNumByCityCodeTest() {
		String cityCode = "110100";
		long num = cityDailyMsgDao.getExpLandNumByCityCode(cityCode);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getNonProLandNumByCityCodeTest() {
		String cityCode = "110100";
		long num = cityDailyMsgDao.getNonProLandNumByCityCode(cityCode);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
	
	@Test
	public void getProLandNumByCityCodeTest() {
		String cityCode = "110100";
		long num = cityDailyMsgDao.getProLandNumByCityCode(cityCode);
		System.err.println(JsonEntityTransform.Object2Json(num));
	}
}