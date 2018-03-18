package com.ziroom.minsu.report.house.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.valenum.HouseStatusEnum;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dao.HouseEfficDao;
import com.ziroom.minsu.report.house.dto.HouseEfficDto;
import com.ziroom.minsu.report.house.vo.HouseEfficVo;
/**
 * 
 * <p>房源效率信息——业务实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Service("report.houseEfficService")
public class HouseEfficService implements ReportService <HouseEfficVo,HouseEfficDto>{

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseEfficService.class);
	 
    @Resource(name="report.houseEfficDao")
    private HouseEfficDao houseEfficDao;
    
    @Resource(name="report.confCityDao")
	 private ConfCityDao confCityDao;
    /**
     * 
     * 房源效率信息分页查询
     *
     * @author ls
     * @created 2017年5月8日 上午10:02:26
     *
     * @param paramDto
     * @return
     */
    public PagingResult<HouseEfficVo> getHouseEfficExcelList(HouseEfficDto paramDto){
    	LogUtil.info(LOGGER, "houseEfficService, getHouseEfficExcelList, param={}", JsonEntityTransform.Object2Json(paramDto));
    	if(checkAllParamNull(paramDto)){
			paramDto.setRentWay(0);
		}
    	PagingResult<HouseEfficVo>  allHfidList = getHfidListByParam(paramDto);
    	//3：如果符合条件的房源==0，直接将结果返回，不在调用getAllVoByConfirmHfid方法
    	if(allHfidList.getTotal() == 0){
			return allHfidList;
		}
    	
    	//调用getAllVoByConfirmHfid，根据Hfid将Vo中所有数据产寻到
    	return getAllVoByConfirmHfid(allHfidList,paramDto);
    }
    
    /**
	 * 获取所有符合条件的Hfid或者Rfid
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
    public PagingResult<HouseEfficVo> getHfidListByParam(HouseEfficDto paramDto){
    	LogUtil.info(LOGGER, "houseEfficService, getHouseEfficExcelList, param={}", JsonEntityTransform.Object2Json(paramDto));
		if(!Check.NuNStr(paramDto.getRegionCode())){
			List<String> pCodeListByRCode = getPCodeListByRCode(paramDto.getRegionCode());
			if(Check.NuNCollection(pCodeListByRCode)){
				return new PagingResult<HouseEfficVo>();
			}
			paramDto.setProvinceCodeList(pCodeListByRCode);
		}
		if(paramDto.getRentWay()==0){//整租，查询出所有房源Hfid集合
			PagingResult<HouseEfficVo> allHfidList = houseEfficDao.getEntireRentList(paramDto);
			return allHfidList;
		}
		//分租，查询出所有房间roomFid集合
		PagingResult<HouseEfficVo> allHfidList = houseEfficDao.getJoinRentList(paramDto);
		LogUtil.info(LOGGER, "houseEfficService, getHouseEfficExcelList, result={}", JsonEntityTransform.Object2Json(allHfidList));
		return allHfidList;
	}
    
    /**
	 * 根据regionCode获取所有ProvinceCode集合
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
    public List<String> getPCodeListByRCode(String regionCode){
    	List<String> pCodeList = houseEfficDao.getPCodeListByRCode(regionCode);
    	return pCodeList;
    }
    
    /**
	 * 根据所有确定的Hfid或者Rfid，将Vo中需要展示的字段全部填充
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
    public PagingResult<HouseEfficVo> getAllVoByConfirmHfid(PagingResult<HouseEfficVo> allConfirmHfid, HouseEfficDto paramDto){
       LogUtil.info(LOGGER, "houseEfficService, getAllVoByConfirmHfid, param={}", JsonEntityTransform.Object2Json(allConfirmHfid));
 	   List<HouseEfficVo> transferList = allConfirmHfid.getRows();
 	   for (HouseEfficVo temp : transferList) {
 		   
 		//国家code==》国家名称 
       	if(!Check.NuNStr(temp.getNationCode())){
       		NationCodeEntity nationCodeEntity = confCityDao.getNationName(temp.getNationCode());
       		if(!Check.NuNObj(nationCodeEntity) && !Check.NuNObj(nationCodeEntity.getNationName())){
       			temp.setNationName(nationCodeEntity.getNationName());
       		}
       	}
       	
       	//城市code==》城市名称
       	ConfCityEntity cityByCode = confCityDao.getCityByCode(temp.getCityCode());
       	 if(!Check.NuNObj(cityByCode) && !Check.NuNStr(cityByCode.getShowName())){
       		 temp.setCityName(cityByCode.getShowName());
       	 }
       
       	//获取大区名称
       	 if(!Check.NuNStr(temp.getProvinceCode())){
       		CityRegionEntity cityRegionEntity = confCityDao.getRegionByProvinceCode(temp.getProvinceCode());
       		 if(!Check.NuNObj(cityRegionEntity) && !Check.NuNStr(cityRegionEntity.getRegionName())){
       			 temp.setRegionName(cityRegionEntity.getRegionName());
       		 }
       	 }
 		 
 		   //房源状态==>房源状态名称
	 	   	if(!Check.NuNObj(temp.getHouseStatus())){
	 	   	   temp.setHouseStatusName(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()).getName());
	    	}
 	   		
 	       //message库，先从t_msg_first_advisory表查询首次咨询时间，如果查不到，从t_msg_base表中查询
	 	   HouseEfficVo tMsgBEfficVo = null;
	 	   HouseEfficVo tMsgFAEfficVo = null;
	 	   if(paramDto.getRentWay()==0){
	 		     //从`t_msg_base`表中查不到数据，要尝试从t_msg_first_advisory查询数据
	 		     tMsgFAEfficVo = houseEfficDao.getFromTMsgFAByHfid(temp.getHouseBaseFid());
	 		  if(Check.NuNObj(tMsgBEfficVo)){//查不到，从msg_base表中查
	 			 tMsgBEfficVo = houseEfficDao.getFromTMsgByHfid(temp.getHouseBaseFid());
	 		  }
	 	   }else{
	 		    tMsgFAEfficVo = houseEfficDao.getFromTMsgFAByRfid(temp.getHouseBaseFid());
	 		 if(Check.NuNObj(tMsgBEfficVo)){
	 			tMsgBEfficVo = houseEfficDao.getFromTMsgByRfid(temp.getRoomFid());
	 		  }
	 	   }
 		    
 		    if(!Check.NuNObj(tMsgBEfficVo) && !Check.NuNStr(tMsgBEfficVo.getFirstAdviceDate())){
 		    	temp.setFirstAdviceDate(tMsgBEfficVo.getFirstAdviceDate());
 		    }
 		   if(!Check.NuNObj(tMsgFAEfficVo) && !Check.NuNStr(tMsgFAEfficVo.getFirstAdviceDate())){
		    	temp.setFirstAdviceDate(tMsgFAEfficVo.getFirstAdviceDate());
		    }
 	       	//order库，订单快照表关联订单表      首次申请
 	   		HouseEfficVo tOrderEfficVo = null;
 	   		if(paramDto.getRentWay()==0){
 	   			 tOrderEfficVo = houseEfficDao.getApplyFromTOrderByHfid(temp.getHouseBaseFid());
 	   		}else{
 	   			 tOrderEfficVo = houseEfficDao.getApplyFromTOrderByRfid(temp.getRoomFid());
 	   		}
 	    	if(!Check.NuNObj(tOrderEfficVo) && !Check.NuNStr(tOrderEfficVo.getFirstApplyDate())){
		    	temp.setFirstApplyDate(tOrderEfficVo.getFirstApplyDate());
		    }
 	    	
 	   		//order库，订单快照表关联订单表      首次支付
 	   		HouseEfficVo tOrderPayEfficVo = null;
 	   		if(paramDto.getRentWay()==0){
 	   			 tOrderPayEfficVo = houseEfficDao.getFromTOrderByHfid(temp.getHouseBaseFid());
 	   		}else{
 	   			 tOrderPayEfficVo = houseEfficDao.getFromTOrderRfid(temp.getRoomFid());
 	   		}
 	   		if(!Check.NuNObj(tOrderPayEfficVo)){
 	   			if(!Check.NuNStr(tOrderPayEfficVo.getFirstPayDate())){//首次支付
 	   			   temp.setFirstPayDate(tOrderPayEfficVo.getFirstPayDate());
 	   			}
 	   		}
 	   		
 	   		//order库，快照表关联订单编号，首次实际入住时间
 	   	    HouseEfficVo tOrderRealCheckInVo = null;
 	   		if(paramDto.getRentWay()==0){
 	   		    tOrderRealCheckInVo = houseEfficDao.getFirstRealCheckInTime(temp.getHouseBaseFid());
 	   		}else{
 	   		    tOrderRealCheckInVo = houseEfficDao.getFirstRealCheckInTimeRoom(temp.getRoomFid());
 	   		}
 	   		if(!Check.NuNObj(tOrderRealCheckInVo)){
 	   			if(!Check.NuNStr(tOrderRealCheckInVo.getFirstCheckinDate())){
 	   			    temp.setFirstCheckinDate(tOrderRealCheckInVo.getFirstCheckinDate());
 	   			}
 	   		}
 	   		
 	       	//evaluate库， t_evaluate_order表此房子 	首次评价时间
 	   		HouseEfficVo tEvalOrderEfficVo = null;
 	   		if(paramDto.getRentWay()==0){
 	   			tEvalOrderEfficVo = houseEfficDao.getFromTEvalOrderdByHfid(temp.getHouseBaseFid());
 	   		}else{
 	   			tEvalOrderEfficVo = houseEfficDao.getFromTEvalOrderdByRfid(temp.getRoomFid());
 	   		}
 	      	if(!Check.NuNObj(tEvalOrderEfficVo) && !Check.NuNStr(tEvalOrderEfficVo.getFirstEvalDate())){
 	      		temp.setFirstEvalDate(tEvalOrderEfficVo.getFirstEvalDate());
	   		}
 	   		
 	   }
 	   allConfirmHfid.setRows(transferList);
 	   return allConfirmHfid;
    }
    
    /**
     * 判断是否所有的查询参数都为空，都为空返true  不为空返false
     * @author ls
     * @created 2017年5月3日 上午11:31:14
     *
     * @param paramDto
     * @return
     */
    public boolean checkAllParamNull(HouseEfficDto paramDto){
    	if(Check.NuNObj(paramDto) || Check.NuNStr(paramDto.getNationCode())
				&& Check.NuNStr(paramDto.getCityCode()) 
				&& Check.NuNObj(paramDto.getRentWay())
				&& Check.NuNStr(paramDto.getRegionCode()) 
				&& Check.NuNStr(paramDto.getFirstUpBeginDate()) 
				&& Check.NuNStr(paramDto.getFirstUpEndDate())
				&& Check.NuNObj(paramDto.getHouseStatus())){
    		return true;
    	}
    	return false;
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<HouseEfficVo> getPageInfo(HouseEfficDto par) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseEfficDto par) {
		return null;
	}
    
}
