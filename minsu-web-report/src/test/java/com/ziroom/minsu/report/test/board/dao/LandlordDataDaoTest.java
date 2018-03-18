package com.ziroom.minsu.report.test.board.dao;

import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.report.board.dto.LandlordRequest;
import com.ziroom.minsu.report.board.vo.LandlordDataItem;
import com.ziroom.minsu.report.board.dao.LandlordDataDao;
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
public class LandlordDataDaoTest extends BaseTest {

	@Resource(name = "report.landlordDataDao")
	private LandlordDataDao landlordDataDao;
	
	@Test
	public void findLandlordDataItemListTest() {
		LandlordRequest landlordRequest = new LandlordRequest();
		landlordRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
		List<LandlordDataItem> list = landlordDataDao.findLandlordDataItemList(landlordRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findLandlordDataItemListFromTaskTest() {
		LandlordRequest landlordRequest = new LandlordRequest();
		landlordRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
		landlordRequest.setQueryDate("2017-02-15");
		List<LandlordDataItem> list = landlordDataDao.findLandlordDataItemListFromTask(landlordRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
}