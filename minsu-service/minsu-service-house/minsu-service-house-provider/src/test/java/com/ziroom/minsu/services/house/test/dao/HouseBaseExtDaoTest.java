/**
 * @FileName: HouseBaseExtDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月8日 下午4:17:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.IsPhotographyEnum;

/**
 * <p>楼兰基础测试</p>
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
public class HouseBaseExtDaoTest extends BaseTest{
	
	@Resource(name="house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;
	
	/**
	 * 
	 * 插入房源基础信息扩展测试
	 *
	 * @author bushujie
	 * @created 2016年4月8日 下午4:19:41
	 *
	 */
	@Test
	public void insertHouseBaseExtTest(){
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setFid(UUIDGenerator.hexUUID());
		houseBaseExtEntity.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80004");
		houseBaseExtEntity.setBuildingNum("22号楼");
		houseBaseExtEntity.setUnitNum("1单元");
		houseBaseExtEntity.setFloorNum("5楼");
		houseBaseExtEntity.setHouseNum("501");
		houseBaseExtEntity.setHouseStreet("将台路12222");
		houseBaseExtEntity.setMinDay(5);
		houseBaseExtEntity.setRentRoomNum(4);
		houseBaseExtDao.insertHouseBaseExt(houseBaseExtEntity);
	}
	
	/**
	 * 
	 * 更新房源基础信息扩展测试
	 *
	 * @author liujun
	 * @created 2016年4月9日 上午11:40:33
	 *
	 */
	@Test
	public void updateHouseBaseExtTest(){
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setFid("8a9e989e5cafd8d3015cafd8d3890000");
		houseBaseExtEntity.setBuildingNum("22号楼");
		houseBaseExtEntity.setUnitNum("1单元");
		houseBaseExtEntity.setFloorNum("5楼");
		houseBaseExtEntity.setHouseNum("501");
		houseBaseExtEntity.setHouseStreet("酒仙桥东");
		houseBaseExtEntity.setRentRoomNum(4);
		houseBaseExtDao.updateHouseBaseExt(houseBaseExtEntity);
		
	}
	
	@Test
	public void getHouseBaseExtByHouseBaseFidTest(){

		HouseBaseExtEntity houseBaseExtEntity = houseBaseExtDao.getHouseBaseExtByHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80004");
		System.out.println(houseBaseExtEntity);
	}
	
	@Test
	public void findBaseExtVoByHouseBaseFidTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		HouseBaseExtVo houseBaseExt = houseBaseExtDao.findBaseExtVoByHouseBaseFid(houseBaseFid);
		System.err.println(JsonEntityTransform.Object2Json(houseBaseExt));
	}
	
	@Test
	public void updateHouseBaseExtByHouseBaseFidTest(){
		HouseBaseExtVo houseBaseExtVo = new HouseBaseExtVo();
		houseBaseExtVo.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80004");
		houseBaseExtVo.setIsPhotography(IsPhotographyEnum.IS_PHOTOGRAPHY.getCode());
		houseBaseExtVo.setRentRoomNum(2);
		int updateHouseBaseExtByHouseBaseFid = houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtVo);
		System.out.println(updateHouseBaseExtByHouseBaseFid);
	}
	
}
