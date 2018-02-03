package com.ziroom.minsu.services.order.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.services.order.dao.OrderHouseSnapshotDao;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.HouseSnapshotVo;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * 
 * <p>订单快照测试</p>
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
public class OrderHouseSnapshotDaoTest extends BaseTest{
	
    @Resource(name = "order.houseSnapshotDao")
    private OrderHouseSnapshotDao houseSnapshotDao;





    @Test
    public void findMaliceOrder(){

        houseSnapshotDao.findMaliceOrder(3);


    }



    @Test
    public void installLockByOrderSn(){

        houseSnapshotDao.installLockByOrderSn("160513D80S550X171825");


    }



    @Test
    public void TestfindHouseSnapshotByOrderSn(){

    	OrderHouseSnapshotEntity orderHouseSnapshotEntity = houseSnapshotDao.findHouseSnapshotByOrderSn("16051176ZD432U160931");
    	
    	System.out.println(orderHouseSnapshotEntity);

    }


    @Test
	public void insertOrderPayResTest(){
		OrderHouseSnapshotEntity ohse = new OrderHouseSnapshotEntity();
		ohse.setOrderSn(UUIDGenerator.hexUUID());
		ohse.setHouseFid(UUIDGenerator.hexUUID());
		ohse.setPicUrl("/8a9e9cd253d0597c0153d0597c760001/a.png");
		ohse.setPrice(34);
        ohse.setHouseName("house_name");
        ohse.setHouseAddr("ss");
        ohse.setRentWay(1);
        ohse.setOrderType(2);
        ohse.setCheckInTime("ss");
        ohse.setCheckOutTime("ss");
        ohse.setCheckOutRulesCode("cd");
        ohse.setDepositRulesCode("po");
        ohse.setDiscountRulesCode("dis");
		houseSnapshotDao.insertHouseSnapshotRes(ohse);
		
	}
    
    @Test
	public void findHouseSnapshotByCondictionTest(){
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("orderSn","8a9e9cd253d0b29d0153d0b29d460000");
    	paramMap.put("houseFid", "8a9e9cd253d0b29d0153d0b29d460001");
    	List<OrderHouseSnapshotEntity> oaeList = houseSnapshotDao.findHouseSnapshotByCondiction(paramMap);
	    System.out.println("----------------------"+JsonEntityTransform.Object2Json(oaeList));
    }
    
    @Test
    public void findHouseSnapshotByOrderTest(){
    	OrderRequest orderRequest = new OrderRequest();
    	
    	orderRequest.setUserUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
    	PagingResult<HouseSnapshotVo> listPagingResult = this.houseSnapshotDao.findHouseSnapshotByOrder(orderRequest);
    	
    	System.out.println(listPagingResult);
    }
    
}
