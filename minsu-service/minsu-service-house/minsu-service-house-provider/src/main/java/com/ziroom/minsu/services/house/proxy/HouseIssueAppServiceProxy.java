/**
 * @FileName: HouseIssueAppServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author bushujie
 * @created 2017年6月19日 下午2:45:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseIssueAppService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.issue.dto.*;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.issue.vo.HousePhyExtVo;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.*;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>发布房源代理层（原生app使用）</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component("house.houseIssueAppServiceProxy")
public class HouseIssueAppServiceProxy implements HouseIssueAppService{
	
	@Resource(name="house.houseIssueAppServiceImpl")
	private HouseIssueAppServiceImpl houseIssueAppServiceImpl;
	
	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	
	@Resource(name="house.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;
	
	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Resource(name="house.houseBizServiceImpl")
	private HouseBizServiceImpl houseBizServiceImpl;
	
	/**
	 * 日志工具类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueAppServiceProxy.class);

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueAppService#searchHousePhyAndExt(java.lang.String)
	 */
	@Override
	public String searchHousePhyAndExt(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePhyExtVo housePhyExtVo=houseIssueAppServiceImpl.getHousePhyExtByHouseBaseFid(houseBaseFid);
			Integer bizNum=houseBizServiceImpl.getHouseBizNumByHouseBaseFid(houseBaseFid);
			if(bizNum==0){
				bizNum=houseBizServiceImpl.findToStatusNum(houseBaseFid, HouseStatusEnum.YFB.getCode());
			}
			dto.putValue("housePhyExtVo", housePhyExtVo);
			dto.putValue("bizNum", bizNum);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueAppService#saveHousePhyAndExt(java.lang.String)
	 */
	@Override
	public String saveHousePhyAndExt(String paramJson) {
        LogUtil.info(LOGGER, "参数:saveHousePhyAndExt paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
		try {
			HouseTypeLocationDto houseTypeLocationDto=JsonEntityTransform.json2Object(paramJson, HouseTypeLocationDto.class);

			/**
			 * 调用方法的业务有修改
			 * @author yanb
			 * @created 2017年11月21日 22:05:37
			 */
			HouseBaseVo houseBaseVo=houseIssueAppServiceImpl.saveHousePhyAndExt(houseTypeLocationDto);
			dto.putValue("houseBaseVo", houseBaseVo);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
        LogUtil.info(LOGGER, "结果 saveHousePhyAndExt ={}", dto.toJsonString());
        return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueAppService#saveHouseDesc(java.lang.String)
	 */
	@Override
	public String saveHouseDesc(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseDetailVo vo=JsonEntityTransform.json2Object(paramJson, HouseBaseDetailVo.class);
			houseIssueAppServiceImpl.saveHouseDesc(vo);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueAppService#searchHouseConfAndPrice(java.lang.String)
	 */
	@Override
	public String searchHouseConfAndPrice(String fid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", fid);
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseBaseExtDto houseBaseExtDto = houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(fid);
			dto.putValue("houseBaseExtDto", houseBaseExtDto);
			List<HousePriceWeekConfEntity> weekPriceList = houseManageServiceImpl.findWeekPriceByFid(fid, null);
			dto.putValue("weekPriceList", weekPriceList);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssueAppService#saveHousePrice(java.lang.String)
	 */
	@Override
	public String saveHousePrice(String paramJson) {
		LogUtil.info(LOGGER, "参数:saveHousePrice paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePriceDto housePriceDto=JsonEntityTransform.json2Object(paramJson, HousePriceDto.class);
			houseIssueAppServiceImpl.saveHousePrice(housePriceDto);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	@Override
	public String searchHouseCheckInMsg(String paramJson) {
		LogUtil.info(LOGGER,"searchHouseCheckInMsg 参数={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		HouseBaseParamsDto paramsDto = JsonEntityTransform.json2Object(paramJson, HouseBaseParamsDto.class);
		if (Check.NuNStr(paramsDto.getHouseBaseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(paramsDto.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("出租方式为空");
			return dto.toJsonString();
		}

        Integer minDay = null;
        String checkInTime = null;
		String checkOutTime = null;
		if (paramsDto.getRentWay() == RentWayEnum.HOUSE.getCode() || Check.NuNStr(paramsDto.getRoomFid())){
			HouseBaseExtEntity houseBaseExtEntity = houseIssueServiceImpl.getHouseBaseExtByHouseBaseFid(paramsDto.getHouseBaseFid());
			if (Check.NuNObj(houseBaseExtEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源扩展信息不存在");
				return dto.toJsonString();
			}
			minDay = houseBaseExtEntity.getMinDay();
			checkInTime = houseBaseExtEntity.getCheckInTime();
			checkOutTime = houseBaseExtEntity.getCheckOutTime();
			//共享客厅兼容处理
			Integer isHall=houseIssueServiceImpl.isHall(paramsDto.getHouseBaseFid());
			if(isHall==RoomTypeEnum.HALL_TYPE.getCode()){
				List<HouseRoomMsgEntity> roomList=houseIssueServiceImpl.findRoomListByHouseBaseFid(paramsDto.getHouseBaseFid());
				if(!Check.NuNCollection(roomList)){
					HouseRoomExtEntity houseRoomExtEntity = houseIssueServiceImpl.getRoomExtByRoomFid(roomList.get(0).getFid());
					if (Check.NuNObj(houseRoomExtEntity)){
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("房间扩展信息不存在");
						return dto.toJsonString();
					}
					minDay = houseRoomExtEntity.getMinDay();
					checkInTime = houseRoomExtEntity.getCheckInTime();
					checkOutTime = houseRoomExtEntity.getCheckOutTime();
				}
			}
		}

		if (paramsDto.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNStr(paramsDto.getRoomFid())){
			HouseRoomExtEntity houseRoomExtEntity = houseIssueServiceImpl.getRoomExtByRoomFid(paramsDto.getRoomFid());
			if (Check.NuNObj(houseRoomExtEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间扩展信息不存在");
				return dto.toJsonString();
			}
			minDay = houseRoomExtEntity.getMinDay();
			checkInTime = houseRoomExtEntity.getCheckInTime();
			checkOutTime = houseRoomExtEntity.getCheckOutTime();
		}
        if (Check.NuNStr(checkInTime) || "0".equals(checkInTime)) {
            checkInTime = HouseConstant.DEFAULT_CHECKIN_TIME;
        }
        if (Check.NuNStr(checkOutTime) || "0".equals(checkOutTime)) {
            checkOutTime = HouseConstant.DEFAULT_CHECKOUT_TIME;
        }
        if (Check.NuNObj(minDay)) {
            minDay = HouseConstant.DEFAULT_MIN_DAY;
        }

		dto.putValue("minDay",minDay);
		dto.putValue("checkInTime",checkInTime);
		dto.putValue("checkOutTime",checkOutTime);

		return dto.toJsonString();
	}

	@Override
	public String saveHouseCheckInMsg(String paramJson) {
		LogUtil.info(LOGGER,"saveHouseCheckInMsg 参数={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		ValidateResult<HouseCheckInMsgDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, HouseCheckInMsgDto.class);
		if(!validateResult.isSuccess()){
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseCheckInMsgDto resultObj = validateResult.getResultObj();
		int count = houseIssueAppServiceImpl.saveHouseCheckInMsg(resultObj);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String saveHouseOrRoomPriceForModify(String paramJson) {
		LogUtil.info(LOGGER, "参数:saveHouseOrRoomPriceForModify paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePriceDto housePriceDto=JsonEntityTransform.json2Object(paramJson, HousePriceDto.class);
			houseIssueAppServiceImpl.saveHouseOrRoomPriceForModify(housePriceDto);
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String updateRoomNumAndRoomMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:updateRoomNumAndRoomMsg paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		HouseRoomUpDto houseRoomUpDto = JsonEntityTransform.json2Object(paramJson, HouseRoomUpDto.class);
		if (Check.NuNObj(houseRoomUpDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
        HouseBaseMsgEntity houseBaseMsgEntity = houseIssueServiceImpl.findHouseBaseMsgByFid(houseRoomUpDto.getHouseBaseFid());
        if (Check.NuNObj(houseBaseMsgEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源不存在");
            return dto.toJsonString();
        }
        Integer rentWay = houseBaseMsgEntity.getRentWay();
        if (rentWay == RentWayEnum.HOUSE.getCode()) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源类型错误");
            return dto.toJsonString();
        }
        Integer roomNum = houseBaseMsgEntity.getRoomNum();
        if (roomNum < houseRoomUpDto.getRentRoomNum()) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("可出租房间数不能大于户型房间数");
            return dto.toJsonString();
        }
        List<HouseRoomMsgEntity> houseRoomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseRoomUpDto.getHouseBaseFid());
        if (Check.NuNCollection(houseRoomList)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请添加房间");
            return dto.toJsonString();
        }
        List<String> delRoomFids = houseRoomUpDto.getDelRoomFidList();
        List<HouseRoomMsgEntity> roomList = new ArrayList<>();
        if (!Check.NuNCollection(delRoomFids)) {
            for (HouseRoomMsgEntity houseRoomMsgEntity : houseRoomList) {
                boolean flag = true;
                for (String roomFid : delRoomFids) {
                    if (roomFid.equals(houseRoomMsgEntity.getFid())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    roomList.add(houseRoomMsgEntity);
                }
            }
        } else {
            roomList = houseRoomList;
        }

        for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
            String roomName = houseRoomMsgEntity.getRoomName();
            Integer roomPrice = houseRoomMsgEntity.getRoomPrice();
            Double roomArea = houseRoomMsgEntity.getRoomArea();
            if (Check.NuNStr(roomName) || Check.NuNObj(roomPrice) || Check.NuNObj(roomArea)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("房间信息填写不完整");
                return dto.toJsonString();
            }
        }

        int count = houseIssueAppServiceImpl.updateRoomNumAndRoomMsg(houseRoomUpDto);
        dto.putValue("count",count);
		return dto.toJsonString();
	}


	@Override
    public String saveHouseDescAndBaseInfoEntire(String paramJson) {
        LogUtil.info(LOGGER, "saveHouseDescAndBaseInfoEntire(), 参数:paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
			ValidateResult<HouseDescAndBaseInfoDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseDescAndBaseInfoDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "saveHouseDescAndBaseInfoEntire(),参数校验错误:{}", validateResult.getDto().getMsg());
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(validateResult.getDto().getMsg());
			}
			HouseDescAndBaseInfoDto houseDescAndBaseInfoDto = validateResult.getResultObj();
            houseIssueAppServiceImpl.saveHouseDescAndBaseInfoEntire(houseDescAndBaseInfoDto);
        } catch (Exception e){
            LogUtil.error(LOGGER, "saveHouseDescAndBaseInfoEntire(),error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    @Override
    public String saveHouseDescAndBaseInfoSublet(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseDescAndConfDto houseDescAndConfDto = JsonEntityTransform.json2Object(paramJson, HouseDescAndConfDto.class);
			if (Check.NuNObj(houseDescAndConfDto)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			HouseBaseDetailVo houseBaseDetailVo = houseDescAndConfDto.getHouseBaseDetailVo();
			houseBaseDetailVo.setRoomFid(houseDescAndConfDto.getRoomFid());
			HouseBaseExtDto houseBaseExtDto = houseDescAndConfDto.getHouseBaseExtDto();
			List<HouseConfMsgEntity>  houseConfMsgList = houseDescAndConfDto.getHouseConfMsgList();

			if (Check.NuNObj(houseBaseDetailVo)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				return dto.toJsonString();
			}
			if (Check.NuNObj(houseBaseExtDto)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
				return dto.toJsonString();
			}

			// 校验房源逻辑id不能为空
			String houseFid = houseBaseExtDto.getFid();
			if (Check.NuNObj(houseFid)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
				return dto.toJsonString();
			}
			if(Check.NuNCollection(houseConfMsgList)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}

			houseIssueAppServiceImpl.saveHouseDesc(houseBaseDetailVo);

			houseIssueServiceImpl.updateHouseBaseAndExt(houseBaseExtDto);

			houseIssueServiceImpl.updateHouseConf(houseConfMsgList);

		} catch (Exception e){
			LogUtil.error(LOGGER, "saveHouseDescAndBaseInfoSublet error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
    }
 
    
	 
	/**
	 * 
	 * 保存房间及相关信息
	 * 
	 * 1. 校验需要审核的字段  需要审核的字段提前不入库
	 * 
	 * 2. 相关信息入库
	 * 
	 * (这块代码 有问题   不在一个事务中  TODO)
	 *
	 * @author zl
	 * @created 2017年7月3日 下午5:23:04
	 *
	 * @return
	 */
	@Override
	public String saveAssembleRoomMsg(String paramJson) {
		LogUtil.info(LOGGER, "[saveRoomMsg]paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
        	
        	AssembleRoomMsgDto assembleRoomMsgDto = JsonEntityTransform.json2Object(paramJson, AssembleRoomMsgDto.class);
        	boolean add = false;
			if (Check.NuNStr(assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid())) {
				assembleRoomMsgDto.getRoomMsgVo().setHouseRoomFid(UUIDGenerator.hexUUID());
				add = true;
			}
			
        	troyHouseMgtServiceImpl.updateHouseBaseExtByHouseBaseFid(assembleRoomMsgDto.getHouseBaseExtDto().getHouseBaseExt(),0);
        	
        	if(!Check.NuNObj(assembleRoomMsgDto.getRoomMsgVo().getRoomPrice())){
        		assembleRoomMsgDto.getRoomMsgVo().setRoomPrice(assembleRoomMsgDto.getRoomMsgVo().getRoomPrice().intValue()*100);  //转化为分      	
        	}
        	if(!Check.NuNObj(assembleRoomMsgDto.getRoomMsgVo().getRoomCleaningFees())){
        		assembleRoomMsgDto.getRoomMsgVo().setRoomCleaningFees(assembleRoomMsgDto.getRoomMsgVo().getRoomCleaningFees().intValue()*100);  //转化为分      	
        	}
        	
        	assembleRoomMsgDto.getRoomMsgVo().getBedList().addAll(assembleRoomMsgDto.getAddbedList());
        	assembleRoomMsgDto.getRoomMsgVo().getBedList().addAll(assembleRoomMsgDto.getDelbedList());
        	houseIssueServiceImpl.mergeRoomAndBedList(assembleRoomMsgDto.getRoomMsgVo());
        	
        	//周末价格
			Integer weekendPriceSwitch = assembleRoomMsgDto.getWeekendPriceSwitch();
			List<HousePriceWeekConfEntity> weekendPriceList  = assembleRoomMsgDto.getWeekendPriceList();
			
			if(!Check.NuNCollection(weekendPriceList)){
				Set<Integer> setWeeks = new HashSet<>();
				
				for (HousePriceWeekConfEntity weekConfEntity : weekendPriceList) {
					setWeeks.add(weekConfEntity.getSetWeek());
					weekConfEntity.setPriceVal(weekConfEntity.getPriceVal()*100);
				}
				
				//已经保存的
				Set<Integer> oldSetWeeks = new HashSet<>();
				List<HousePriceWeekConfEntity> oldWeekPriceList = new ArrayList<>();
				if(!add){
					oldWeekPriceList = houseManageServiceImpl.findWeekPriceByFid(assembleRoomMsgDto.getRoomMsgVo().getHouseBaseFid(), assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid());
					if(!Check.NuNCollection(oldWeekPriceList)){
						for (HousePriceWeekConfEntity housePriceWeekConfEntity : oldWeekPriceList) {
							oldSetWeeks.add(housePriceWeekConfEntity.getSetWeek());
						}
					}
				}
				
				if(!Check.NuNCollection(setWeeks) && !Check.NuNCollection(setWeeks) && setWeeks.containsAll(oldSetWeeks) && oldSetWeeks.containsAll(setWeeks)){//直接更新状态
					for (HousePriceWeekConfEntity weekConfEntity : weekendPriceList) {
						for (HousePriceWeekConfEntity housePriceWeekConfEntity : oldWeekPriceList) {
							if(weekConfEntity.getSetWeek()==housePriceWeekConfEntity.getSetWeek()){
								weekConfEntity.setFid(housePriceWeekConfEntity.getFid()); 
							}
						}
						
						weekConfEntity.setIsDel(YesOrNoEnum.NO.getCode());						
						if(weekendPriceSwitch==YesOrNoEnum.YES.getCode()){
							weekConfEntity.setIsValid(YesOrNoEnum.YES.getCode());
						}else{
							weekConfEntity.setIsValid(YesOrNoEnum.NO.getCode());
						}
						
						weekConfEntity.setLastModifyDate(new Date());
					}	
					
					houseManageServiceImpl.updateHousePriceWeekListByFid(weekendPriceList);
					
				}else{ 		
					HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
					weekPriceDto.setCreateUid(weekendPriceList.get(0).getCreateUid());
					weekPriceDto.setHouseBaseFid(assembleRoomMsgDto.getRoomMsgVo().getHouseBaseFid());
					weekPriceDto.setHouseRoomFid(assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid());
					weekPriceDto.setPriceVal(weekendPriceList.get(0).getPriceVal());
					weekPriceDto.setRentWay(RentWayEnum.ROOM.getCode());
					weekPriceDto.setSetWeeks(setWeeks);  
					if(weekendPriceSwitch==YesOrNoEnum.YES.getCode()){
						weekPriceDto.setIsValid(YesOrNoEnum.YES.getCode());
					}else{
						weekPriceDto.setIsValid(YesOrNoEnum.NO.getCode());
					}
					
					houseManageServiceImpl.saveHousePriceWeekConf(weekPriceDto);
				}
				
			}
			
			//长租折扣
			Integer fullDayRateSwitch = assembleRoomMsgDto.getFullDayRateSwitch();
			List<HouseConfMsgEntity> fullDayRateList = assembleRoomMsgDto.getFullDayRateList();
			Map<String, HouseConfMsgEntity> addFullDayRate = new HashMap<>();
			if(!Check.NuNCollection(fullDayRateList)){	
				for (HouseConfMsgEntity houseConfMsgEntity : fullDayRateList) {
					houseConfMsgEntity.setRoomFid(assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid());
					houseConfMsgEntity.setDicVal(String.valueOf(new Double(Double.valueOf(houseConfMsgEntity.getDicVal())*10).intValue()));
					addFullDayRate.put(houseConfMsgEntity.getDicCode(), houseConfMsgEntity);
				} 
			}
			
			Map<String, HouseConfMsgEntity> upFullDayRate = new HashMap<>();
			if(!add){
				HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
				confMsgEntity.setHouseBaseFid(assembleRoomMsgDto.getRoomMsgVo().getHouseBaseFid());
				confMsgEntity.setRoomFid(assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid());
				confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
				List<HouseConfMsgEntity> oldFullDayRateList = houseIssueServiceImpl.findGapAndFlexPrice(confMsgEntity);
				if(!Check.NuNCollection(oldFullDayRateList)){
					for (HouseConfMsgEntity houseConfMsgEntity : oldFullDayRateList) {
						if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum0019.ProductRulesEnum0019001.getValue())){
							upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), houseConfMsgEntity);						
						}else if(houseConfMsgEntity.getDicCode().equals(ProductRulesEnum0019.ProductRulesEnum0019002.getValue())){
							upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), houseConfMsgEntity);
						} 
						
					}
				}
			}			
			if(!Check.NuNMap(upFullDayRate)){
				HouseConfMsgEntity oldSevenEntity = upFullDayRate.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
				if(!Check.NuNObj(oldSevenEntity)){
					HouseConfMsgEntity sevenDiscountRate = addFullDayRate.get(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
					if(!Check.NuNObj(sevenDiscountRate)){
						oldSevenEntity.setDicVal(sevenDiscountRate.getDicVal());
					}else{
						oldSevenEntity.setDicVal(String.valueOf(-1));//兼容以前
					}
					if(fullDayRateSwitch==YesOrNoEnum.YES.getCode()){
						oldSevenEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
					}else{
						oldSevenEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
					}
					
					upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), oldSevenEntity);
					if(addFullDayRate.containsKey(ProductRulesEnum0019.ProductRulesEnum0019001.getValue())){
						addFullDayRate.remove(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
					}
				} 
				
				HouseConfMsgEntity oldThirtyEntity = upFullDayRate.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
				if(!Check.NuNObj(oldThirtyEntity)){
					HouseConfMsgEntity thirtyDiscountRate = addFullDayRate.get(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
					if(!Check.NuNObj(thirtyDiscountRate)){
						oldThirtyEntity.setDicVal(thirtyDiscountRate.getDicVal());
					}else{
						oldThirtyEntity.setDicVal(String.valueOf(-1));//兼容以前
					}
					if(fullDayRateSwitch==YesOrNoEnum.YES.getCode()){
						oldThirtyEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
					}else{
						oldThirtyEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
					}
					upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), oldThirtyEntity);
					if(addFullDayRate.containsKey(ProductRulesEnum0019.ProductRulesEnum0019002.getValue())){
						addFullDayRate.remove(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
					}
				}
				
			}
			
			
			if(!Check.NuNMap(addFullDayRate)){
				houseIssueServiceImpl.saveHouseConfList(new ArrayList<>(addFullDayRate.values()));
			}			
			if(!Check.NuNMap(upFullDayRate)){
				houseIssueServiceImpl.updateHouseConfList(new ArrayList<>(upFullDayRate.values()));
			}
        	 
			dto.putValue("roomFid", assembleRoomMsgDto.getRoomMsgVo().getHouseRoomFid());
			
        } catch (Exception e){
            LogUtil.error(LOGGER, "saveAssembleRoomMsg error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
	}
 
	@Override
	public String saveHouseMsgAndConf(String paramJson) {
		LogUtil.info(LOGGER, "[saveHouseMsgAndConf] paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
        	
        	HouseAndConfDto houseAndConfDto = JsonEntityTransform.json2Object(paramJson, HouseAndConfDto.class);
        	if (Check.NuNObj(houseAndConfDto)) {
        		dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
			}
        	
        	HouseBaseExtDto houseBase = houseAndConfDto.getHouseBaseExtDto();
    		if (Check.NuNObj(houseBase)) {
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL));
    			return dto.toJsonString();
    		}

    		// 校验房源逻辑id不能为空
    		if (Check.NuNObj(houseBase.getFid())) {
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
    			return dto.toJsonString();
    		}
    		if(Check.NuNCollection(houseAndConfDto.getHouseConfMsgList())){
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
    			return dto.toJsonString();
    		}
    		
    		//更新房源基本信息
    		houseIssueServiceImpl.updateHouseBaseAndExt(houseBase);
    		 
			houseIssueServiceImpl.updateHouseConf(houseAndConfDto.getHouseConfMsgList());

			/**
			 *
			 * @author yanb
			 * @created 2017年11月20日 00:35:54
			 * 新增查询是否为共享客厅
			 */
			HouseHallVo houseHallVo= houseIssueServiceImpl.findHall(houseAndConfDto.getHouseBaseExtDto().getHouseBaseExt().getHouseBaseFid());
			if (!Check.NuNObj(houseHallVo)) {
				if (houseHallVo.getRoomType().equals(RoomTypeEnum.HALL_TYPE.getCode())) {
					dto.putValue("houseHallVo",houseHallVo);
				}
			}

        } catch (Exception e){
            LogUtil.error(LOGGER, "saveHouseMsgAndConf error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
	}
}
