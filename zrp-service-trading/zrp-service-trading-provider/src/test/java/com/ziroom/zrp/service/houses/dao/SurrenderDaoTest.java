package com.ziroom.zrp.service.houses.dao;

import java.util.Date;

import com.alibaba.dubbo.common.json.JSON;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.SurrenderDao;
import com.ziroom.zrp.service.trading.pojo.CalSurrenderPojo;
import com.ziroom.zrp.trading.entity.SurrenderEntity;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月18日 13时06分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderDaoTest extends BaseTest {

    @Resource(name = "trading.surrenderDao")
    private SurrenderDao surrenderDao;

    @Test
    public void testgetSurAndSurCostInfoByConId(){
        CalSurrenderPojo surAndSurCostInfoByConId = surrenderDao.getSurAndSurCostInfoByConId("8a9099cb5de48f08015e227fc5261c48");
        System.out.println(surAndSurCostInfoByConId);
    }
    @Test
    public void testupdateSurrenderApplyTime(){
    	int i = surrenderDao.updateSurrenderApplyTime(45, "8a90a3ab582859eb01583e96114500c6");
    	System.out.println(i);
    }
    @Test
    public void testsaveSurrrender(){
    	SurrenderEntity surrender = new SurrenderEntity(); 
    	surrender.setRoomId("123456");
    	surrender.setContractId("65646946541");
    	surrender.setFsurrendercode("21561");
    	surrender.setSurrenderId("51651651");
    	surrender.setFapplicationdate(new Date());
    	surrender.setFexpecteddate(new Date());
    	int i = surrenderDao.saveSurrrender(surrender);
    	System.out.println(i);
    }
    @Test
    public void testselectSurrenderTimeByCode(){
    	Integer i = surrenderDao.selectSurrenderTimeByCode("SHZD081732377Z");
    	System.out.println(i);
    }
    @Test
    public void testfindSurrenderById(){
    	String surrenderId = "2c908d174e4d9a9c014e58c39b9001ac";
    	SurrenderEntity surrender = surrenderDao.findSurrenderById(surrenderId);
    	System.out.println(JsonEntityTransform.Object2Json(surrender));
    }
    @Test
    public void testdeleteSurrenderByContractId(){
    	String contractId = "8a9099cb5da77ecc015dbf59e56a0fa7";
    	System.out.println(surrenderDao.deleteSurrenderByContractId(contractId));
    }
}
