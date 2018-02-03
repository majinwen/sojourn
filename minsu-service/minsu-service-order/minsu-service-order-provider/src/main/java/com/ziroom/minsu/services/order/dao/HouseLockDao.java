package com.ziroom.minsu.services.order.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.utils.HouseLockUtil;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p> 房源的锁定 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.houseLockDao")
public class HouseLockDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HouseLockDao.class);

	private String SQLID = "order.houseLockDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");





	/**
	 * 获取当前房源在当前的占用数
	 * @author afi
	 * @created 2016年4月1日 下午20:22:38
	 * @param houseFid
	 * @param dateList
	 * @return
	 */
	public Long countHouseLock(String houseFid , List<Date> dateList){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("houseFid",houseFid);
		countMap.put("dateList", dateList);
		Long rst = mybatisDaoContext.count(SQLID + "countHouseLock", countMap);
		return rst;
	}

	/**
	 * 获取当前房间在当前的占用数
	 * @author afi
	 * @created 2016年4月1日 下午20:22:38
	 * @param roomFid
	 * @param dateList
	 * @return
	 */
	public Long countRoomLock(String roomFid , List<Date> dateList){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("roomFid",roomFid);
		countMap.put("dateList", dateList);
		return mybatisDaoContext.count(SQLID + "countRoomLock", countMap);
	}

	/**
	 * 获取当前床位在当前的占用数
	 * @author afi
	 * @created 2016年4月1日 下午20:22:38
	 * @param bedFid
	 * @param dateList
	 * @return
	 */
	public Long countBedLock(String bedFid ,  List<Date> dateList){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("bedFid",bedFid);
		countMap.put("dateList", dateList);
		return mybatisDaoContext.count(SQLID + "countBedLock", countMap);
	}





	/**
	 * 获取当前房源在指定时间的占用订单
	 * @author lishaochuan
	 * @create 2016年7月5日下午6:04:31
	 * @param houseFid
	 * @param dateList
	 * @return
	 */
	public List<String> getHouseLockOrderSn(String houseFid , List<Date> dateList){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("houseFid",houseFid);
		countMap.put("dateList", dateList);
		return mybatisDaoContext.findAllByMaster(SQLID + "getHouseLockOrderSn", String.class, countMap);
	}

	/**
	 * 获取当前房间在指定时间的占用订单
	 * @author lishaochuan
	 * @create 2016年7月5日下午6:04:42
	 * @param roomFid
	 * @param dateList
	 * @return
	 */
	public List<String> getRoomLockOrderSn(String roomFid , List<Date> dateList){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("roomFid",roomFid);
		countMap.put("dateList", dateList);
		return mybatisDaoContext.findAllByMaster(SQLID + "getRoomLockOrderSn", String.class, countMap);
	}

	/**
	 * 获取当前床位在指定时间的占用订单
	 * @author lishaochuan
	 * @create 2016年7月5日下午6:04:54
	 * @param bedFid
	 * @param dateList
	 * @return
	 */
	public List<String> getBedLockOrderSn(String bedFid, List<Date> dateList) {
		Map<String, Object> countMap = new HashMap<>();
		countMap.put("bedFid", bedFid);
		countMap.put("dateList", dateList);
		return mybatisDaoContext.findAllByMaster(SQLID + "getBedLockOrderSn", String.class, countMap);
	}



	/**
	 * 获取房源的锁信息
	 * @param houseFid
	 * @return
	 */
	public HouseLockEntity getHouseLockByHouseFidBefore(String houseFid,Date startTime) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid)){
			throw new BusinessException("houseFid is null");
		}
		if (Check.NuNObj(startTime)){
			throw new BusinessException("startTime is null");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		par.put("startTime", startTime);
		return mybatisDaoContext.findOne(SQLID + "getHouseLockByHouseFidBefore", HouseLockEntity.class, par);
	}





	/**
	 * 获取房源的锁信息
	 * @param roomFid
	 * @return
	 */
	public HouseLockEntity getHouseLockByRoomFidBefore(String roomFid,Date startTime) {
		//校验当前的参数信息
		if(Check.NuNStr(roomFid)){
			throw new BusinessException("roomFid is null");
		}
		if (Check.NuNObj(startTime)){
			throw new BusinessException("roomFid is null");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("roomFid",roomFid);
		par.put("startTime", startTime);
		return mybatisDaoContext.findOne(SQLID + "getHouseLockByRoomFidBefore", HouseLockEntity.class, par);
	}
	/**
	 * 获取房源的锁信息
	 * @param roomFid
	 * @return
	 */
	public HouseLockEntity getHouseLockByRoomFidEnd(String roomFid,Date endTime) {
		//校验当前的参数信息
		if(Check.NuNStr(roomFid)){
			throw new BusinessException("roomFid is null");
		}
		if (Check.NuNObj(endTime)){
			throw new BusinessException("endTime is null");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("roomFid",roomFid);
		par.put("endTime", DateSplitUtil.getYesterday(endTime));
		return mybatisDaoContext.findOne(SQLID + "getHouseLockByRoomFidEnd", HouseLockEntity.class, par);
	}


	/**
	 * 获取房源的锁信息
	 * @param houseFid
	 * @return
	 */
	public HouseLockEntity getHouseLockByHouseFidEnd(String houseFid,Date endTime) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid)){
			throw new BusinessException("houseFid is null");
		}
		if (Check.NuNObj(endTime)){
			throw new BusinessException("endTime is null");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		par.put("endTime", DateSplitUtil.getYesterday(endTime));
		return mybatisDaoContext.findOne(SQLID + "getHouseLockByHouseFidEnd", HouseLockEntity.class, par);
	}


	/**
	 * 获取房源的锁信息
	 * @param houseFid
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByHouseFidList(String houseFid,List<Date> dayList) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid)){
			throw new BusinessException("houseFid is null");
		}
		List<Date> dateListQuery = new ArrayList<>();
		for (Date date: dayList){
			dateListQuery.add(DateSplitUtil.connectDate(date,"00:00:00"));
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		par.put("dateList", dateListQuery);
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByHouseFidList", HouseLockEntity.class, par);
	}
	/**
	 * 
	 * 返回查询的房源锁
	 *
	 * @author jixd
	 * @created 2016年8月4日 下午6:16:12
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByFidAndSetTime(Map<String,Object> paramMap){
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByFidAndSetTime", HouseLockEntity.class, paramMap);
	}

	/**
	 * 获取房间的锁信息
	 * @author afi
	 * @param houseFid
	 * @param roomFid
	 * @param dayList
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByRoomFidList(String houseFid,String roomFid,List<Date> dayList) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid) || Check.NuNStr(roomFid)){
			throw new BusinessException("houseFid or roomFid is null ");
		}
		List<Date> dateListQuery = new ArrayList<>();
		for (Date date: dayList){
			dateListQuery.add(DateSplitUtil.connectDate(date,"00:00:00"));
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		par.put("roomFid",roomFid);
		par.put("dateList", dateListQuery);
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByRoomFidList", HouseLockEntity.class, par);
	}


	/**
	 * 获取房源的锁信息
	 * @param houseFid
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByHouseFid(String houseFid,Date startTime,Date endTime) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid)){
			throw new BusinessException("houseFid is null");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		if(!Check.NuNObj(startTime)){
			par.put("startTime",startTime);
		}
		if(Check.NuNObj(startTime) && !Check.NuNObj(endTime)){
			throw new BusinessException("start time is null but endTime is not null");
		}
		if(!Check.NuNObj(endTime)){
			par.put("endTime",endTime);
		}
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByHouseFid", HouseLockEntity.class, par);
	}

	/**
	 * 获取房间的锁信息
	 * @param roomFid
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByRoomFid(String houseFid,String roomFid,Date startTime,Date endTime) {
		//校验当前的参数信息
		if(Check.NuNStr(houseFid) || Check.NuNStr(roomFid)){
			throw new BusinessException("houseFid or roomFid is null ");
		}
		Map<String,Object> par = new HashMap<>();
		par.put("houseFid",houseFid);
		par.put("roomFid",roomFid);
		if(!Check.NuNObj(startTime)){
			par.put("startTime",startTime);
		}
		if(Check.NuNObj(startTime) && !Check.NuNObj(endTime)){
			throw new BusinessException("start time is null but endTime is not null");
		}
		if(!Check.NuNObj(endTime) && !startTime.equals(endTime)){
			par.put("endTime",endTime);
		}
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByRoomFid", HouseLockEntity.class, par);
	}

	/**
	 * 获取床铺锁信息
	 * @param bedFid
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByBedFid(String bedFid,Date startTime,Date endTime) {
		Map<String,Object> par = new HashMap<>();
		par.put("bedFid",bedFid);
		if(!Check.NuNObj(startTime)){
			par.put("startTime", startTime);
		}
		if(!Check.NuNObj(endTime)){
			par.put("endTime", endTime);
		}
		return mybatisDaoContext.findAll(SQLID + "getHouseLockByBedFid", HouseLockEntity.class, par);
	}

	/**
	 * 保存订单的房源锁定信息
	 * @author afi
	 * @created 2016年4月1日 下午20:22:38
	 * @param lock
	 * @return
	 */
	public int insertHouseLock(HouseLockEntity lock) {
		if(Check.NuNObj(lock)){
			LogUtil.error(logger, "current lock is null on insertHouseLock");
			throw new BusinessException("current lock is null on insertHouseLock");
		}

		if(Check.NuNObj(lock.getCreateTime())){
			lock.setCreateTime(new Date());
		}
		if(Check.NuNStr(lock.getHouseFid())){
			LogUtil.error(logger,"houseFid is null on insertHouseLock");
			throw new BusinessException("houseFid is null on insertHouseLock");
		}
		if(Check.NuNObj(lock.getRentWay())){
			LogUtil.error(logger,"rentWay is null on insertHouseLock");
			throw new BusinessException("rentWay is null on insertHouseLock");
		}
		if(Check.NuNObj(lock.getLockTime())){
			LogUtil.error(logger, "lockTime is null on insertHouseLock");
			throw new BusinessException("lockTime is null on insertHouseLock");
		}else {
			lock.setLockTime(DateSplitUtil.connectDate(lock.getLockTime(),"00:00:00"));
		}
		if(Check.NuNObj(lock.getLockType())){
			LogUtil.error(logger, "lockType is null on insertHouseLock");
			throw new BusinessException("lockType is null on insertHouseLock");
		}
		//afi 对当前的房源锁获取有效信息的md5 保证当前的锁不会重复
		lock.setFid(HouseLockUtil.generateMd54lock(lock));

		return mybatisDaoContext.save(SQLID + "insertHouseLock", lock);
	}


	/**
	 * 根据订单号更新 当前订单锁的支付状态
	 * @author afi
	 * @created 2016年4月5日
	 * @param orderSn
	 * @return
	 */
	public int payLockHouseByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("orderSn", orderSn);
		return mybatisDaoContext.update(SQLID + "payLockHouseByOrderSn", paramMap);
	}



	/**
	 * 根据订单编号，解锁房源  (限于订单锁定类型，定时任务和结算时释放房源)
	 * @author liyingjie
	 * @created 2016年4月5日
	 * @param orderSn
	 * @return
	 */
	public int delLockHouseByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("orderSn", orderSn);
		return mybatisDaoContext.update(SQLID + "delLockByOrderSn", paramMap);
	}

	/**
	 * 根据订单编号，解锁房源  (限于订单锁定类型，定时任务和结算时释放房源)
	 * @author liyingjie
	 * @created 2016年4月5日
	 * @param orderSn
	 * @return
	 */
	public int unLockHouseByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("orderSn", orderSn);
		return mybatisDaoContext.update(SQLID + "unLockByOrderSn", paramMap);
	}


	/**
	 * 解锁房源 limitDate 之后的需要解锁
	 * @author afi
	 * @created 2016年4月5日
	 * @param orderSn
	 * @param limitDate 截止时间
	 * @return
	 */
	public int unLockHouseByOrderSn(String orderSn,Date limitDate){
		if(Check.NuNStr(orderSn)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("orderSn", orderSn);
		paramMap.put("limitDate", limitDate);
		return mybatisDaoContext.update(SQLID + "unLockByOrderSn", paramMap);

	}


	/**
	 * 解锁房源 limitDate 之后的需要解锁
	 * @author afi
	 * @created 2016年4月5日
	 * @param orderSn
	 * @param limitDate 截止时间
	 * @return
	 */
	public int delLockHouseByOrderSn(String orderSn,Date limitDate){
		if(Check.NuNStr(orderSn)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("orderSn", orderSn);
		paramMap.put("limitDate", limitDate);
		return mybatisDaoContext.delete(SQLID + "delLockByOrderSn", paramMap);

	}

	/**
	 * 
	 * 房源或者房间30预订数（可以根据类型查询  lock_type 1订单锁定 2房东）
	 *
	 * @author bushujie
	 * @created 2016年4月11日 下午8:51:19
	 *
	 * @param paramMap
	 * @return
	 */
	public int getBookDaysByFid(Map<String, Object> paramMap){
		return mybatisDaoContext.findOneSlave(SQLID+"getBookDaysByFid", Integer.class, paramMap);
	}



	/**
	 * 
	 * 整租转合租前房源30天内预订天数
	 *
	 * @author bushujie
	 * @created 2016年4月11日 下午8:56:45
	 *
	 * @param paramMap
	 * @return
	 */
	public int getBookDaysByHouseFidAndRoomFid(Map<String, Object> paramMap){
		return mybatisDaoContext.findOneSlave(SQLID+"getBookDaysByHouseFidAndRoomFid", Integer.class, paramMap);
	}

	/**
	 * 更新房源锁定信息
	 *
	 * @author liujun
	 * @created 2016年5月13日
	 *
	 * @param houseLockEntity
	 * @return
	 */
	public int updateHouseLock(HouseLockEntity houseLockEntity) {
		return mybatisDaoContext.update(SQLID+"updateHouseLockByFid", houseLockEntity);
	}

	/**
	 * 
	 * 物理删除房源锁定信息
	 *
	 * @author liujun
	 * @created 2016年7月14日
	 *
	 * @param fid
	 * @return
	 */
	public int phyDelHouseLockByFid(String fid){
		if(Check.NuNStr(fid)){
			return 0;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>(1);
		paramMap.put("fid", fid);
		return mybatisDaoContext.delete(SQLID + "delHouseLockByFid", paramMap);
	}



	/**
	 * 获取当前的房源的锁定信息
	 * @param houseFid
	 * @return
	 */
	public List<String> getLocksByHouseFid(HousePriorityDto housePriorityDto){
		List<String> rst = new ArrayList<>();
		List<Date> list = null;
		try {
			list = mybatisDaoContext.findAll(SQLID + "getLocksByHouseFid", Date.class, housePriorityDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!Check.NuNCollection(list)){
			for(Date date: list){
				rst.add(sdf.format(date));
			}
		}
		return rst;
	}


	/**
	 * 获取当前的房间的锁定信息
	 * @param roomFid
	 * @return
	 */
	public List<String> getLocksByRoomFid(HousePriorityDto housePriorityDto){
		List<String> rst = new ArrayList<>();
		List<Date> list = mybatisDaoContext.findAll(SQLID + "getLocksByRoomFid", Date.class, housePriorityDto);
		if(!Check.NuNCollection(list)){
			for(Date date: list){
				rst.add(sdf.format(date));
			}
		}
		return rst;
	}

	/**
	 * 获取airbnb设置的锁
	 * @author jixd
	 * @created 2017年04月17日 21:22:08
	 * @param
	 * @return
	 */
	public List<HouseLockEntity> listAirLocks(LockHouseRequest lockHouseRequest){
		return mybatisDaoContext.findAll(SQLID + "listAirLocks",HouseLockEntity.class,lockHouseRequest);
	}

    /**
     * 删除airbnb锁
     * @author jixd
     * @created 2017年04月18日 10:41:20
     * @param
     * @return
     */
    public int deleteAirLock(LockHouseRequest lockHouseRequest){
        List<Date> dateListQuery = new ArrayList<>();
        List<Date> lockDayList = lockHouseRequest.getLockDayList();
        for (Date date: lockDayList){
            dateListQuery.add(DateSplitUtil.connectDate(date,"00:00:00"));
        }
        Map<String,Object> par = new HashMap<>();
        
        if(!Check.NuNStr(lockHouseRequest.getHouseFid())){
        	   par.put("houseFid",lockHouseRequest.getHouseFid());
        }else{
        	  par.put("roomFid",lockHouseRequest.getRoomFid());
        }
    
        par.put("dateList", dateListQuery);
        return mybatisDaoContext.delete(SQLID + "deleteAirLock",par);
    }



	/**
	 * 
	 * 查询 当前房源 的灵活定价 日期
	 * 说明： 查询日历 是当前日期往后的日期（故时间 一定是 当前时间往后）
	 *
	 * @author yd
	 * @created 2016年12月6日 下午5:53:29
	 *
	 * @param housePriorityDto
	 * @return
	 */
	public List<String> getPriorityDate(HousePriorityDto housePriorityDto){
		
		List<String> lockDateList =null;
		if(Check.NuNObj(housePriorityDto)
				||Check.NuNStrStrict(housePriorityDto.getStartDateStr())
				||Check.NuNStrStrict(housePriorityDto.getEndDateStr())){
			return lockDateList;
		}
		if (!Check.NuNStr(housePriorityDto.getHouseRoomFid()) && RentWayEnum.ROOM.getCode()==housePriorityDto.getRentWay()){
			lockDateList = getLocksByRoomFid(housePriorityDto);
		}else if (!Check.NuNStr(housePriorityDto.getHouseBaseFid()) && RentWayEnum.HOUSE.getCode()==housePriorityDto.getRentWay()){
			lockDateList = getLocksByHouseFid(housePriorityDto);
		}
		return lockDateList;
	/*	Set<String> priorityDateList = new TreeSet<String>(); 
		if(!Check.NuNCollection(lockDateList)){

			Calendar cursor = Calendar.getInstance(); 
			cursor.setTime(housePriorityDto.getNowDate());

			List<Set<String>> notLockContinuousDaysList = new ArrayList<Set<String>>();

			Set<String> notLockContinuousDays = new HashSet<String>();

			if (!lockDateList.contains(sdf.format(cursor.getTime()))) {//当天
				notLockContinuousDays.add(sdf.format(cursor.getTime()));
				if (!Check.NuNCollection(notLockContinuousDays)) {							
					notLockContinuousDaysList.add(notLockContinuousDays);
				}
				notLockContinuousDays = new HashSet<String>();
				cursor.add(Calendar.DATE, 1);
			}
			cursor.setTime(housePriorityDto.getStartDate());
			for (; ;) {  
				if(cursor.getTime().after(housePriorityDto.getEndDate())){ 
					break;
				} 
				if (!lockDateList.contains(sdf.format(cursor.getTime()))) {//没有锁定
					notLockContinuousDays.add(sdf.format(cursor.getTime()));
				}else{
					if (!Check.NuNCollection(notLockContinuousDays)) {							
						notLockContinuousDaysList.add(notLockContinuousDays);
					}
					notLockContinuousDays = new HashSet<String>(); 
				}

				cursor.add(Calendar.DATE, 1);
			}

			if (!Check.NuNCollection(notLockContinuousDays)) {							
				notLockContinuousDaysList.add(notLockContinuousDays);//最后一部分
			}

			if (!Check.NuNCollection(notLockContinuousDaysList)) {
				for (Set<String> notLockDays : notLockContinuousDaysList) {
					if (notLockDays.size()<=housePriorityDto.getPriorityDateLimit()) {//满足夹心日期条件
						priorityDateList.addAll(notLockDays);
					}
				}
			}


		}
		List<String> priorityDays = new ArrayList<String>(); 
		if (!Check.NuNCollection(priorityDateList)) {
			priorityDays.addAll(priorityDateList);
		}
		return  priorityDays;*/
	}

	/**
	 * 
	 * 根据orderSn修改lockType
	 *
	 * @author loushuai
	 * @created 2017年5月11日 下午8:51:59
	 *
	 * @param orderSn
	 */
	public int updateSystemLock(String orderSn,Date limitDate) {
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		params.put("limitDate",limitDate);
		return mybatisDaoContext.update(SQLID + "updateSystemLock", params);
	}
	/**
	 *  查询房源fid根据锁定时间
	 * @param lockTime
	 * @return
	 */
	public List<String> findHouseFidsByLockTime(String lockTime){
		return mybatisDaoContext.findAll(SQLID+"findHouseFidsByLockTime", String.class, lockTime);
	}
	
	/**
	 * 查询房间fid根据锁定时间
	 * @param lockTime
	 * @return
	 */
	public List<String> findRoomFidsByLockTime(String lockTime){
		return mybatisDaoContext.findAll(SQLID+"findRoomFidsByLockTime", String.class, lockTime);
	}
	
	/**
	 * 查询当前房源当前被下单支付锁定的记录数（整租）
	 * @author lusp   2017/05/17
	 * @param tonightDiscountEntity
	 * @return
	 */
	public Long getcountHousePayLockCurrentDayByHousefid(TonightDiscountEntity tonightDiscountEntity){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("houseFid",tonightDiscountEntity.getHouseFid());
		return mybatisDaoContext.count(SQLID + "getcountHousePayLockCurrentDayByHousefid", countMap);
	}
	/**
	 * 查询当前房源当前被下单支付锁定的记录数（分租）
	 * @author lusp   2017/05/17
	 * @param tonightDiscountEntity
	 * @return
	 */
	public Long getcountHousePayLockCurrentDayByHousefidAndRoomfid(TonightDiscountEntity tonightDiscountEntity){
		Map<String,Object> countMap = new HashMap<>();
		countMap.put("roomFid", tonightDiscountEntity.getRoomFid());
		return mybatisDaoContext.count(SQLID + "getcountHousePayLockCurrentDayByHousefidAndRoomfid", countMap);
	}

	/**
	 * 根据orderSn获取所有houseLock对象
	 *
	 * @author loushuai
	 * @created 2017年5月20日 下午5:46:31
	 *
	 * @param orderSn
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByOrderSn(String orderSn) {
		Map<String, Object> params = new HashMap<>();
		params.put("orderSn", orderSn);
		return mybatisDaoContext.findAll(SQLID+"getHouseLockByOrderSn", HouseLockEntity.class, params);
	}
	
	/**
	 * 
	 * 查询房源当前时间后的锁定记录
	 *
	 * @author zhangyl
	 * @created 2017年6月29日 下午2:38:21
	 *
	 * @param houseLockRequest
	 * @return
	 */
	public List<CalendarDataVo> getHouseLockDayList(HouseLockRequest houseLockRequest) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("houseFid", houseLockRequest.getFid());
		paramMap.put("roomFid", houseLockRequest.getRoomFid());
		paramMap.put("rentWay", houseLockRequest.getRentWay());
		paramMap.put("startTime", houseLockRequest.getStarTime());
		return mybatisDaoContext.findAll(SQLID + "getHouseLockDayList", CalendarDataVo.class, paramMap);
	}
}
