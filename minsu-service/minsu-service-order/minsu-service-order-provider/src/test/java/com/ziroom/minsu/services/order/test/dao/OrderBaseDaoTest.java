package com.ziroom.minsu.services.order.test.dao;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.order.dao.OrderBaseDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>订单类测测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderBaseDaoTest extends BaseTest {


    @Resource(name = "order.orderBaseDao")
    private OrderBaseDao orderBaseDao;



    @Test
    public void getOrderSnList4Lock(){

        List<String> num = orderBaseDao.getOrderSnList4LockByHouse("8a90a2d4549ac7990154a3ee6eee0237");
        System.out.println(JsonEntityTransform.Object2Json(num));
    }


    @Test
    public void getOrderSnList4LockByRooms(){
        List<String> aa = new ArrayList<>();
        aa.add("aaa");
        List<String> num = orderBaseDao.getOrderSnList4LockByRooms(aa);
        System.out.println(JsonEntityTransform.Object2Json(num));
    }



	@Test
	public void TestGetOrderCountByStatus(){
        Long num = orderBaseDao.getOrderCountByStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus());
        System.out.println("num:"+num);
    }
	
	@Test
	public void TestGetOrderListByStatus(){
		List<OrderEntity> list = orderBaseDao.getOrderListByStatus(OrderStatusEnum.CHECKED_IN.getOrderStatus(), 100);
		System.out.println("list:"+list);
    }


    @Test
    public void TestcountCurrentNoPayOrder() {
        Long aa = orderBaseDao.countCurrentNoPayOrder("ss","22");
    }


    @Test
    public void TestgetOrderBaseByOrderSn() {
        OrderEntity orderEntity = orderBaseDao.getOrderBaseByOrderSn("ss");
    }


    @Test
    public void TestInsertOrderInfo(){
    	 OrderEntity orderEntity = new OrderEntity();
    	for (int i = 0; i < 2; i++) {
		    orderEntity = new OrderEntity();
		    orderEntity.setFid(UUIDGenerator.hexUUID());
	        orderEntity.setOrderSn(UUIDGenerator.hexUUID());
	        orderEntity.setProvinceCode("BJ");
	        orderEntity.setNationCode("CN");
	        orderEntity.setCityCode("BJ");
	        orderEntity.setCheckType(1);
	        orderEntity.setAreaCode("东城区");
	        orderEntity.setOrderSource(1);
	        orderEntity.setOrderStatus(20);
	        orderEntity.setAccountsStatus(0);
	        orderEntity.setPayStatus(0);
	        orderEntity.setLandlordUid(UUIDGenerator.hexUUID());
	        orderEntity.setLandlordName("name");
	        orderEntity.setLandlordTel("tel");
	        orderEntity.setUserUid(UUIDGenerator.hexUUID());
	        orderEntity.setUserTel("18301315875");
	        orderEntity.setUserName("小草");
	        orderEntity.setPeopleNum(3);
	        orderEntity.setStartTime(new Date());
	        orderEntity.setEndTime(new Date());
	        orderEntity.setPayStatus(0);
	        orderEntity.setOrderStatus(20);

	        orderBaseDao.insertOrderBase(orderEntity);
		}
    	
    }


    @Test
    public void TestinsertOrderBase(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setFid(UUIDGenerator.hexUUID());
        orderEntity.setOrderSn("orderSn");
        orderEntity.setProvinceCode("BJ");
        orderEntity.setNationCode("CN");
        orderEntity.setCityCode("BJ");
        orderEntity.setCheckType(1);
        orderEntity.setAreaCode("东城区");
        orderEntity.setOrderSource(1);
        orderEntity.setOrderStatus(20);
        orderEntity.setAccountsStatus(0);
        orderEntity.setPayStatus(0);
        orderEntity.setLandlordUid(UUIDGenerator.hexUUID());
        orderEntity.setLandlordName("name");
        orderEntity.setLandlordTel("tel");
        orderEntity.setUserUid(UUIDGenerator.hexUUID());
        orderEntity.setUserTel("18301315875");
        orderEntity.setUserName("小草");
        orderEntity.setPeopleNum(3);
        orderEntity.setStartTime(new Date());
        orderEntity.setEndTime(new Date());
        orderEntity.setPayStatus(0);
        orderEntity.setOrderStatus(20);
        orderEntity.setTripPurpose("tripPurposetripPurposetripPurposetripPurposetripPurposetripPurposet");
        orderBaseDao.insertOrderBase(orderEntity);

    }


    @Test
    public void TestupdateOrderBaseByOrderSn(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setFid(UUIDGenerator.hexUUID());
        orderEntity.setOrderSn("8a9e9a8b53f4f1c50153f4f1c5050001");
        orderEntity.setNationCode("CN111");
        orderEntity.setCityCode("BJ111");
        orderEntity.setOrderSource(1);
        orderEntity.setOrderStatus(20);
        orderEntity.setAccountsStatus(0);
        orderEntity.setPayStatus(0);
        orderEntity.setLandlordUid(UUIDGenerator.hexUUID());
        orderEntity.setLandlordName("name");
        orderEntity.setLandlordTel("tel");
        orderEntity.setUserUid(UUIDGenerator.hexUUID());
        orderEntity.setUserTel("18301315875");
        orderEntity.setUserName("小草");
        orderEntity.setPeopleNum(3);
        orderEntity.setStartTime(new Date());
        orderEntity.setEndTime(new Date());
        orderEntity.setPayStatus(0);
        orderEntity.setOrderStatus(20);

        orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
    }
    
    @Test
    public void TesttaskGetTomorrowOrderList(){
    }
    
    
    @Test
	public void TestGetNoOperateOrderCount() {
		List<Integer> orderStatusList = new ArrayList<Integer>();
		// orderStatusList.add(20);
		orderStatusList.add(OrderStatusEnum.WAITING_EXT.getOrderStatus());
		orderStatusList.add(OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus());
		
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -1);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));

		Long count = orderBaseDao.getNoOperateOrderCount(orderStatusList, limitTime);
		System.err.println("count:" + count);
	}
    
    @Test
    public void TestGetNoOperateOrderList(){
    	List<Integer> orderStatusList = new ArrayList<Integer>();
    	// orderStatusList.add(20);
		orderStatusList.add(OrderStatusEnum.WAITING_EXT.getOrderStatus());
		orderStatusList.add(OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus());
		Date limitTime = DateSplitUtil.jumpHours(new Date(), -1);
		System.err.println("limitTime:" + DateUtil.timestampFormat(limitTime));
		
		List<OrderEntity> list = orderBaseDao.getNoOperateOrderList(orderStatusList, limitTime, 100);
		System.err.println("list:"+list);
    }

    @Test
    public void getRemindOrderListTest(){
    	PageRequest pageRequest = new PageRequest();
    	
    	PagingResult<RemindOrderVo> remindOrderList = orderBaseDao.getRemindOrderList(pageRequest);
    	System.err.println(JsonEntityTransform.Object2Json(remindOrderList));
    }
    
    @Test
    public void getRefuseOrderListTest(){
    	PageRequest pageRequest = new PageRequest();
    	
    	PagingResult<RefuseOrderVo> refuseOrderList = orderBaseDao.getRefuseOrderList(pageRequest);
    	System.err.println(JsonEntityTransform.Object2Json(refuseOrderList));
    	
    }
    
    
    @Test
    public void getOrdersBySns(){
    	Set<String> orderSns = new HashSet<>();
    	orderSns.add("1605114072BBU7183446");
    	orderSns.add("1605112PL6M99B185049");
    	orderSns.add("160511MZ4T3XV9185055");
    	List<OrderEntity> ordersBySns = orderBaseDao.getOrdersBySns(orderSns);
    	System.err.println(JsonEntityTransform.Object2Json(ordersBySns));
    }
    
	// “订单审核时限“的值为0时，“申请预定提醒时限”的值不受“订单审核时限“的值限制；
	// 当“申请预订提醒时限”的值的状态为停用或者值为0时，不触发定时作业；
	// 当“申请预订提醒时限”的值大于“订单审核时限“时，“申请预订提醒时限”=“订单审核时限“/2

    @Test
    public void getWaitConfimOrderList(){
    	
    	List<OrderEntity> list = 	this.orderBaseDao.getWaitConfimOrderList(1, new Date());
    	
    	System.out.println(list);
    }

    @Test
    public void getAdvisoryOrderInfo(){

        MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = new MsgFirstAdvisoryEntity();
//        msgFirstAdvisoryEntity.setHouseFid("8a90997754d7325c0154d778d732006a");
//        msgFirstAdvisoryEntity.setFromUid("f0953f1c-19bb-0cd3-a99f-7c413278f33d");
        Date date = new Date(116,4,21,15,30,50);
//        msgFirstAdvisoryEntity.setCreateTime(date);
        OrderEntity orderEntity = orderBaseDao.getAdvisoryOrderInfo(msgFirstAdvisoryEntity);
        System.out.print(orderEntity);

    }
}

