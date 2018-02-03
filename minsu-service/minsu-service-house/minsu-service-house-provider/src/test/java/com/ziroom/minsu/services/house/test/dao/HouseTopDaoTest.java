/**
 * @FileName: HouseTopDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2017年3月17日 下午5:02:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseTopEntity;
import com.ziroom.minsu.services.house.dao.HouseTopDao;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.dto.HouseTopSaveDto;
import com.ziroom.minsu.services.house.entity.HouseTopDetail;
import com.ziroom.minsu.services.house.entity.HouseTopListVo;
import com.ziroom.minsu.services.house.entity.HouseTopVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;


/**
 * <p>TODO</p>
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
public class HouseTopDaoTest extends BaseTest{

	
	@Resource(name = "house.houseTopDao")
	private HouseTopDao houseTopDao;
	@Test
	public void insertHouseTopTest() {
		
	
		
		HouseTopEntity houseTop = new HouseTopEntity();
		
		houseTop.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseTop.setIsDel(IsDelEnum.NOT_DEL.getCode());
		houseTop.setRentWay(RentWayEnum.HOUSE.getCode());
		houseTop.setTopMiddlePic("http://10.16.34.44:8000/minsu/group1/M00/00/34/ChAiMFc6Lx2ALL41AABoXASCvmY306.jpg_Z_720_480.jpg");
		houseTop.setTopShareTitle("TOP50测试分享标题");
		houseTop.setTopTitle("该民宿入选年度发现TOP 50");
		houseTop.setTopTitlePic("http://10.16.34.44:8000/minsu/group1/M00/02/51/ChAiMFithByAOESRAAXUD2pQnP828.jpeg_Z_120_120.jpg");
		houseTop.setTopSort(1);
		houseTop.setCreateFid("9afeae98-56ff-0c35-77cf-8624b2e1efae");
		
		HouseTopSaveDto houseTopEntity=new HouseTopSaveDto();
		BeanUtils.copyProperties(houseTop, houseTopEntity);
		System.err.println(JsonEntityTransform.Object2Json(houseTopEntity));
		//int i = this.houseTopDao.insertHouseTop(houseTop);
		
		//System.out.println(i);
	}

	@Test
	public void findTopHouseListPageTest(){
		HouseTopDto houseTopDto=new HouseTopDto();
		houseTopDto.setTopStatus(0);
		PagingResult<HouseTopListVo> pagresult=houseTopDao.findTopHouseListPage(houseTopDto);
		System.err.println(pagresult.getTotal());
		System.err.println(JsonEntityTransform.Object2Json(pagresult.getRows()));
	}
	
	@Test
	public void findHouseTopVoByHouseFidTest(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseBaseFid", "8a9e9a8b53d6089f0153d608a1f80002");
		paramMap.put("rentWay", 0);
		paramMap.put("topStatus", 1);
		HouseTopVo houseTopVo = this.houseTopDao.findHouseTopVoByHouse(paramMap);
		
		System.out.println(houseTopVo);
	}
	
	@Test
	public void findHouseTopDetailTest(){
		HouseTopDetail houseTopDetail=houseTopDao.findHouseTopDetail("55ffbf8ef5526bf5fb66ea74bbb40a2d");
		System.err.println(JsonEntityTransform.Object2Json(houseTopDetail));
	}

	@Test
	public void testUpdateHouseTopByTopSort(){
		int i = houseTopDao.updateHouseTopByTopSort(3, 2);
		System.err.println(i);
	}

	@Test
	public void testUpdateHouseTopByfid(){
		HouseTopEntity houseTop = new HouseTopEntity();
		houseTop.setFid("55ffbf8ef5526bf5fb66ea74bbb40a2d");
		houseTop.setTopSort(20);
		int i = houseTopDao.updateHouseTopByfid(houseTop);
		System.err.println(i);
	}
}
