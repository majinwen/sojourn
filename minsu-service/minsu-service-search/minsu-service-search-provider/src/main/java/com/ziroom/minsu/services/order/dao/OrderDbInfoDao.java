package com.ziroom.minsu.services.order.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.sql.visitor.functions.If;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.search.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.search.vo.WeightEvalVo;
import com.ziroom.minsu.services.search.vo.WeightOrderNumVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * 
 * <p>订单的信息的dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Repository("search.orderDbInfoDao")
public class OrderDbInfoDao {


    private String SQLID="search.orderDbInfoDao.";

    @Autowired
    @Qualifier("searchOrder.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    private static final String datePatten = "yyMMdd";    

    /**
     * 获取当前的房源的锁定信息
     * @param houseFid
     * @return
     */
    public List<String> getLocksByHouseFid(String houseFid){
        List<String> rst = new ArrayList<>();
        List<Date> list = mybatisDaoContext.findAll(SQLID + "getLocksByHouseFid", Date.class, houseFid);
        if(!Check.NuNCollection(list)){
            for(Date date: list){
                rst.add(DateUtil.dateFormat(date, datePatten));
            }
        }
        return rst;
    }
    
    /**
     * 获取当前的房源的锁定信息（已经支付）
     * @param houseFid
     * @return
     */
    public List<String> getLocksByHouseFidPaid(String houseFid){
        List<String> rst = new ArrayList<>();
        List<Date> list = mybatisDaoContext.findAll(SQLID + "getLocksByHouseFidPaid", Date.class, houseFid);
        if(!Check.NuNCollection(list)){
            for(Date date: list){
                rst.add(DateUtil.dateFormat(date, datePatten));
            }
        }
        return rst;
    }


    /**
     * 获取当前的房间的锁定信息
     * @param roomFid
     * @return
     */
    public List<String> getLocksByRoomFid(String roomFid){
        List<String> rst = new ArrayList<>();
        List<Date> list = mybatisDaoContext.findAll(SQLID + "getLocksByRoomFid", Date.class, roomFid);
        if(!Check.NuNCollection(list)){
            for(Date date: list){
                rst.add(DateUtil.dateFormat(date, datePatten));
            }
        }
        return rst;
    }
    
    /**
     * 获取当前的房间的锁定信息（已经支付）
     * @param roomFid
     * @return
     */
    public List<String> getLocksByRoomFidPaid(String roomFid){
        List<String> rst = new ArrayList<>();
        List<Date> list = mybatisDaoContext.findAll(SQLID + "getLocksByRoomFidPaid", Date.class, roomFid);
        if(!Check.NuNCollection(list)){
            for(Date date: list){
                rst.add(DateUtil.dateFormat(date, datePatten));
            }
        }
        return rst;
    }
    
   /**
    * 
    * 夹心日期
    *
    * @author zl
    * @created 2016年11月30日 下午5:49:29
    *
    * @param leaseCalendarDto
    * @return
    */
    public List<String> getPriorityDate(LeaseCalendarDto leaseCalendarDto){
        List<String> lockDateList =null;
        if (!Check.NuNStr(leaseCalendarDto.getHouseRoomFid()) && RentWayEnum.ROOM.getCode()==leaseCalendarDto.getRentWay()){
            lockDateList = getLocksByRoomFidPaid(leaseCalendarDto.getHouseRoomFid());
        }else if (!Check.NuNStr(leaseCalendarDto.getHouseBaseFid()) && RentWayEnum.HOUSE.getCode()==leaseCalendarDto.getRentWay()){
            lockDateList = getLocksByHouseFidPaid(leaseCalendarDto.getHouseBaseFid());
        }

        Set<String> priorityDateList = new TreeSet<String>(); 
        if(!Check.NuNCollection(lockDateList)){
        	
            Calendar cursor = Calendar.getInstance(); 
    		cursor.setTime(leaseCalendarDto.getNowDate());
    		
    		List<Set<String>> notLockContinuousDaysList = new ArrayList<Set<String>>();
    		
    		Set<String> notLockContinuousDays = new HashSet<String>();
    		
//    		if (!lockDateList.contains(sdf.format(cursor.getTime()))) {//当天
//    			notLockContinuousDays.add(sdf.format(cursor.getTime()));
//    			if (!Check.NuNCollection(notLockContinuousDays)) {							
//					notLockContinuousDaysList.add(notLockContinuousDays);
//				}
//				notLockContinuousDays = new HashSet<String>();
//    			cursor.add(Calendar.DATE, 1);
//			}
    		
    		for (; ;) {  
    			
    			if(cursor.getTime().after(leaseCalendarDto.getEndDate())){ 
    				break;
    			} 
    			if (!lockDateList.contains(DateUtil.dateFormat(cursor.getTime(), datePatten))) {//没有锁定
        			notLockContinuousDays.add(DateUtil.dateFormat(cursor.getTime(), datePatten));
				}else{
					if (!Check.NuNCollection(notLockContinuousDays)) {							
						notLockContinuousDaysList.add(notLockContinuousDays);
					}
					notLockContinuousDays = new HashSet<String>(); 
				}
    			
    			cursor.add(Calendar.DATE, 1);
    		}
    		
    		if (!Check.NuNCollection(notLockContinuousDays) && (lockDateList.contains(DateUtil.dateFormat(cursor.getTime(), datePatten))) || !DateUtil.dateFormat(cursor.getTime(), datePatten).equals(DateUtil.dateFormat(leaseCalendarDto.getEndDate(), datePatten))) {							
				notLockContinuousDaysList.add(notLockContinuousDays);//截止日期被锁定才算夹心，否则不算
			}
    		
    		if (!Check.NuNCollection(notLockContinuousDaysList)) {
				for (Set<String> notLockDays : notLockContinuousDaysList) {
					if (notLockDays.size()<=leaseCalendarDto.getPriorityDateLimit()) {//满足夹心日期条件
						priorityDateList.addAll(notLockDays);
					}
				}
			}
    		
    		
        }else{
//        	priorityDateList.add(sdf.format(new Date()));//当天
        }
        List<String> priorityDays = new ArrayList<String>(); 
        if (!Check.NuNCollection(priorityDateList)) {
        	priorityDays.addAll(priorityDateList);
        }
        return  priorityDays;
    }
    

    /**
     * 获取当前房源的订单的数量
     * @param houseFId
     * @return
     */
    public Long countHouseOrder(String houseFId){
        Map<String,Object> par = new HashMap<>();
        par.put("houseFid",houseFId);
        return mybatisDaoContext.countBySlave(SQLID + "countHouseOrder", par);
    }




    /**
     * 获取当前房源的订单的数量(有效的)
     * @param houseFId
     * @return
     */
    public Long countHouseOrderEffective(String houseFId){
        Map<String,Object> par = new HashMap<>();
        par.put("houseFid",houseFId);
        return mybatisDaoContext.countBySlave(SQLID + "countHouseOrderEffective", par);
    }



    /**
     * 获取当前房间的订单数量
     * @param roomFid
     * @return
     */
    public Long countRoomOrder(String roomFid){
        Map<String,Object> par = new HashMap<>();
        par.put("roomFid", roomFid);
        return mybatisDaoContext.countBySlave(SQLID + "countRoomOrder", par);
    }



    /**
     * 获取有效的订单的房源数量
     * @return
     */
    public Long countHouseEffective(){
        return mybatisDaoContext.countBySlave(SQLID + "countHouseEffective");
    }

    /**
     * 获取有效的订单的房源数量
     * @return
     */
    public Long countHouseEffectiveByEffect(Long effectNum){
        if (Check.NuNObj(effectNum)){
            return 0L;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("effectNum",effectNum);
        return mybatisDaoContext.countBySlave(SQLID + "countHouseEffectiveByEffect",par);
    }

    /**
     * 获取当前房间的订单数量 有效
     * @param roomFid
     * @return
     */
    public Long countRoomOrderEffective(String roomFid){
        Map<String,Object> par = new HashMap<>();
        par.put("roomFid", roomFid);
        return mybatisDaoContext.countBySlave(SQLID + "countRoomOrderEffective", par);
    }



    /**
     * 获取当前房源的最新的刷新日历的时间
     * @param houseFId
     * @return
     */
    public Date getHouseCalendarFreshTime(String houseFId){

        return mybatisDaoContext.findOneSlave(SQLID + "getHouseCalendarFreshTime",Date.class,houseFId);
    }

    /**
     * 获取当前房源的订单的数量
     * @param roomFId
     * @return
     */
    public Date getRoomCalendarFreshTime(String roomFId){

        return mybatisDaoContext.findOneSlave(SQLID + "getRoomCalendarFreshTime",Date.class,roomFId);
    }



    /**
     * 获取当前房源的订单的评价信息
     * @param houseFId
     * @return
     */
    public Long countHouseOrderEval(String houseFId){
        Map<String,Object> par = new HashMap<>();
        par.put("houseFId",houseFId);
        return mybatisDaoContext.countBySlave(SQLID + "countHouseOrderEval", par);
    }



    /**
     * 获取当前房间的评价信息
     * @param roomFid
     * @return
     */
    public Long countRoomOrderEval(String roomFid){
        Map<String,Object> par = new HashMap<>();
        par.put("roomFid", roomFid);
        return mybatisDaoContext.countBySlave(SQLID + "countRoomOrderEval", par);
    }


    /**
     * 获取当前房源的订单的评价比例
     * @param houseFId
     * @return
     */
    public WeightEvalVo getHouseEvalRate(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseEvalRate",WeightEvalVo.class, houseFId);
    }


    /**
     * 获取当前房间的评价信息比例
     * @param roomFid
     * @return
     */
    public WeightEvalVo getRoomEvalRate(String roomFid){
        if (Check.NuNStr(roomFid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomEvalRate",WeightEvalVo.class, roomFid);
    }


    /**
     * 获取当前房源的订单的数量
     * @param houseFId
     * @return
     */
    public WeightOrderNumVo getHouseOrderRate(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseOrderRate",WeightOrderNumVo.class, houseFId);
    }

    /**
     * 获取当前房间的订单的数量
     * @param roomFid
     * @return
     */
    public WeightOrderNumVo getRoomOrderRate(String roomFid){
        if (Check.NuNStr(roomFid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomOrderRate",WeightOrderNumVo.class, roomFid);
    }

    /**
     * 获取当前房源的订单的数量
     * @param houseFId
     * @return
     */
    public Double getHouseAvgAcceptTime(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseAvgAcceptTime",Double.class, houseFId);
    }

    /**
     * 获取当前房间的订单的数量
     * @param roomFid
     * @return
     */
    public Double getRoomAvgAcceptTime(String roomFid){
        if (Check.NuNStr(roomFid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomAvgAcceptTime",Double.class, roomFid);
    }
    /**
     * 查询所有的评价数量
     * TODO
     *
     * @author zl
     * @created 2016年12月6日 上午9:24:16
     *
     * @return
     */
    public Long countOrderEvalAll(){
    	return mybatisDaoContext.findOneSlave(SQLID + "countOrderEvalAll",Long.class);
    }
    /**
     * 查询房源的评价数量排名
     * TODO
     *
     * @author zl
     * @created 2016年12月6日 上午9:24:48
     *
     * @param houseFid
     * @return
     */
    public Long countHouseOrderEvalAllRank(String houseFid){
    	if (Check.NuNStr(houseFid)){
            return 0L;
        }
    	return mybatisDaoContext.findOneSlave(SQLID + "countHouseOrderEvalAllRank",Long.class, houseFid);
    }
    
    /**
     * 查询房间的评价数量排名
     * TODO
     *
     * @author zl
     * @created 2016年12月6日 上午9:25:07
     *
     * @param roomFid
     * @return
     */
	public Long countRoomOrderEvalAllRank(String roomFid){
		if (Check.NuNStr(roomFid)){
            return 0L;
        }
		return mybatisDaoContext.findOneSlave(SQLID + "countRoomOrderEvalAllRank",Long.class, roomFid);
	}
	
	/**
	 * 
	 * 获取房源或者房间60天内的确认的订单数
	 *
	 * @author zl
	 * @created 2017年5月31日 下午4:49:38
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public Long getAcceptOrder60DaysCount(String houseFid,String roomFid,Integer rentWay){
		Map<String, Object> map = new HashMap<>();
		map.put("houseFid", houseFid);
		map.put("roomFid", roomFid);
		map.put("rentWay", rentWay);
		return mybatisDaoContext.findOneSlave(SQLID + "getAcceptOrder60DaysCount",Long.class, map);
	}
	
	/**
	 * 
	 * 获取房源或者房间60天内所有的订单数
	 *
	 * @author zl
	 * @created 2017年5月31日 下午4:49:55
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public Long getOrder60DaysCount(String houseFid,String roomFid,Integer rentWay){
		Map<String, Object> map = new HashMap<>();
		map.put("houseFid", houseFid);
		map.put("roomFid", roomFid);
		map.put("rentWay", rentWay);
		return mybatisDaoContext.findOneSlave(SQLID + "getOrder60DaysCount",Long.class, map);
	}


}