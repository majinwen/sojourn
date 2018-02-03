
package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.AbHouseLockLogEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.entity.order.HouseLockLogEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.order.dao.AbHouseLockLogDao;
import com.ziroom.minsu.services.order.dao.HouseLockDao;
import com.ziroom.minsu.services.order.dao.HouseLockLogDao;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseCountRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.LockSourceEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.*;

/**
 * <p>房源的锁定情况</p>
 * <p/>
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
@Service("order.houseLockServiceImpl")
public class HouseLockServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseLockServiceImpl.class);

    @Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;

	@Resource(name = "order.houseLockLogDao")
	private HouseLockLogDao houseLockLogDao;

	@Resource(name = "order.abHouseLockLogDao")
	private AbHouseLockLogDao abHouseLockLogDao;


	/**
	 * 获取当前的夹缝价格
	 * @param fid
	 * @param rentWay
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getCreviceCount(String fid,Integer rentWay,Date startTime,Date endTime,Date tillTime){
		HouseLockEntity end;
		HouseLockEntity start;
		if (RentWayEnum.HOUSE.getCode() == rentWay){
			end = houseLockDao.getHouseLockByHouseFidEnd(fid,endTime);
			if (Check.NuNObj(end)){
				//没有结束 就没有夹心价格
				LogUtil.info(LOGGER, "【夹缝价格】 没有结束时间");
				return null;
			}
			start = houseLockDao.getHouseLockByHouseFidBefore(fid,startTime);

		}else{
			end = houseLockDao.getHouseLockByRoomFidEnd(fid,endTime);
			if (Check.NuNObj(end)){
				//没有结束 就没有夹心价格
				LogUtil.info(LOGGER, "【夹缝价格】 没有结束时间");
				return null;
			}
			start = houseLockDao.getHouseLockByRoomFidBefore(fid,startTime);
		}
		//设置夹心
		if (!Check.NuNObj(tillTime) && !Check.NuNObj(end)  && DateSplitUtil.transDateTime2Date(tillTime).before(DateSplitUtil.transDateTime2Date(end.getLockTime()))){
			return null;
		}
		if (Check.NuNObj(start)){
			return DateSplitUtil.countDateSplit(DateSplitUtil.getYesterday(new Date()),end.getLockTime())-1;
		}else if(!DateSplitUtil.transDateTime2Date(start.getLockTime()).before(DateSplitUtil.transDateTime2Date(new Date()))){
			//锁在当前之前
			return DateSplitUtil.countDateSplit(start.getLockTime(),end.getLockTime())-1;
		}else {
			return DateSplitUtil.countDateSplit(DateSplitUtil.getYesterday(new Date()),end.getLockTime())-1;
		}
	}




    /**
     * 解锁订单对房源的锁定
     * @author afi
     * @param orderSn
     */
    public void unLockHouseByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
            throw new BusinessException("订单号为空");
        }
        houseLockDao.unLockHouseByOrderSn(orderSn);
    }


    /**
     * 保存订单的房源锁定信息
     * @author afi
     * @created 2016年4月1日 下午20:22:38
     * @param lock
     * @return
     */
    public int insertHouseLock(HouseLockEntity lock){
        return houseLockDao.insertHouseLock(lock);
    }
    
    /**
     * 下单锁定房源
     * @author lishaochuan
     * @create 2016年7月5日下午4:46:25
     * @param houseLocks
     */
	public void insertHouseLockListByOrder(List<HouseLockEntity> houseLocks) {
		int num = 0;
		for (HouseLockEntity houseLock : houseLocks) {
			num += houseLockDao.insertHouseLock(houseLock);
		}
		if (num != houseLocks.size()) {
			LogUtil.error(LOGGER, "锁房源入口条数错误, needNum:{},resultNum:{},houseLocks:{}", houseLocks.size(), num, houseLocks);
			throw new BusinessException("锁房源错误");
		}
	}

    /**
     * 保存订单的房源锁定信息
     * @author afi
     * @created 2016年4月23日 下午20:22:38
     * @param locks
     * @return
     */
    public int insertHouseLockList(LockHouseRequest locks){
        if(Check.NuNObj(locks) || Check.NuNCollection(locks.getLockDayList()) ){
            return 0;
        }
        int total =0;
        for(Date day:locks.getLockDayList()){
            HouseLockEntity lock = new HouseLockEntity();
            lock.setLockType(locks.getLockType());
            lock.setRentWay(locks.getRentWay());
            lock.setHouseFid(locks.getHouseFid());
            lock.setRoomFid(locks.getRoomFid());
            lock.setBedFid(locks.getBedFid());
            lock.setLockTime(DateSplitUtil.transDateTime2Date(day));
            total += houseLockDao.insertHouseLock(lock);
        }
        return total;
    }


    /**
     * 获取房源的锁定信息
     * @afi
     * @param request
     * @return
     */
    public List<HouseLockEntity>  checkHouseLockList(LockHouseRequest request){


        if(request.getRentWay() == RentWayEnum.HOUSE.getCode()){
            //房源的锁定情况
            return houseLockDao.getHouseLockByHouseFidList(request.getHouseFid(), request.getLockDayList());
        }else if(request.getRentWay() == RentWayEnum.ROOM.getCode()){
            //房源的锁定情况
            return houseLockDao.getHouseLockByRoomFidList(request.getHouseFid(), request.getRoomFid(), request.getLockDayList());
        }else if(request.getRentWay() == RentWayEnum.BED.getCode()){
            //房源的锁定情况
        }else{
            LogUtil.error(LOGGER, "租住方式错误，request:{}", JsonEntityTransform.Object2Json(request));
            throw new BusinessException("租住方式错误");
        }
        return null;
    }
    
    /**
     * 
     * 锁定房源
     *
     * @author jixd
     * @created 2016年8月4日 下午5:46:12
     *
     * @param request
     * @return
     */
    public int lockHouseForPC(LockHouseRequest request){
    	List<Date> setLockList = request.getLockDayList();
    	Map<String,Object> paraMap= new HashMap<>();
    	paraMap.put("houseFid",request.getHouseFid());
    	paraMap.put("roomFid", request.getRoomFid());
    	paraMap.put("rentWay", request.getRentWay());
        int num = 0; 
    	for(Date date : setLockList){
    		paraMap.put("lockTime", date);
    		List<HouseLockEntity> lockList = houseLockDao.getHouseLockByFidAndSetTime(paraMap);
    		if(Check.NuNCollection(lockList)){
    			HouseLockEntity lockEntity = new HouseLockEntity();
    			lockEntity.setHouseFid(request.getHouseFid());
    			lockEntity.setRentWay(request.getRentWay());
    			lockEntity.setRoomFid(request.getRoomFid());
    			lockEntity.setLockTime(date);
    			lockEntity.setLockType(LockTypeEnum.LANDLADY.getCode());
    			int count = houseLockDao.insertHouseLock(lockEntity);
    			num += count;
    		}
    	}
    	return num;
    }

    /**
     * 获取房源的锁定信息
     * @afi
     * @param request
     * @return
     */
    public List<HouseLockEntity>  getHouseLock(HouseLockRequest request){
        if(request.getRentWay() == RentWayEnum.HOUSE.getCode()){
            //房源的锁定情况
            return houseLockDao.getHouseLockByHouseFid(request.getFid(), DateSplitUtil.transDateTime2Date(request.getStarTime()),DateSplitUtil.transDateTime2Date(request.getEndTime()));
        }else if(request.getRentWay() == RentWayEnum.ROOM.getCode()){
            //房源的锁定情况
            return houseLockDao.getHouseLockByRoomFid(request.getFid(),request.getRoomFid(),DateSplitUtil.transDateTime2Date(request.getStarTime()), DateSplitUtil.transDateTime2Date(request.getEndTime()));
        }else if(request.getRentWay() == RentWayEnum.BED.getCode()){
            //房源的锁定情况
            return houseLockDao.getHouseLockByBedFid(request.getFid(), DateSplitUtil.transDateTime2Date(request.getStarTime()), DateSplitUtil.transDateTime2Date(request.getEndTime()));
        }else{
            LogUtil.error(LOGGER,"参数错误，HouseLockRequest：{}", request);
            throw new BusinessException(" rentWay is null on getHouseLock");
        }
    }

	/**
	 * 逻辑删除房东锁定房源信息
	 *
	 * @author liujun
	 * @created 2016年5月13日
	 *
	 * @param houseLockList
	 */
	public int logicDeleteHouseLockList(List<HouseLockEntity> houseLockList) {
		int upNum = 0;
		for (HouseLockEntity houseLockEntity : houseLockList) {
			houseLockEntity.setIsDel(SysConst.IS_DEL);
			houseLockEntity.setLastModifyDate(new Date());
			upNum += houseLockDao.updateHouseLock(houseLockEntity);
		}
		return upNum;
	}
	
	/**
	 * 物理删除房东锁定房源信息
	 *
	 * @author liujun
	 * @created 2016年5月13日
	 *
	 * @param houseLockList
	 */
	public int phyDeleteHouseLockList(List<HouseLockEntity> houseLockList) {
		int upNum = 0;
		for (HouseLockEntity houseLockEntity : houseLockList) {
			upNum += houseLockDao.phyDelHouseLockByFid(houseLockEntity.getFid());
		}
		return upNum;
	}
	
	/**
	 * 
	 * 整租房源30预定天数
	 *
	 * @author jixd
	 * @created 2016年6月26日 下午1:21:32
	 *
	 * @return
	 */
	public int getBookDaysByFid(LockHouseCountRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("houseFid", request.getHouseFid());
		paramMap.put("roomFid", request.getRoomFid());
		paramMap.put("startTime", request.getStartTime());
		paramMap.put("endTime", request.getEndTime());
		paramMap.put("lockType", request.getLockType());
		return houseLockDao.getBookDaysByFid(paramMap);
	}
	
	
	
	
	/**
	 * 获取当前房源在指定时间的占用订单
	 * @author lishaochuan
	 * @create 2016年7月5日下午6:07:42
	 * @param houseFid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    public List<String> getHouseLockOrderSn(String houseFid , Date startTime ,Date endTime){
        List<Date> dateList = new ArrayList<>();
        List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
        for (Date date: dayList){
            dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
        }
        return houseLockDao.getHouseLockOrderSn(houseFid, dateList);
    }

    /**
     * 获取当前房间在指定时间的占用订单
     * @author lishaochuan
     * @create 2016年7月5日下午6:08:00
     * @param roomFid
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getRoomLockOrderSn(String roomFid , Date startTime ,Date endTime){
        List<Date> dateList = new ArrayList<>();
        List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
        for (Date date: dayList){
            dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
        }
        return houseLockDao.getRoomLockOrderSn(roomFid, dateList);
    }

    /**
     * 获取当前床位在指定时间的占用订单
     * @author lishaochuan
     * @create 2016年7月5日下午6:08:07
     * @param bedFid
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getBedLockOrderSn(String bedFid , Date startTime ,Date endTime){
        List<Date> dateList = new ArrayList<>();
        List<Date> dayList = DateSplitUtil.dateSplit(startTime, endTime);
        for (Date date: dayList){
            dateList.add(DateSplitUtil.connectDate(date,"00:00:00"));
        }
        return houseLockDao.getBedLockOrderSn(bedFid, dateList);
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
		return this.houseLockDao.getPriorityDate(housePriorityDto);
	}

	/**
	 * 保存操作房源锁后台记录
	 * @author jixd
	 * @created 2017年04月11日 14:59:57
	 * @param
	 * @return
	 */
	public int saveHouseLockLog(HouseLockLogEntity houseLockLogEntity){
		return houseLockLogDao.saveLog(houseLockLogEntity);
	}

	/**
	 * 同步airbnb房源锁
	 * @author jixd
	 * @created 2017年04月18日 10:59:55
	 * @param
	 * @return
	 */
    public int syncAirLock(LockHouseRequest lockHouseRequest) {
        int count = 0;

        List<Date> lockDayList = lockHouseRequest.getLockDayList();
        List<String> requestLockDaysStrs = new ArrayList<>();
        if (Check.NuNCollection(lockDayList)) {
            return count;
        }
        for (Date date : lockDayList) {
            requestLockDaysStrs.add(DateUtil.dateFormat(date, "yyyy-MM-dd"));
        }

        //记录同步日志
        AbHouseLockLogEntity abHouseLockLogEntity = new AbHouseLockLogEntity();
        abHouseLockLogEntity.setHouseFid(lockHouseRequest.getHouseFid());
        abHouseLockLogEntity.setRoomFid(lockHouseRequest.getRoomFid());
        abHouseLockLogEntity.setRentWay(lockHouseRequest.getRentWay());

        List<HouseLockEntity> houseLockList = houseLockDao.listAirLocks(lockHouseRequest);

        //如果查询没有airbnb锁定的  直接插入锁
        if (!Check.NuNCollection(houseLockList)) {
            List<String> dblockDays = new ArrayList<>();
            for (HouseLockEntity houseLockEntity : houseLockList) {
                dblockDays.add(DateUtil.dateFormat(houseLockEntity.getLockTime(), "yyyy-MM-dd"));
            }
            //先copy一份 等会做交集处理
            List<String> jiaojiLockDays = new ArrayList<>(requestLockDaysStrs);
            //做交集处理
            jiaojiLockDays.retainAll(dblockDays);
            //集合插入处理
            requestLockDaysStrs.removeAll(jiaojiLockDays);
            //集合删除处理
            dblockDays.removeAll(jiaojiLockDays);

            if (!Check.NuNCollection(dblockDays)) {
                List<Date> dblockDaysDel = new ArrayList<>();
                for (String dayStr : dblockDays) {
                    try {
                        dblockDaysDel.add(DateUtil.parseDate(dayStr, "yyyy-MM-dd"));
                    } catch (ParseException e) {
                        LogUtil.info(LOGGER, "日期转化错误，day={}, e={}", dayStr, e);
                    }
                }

                lockHouseRequest.setLockDayList(dblockDaysDel);
                int delNum = houseLockDao.deleteAirLock(lockHouseRequest);
                //删除
                abHouseLockLogEntity.setOperatorType(1);
                abHouseLockLogEntity.setOperatorNumber(delNum);
                abHouseLockLogDao.saveLog(abHouseLockLogEntity);
                count += delNum;
            }
        }
        //如果不为空 直接插入
        if (!Check.NuNCollection(requestLockDaysStrs)) {
            int saveNum = 0;
            for (String dayStr : requestLockDaysStrs) {
                try {
                    HouseLockEntity houseLockEntity = new HouseLockEntity();
                    houseLockEntity.setHouseFid(lockHouseRequest.getHouseFid());
                    houseLockEntity.setRoomFid(lockHouseRequest.getRoomFid());
                    houseLockEntity.setRentWay(lockHouseRequest.getRentWay());
                    houseLockEntity.setLockSource(LockSourceEnum.AIRBNB.getCode());
                    houseLockEntity.setLockType(LockTypeEnum.LANDLADY.getCode());
                    houseLockEntity.setLockTime(DateUtil.parseDate(dayStr, "yyyy-MM-dd"));
                    saveNum += houseLockDao.insertHouseLock(houseLockEntity);
                } catch (Exception e) {
                    LogUtil.info(LOGGER, "日期转化错误，day={} e={}", dayStr, e);
                }
            }
            count += saveNum;
            abHouseLockLogEntity.setOperatorType(2);
            abHouseLockLogEntity.setOperatorNumber(saveNum);
            abHouseLockLogDao.saveLog(abHouseLockLogEntity);
        }

        return count;
    }

	/**
	 * 
	 * 查询房源fid根据锁定时间（整租）
	 *
	 * @author bushujie
	 * @created 2017年5月16日 下午3:25:31
	 *
	 * @param lockTime
	 * @return
	 */
	public List<String> findHouseFidsByLockTime(String lockTime){
		return houseLockDao.findHouseFidsByLockTime(lockTime);
	}
	
	/**
	 * 
	 * 查询房间fid根据锁定时间（合租）
	 *
	 * @author bushujie
	 * @created 2017年5月16日 下午3:27:24
	 *
	 * @param lockTime
	 * @return
	 */
	public List<String> findRoomFidsByLockTime(String lockTime){
		return houseLockDao.findRoomFidsByLockTime(lockTime);
	}
	
	
	/**
	 * 查询当前房源是否被锁定
	 * @author lusp   2017/05/17
	 * @param tonightDiscountEntity
	 * @return
	 */
	public boolean isHousePayLockCurrentDay(TonightDiscountEntity tonightDiscountEntity){
		
		Long count = null;
		if (RentWayEnum.HOUSE.getCode() == tonightDiscountEntity.getRentWay()) {
            count = houseLockDao.getcountHousePayLockCurrentDayByHousefid(tonightDiscountEntity);
        } else if(RentWayEnum.ROOM.getCode() == tonightDiscountEntity.getRentWay()){
        	count = houseLockDao.getcountHousePayLockCurrentDayByHousefidAndRoomfid(tonightDiscountEntity);
        }
		if(count != null && count == 1){
			return true;
		}
		return false;
	}




	/**
	 * 根据订单，获取所有房源锁定对象
	 *
	 * @author loushuai
	 * @created 2017年5月20日 下午5:44:45
	 *
	 * @param orderSn
	 * @return
	 */
	public List<HouseLockEntity> getHouseLockByOrderSn(String orderSn) {
		return houseLockDao.getHouseLockByOrderSn(orderSn);
	}
	
	/**
	 * 
	 * 查询房源当前时间后的锁定记录
	 * @author zyl
	 * @created 2017年6月27日 下午3:50:10
	 * 
	 * @param houseLockRequest
	 * @return
	 */
	public List<CalendarDataVo> getHouseLockDayList(HouseLockRequest houseLockRequest) {
		return houseLockDao.getHouseLockDayList(houseLockRequest);
	}
	
}

