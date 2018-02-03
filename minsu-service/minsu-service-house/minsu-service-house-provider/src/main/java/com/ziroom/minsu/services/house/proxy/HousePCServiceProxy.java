/**
 * @FileName: HousePCServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author jixd
 * @created 2016年7月30日 下午2:43:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseStatisticsMsgEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.house.api.inner.HousePCService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogSpDto;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.TenantHouseServiceImpl;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房源PC相关方法</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Component("house.housePCServiceProxy")
public class HousePCServiceProxy implements HousePCService{

	private static final Logger LOGGER = LoggerFactory.getLogger(HousePCServiceProxy.class);
	
	@Resource(name = "house.houseManageServiceImpl")
    private HouseManageServiceImpl houseManageServiceImpl;
	
	@Resource(name="house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	
	
	@Resource(name="house.messageSource")
    private MessageSource messageSource;
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name = "house.tenantHouseServiceImpl")
	private TenantHouseServiceImpl tenantHouseServiceImpl;
	
	/**
	 * 查询房东房间或者房源的数量
	 */
	@Override
	public String countHouseAndRoomNumByUid(String landLordUid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(landLordUid)){
		 	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
            return dto.toJsonString();
		}
		try{
			Long houseNum = houseManageServiceImpl.countZHouseNumByUid(landLordUid);
			Long roomNum = houseManageServiceImpl.countRoomNumByUid(landLordUid);
			dto.putValue("houseNum", houseNum);
			dto.putValue("roomNum", roomNum);
		}catch(Exception e){
			LogUtil.error(LOGGER, "查询房源和房间数量异常;param:landlordUid={},error={}", landLordUid,e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 获取房东房源 或者 房间列表
	 */
	@Override
	public String getLandlordHouseOrRoomList(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
            return dto.toJsonString();
		}
		HouseBaseListDto listParamDto = JSONObject.parseObject(param, HouseBaseListDto.class);
		if(Check.NuNStr(listParamDto.getLandlordUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
            return dto.toJsonString();
		}
		Integer rentWay = listParamDto.getRentWay();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
            return dto.toJsonString();
		}
		
		PagingResult<HouseRoomVo> pagingResult = null;
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			pagingResult = houseManageServiceImpl.getHousePCList(listParamDto);
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			pagingResult = houseManageServiceImpl.getRoomPCList(listParamDto);
		}
		
		HousePicMsgEntity housePicMsg=null;
		List<HouseRoomVo> houseRoomList = pagingResult.getRows();
		for(HouseRoomVo hr : houseRoomList){
			String readKey = "";
			String housePv = "";
			HouseStatisticsMsgEntity  houseStatisticsMsg = new HouseStatisticsMsgEntity();	
			houseStatisticsMsg.setRentWay(rentWay);
			houseStatisticsMsg.setHouseBaseFid(hr.getHouseBaseFid());
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				readKey = RedisKeyConst.getHouseKey(hr.getHouseBaseFid(),hr.getRentWay());
				housePicMsg = houseManageServiceImpl.getLandlordHouseDefaultPic(hr.getHouseBaseFid());
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				houseStatisticsMsg.setRoomFid(hr.getHouseRoomFid());
				readKey = RedisKeyConst.getHouseKey(hr.getHouseBaseFid(),hr.getHouseRoomFid(),hr.getRentWay());
				housePicMsg=houseManageServiceImpl.findLandlordRoomDefaultPic(hr.getHouseRoomFid());
			}
			if(!Check.NuNObj(housePicMsg)){
				hr.setDefaultPic(housePicMsg.getPicBaseUrl()+housePicMsg.getPicSuffix());
			}
			if(!Check.NuNStr(readKey)){
				try{
					housePv = redisOperations.get(readKey);
				}catch(Exception e){
					 LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}
			}
			if(!Check.NuNStr(housePv)){
				hr.setHousePv(Integer.parseInt(housePv));
			}else{
				hr.setHousePv(0);
				houseStatisticsMsg = this.tenantHouseServiceImpl.getHouseStatisticsMsgByParam(houseStatisticsMsg);
				if(!Check.NuNObj(houseStatisticsMsg)
						&&!Check.NuNObj(houseStatisticsMsg.getHousePv())){
					hr.setHousePv(houseStatisticsMsg.getHousePv());
				}
				
			
			}
			
		}
		
		//如果房源为分租的话，查询是否有没有房间的房源，添加到房间列表中
		List<HouseRoomVo> emptyRoomHouseList = new ArrayList<>();
		if(rentWay == RentWayEnum.ROOM.getCode()){
			//如果是分租查询第一页，则重新设值查询分租的房源,遍历查询分租带发布房源房间数目是否为0，如果为0，则添加到list最前面
			if(listParamDto.getPage() == 1){
				listParamDto.setLimit(50);
				listParamDto.setHouseStatus(HouseStatusEnum.DFB.getCode());
				listParamDto.setRentWay(RentWayEnum.ROOM.getCode());
				PagingResult<HouseRoomVo> pagingResultF = houseManageServiceImpl.getHousePCList(listParamDto);
				List<HouseRoomVo> houseFList = pagingResultF.getRows();
				if(!Check.NuNCollection(houseFList)){
					for(HouseRoomVo roomVo : houseFList){
						Long roomNum = houseManageServiceImpl.countRoomNumByHouseFid(roomVo.getHouseBaseFid());
						if(roomNum == 0){
							//设置为-1，查询出租率和评价的时候可以不去查该房源
							roomVo.setHouseBookRate(-1.0);
							roomVo.setHouseEvaScore("-1");
							roomVo.setHousePv(0);
							emptyRoomHouseList.add(roomVo);
						}
					}
				}
			}
		}
		//如果不为空，则插入到前面
		if(!Check.NuNCollection(emptyRoomHouseList)){
			houseRoomList.addAll(0, emptyRoomHouseList);
		}
		dto.putValue("total", pagingResult.getTotal());
		dto.putValue("list", houseRoomList);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 获取已上架 整租房源和合租房间集合，并按照最后更新时间排序
	 *
	 * @author jixd
	 * @created 2016年8月2日 下午5:03:00
	 *
	 * @return
	 */
	public String getOnlineHouseRoomList(String param){
		DataTransferObject dto = new DataTransferObject();
		
		try {
			if(Check.NuNStr(param)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			HouseBaseListDto listParamDto = JSONObject.parseObject(param, HouseBaseListDto.class);
			if(Check.NuNStr(listParamDto.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
				return dto.toJsonString();
			}
			
			List<HouseRoomVo> list = new ArrayList<>();
			listParamDto.setLimit(50);
			listParamDto.setPage(1);
			listParamDto.setRentWay(RentWayEnum.HOUSE.getCode());
			listParamDto.setHouseStatus(HouseStatusEnum.SJ.getCode());
			int p =1;
			for(;;){
				listParamDto.setPage(p);
				PagingResult<HouseRoomVo> pagingHouse = houseManageServiceImpl.getHousePCList(listParamDto);
				//获取整租已上架房源集合
				List<HouseRoomVo> houseList = pagingHouse.getRows();
				if(!Check.NuNCollection(houseList)){
					list.addAll(houseList);
				}else{
					break;
				}
				p++;
			}
			
			listParamDto.setRentWay(RentWayEnum.ROOM.getCode());
			//获取已上架房间集合
			p=1;
			for(;;){
				listParamDto.setPage(p);
				PagingResult<HouseRoomVo> pagingRoom = houseManageServiceImpl.getRoomPCList(listParamDto);
				List<HouseRoomVo> roomList = pagingRoom.getRows();
				if(!Check.NuNCollection(roomList)){
					list.addAll(roomList);
				}else{
					break;
				}
				p++;
			}
			//对结果进行排序
			Collections.sort(list, new Comparator<HouseRoomVo>(){
				@Override
				public int compare(HouseRoomVo o1, HouseRoomVo o2) {
					Date lastModifyDate1 = o1.getLastModifyDate();
					Date lastModifyDate2 = o2.getLastModifyDate();
					if(!Check.NuNObj(lastModifyDate1) && !Check.NuNObj(lastModifyDate2)){
						if(lastModifyDate1.before(lastModifyDate2)){
							return -1;
						}else{
							return 1;
						}
					}
					return 0;
				}});
			
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取已上架 整租房源和合租房间集合失败，params={},e={}",param,e);
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HousePCService#getCalendarDateList(java.lang.String)
	 */
	@Override
	public String getCalendarDate(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
            return dto.toJsonString();
		}
		HouseBaseDto houseBaseParam = JSONObject.parseObject(param, HouseBaseDto.class);
		Integer rentWay = houseBaseParam.getRentWay();
		if(Check.NuNObj(rentWay)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_RENTWAY_NULL));
			return dto.toJsonString();
		}
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			if(Check.NuNStr(houseBaseParam.getHouseFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				return dto.toJsonString();
			}
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			if(Check.NuNStr(houseBaseParam.getRoomFid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	            dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_FID_NULL));
	            return dto.toJsonString();
			}
		}
		
		HouseOpLogSpDto logDto = new HouseOpLogSpDto();
		logDto.setHouseFid(houseBaseParam.getHouseFid());
		if(rentWay == RentWayEnum.ROOM.getCode()){
			logDto.setRoomFid(houseBaseParam.getRoomFid());
		}
		logDto.setToStatus(HouseStatusEnum.SJ.getCode());
		logDto.setPage(1);
		logDto.setLimit(1);
		PagingResult<HouseOperateLogEntity> logPageList = houseIssueServiceImpl.findOperateLogList(logDto);
		List<HouseOperateLogEntity> logList = logPageList.getRows();
		Date onLineDate = null;
		if(!Check.NuNCollection(logList) && logList.size() == 1){
			HouseOperateLogEntity houseOperateLogEntity = logList.get(0);
			onLineDate = houseOperateLogEntity.getCreateDate();
		}else{
			onLineDate = new Date();
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			HouseRoomMsgEntity roomMsg = houseManageServiceImpl.getHouseRoomByFid(houseBaseParam.getRoomFid());
			houseBaseParam.setHouseFid(roomMsg.getHouseBaseFid());
		}
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseParam.getHouseFid());
		Date tillDate = houseBaseMsgEntity.getTillDate();
		dto.putValue("startDate", DateUtil.dateFormat(onLineDate));
		dto.putValue("endDate", DateUtil.dateFormat(tillDate));
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HousePCService#getCalendarHouseList(java.lang.String)
	 */
	@Override
	public String getCalendarHouseList(String param) {
		DataTransferObject dto = new DataTransferObject();
		
		try {
			if(Check.NuNStr(param)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			HouseBaseListDto listParamDto = JSONObject.parseObject(param, HouseBaseListDto.class);
			if(Check.NuNStr(listParamDto.getLandlordUid())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_LANDLORDUID_NULL));
				return dto.toJsonString();
			}
			
			List<HouseRoomVo> list = new ArrayList<>();
			listParamDto.setLimit(50);
			listParamDto.setPage(1);
			listParamDto.setRentWay(RentWayEnum.HOUSE.getCode());
			listParamDto.setHouseStatus(HouseStatusEnum.DFB.getCode());
			int p =1;
			for(;;){
				listParamDto.setPage(p);
				PagingResult<HouseRoomVo> pagingHouse = houseManageServiceImpl.getCalendarHousePCList(listParamDto);
				//获取整租已上架房源集合
				List<HouseRoomVo> houseList = pagingHouse.getRows();
				if(!Check.NuNCollection(houseList)){
					list.addAll(houseList);
				}else{
					break;
				}
				p++;
			}
			
			listParamDto.setRentWay(RentWayEnum.ROOM.getCode());
			//获取已上架房间集合
			p=1;
			for(;;){
				listParamDto.setPage(p);
				PagingResult<HouseRoomVo> pagingRoom = houseManageServiceImpl.getCalendarRoomPCList(listParamDto);
				List<HouseRoomVo> roomList = pagingRoom.getRows();
				if(!Check.NuNCollection(roomList)){
					list.addAll(roomList);
				}else{
					break;
				}
				p++;
			}
			//对结果进行排序
			Collections.sort(list, new Comparator<HouseRoomVo>(){
				@Override
				public int compare(HouseRoomVo o1, HouseRoomVo o2) {
					Date lastModifyDate1 = o1.getLastModifyDate();
					Date lastModifyDate2 = o2.getLastModifyDate();
					if(!Check.NuNObj(lastModifyDate1) && !Check.NuNObj(lastModifyDate2)){
						if(lastModifyDate1.before(lastModifyDate2)){
							return -1;
						}else{
							return 1;
						}
					}
					return 0;
				}});
			
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取日历上要展示的房源集合失败，params={},e={}",param,e);
		}
		return dto.toJsonString();
	}
}
