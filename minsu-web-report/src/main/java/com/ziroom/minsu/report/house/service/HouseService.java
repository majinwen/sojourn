package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.board.dao.EmpTargetDao;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.house.entity.HouseStatusDataEntity;
import com.ziroom.minsu.report.house.dao.HouseDao;
import com.ziroom.minsu.report.house.dao.HouseDetailDao;
import com.ziroom.minsu.report.house.dao.HouseStatusDataDao;
import com.ziroom.minsu.report.house.vo.HouseCountResultVo;
import com.ziroom.minsu.report.house.vo.HouseLifeCycleVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源 HouseService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
@Service("report.houseService")
public class HouseService implements ReportService <HouseLifeCycleVo,HouseRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseService.class);

    @Resource(name="report.houseDao")
    private HouseDao houseDao;
    
    @Resource(name="report.houseDetailDao")
    private HouseDetailDao houseDetailDao;
    
    @Resource(name="report.houseStatusDataDao")
    private HouseStatusDataDao houseStatusDataDao;

	@Resource(name="report.empTargetDao")
	private EmpTargetDao empTargetDao;


    @Override
    public PagingResult<HouseLifeCycleVo> getPageInfo(HouseRequest houseRequest) {
    	if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, " HouseService getPageInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return null;
    	}
    	return houseDetailDao.getHouseLifeCycle(houseRequest);
    }

	/**
	 * 整租 与分租统计各个节点状态数量
	 * @param houseRequest
	 * @return
	 */
	public HouseCountResultVo getEntireCurrentStatusInfo(HouseRequest houseRequest){
		HouseCountResultVo resultVo = new HouseCountResultVo();
		if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "getEntireCurrentStatusInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return resultVo;
    	}
		
		Long entirePublishRes = this.countEntireData(houseRequest,11,11);
		Long entireGuardRejictRes = this.countEntireData(houseRequest,21,21);
		Long entireGuardAcceptRes = this.countEntireData(houseRequest,20,20);
		Long entireQualityRejectRes = this.countEntireData(houseRequest,30,30);
		Long entireOnRes = this.countEntireData(houseRequest,40,40);
		Long entireOffRes = this.countEntireData(houseRequest,41,41);
		Long entireForceOffRes = this.countEntireData(houseRequest,50,50);
		resultVo.setPublish(entirePublishRes);
		resultVo.setGuardRejict(entireGuardRejictRes);
		resultVo.setGuardAccept(entireGuardAcceptRes);
		resultVo.setQualityReject(entireQualityRejectRes);
		resultVo.setOn(entireOnRes);
		resultVo.setOff(entireOffRes);
		resultVo.setForceOff(entireForceOffRes);
		
		return resultVo;
	}
	
	
	/**
	 * 整租 统计发布数量
	 * @param houseRequest
	 * @return
	 */
	public Long countEntireData(HouseRequest houseRequest,Integer houseStatus,Integer toStatus) {
		Long result = 0l;
    	if(!checkParam(houseRequest)){
    		LogUtil.info(LOGGER, "countEntireData param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		
    		return result;
    	}
    	houseRequest.setHouseStatus(houseStatus);
    	houseRequest.setToStatus(toStatus);
    	result = houseDao.countEntireRentDao(houseRequest);
    	return result;
	}
	
	
	
	
	/**
	 * 整租与分租 发布 并 审核通过 ，发布并且品质审核驳回等等 统计查询
	 * @param houseRequest
	 * @return
	 */
	public HouseCountResultVo getLimitEntireCurrentStatusInfo(HouseRequest houseRequest){
		HouseCountResultVo resultVo = new HouseCountResultVo();
		if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "getLimitEntireCurrentStatusInfo param:{}", JsonEntityTransform.Object2Json(houseRequest));
    		return resultVo;
    	}
		
		Long entirePublishRes = this.countEntireData(houseRequest,null,11);
		Long entireGuardRejictRes = this.countLimitEntireData(houseRequest,11,21);
		Long entireGuardAcceptRes = this.countLimitEntireData(houseRequest,11,20);
		Long entireQualityRejectRes = this.countLimitEntireData(houseRequest,11,30);
		Long entireOnRes = this.countLimitEntireData(houseRequest,11,40);
		Long entireOffRes = this.countLimitEntireData(houseRequest,11,41);
		Long entireForceOffRes = this.countLimitEntireData(houseRequest,11,50);
		resultVo.setPublish(entirePublishRes);
		resultVo.setGuardRejict(entireGuardRejictRes);
		resultVo.setGuardAccept(entireGuardAcceptRes);
		resultVo.setQualityReject(entireQualityRejectRes);
		resultVo.setOn(entireOnRes);
		resultVo.setOff(entireOffRes);
		resultVo.setForceOff(entireForceOffRes);
		
		return resultVo;
	}
	
	
	/**
	 * 整租 统计发布数量
	 * @param houseRequest
	 * @return
	 */
	public Long countLimitEntireData(HouseRequest houseRequest,Integer houseStatus,Integer toStatus) {
		Long result = 0l;
    	if(!checkParam(houseRequest)){
    		return result;
    	}
    	houseRequest.setHouseStatus(houseStatus);
    	houseRequest.setToStatus(toStatus);
    	result = houseDao.countLimitEntireRentDao(houseRequest);
    	return result;
	}
	

	
	/**
	 * 校验请求参数
	 * @param houseRequest
	 * @return
	 */
	private boolean checkParam(HouseRequest houseRequest){
		boolean result = true;
		if(Check.NuNObj(houseRequest)){
			result = false;
    		return result;
    	}
		
		if(Check.NuNStr(houseRequest.getCityCode())){
			result = false;
    		return result;
    	}
    	return result;
	}


	@Override
	public Long countDataInfo(HouseRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * 统计房源状态变化周期
	 *
	 * @author bushujie
	 * @created 2016年9月29日 下午5:03:47
	 *
	 * @param startTime
	 */
	public void houseDayStatus(Date startTime,Date nowTime){
		//Date nowTime=new Date();
    	int almost=DateUtil.getDatebetweenOfDayNum(startTime, nowTime);
    	System.err.println("相差天数："+almost);
    	for(int i=0;i<almost;i++){
    		Date statisticsSdate=DateUtils.addDays(startTime, i);
    		System.out.println(DateUtil.dateFormat(statisticsSdate, "yyyy-MM-dd"));
    		Date statisticsEdate=DateUtils.addDays(statisticsSdate, 1);
    		Map<String, Object> paramMap=new HashMap<String, Object>();
    		paramMap.put("beginTime", statisticsSdate);
    		paramMap.put("endTime", statisticsEdate);
    		List<HouseStatusDataEntity> list=houseDao.getHouseDayStatus(paramMap);
    		System.err.println(JsonEntityTransform.Object2Json(list));
    		//数据处理
    		for(HouseStatusDataEntity houseStatusDataEntity:list){
    			Map<String, Object> paMap=new HashMap<String,Object>();
    			paMap.put("houseFid", houseStatusDataEntity.getHouseFid());
    			paMap.put("roomFid", houseStatusDataEntity.getRoomFid());
    			HouseStatusDataEntity oldHouseStatusDataEntity=houseStatusDataDao.getOldStatusStart(paMap);
    			if(Check.NuNObj(oldHouseStatusDataEntity)){
					//整租
					if(houseStatusDataEntity.getRentWay()==RentWayEnum.HOUSE.getCode()){
						houseStatusDataEntity.setOldStatusStart(houseDao.getHouseCreateDate(houseStatusDataEntity.getHouseFid()));
					}
    				//合租
    				if(houseStatusDataEntity.getRentWay()==RentWayEnum.ROOM.getCode()){
    					houseStatusDataEntity.setOldStatusStart(houseDao.getRoomCreateDate(houseStatusDataEntity.getRoomFid()));
    				}
    				//合租无房间不记录
    				if(houseStatusDataEntity.getOldStatusStart()!=null){
	    				houseStatusDataEntity.setNewStatusStart(houseStatusDataEntity.getStatisticsDate());
	    				houseStatusDataEntity.setStatisticsDate(statisticsSdate);
	    				houseStatusDataEntity.setFid(UUIDGenerator.hexUUID());
	    				houseStatusDataDao.insertHouseStatusData(houseStatusDataEntity);
    				}
    			} else {
    				if(!oldHouseStatusDataEntity.getNewStatus().equals(houseStatusDataEntity.getNewStatus())){
    					houseStatusDataEntity.setOldStatusStart(oldHouseStatusDataEntity.getNewStatusStart());
        				houseStatusDataEntity.setNewStatusStart(houseStatusDataEntity.getStatisticsDate());
        				houseStatusDataEntity.setStatisticsDate(statisticsSdate);
        				houseStatusDataEntity.setFid(UUIDGenerator.hexUUID());
        				houseStatusDataDao.insertHouseStatusData(houseStatusDataEntity);
    				}
				}
    		}
    	}
	}


}
