package com.ziroom.minsu.services.order.dao;

import base.BaseTest;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.search.vo.WeightEvalVo;
import com.ziroom.minsu.services.search.vo.WeightOrderNumVo;
import org.junit.Test;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>订单的数据</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class OrderDbInfoDaoTest extends BaseTest {


    @Resource(name="search.orderDbInfoDao")
    private OrderDbInfoDao orderDbInfoDao;






    @Test
    public void getRoomAvgAcceptTime(){
        Double weightOrderNumVo =orderDbInfoDao.getRoomAvgAcceptTime("8a9084df556cd72c01556d1081070010");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }


    @Test
    public void getHouseAvg(){
        Double weightOrderNumVo =orderDbInfoDao.getHouseAvgAcceptTime("8a90a2d4549ac79901549f514bc9013b");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }

    @Test
    public void getRoomOrderRate(){
        WeightOrderNumVo weightOrderNumVo =orderDbInfoDao.getRoomOrderRate("8a9084df54c541c40154c549c1ee005d");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }

    @Test
    public void getHouseOrderRate(){
        WeightOrderNumVo weightOrderNumVo =orderDbInfoDao.getHouseOrderRate("8a90a2d45496695501549a97bdf60221");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }



    @Test
    public void getRoomEvalRate(){
        WeightEvalVo weightEvalVo =orderDbInfoDao.getRoomEvalRate("8a90a2d4549669550154997bd6b0011a");
        System.out.println(JsonEntityTransform.Object2Json(weightEvalVo));
    }


    @Test
    public void countHouseEvalRate(){
        WeightEvalVo weightEvalVo =orderDbInfoDao.getHouseEvalRate("8a90a2d455c421700155c43c14160019");
        System.out.println(JsonEntityTransform.Object2Json(weightEvalVo));
    }




    @Test
    public void countHouseEffectiveByEffect(){
        Long list =orderDbInfoDao.countHouseEffectiveByEffect(11L);
        System.out.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void countHouseEffective(){
        Long list =orderDbInfoDao.countHouseEffective();
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void TestgetLocksByHouseFid(){
        List<String> list =orderDbInfoDao.getLocksByHouseFidPaid("8a9e9cc253eb7d240153eb7d27bc0025");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void TestgetLocksByRoomFid(){
        List<String> list =orderDbInfoDao.getLocksByRoomFidPaid("8a9e9cc253eb7d240153eb7d27bc0025");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


    @Test
    public void TestcountHouseOrder(){
        Long count =orderDbInfoDao.countHouseOrder("8a9e9cd253d0b29d0153d0b29d460001");
        System.out.println(count);
    }

    @Test
    public void TestcountHouseOrderEffective(){
        Long count =orderDbInfoDao.countHouseOrderEffective("8a9e9cd253d0b29d0153d0b29d460001");
        System.out.println(count);
    }
    @Test
    public void TestcountRoomOrder(){
        Long count =orderDbInfoDao.countRoomOrder("8a9e9cd253d0b29d0153d0b29d460001");
        System.out.println(count);
    }

    @Test
    public void TestcountRoomOrderEffective(){
        Long count =orderDbInfoDao.countRoomOrderEffective("8a9e9cd253d0b29d0153d0b29d460001");
        System.out.println(count);
    }



    @Test
    public void TestcountHouseCalendarFreshTime(){
        Date date =orderDbInfoDao.getHouseCalendarFreshTime("houseId");
        System.out.println(date);
    }


    @Test
    public void getRoomCalendarFreshTime(){
        Date date =orderDbInfoDao.getRoomCalendarFreshTime("roomId");
        System.out.println(date);
    }
    
    @Test
    public void getPriorityDate() throws ParseException{
    	LeaseCalendarDto leaseCalendarDto = new LeaseCalendarDto();
    	leaseCalendarDto.setHouseBaseFid("8a9084df5a63f092015a63f4a0950017");
    	leaseCalendarDto.setRentWay(0);
//    	leaseCalendarDto.setHouseRoomFid("8a9e988e5719006801571bf77a110027");
//    	leaseCalendarDto.setRentWay(1);
    	leaseCalendarDto.setEndDate(DateUtil.parseDate("2017-09-08", "yyyy-MM-dd"));
    	leaseCalendarDto.setPriorityDateLimit(10);
    	
    	List<String> list = orderDbInfoDao.getPriorityDate(leaseCalendarDto);
    	System.out.println(JSON.toJSONString(list));
    	
    }
    
    @Test
    public void countOrderEvalAll(){
    	long l = orderDbInfoDao.countOrderEvalAll();
    	System.out.println(l);
    }
    
    @Test
    public void countHouseOrderEvalAllRank(){
    	long l = orderDbInfoDao.countHouseOrderEvalAllRank("8a90a2d4549ac7990154a05d8fac01a9");
    	System.out.println(l);
    }
    @Test
    public void countRoomOrderEvalAllRank(){
    	long l = orderDbInfoDao.countRoomOrderEvalAllRank("8a9084df550d9bdd01550db2ec310105");
    	System.out.println(l);
    }
    

}
