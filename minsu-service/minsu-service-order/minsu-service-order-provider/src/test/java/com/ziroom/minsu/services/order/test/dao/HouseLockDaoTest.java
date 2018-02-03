package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.order.dao.HouseLockDao;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.*;

/**
 * <p>订单房源的锁定</p>
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
public class HouseLockDaoTest extends BaseTest {

    @Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;


    @Test
    public void getHouseLockByHouseFidBefore() {
        HouseLockEntity locks = houseLockDao.getHouseLockByHouseFidBefore("8a9e9892574ad53101574adde9400010",new Date());
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }

    @Test
    public void getHouseLockByHouseFidEnd() throws ParseException {
        HouseLockEntity locks = houseLockDao.getHouseLockByHouseFidEnd("8a9084df5a63f092015a63f4a0950017",DateUtil.parseDate("2017-03-12", "yyyy-MM-dd"));
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }

    @Test
    public void TestgetHouseLockByHouseFidList() throws Exception{
        List<Date> list = new ArrayList<>();
        list.add(DateUtil.parseDate("2016-4-22", "yyyy-MM-dd"));
        List<HouseLockEntity> locks = houseLockDao.getHouseLockByHouseFidList("houseId", list);
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }


    @Test
    public void TestgetHouseLockByHouseFid() {
        List<HouseLockEntity> locks = houseLockDao.getHouseLockByHouseFid("houseId", new Date(), null);
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }

    @Test
    public void TestgetHouseLockByRoomFid() {
        List<HouseLockEntity> locks = houseLockDao.getHouseLockByRoomFid("houseId", "roomId", new Date(), null);
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }

    @Test
    public void TestgetHouseLockByBedFid() {
        List<HouseLockEntity> locks = houseLockDao.getHouseLockByBedFid("bedId", new Date(), null);
        System.out.println(JsonEntityTransform.Object2Json(locks));
    }


    @Test
    public void TestinsertHouseLockThead() throws Exception{

        for (int i = 0; i < 20; i++) {
            Thread task = new Thread(){
                @Override
                public void run() {
                    HouseLockEntity lock = new HouseLockEntity();
                    lock.setHouseFid("houseId");
                    lock.setRoomFid("roomId");
                    lock.setBedFid("bedId");
                    lock.setOrderSn("123");
                    lock.setRentWay(1);
                    lock.setLockTime(new Date());
                    lock.setLockType(2);
                    houseLockDao.insertHouseLock(lock);
                }

            };

            SendThreadPool.execute(task);
        }

        Thread.sleep(2000L);

    }




    @Test
    public void TestinsertHouseLock() throws ParseException {
        Date date = DateUtil.parseDate("2017-04-20", "yyyy-MM-dd");
        HouseLockEntity lock = new HouseLockEntity();
        lock.setHouseFid("8a9084df5a63f092015a63f4a0950017");
        lock.setRentWay(0);
        lock.setLockTime(date);
        lock.setLockType(2);
        lock.setLockSource(1);
        houseLockDao.insertHouseLock(lock);
    }

    @Test
    public void testlistAirLocks(){
        LockHouseRequest request = new LockHouseRequest();
        request.setHouseFid("8a9084df5a63f092015a63f4a0950017");
        request.setRentWay(0);
        List<HouseLockEntity> houseLockEntities = houseLockDao.listAirLocks(request);
        System.err.println(JsonEntityTransform.Object2Json(houseLockEntities));
    }

    @Test
    public void testdeleteAirLock() throws ParseException {
        LockHouseRequest request = new LockHouseRequest();
        request.setHouseFid("8a9084df5a63f092015a63f4a0950017");
        request.setRentWay(0);
        List<Date> dates = new ArrayList<>();
        Date date = DateUtil.parseDate("2017-04-19", "yyyy-MM-dd");
        Date date1 = DateUtil.parseDate("2017-04-20", "yyyy-MM-dd");
        dates.add(date);
        dates.add(date1);
        request.setLockDayList(dates);
        houseLockDao.deleteAirLock(request);
    }

 /*   @Test
    public void getBookDaysByHouseFidTest() {
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("houseFid", "8a9e9cc253eb7d240153eb7d24800001");
    	paramMap.put("startTime","2016-4-6");
    	paramMap.put("endTime","2016-4-6" );
    	int count=houseLockDao.getBookDaysByHouseFid(paramMap);
    	System.err.println(count);
    }
    
    @Test
    public void getBookDaysByRoomFidTest() {
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("houseFid", "8a9e9cc253eb7d240153eb7d24f80003");
    	paramMap.put("roomFid", "8a9e9a9453fb3cb00153fb3cb2270001");
    	paramMap.put("startTime","2016-4-6");
    	paramMap.put("endTime","2016-4-6" );
    	int count=houseLockDao.getBookDaysByRoomFid(paramMap);
    	System.err.println(count);
    }*/
    
    @Test
    public void getBookDaysByHouseFidAndRoomFidTest(){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("houseFid", "8a9e9cc253eb7d240153eb7d24800001");
    	paramMap.put("startTime","2016-4-6");
    	paramMap.put("endTime","2016-4-6" );
    	int count=houseLockDao.getBookDaysByHouseFidAndRoomFid(paramMap);
    	System.err.println(count);
    }


    
    @Test
    public void unLockHouseByOrderSnTest(){
    	
    	int count = houseLockDao.unLockHouseByOrderSn("123", new Date());
    	System.err.println("count:"+count);
    }


    @Test
    public void delLockHouseByOrderSn(){

        int count = houseLockDao.delLockHouseByOrderSn("123", new Date());
        System.err.println("count:"+count);
    }
    
    @Test
    public void getPriorityDateTest(){
    	
    	try {
    		HousePriorityDto housePriorityDto = new HousePriorityDto();
        	housePriorityDto.setRentWay(RentWayEnum.HOUSE.getCode());
        	housePriorityDto.setHouseBaseFid("8a90a2d4549ac7990154a3ee6eee0237");
        	
        	housePriorityDto.setStartDateStr("2016-08-09");
        	housePriorityDto.setEndDateStr("2017-02-09");
        	housePriorityDto.setNowDate(new Date());
        	housePriorityDto.setEndDate(DateUtil.parseDate("2017-02-09", "yyyy-MM-dd"));
        	housePriorityDto.setStartDate(DateUtil.parseDate("2016-08-09", "yyyy-MM-dd"));
        	
        	this.houseLockDao.getPriorityDate(housePriorityDto);
		} catch (Exception e) {
			
		}
    	
    }
}
