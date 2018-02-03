/**
 * @FileName: HouseBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author bushujie
 * @created 2016年4月1日 下午1:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseConfMsgDao;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * 
 * <p>房源配置信息dao测试类</p>
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
public class HouseConfMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseConfMsgDao")
    private HouseConfMsgDao houseConfMsgDao;

	@Test
	public void findGapFlexPriceTest(){
		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		houseConfMsg.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseConfMsg.setIsDel(2);
		List<HouseConfMsgEntity> gapFlexPriceList = houseConfMsgDao.findGapFlexPriceList(houseConfMsg);
		System.err.println(JsonEntityTransform.Object2Json(gapFlexPriceList));
	}

	@Test
	public void updateHouseConfByotherTest(){
		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		houseConfMsg.setHouseBaseFid("8a9e9a8b53d6089f0153d608a1f80002");
		houseConfMsg.setRoomFid("8a9e9a8b53d62d740153d62d76730002");
		houseConfMsg.setDicCode("ProductRulesEnum002003");
		houseConfMsg.setIsDel(2);
		int i = houseConfMsgDao.updateHouseConfMsgByother(houseConfMsg);
		System.err.println(i);
	}



	@Test
	public void insertHouseConfMsgTest(){
		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		houseConfMsg.setFid(UUIDGenerator.hexUUID());
		houseConfMsg.setHouseBaseFid("8a9084df54b27c7b0154b2c322320045");
		houseConfMsg.setDicCode("ProductRulesEnum0012003");
		houseConfMsg.setDicVal("0.10");
		houseConfMsgDao.insertHouseConfMsg(houseConfMsg);
	}
	
	@Test
	public void updateHouseConfMsgTest(){
		HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
		houseConfMsg.setFid("8a9e9a8c53da740e0153da740e1f0000");
		houseConfMsg.setHouseBaseFid(UUIDGenerator.hexUUID());
		houseConfMsg.setRoomFid(UUIDGenerator.hexUUID());
		houseConfMsg.setBedFid(UUIDGenerator.hexUUID());
		houseConfMsg.setDicCode("测试配置项code1");
		houseConfMsg.setDicVal("测试配置项value1");
		int line = houseConfMsgDao.updateHouseConfMsg(houseConfMsg);
		System.err.println(line);
	}
	
	@Test
	public void findHouseConfListTest(){
		List<HouseConfMsgEntity> list=houseConfMsgDao.findHouseConfList("8a9e9a8b53d6089f0153d608a1f80002");
		System.err.println(list);
	}
	
	@Test
	public void findHouseConfListByFidAndCodeTest(){
		String houseBaseFid = "8a9e9a8b53d6089f0153d608a1f80002";
		String enumCode = "测试code";
		List<String> list = houseConfMsgDao.findHouseConfListByFidAndCode(houseBaseFid, enumCode);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findHouseConfListByCodeTest(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", "8a9e9a9454801ac50154801ac5900001");
		paramMap.put("dicCode", "ProductRulesEnum002");
		List<HouseConfVo> list=houseConfMsgDao.findHouseConfVoList(paramMap);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}

    @Test
    public void testdelHouseRoomConf() {
        houseConfMsgDao.delHouseRoomConf("8a9e989e5d15df32015d169faf440074", "8a9e989e5d15df32015d16a34baf007f");
    }
    
	@Test
	public void delHouseConfByLikeCode(){
		houseConfMsgDao.delHouseConfByLikeCode("8a90a2d45d3b45d9015d3b6b1d130111","8a90a2d45d3b45d9015d3b6d231b011d" ,"ProductRulesEnum0024");
	}
    
    
}
