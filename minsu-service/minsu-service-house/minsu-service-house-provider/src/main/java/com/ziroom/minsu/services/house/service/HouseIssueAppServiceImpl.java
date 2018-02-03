/**
 * @FileName: HouseIssueAppServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2017年6月19日 下午2:46:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBedMsgDao;
import com.ziroom.minsu.services.house.dao.HouseConfMsgDao;
import com.ziroom.minsu.services.house.dao.HouseDescDao;
import com.ziroom.minsu.services.house.dao.HouseGuardRelDao;
import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;
import com.ziroom.minsu.services.house.dao.HousePicMsgDao;
import com.ziroom.minsu.services.house.dao.HousePriceConfDao;
import com.ziroom.minsu.services.house.dao.HousePriceWeekConfDao;
import com.ziroom.minsu.services.house.dao.HouseRoomExtDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseRoomListVo;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.house.issue.dto.HouseCheckInMsgDto;
import com.ziroom.minsu.services.house.issue.dto.HouseDescAndBaseInfoDto;
import com.ziroom.minsu.services.house.issue.dto.HousePriceDto;
import com.ziroom.minsu.services.house.issue.dto.HouseRoomUpDto;
import com.ziroom.minsu.services.house.issue.dto.HouseTypeLocationDto;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.issue.vo.HousePhyExtVo;
import com.ziroom.minsu.services.house.issue.vo.HouseRoomVo;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.common.WeekEnum;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>发布房源业务实现（app原生使用）</p>
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
@Service("house.houseIssueAppServiceImpl")
public class HouseIssueAppServiceImpl {
	
	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	@Resource(name="house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;
	
	@Resource(name="house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;
	
	@Resource(name="house.houseDescDao")
	private HouseDescDao houseDescDao;
	
	@Resource(name="house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;
	
	@Resource(name="house.housePriceWeekConfDao")
	private HousePriceWeekConfDao housePriceWeekConfDao;
	
	@Resource(name="house.housePriceConfDao")
	private HousePriceConfDao housePriceConfDao;
	
	@Resource(name="house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;
	
	@Resource(name="house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;

	private static Logger logger  = LoggerFactory.getLogger(HouseIssueAppServiceImpl.class);

	@Resource(name="house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;

    @Resource(name="house.housePicMsgDao")
    private HousePicMsgDao housePicMsgDao;
    
	@Resource(name = "house.houseGuardRelDao")
	private HouseGuardRelDao houseGuardRelDao;

	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;
	
	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;
	/**
	 * 
	 * 查询房源基础、物理、扩展信息（发布房源第一步1-1和1-2显示）
	 *
	 * @author bushujie
	 * @created 2017年6月19日 下午2:48:09
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePhyExtVo getHousePhyExtByHouseBaseFid(String houseBaseFid){
		HousePhyExtVo vo=new HousePhyExtVo();
		vo.setHouseBaseMsgEntity(houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid));
		vo.setHousePhyMsgEntity(housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBaseFid));
		vo.setHouseBaseExtEntity(houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseFid));
		return vo;
	}
	
	/**
	 * 
	 * 保存或者更新房源基础、物理、扩展信息（发布房源第一步1-1和1-2保存或更新）
	 *
	 * @author bushujie
	 * @created 2017年6月20日 下午3:29:40
	 *
	 * @param dto
	 * @return
	 */
	public HouseBaseVo saveHousePhyAndExt(HouseTypeLocationDto dto) throws Exception{
		HouseBaseVo houseBaseVo=new HouseBaseVo();
		//详细地址组合
		StringBuilder houseAddr=new StringBuilder();
		String[] regionNames=dto.getRegionName().split(",");
		String[] regionCode = dto.getRegionCode().split(",");
		if(regionCode[0].equals("100000")){
			if(regionNames.length>3){
				houseAddr.append(regionNames[2]).append(regionNames[3]);
			} else {
				houseAddr.append(regionNames[1]).append(regionNames[2]);
			}
		} else {
			houseAddr.append(regionNames[1]);
		}
		houseAddr.append(dto.getHouseStreet());
		if(!Check.NuNStr(dto.getCommunityName())){
			houseAddr.append(dto.getCommunityName());
		}
		if(!Check.NuNStr(dto.getHouseNumber())){
			houseAddr.append(" ").append(dto.getHouseNumber());
		}
		//房源基础信息
	    HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setRentWay(dto.getRentWay());
		houseBaseMsgEntity.setHouseType(dto.getHouseType());
		houseBaseMsgEntity.setHouseAddr(houseAddr.toString());
		//房源物理信息
		HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
		housePhyMsgEntity.setNationCode(dto.getRegionCode().split(",")[0]);
		housePhyMsgEntity.setProvinceCode(dto.getRegionCode().split(",")[1]);
		housePhyMsgEntity.setCityCode(dto.getRegionCode().split(",")[2]);
		housePhyMsgEntity.setAreaCode(dto.getRegionCode().split(",")[3]);
		housePhyMsgEntity.setCommunityName(dto.getCommunityName());
		// 如果是谷歌地图，保存时也修改百度坐标
		if(!Check.NuNObjs(dto.getGoogleLatitude(),dto.getGoogleLongitude()) && Check.NuNObjs(dto.getLongitude(), dto.getLatitude())){
			Gps baiduGps = CoordinateTransforUtils.wgs84_To_bd09(dto.getGoogleLatitude(), dto.getGoogleLongitude());
			housePhyMsgEntity.setGoogleLatitude(dto.getGoogleLatitude());
			housePhyMsgEntity.setGoogleLongitude(dto.getGoogleLongitude());
			housePhyMsgEntity.setLatitude(baiduGps.getWgLat());
			housePhyMsgEntity.setLongitude(baiduGps.getWgLon());
		}
		// 如果是百度地图，保存时也修改谷歌坐标
		if(Check.NuNObjs(dto.getGoogleLatitude(),dto.getGoogleLongitude()) && !Check.NuNObjs(dto.getLongitude(), dto.getLatitude())){
			Gps googleGps = CoordinateTransforUtils.bd09_To_Gps84(dto.getLatitude(), dto.getLongitude());
			housePhyMsgEntity.setLatitude(dto.getLatitude());
			housePhyMsgEntity.setLongitude(dto.getLongitude());
			housePhyMsgEntity.setGoogleLatitude(googleGps.getWgLat());
			housePhyMsgEntity.setGoogleLongitude(googleGps.getWgLon());
		}
		//房源扩展信息
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseStreet(dto.getHouseStreet());
		houseBaseExtEntity.setDetailAddress(dto.getHouseNumber());
		
		//判断是保存
	    if(Check.NuNStr(dto.getHouseBaseFid())){
	    	//保存房源物理信息
	    	housePhyMsgEntity.setFid(UUIDGenerator.hexUUID());
			housePhyMsgEntity.setBuildingCode(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS"));
			housePhyMsgEntity.setCreateUid(dto.getLandlordUid());
			housePhyMsgDao.insertHousePhyMsg(housePhyMsgEntity);
			//保存房源基础信息
	    	houseBaseMsgEntity.setFid(UUIDGenerator.hexUUID());
	    	houseBaseMsgEntity.setLandlordUid(dto.getLandlordUid());
	    	houseBaseMsgEntity.setPhyFid(housePhyMsgEntity.getFid());
	    	houseBaseMsgEntity.setHouseSource(dto.getHouseSource());
	    	houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.DFB.getCode());
	    	houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.ONE.getCode());
	    	houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.ONE.getValue());
	    	try {
				houseBaseMsgEntity.setTillDate(DateUtil.parseDate(SysConst.House.TILL_DATE, "yyyy-MM-dd"));
			} catch (ParseException e) {
				LogUtil.error(logger, "【发布房源第一步1-1和1-2保存或更新】 截止日期转化异常e={},不影响正常业务", e);
			}
			//房源编号第一次生成之后，不在做修改
			String houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
			if(!Check.NuNStr(houseSn)){
				int i = 0;
				while (i<3) {
					Long count = 	this.houseBaseMsgDao.countByHouseSn(houseSn);
					if(count>0){
						i++;
						houseSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.HOUSE.getCode(), null);
						continue;
					}
					break;
				}
			}
			houseBaseMsgEntity.setHouseSn(houseSn);
			houseBaseMsgEntity.setHouseChannel(HouseChannelEnum.CH_FANGDONG.getCode());
			houseBaseMsgDao.insertHouseBaseMsg(houseBaseMsgEntity); 
			//保存房源扩展信息
			houseBaseExtEntity.setFid(UUIDGenerator.hexUUID());
			houseBaseExtEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
			//如果是共享客厅，是否与房东同住默认是
			if (dto.getRentWay() == RentWayEnum.ROOM.getCode() && dto.getRoomType() != null&&dto.getRoomType() == RoomTypeEnum.HALL_TYPE.getCode()) {
				houseBaseExtEntity.setIsTogetherLandlord(YesOrNoEnum.YES.getCode());
			}
			houseBaseExtDao.insertHouseBaseExt(houseBaseExtEntity);
			
			//如果是共享客厅roomType=1,先保存一条Room信息
			/**
			 * @author yanb
			 * @created 2017年11月16日 22:48:56
			 */
			if (dto.getRentWay() == RentWayEnum.ROOM.getCode() && dto.getRoomType() != null) {
				if (dto.getRoomType() == RoomTypeEnum.HALL_TYPE.getCode()) {
					String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
					HouseRoomMsgEntity hall = new HouseRoomMsgEntity();
					hall.setFid(UUIDGenerator.hexUUID());
					hall.setHouseBaseFid(houseBaseMsgEntity.getFid());
					hall.setRoomPrice(0);
					hall.setRoomType(dto.getRoomType());
					hall.setRoomSn(roomSn);
					hall.setRoomStatus(HouseStatusEnum.DFB.getCode());
					hall.setIsToilet(YesOrNoEnum.NO.getCode());
					hall.setCreateUid(dto.getLandlordUid());
					houseRoomMsgDao.insertHouseRoomMsg(hall);
					houseBaseVo.setRoomType(hall.getRoomType());
					//houseBaseVo.setRoomFid(hall.getFid());
				}
			}
			//保存运营专员管家信息
			if(!Check.NuNObj(dto.getHouseGuardRel())){
				dto.getHouseGuardRel().setHouseFid(houseBaseMsgEntity.getFid());
				houseGuardRelDao.insertHouseGuardRel(dto.getHouseGuardRel());
			}
	    } else {
	    	int isHall = houseIssueServiceImpl.isHall(dto.getHouseBaseFid());
			Integer dtoRoomType = dto.getRoomType();
			//null和0都是非共享客厅,所以把null重新赋值为0,方便做判断时理清逻辑
			if (Check.NuNObj(dtoRoomType)) {
				dtoRoomType = RoomTypeEnum.ROOM_TYPE.getCode();
			}
	    	//判断是否修改过出租方式
	    	HouseBaseMsgEntity oldBase=houseBaseMsgDao.getHouseBaseMsgEntityByFid(dto.getHouseBaseFid());
	    	if(dto.getRentWay()!=oldBase.getRentWay()){
	    		houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.ONE.getCode());
	    		houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.ONE.getValue());
	    		//判断是分组转整租
	    		if(dto.getRentWay()==RentWayEnum.HOUSE.getCode()){
	    			//删除分组conf
	    			houseConfMsgDao.delRoomConfByHouseBaseFid(dto.getHouseBaseFid());
	    			//删除分组周末价格
	    			housePriceWeekConfDao.delRoomPriceWeekByHouseBaseFid(dto.getHouseBaseFid());
	    		}
	    	} else {
				if(dtoRoomType!=isHall){
					houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.ONE.getCode());
		    		houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.ONE.getValue());
	    			//删除分组conf
	    			houseConfMsgDao.delRoomConfByHouseBaseFid(dto.getHouseBaseFid());
	    			//删除分组周末价格
	    			housePriceWeekConfDao.delRoomPriceWeekByHouseBaseFid(dto.getHouseBaseFid());
				}
			}
			/****审核未通过的房源修改部分审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
			if(oldBase.getRentWay() == RentWayEnum.HOUSE.getCode()){
				if(oldBase.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
					houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
				}
			}else{
				List<HouseRoomListVo> roomListVos = houseRoomMsgDao.getRoomListByHouseFid(oldBase.getFid());
				if(Check.NuNCollection(roomListVos)){
					for(HouseRoomListVo houseRoomListVo:roomListVos){
						if(Check.NuNObj(houseRoomListVo)){
							if(houseRoomListVo.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
								houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
								break;
							}
						}
					}
				}
			}
			/****审核未通过的房源修改部分审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/

			//更新房源基础信息
	    	houseBaseMsgEntity.setFid(dto.getHouseBaseFid());
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
			//更新房源扩展信息
			houseBaseExtEntity.setHouseBaseFid(dto.getHouseBaseFid());
			houseBaseExtDao.specialUpdateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
			//更新房源物理信息
			housePhyMsgEntity.setFid(houseBaseMsgDao.getHouseBaseMsgEntityByFid(dto.getHouseBaseFid()).getPhyFid());
			housePhyMsgDao.specialUpdateHousePhyMsgByFid(housePhyMsgEntity);
			//更新房源管家运营专员信息
			HouseGuardRelEntity houseGuardRelEntity=houseGuardRelDao.findHouseGuardRelByHouseBaseFid(dto.getHouseBaseFid());
			if(!Check.NuNObj(dto.getHouseGuardRel())&&!Check.NuNObj(houseGuardRelEntity)){
				houseGuardRelEntity.setEmpGuardCode(dto.getHouseGuardRel().getEmpGuardCode());
				houseGuardRelEntity.setEmpGuardName(dto.getHouseGuardRel().getEmpGuardName());
				houseGuardRelDao.updateHouseGuardRelByFid(houseGuardRelEntity);
			}else if(!Check.NuNObj(dto.getHouseGuardRel())&&Check.NuNObj(houseGuardRelEntity)){
				dto.getHouseGuardRel().setHouseFid(dto.getHouseBaseFid());
				houseGuardRelDao.insertHouseGuardRel(dto.getHouseGuardRel());
			}

			/**
			 * yanb
			 * 做逻辑判断，根据房间类型roomType(其中ROOM_TYPE指的是非共享客厅，HALL_TYPE是共享客厅)
			 * isHall为判断是否存在room记录为共享客厅(以前是不是共享客厅)
			 * dtoRoomType为传入的roomType (是否要修改为共享客厅)
			 * 三种情况的处理方式:
			 * 		1.以前是共享客厅,修改后依旧是共享客厅,不做对room记录变更
			 * 		2.以前不是共享客厅,修改后变成共享客厅,做提前保存一个客厅记录的操作
			 * 		3.修改为非共享客厅,做删除客厅类型的room记录的操作
			 */
			if (dtoRoomType.equals(RoomTypeEnum.ROOM_TYPE.getCode()) ) {
				//第3种情况，如果要转换成非共享客厅
				//直接执行一遍删除共享客厅的操作(不论之前是否是共享客厅)
				houseRoomMsgDao.deleteHallMsgByhouseBaseFid(dto.getHouseBaseFid());
				if (isHall == RoomTypeEnum.HALL_TYPE.getCode()) {
					//如果是从共享客厅切换过来,清空户型
					HouseBaseMsgEntity clearStructure = new HouseBaseMsgEntity();
					clearStructure.setFid(dto.getHouseBaseFid());
					clearStructure.setRoomNum(0);
					clearStructure.setToiletNum(0);
					clearStructure.setHallNum(0);
					clearStructure.setBalconyNum(0);
					clearStructure.setKitchenNum(0);
					houseBaseMsgDao.updateHouseBaseMsg(clearStructure);
					//清空出租数量
					HouseBaseExtEntity clearRoomNumEntity = new HouseBaseExtEntity();
					clearRoomNumEntity.setHouseBaseFid(dto.getHouseBaseFid());
					clearRoomNumEntity.setRentRoomNum(0);
					houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(clearRoomNumEntity);
				}
			} else if (isHall == RoomTypeEnum.ROOM_TYPE.getCode()) {
				//第2种情况，以前不是共享客厅,修改后变成共享客厅
				//先清空户型
				HouseBaseMsgEntity clearStructure = new HouseBaseMsgEntity();
				clearStructure.setFid(dto.getHouseBaseFid());
				clearStructure.setRoomNum(0);
				clearStructure.setToiletNum(0);
				clearStructure.setHallNum(0);
				clearStructure.setBalconyNum(0);
				clearStructure.setKitchenNum(0);
				houseBaseMsgDao.updateHouseBaseMsg(clearStructure);
				//先根据houseBaseFid把所有的房间删除
				houseRoomMsgDao.deleteHouseRoomMsgByHouseFid(dto.getHouseBaseFid());
				//清空出租数量
				HouseBaseExtEntity clearRoomNumEntity = new HouseBaseExtEntity();
				clearRoomNumEntity.setHouseBaseFid(dto.getHouseBaseFid());
				clearRoomNumEntity.setRentRoomNum(0);
				houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(clearRoomNumEntity);
				//再进行提前保存一个客厅类型的房间的操作
				String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
				HouseRoomMsgEntity hall = new HouseRoomMsgEntity();
				hall.setFid(UUIDGenerator.hexUUID());
				hall.setHouseBaseFid(dto.getHouseBaseFid());
				hall.setRoomPrice(0);
				hall.setRoomType(dto.getRoomType());
				hall.setRoomSn(roomSn);
				hall.setRoomStatus(HouseStatusEnum.DFB.getCode());
				hall.setIsToilet(YesOrNoEnum.NO.getCode());
				hall.setCreateUid(dto.getLandlordUid());
				houseRoomMsgDao.insertHouseRoomMsg(hall);
				houseBaseVo.setRoomType(hall.getRoomType());
			}


		}
		houseBaseVo.setHouseBaseFid(houseBaseMsgEntity.getFid());
		houseBaseVo.setRentWay(houseBaseMsgEntity.getRentWay());
		return houseBaseVo;
	}
	
	/**
	 * 
	 * 房源描述保存（发布房源第二步2-1）
	 *
	 * @author bushujie
	 * @created 2017年6月21日 下午3:14:49
	 *
	 * @param houseBaseDetailVo
	 * @return
	 */
	public void saveHouseDesc(HouseBaseDetailVo houseBaseDetailVo)throws Exception{
		HouseDescEntity desc=houseDescDao.findHouseDescByHouseBaseFid(houseBaseDetailVo.getFid());
		HouseBaseMsgEntity baseMsgEntity=new HouseBaseMsgEntity();
		baseMsgEntity.setFid(houseBaseDetailVo.getFid());
		if(RentWayEnum.HOUSE.getCode()==houseBaseDetailVo.getRentWay()){
			baseMsgEntity.setHouseName(houseBaseDetailVo.getHouseName());
		}
		if(Check.NuNObj(desc)){
			desc=new HouseDescEntity();
			desc.setFid(UUIDGenerator.hexUUID());
			desc.setHouseBaseFid(houseBaseDetailVo.getFid());
			desc.setHouseDesc(houseBaseDetailVo.getHouseDesc());
			desc.setHouseAroundDesc(houseBaseDetailVo.getHouseAroundDesc());
			houseDescDao.insertHouseDesc(desc);
			baseMsgEntity.setOperateSeq(HouseIssueStepEnum.TWO.getCode());
			baseMsgEntity.setIntactRate(HouseIssueStepEnum.TWO.getValue());
		} else {
			desc.setHouseDesc(houseBaseDetailVo.getHouseDesc());
			desc.setHouseAroundDesc(houseBaseDetailVo.getHouseAroundDesc());
			//busj  整租和分租状态要区分
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			if(Check.NuNStr(houseBaseDetailVo.getRoomFid())){
				HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseDetailVo.getFid());
				if(!Check.NuNObj(houseBaseMsgEntity)){
					houseStatus=houseBaseMsgEntity.getHouseStatus();
				}
			} else {
				HouseRoomMsgEntity houseRoomMsgEntity = houseRoomMsgDao.getHouseRoomByFid(houseBaseDetailVo.getRoomFid());
				if(!Check.NuNObj(houseRoomMsgEntity)){
					houseStatus=houseRoomMsgEntity.getRoomStatus();
				}
			}

			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
			if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
				desc = HouseUtils.FilterAuditField(desc,houseUpdateFieldAuditManagerEntities);
			}
			if(houseStatus==HouseStatusEnum.SJ.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
				desc = HouseUtils.FilterAuditField(desc,houseUpdateFieldAuditManagerEntities);
			}
			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

			houseDescDao.updateHouseDesc(desc);
		}
		houseBaseMsgDao.updateHouseBaseMsg(baseMsgEntity);
	}
	
	/**
	 * 
	 * 价格、配置信息保存
	 *
	 * @author bushujie
	 * @created 2017年6月27日 上午11:34:17
	 *
	 * @param housePriceDto
	 */
	public void saveHousePrice(HousePriceDto housePriceDto){
		//查询房间基础信息
		HouseBaseMsgEntity baseMsg=houseBaseMsgDao.getHouseBaseMsgEntityByFid(housePriceDto.getHouseBaseFid());
		//修改房间基础信息
		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(housePriceDto.getHouseBaseFid());
		houseBaseMsgEntity.setHouseArea(housePriceDto.getHouseArea());
		houseBaseMsgEntity.setLeasePrice(DecimalCalculate.mul(housePriceDto.getLeasePrice().toString(), "100").intValue());
		houseBaseMsgEntity.setHouseCleaningFees(DecimalCalculate.mul(housePriceDto.getCleaningFees().toString(), "100").intValue());
		if(!Check.NuNObj(housePriceDto.getStep()) && housePriceDto.getStep() >baseMsg.getOperateSeq()){
			houseBaseMsgEntity.setOperateSeq(housePriceDto.getStep());
		}
		//处理户型 卧室,客厅,卫生间,厨房,阳台
		if(!Check.NuNStr(housePriceDto.getHouseModel())){
			List<String> modelList=Arrays.asList(housePriceDto.getHouseModel().split(","));
			houseBaseMsgEntity.setRoomNum(Integer.valueOf(modelList.get(0)));
			houseBaseMsgEntity.setHallNum(Integer.valueOf(modelList.get(1)));
			houseBaseMsgEntity.setToiletNum(Integer.valueOf(modelList.get(2)));
			houseBaseMsgEntity.setKitchenNum(Integer.valueOf(modelList.get(3)));
			houseBaseMsgEntity.setBalconyNum(Integer.valueOf(modelList.get(4)));
		}
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
		//更新房源扩展信息 ext 入住人数限制
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
		houseBaseExtEntity.setCheckInLimit(housePriceDto.getCheckInLimit());
		//添加一个出租房间数
		if(!Check.NuNCollection(housePriceDto.getHouseRoomList())){
			houseBaseExtEntity.setRentRoomNum(housePriceDto.getHouseRoomList().size());
		} else {
			houseBaseExtEntity.setRentRoomNum(0);
		}
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
		//便利设施保存
		houseFacilityHandle(housePriceDto);
		//周末价格处理
		weekendPriceHandle(housePriceDto, null);
		//长租折扣处理
		longLeaseDiscountHandle(housePriceDto);
		//删除房间或者更新房间 或者删除房间数据
		roomMsgHandle(housePriceDto, baseMsg);

	}

	/**
	 * 房源便利设施保存
	 * @author jixd
	 * @created 2017年07月03日 16:26:47
	 * @param
	 * @return
	 */
	private void houseFacilityHandle(HousePriceDto housePriceDto) {
		if(!Check.NuNStr(housePriceDto.getHouseFacility())){
			List<String> facilityList= Arrays.asList(housePriceDto.getHouseFacility().split(","));
			//查询配套设施
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("houseBaseFid", housePriceDto.getHouseBaseFid());
			paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum002.getValue()+"0");
			List<HouseConfVo> faConfList=houseConfMsgDao.findHouseConfVoList(paramMap);
			paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum0015.getValue());
			List<HouseConfVo> seConfList=houseConfMsgDao.findHouseConfVoList(paramMap);
			faConfList.addAll(seConfList);
			//查询要删除的
			for( HouseConfVo vo:faConfList){
				boolean isDel=true;
				for(String facility:facilityList){
					if(vo.getDicCode().equals(facility.split("_")[0])&&vo.getDicValue().equals(facility.split("_")[1])){
						isDel=false;
						break;
					}
				}
				if(isDel){
					houseConfMsgDao.delHouseConfByFid(vo.getFid());
				}
			}
			//查询需要插入的
			for(String facility:facilityList ){
				boolean isInsert=true;
				for( HouseConfVo vo:faConfList){
					if(vo.getDicCode().equals(facility.split("_")[0])&&vo.getDicValue().equals(facility.split("_")[1])){
						isInsert=false;
						break;
					}
				}
				if(isInsert){
					HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
					houseConfMsgEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
					houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
					houseConfMsgEntity.setDicCode(facility.split("_")[0]);
					houseConfMsgEntity.setDicVal(facility.split("_")[1]);
					houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
				}
			}
		}
	}

	/**
	 * 整租保存房间数据
	 * @author jixd
	 * @created 2017年07月03日 16:27:02
	 * @param
	 * @return
	 */
	private void roomMsgHandle(HousePriceDto housePriceDto, HouseBaseMsgEntity baseMsg) {
		if(!Check.NuNCollection(housePriceDto.getHouseRoomList())){
			for(HouseRoomVo vo:housePriceDto.getHouseRoomList()){
				//如果是新建房间
				if(Check.NuNStr(vo.getFid())){
					HouseRoomMsgEntity room=new HouseRoomMsgEntity();
					room.setFid(UUIDGenerator.hexUUID());
					room.setHouseBaseFid(housePriceDto.getHouseBaseFid());
					//生成roomSn
					HousePhyMsgEntity housePhyMsgEntity=housePhyMsgDao.findHousePhyMsgByHouseBaseFid(housePriceDto.getHouseBaseFid());
					String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
					if(!Check.NuNStr(roomSn)){
						int i = 0;
						while (i<3) {
							Long count = houseRoomMsgDao.countByRoomSn(roomSn);
							if(count>0){
								i++;
								roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
								continue;
							}
							break;
						}
					}
					room.setRoomSn(roomSn);
					room.setRoomPrice(0);
					room.setRoomStatus(baseMsg.getHouseStatus());
					houseRoomMsgDao.insertHouseRoomMsg(room);
					//插入床信息
					if(!Check.NuNStr(vo.getBedMsg())){
						Integer bedSnInteger=100;
						for(String bed:vo.getBedMsg().split(",")){
							Integer bedNum=Integer.valueOf(bed.split("_")[1]);
							for(int i=0;i<bedNum;i++){
								HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
								bedMsgEntity.setFid(UUIDGenerator.hexUUID());
								bedMsgEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
								bedMsgEntity.setBedSn(bedSnInteger++);
								bedMsgEntity.setRoomFid(room.getFid());
								bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
								bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
								houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
							}
						}
					}
				} else {
					//要保存的传类型列表
					List<Integer> bedTypeList=new ArrayList<>();
					if(!Check.NuNStr(vo.getBedMsg())){
						Integer bedSnInteger=100;
						for(String bed:vo.getBedMsg().split(",")){
							Integer bedNum=Integer.valueOf(bed.split("_")[1]);
							Integer bedType=Integer.valueOf(bed.split("_")[0]);
							bedTypeList.add(bedType);
							Map<String,Object> paramMap=new HashMap<String,Object>();
							paramMap.put("houseBaseFid", housePriceDto.getHouseBaseFid());
							paramMap.put("roomFid", vo.getFid());
							paramMap.put("bedType", bedType);
							List<HouseBedMsgEntity> bedList=houseBedMsgDao.getBedNumByType(paramMap);
							//判断下数量是增是减
							if(Check.NuNCollection(bedList)){
								for(int i=0;i<bedNum;i++){
									HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
									bedMsgEntity.setFid(UUIDGenerator.hexUUID());
									bedMsgEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
									bedMsgEntity.setBedSn(bedSnInteger++);
									bedMsgEntity.setRoomFid(vo.getFid());
									bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
									bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
									houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
								}
							} else if(bedNum>bedList.size()){
								for(int i=0;i<bedNum-bedList.size();i++){
									HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
									bedMsgEntity.setFid(UUIDGenerator.hexUUID());
									bedMsgEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
									bedMsgEntity.setBedSn(bedSnInteger++);
									bedMsgEntity.setRoomFid(vo.getFid());
									bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
									bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
									houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
								}
							} else if(bedNum<bedList.size()) {
								for(int i=0;i<bedList.size()-bedNum;i++){
									houseBedMsgDao.deleteHouseBedMsgByFid(bedList.get(i).getFid());
								}
							}
						}
					}
					//查询房间下床列表
					List<HouseBedMsgEntity>bedMsgList=houseBedMsgDao.findBedListByRoomFid(vo.getFid());
					if(!Check.NuNCollection(bedMsgList)){
						for(HouseBedMsgEntity bed:bedMsgList){
							if(!bedTypeList.contains(bed.getBedType())){
								houseBedMsgDao.deleteHouseBedMsgByFid(bed.getFid());
							}
						}
					}
				}
			}
		}

		//删除房间数据
		List<String> delRoomFidList = housePriceDto.getDelRoomFidList();
		deleteRoomMsg(delRoomFidList);

	}

	/**
	 * 保存更新入住信息 发布房源第四步
	 * @author jixd
	 * @created 2017年06月27日 21:41:01
	 * @param
	 * @return
	 */
	public int saveHouseCheckInMsg(HouseCheckInMsgDto houseCheckInMsgDto){
		int count = 0;
		if (houseCheckInMsgDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
			HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
			houseBaseExtEntity.setHouseBaseFid(houseCheckInMsgDto.getHouseBaseFid());
			houseBaseExtEntity.setMinDay(houseCheckInMsgDto.getMinDay());
			houseBaseExtEntity.setCheckInTime(houseCheckInMsgDto.getCheckInTime());
			houseBaseExtEntity.setCheckOutTime(houseCheckInMsgDto.getCheckOutTime());
			count += houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);

		}
		if (houseCheckInMsgDto.getRentWay() == RentWayEnum.ROOM.getCode()){
			if (Check.NuNStr(houseCheckInMsgDto.getRoomFid())){
				//更新所有对应房间信息
				HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseBaseFid(houseCheckInMsgDto.getHouseBaseFid());
				houseBaseExtEntity.setMinDay(houseCheckInMsgDto.getMinDay());
				houseBaseExtEntity.setCheckInTime(houseCheckInMsgDto.getCheckInTime());
				houseBaseExtEntity.setCheckOutTime(houseCheckInMsgDto.getCheckOutTime());
				count += houseRoomExtDao.updateCheckInMsgByHouseFid(houseBaseExtEntity);
                count += houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);

			}else{
				HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();
				houseRoomExtEntity.setRoomFid(houseCheckInMsgDto.getRoomFid());
				houseRoomExtEntity.setMinDay(houseCheckInMsgDto.getMinDay());
				houseRoomExtEntity.setCheckInTime(houseCheckInMsgDto.getCheckInTime());
				houseRoomExtEntity.setCheckOutTime(houseCheckInMsgDto.getCheckOutTime());
				count += houseRoomExtDao.updateByRoomfid(houseRoomExtEntity);
			}
		}
		
		if (!Check.NuNObj(houseCheckInMsgDto.getStep()) && !Check.NuNObj(HouseIssueStepEnum.getValueByCode(houseCheckInMsgDto.getStep()))) {
			HouseBaseMsgEntity houseBaseMsg =houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseCheckInMsgDto.getHouseBaseFid());
			if(houseBaseMsg.getOperateSeq()<houseCheckInMsgDto.getStep()){
				houseBaseMsg = new HouseBaseMsgEntity();
				houseBaseMsg.setFid(houseCheckInMsgDto.getHouseBaseFid());
				houseBaseMsg.setOperateSeq(houseCheckInMsgDto.getStep());
				houseBaseMsg.setIntactRate(HouseIssueStepEnum.getValueByCode(houseCheckInMsgDto.getStep()));
				houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
			}
			
		}
		
		return count;
	}

	/**
	 * 周末价格处理 （整租/分租）
	 * @author wangwt
	 * @created 2017年06月29日 15:46:31
	 * @param HousePriceDto housePriceDto, String roomFid, Integer isValid
	 * @return
	 */
	private void weekendPriceHandle(HousePriceDto housePriceDto, Integer isValid) {
		List<Integer> weekList=new ArrayList<Integer>();
		if(!Check.NuNStr(housePriceDto.getWeekendPriceType())){
			for(String week:housePriceDto.getWeekendPriceType().split(",")){
				weekList.add(Integer.valueOf(week));
			}
		}
		//查询已有的周末价格设置列表
		List<HousePriceWeekConfEntity> weekendPriceList=housePriceWeekConfDao.findSpecialPriceList(
				housePriceDto.getHouseBaseFid(), housePriceDto.getRoomFid(), isValid);
		Integer weekendPriceSwitch = YesOrNoEnum.NO.getCode();
		Set<Integer> weekdays =new HashSet<>();
		if (!Check.NuNCollection(weekendPriceList)) {
			for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
				if (housePriceWeekConf.getIsDel().intValue() == YesOrNoEnum.NO.getCode()) {
					weekdays.add(housePriceWeekConf.getSetWeek());
					if (housePriceWeekConf.getIsValid().intValue() == YesOrNoEnum.YES.getCode()) {
						weekendPriceSwitch = YesOrNoEnum.YES.getCode();
					}

				}
			}
		}
		WeekendPriceEnum weekEnum = WeekendPriceEnum.getEnumByColl(weekdays);
		if(housePriceDto.getWeekendPriceSwitch()==YesOrNoEnum.YES.getCode()){
			//参数里的开关是打开状态
			LeaseCalendarDto leaseCalendarDto = new LeaseCalendarDto();
			leaseCalendarDto.setHouseBaseFid(housePriceDto.getHouseBaseFid());
			leaseCalendarDto.setHouseRoomFid(housePriceDto.getRoomFid());
			leaseCalendarDto.setRentWay(housePriceDto.getRentWay());
			//1.如果房源或房间设置有特殊价格， 删除与周末价格有冲突的特殊价格
			List<SpecialPriceVo> specialPriceList = housePriceConfDao.findSpecialPriceList(leaseCalendarDto);
			if (!Check.NuNCollection(specialPriceList)&&!Check.NuNCollection(weekList)) {// 按照周末规则更新特殊价格
				for (SpecialPriceVo specialPrice : specialPriceList) {
					if (weekList.contains(WeekEnum.getWeek(specialPrice.getSetDate()).getNumber())) {
						housePriceConfDao.deleteHousePriceConfByFid(specialPrice.getFid());// 删除设置星期的特殊价格
					}
				}
			}

			//2.设置周末价格
			if(Check.NuNCollection(weekendPriceList)){
				//2.1. 如果房源或房间之前没有设置周末价格， 直接保存
				housePriceWeekConfDao.saveHousePriceWeekConf(housePriceDto.getCreateFid(), housePriceDto.getHouseBaseFid(),
						housePriceDto.getRoomFid(), weekList,DecimalCalculate.mul(housePriceDto.getWeekendPriceVal()+"", "100").intValue(),null);
			} else {
				if(!weekEnum.getValue().equals(housePriceDto.getWeekendPriceType())){
					//2.2 周末类型不一样， 直接保存
					housePriceWeekConfDao.saveHousePriceWeekConf(housePriceDto.getCreateFid(), housePriceDto.getHouseBaseFid(),
							housePriceDto.getRoomFid(), weekList,DecimalCalculate.mul(housePriceDto.getWeekendPriceVal()+"", "100").intValue(),null);
				} else {
					for(HousePriceWeekConfEntity weekConf:weekendPriceList){
						weekConf.setIsValid(YesOrNoEnum.YES.getCode());
						weekConf.setPriceVal(DecimalCalculate.mul(housePriceDto.getWeekendPriceVal()+"", "100").intValue());
						housePriceWeekConfDao.updateHousePriceWeekConfByFid(weekConf);
					}
				}
			}
		} else {
			//参数里的开关是关闭状态， 关闭之前设置的周末价格开关
			if(!Check.NuNCollection(weekendPriceList)&&weekendPriceSwitch==YesOrNoEnum.YES.getCode()){
				for(HousePriceWeekConfEntity weekConf:weekendPriceList){
					weekConf.setIsValid(YesOrNoEnum.NO.getCode());
					housePriceWeekConfDao.updateHousePriceWeekConfByFid(weekConf);
				}
			}
		}
	}

	/**
	 * 长租折扣处理
	 * @author wangwt
	 * @created 2017年06月29日 15:47:44
	 * @param
	 * @return
	 */
	private void longLeaseDiscountHandle(HousePriceDto housePriceDto){
		HouseConfMsgEntity param=new HouseConfMsgEntity();
		param.setHouseBaseFid(housePriceDto.getHouseBaseFid());
		param.setRoomFid(housePriceDto.getRoomFid());
		param.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());

		//满七天的
		List<HouseConfMsgEntity> sevenList=houseConfMsgDao.findGapFlexPriceList(param);

		param.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		//满30天的
		List<HouseConfMsgEntity> thirtyList=houseConfMsgDao.findGapFlexPriceList(param);

		//长租折扣开关打开
		if(housePriceDto.getFullDayRateSwitch()==YesOrNoEnum.YES.getCode()){

			if(Check.NuNCollection(sevenList)){//没有设置满7天折扣
				HouseConfMsgEntity sevenEntity=new HouseConfMsgEntity();
				sevenEntity.setFid(UUIDGenerator.hexUUID());
				sevenEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
				sevenEntity.setRoomFid(housePriceDto.getRoomFid());
				sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
				if(!Check.NuNObj(housePriceDto.getSevenDiscountRate())&&housePriceDto.getSevenDiscountRate().intValue()!=0){
					sevenEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getSevenDiscountRate().toString(), "10").intValue()+"");
				}else {
					sevenEntity.setDicVal("-1");
				}
				sevenEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
				houseConfMsgDao.insertHouseConfMsg(sevenEntity);
			} else {//设置满7天折扣的
				HouseConfMsgEntity sevenEntity=sevenList.get(0);
				if(!Check.NuNObj(housePriceDto.getSevenDiscountRate())&&housePriceDto.getSevenDiscountRate().intValue()!=0){
					sevenEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getSevenDiscountRate().toString(), "10").intValue()+"");
				}else {
					sevenEntity.setDicVal("-1");
				}
				sevenEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
				houseConfMsgDao.updateHouseConfMsg(sevenEntity);
			}
			if(Check.NuNCollection(thirtyList)){//没有设置满30天折扣
				HouseConfMsgEntity thirtyEntity=new HouseConfMsgEntity();
				thirtyEntity.setFid(UUIDGenerator.hexUUID());
				thirtyEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
				thirtyEntity.setRoomFid(housePriceDto.getRoomFid());
				thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
				if(!Check.NuNObj(housePriceDto.getThirtyDiscountRate())&&housePriceDto.getThirtyDiscountRate().intValue()!=0){
					thirtyEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getThirtyDiscountRate().toString(), "10").intValue()+"");
				}else {
					thirtyEntity.setDicVal("-1");
				}
				thirtyEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
				houseConfMsgDao.insertHouseConfMsg(thirtyEntity);
			} else {//设置满30天折扣
				HouseConfMsgEntity thirtyEntity=thirtyList.get(0);
				if(!Check.NuNObj(housePriceDto.getThirtyDiscountRate())&&housePriceDto.getThirtyDiscountRate().intValue()!=0){
					thirtyEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getThirtyDiscountRate().toString(), "10").intValue()+"");
				}else {
					thirtyEntity.setDicVal("-1");
				}
				thirtyEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
				houseConfMsgDao.updateHouseConfMsg(thirtyEntity);
			}
		} else {//长租折扣开关关闭
			if(Check.NuNCollection(sevenList)){//没有设置满7天折扣
				HouseConfMsgEntity sevenEntity=new HouseConfMsgEntity();
				sevenEntity.setFid(UUIDGenerator.hexUUID());
				sevenEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
				sevenEntity.setRoomFid(housePriceDto.getRoomFid());
				sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
				if(!Check.NuNObj(housePriceDto.getSevenDiscountRate())&&housePriceDto.getSevenDiscountRate().intValue()!=0){
					sevenEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getSevenDiscountRate().toString(), "10").intValue()+"");
				}else {
					sevenEntity.setDicVal("-1");
				}
				sevenEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
				houseConfMsgDao.insertHouseConfMsg(sevenEntity);
			} else {//设置满7天折扣
				HouseConfMsgEntity sevenEntity=sevenList.get(0);
				if(!Check.NuNObj(housePriceDto.getSevenDiscountRate())&&housePriceDto.getSevenDiscountRate().intValue()!=0){
					sevenEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getSevenDiscountRate().toString(), "10").intValue()+"");
				}else {
					sevenEntity.setDicVal("-1");
				}
				sevenEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
				houseConfMsgDao.updateHouseConfMsg(sevenEntity);
			}
			if(Check.NuNCollection(thirtyList)){//没有设置满30天折扣
				HouseConfMsgEntity thirtyEntity=new HouseConfMsgEntity();
				thirtyEntity.setFid(UUIDGenerator.hexUUID());
				thirtyEntity.setHouseBaseFid(housePriceDto.getHouseBaseFid());
				thirtyEntity.setRoomFid(housePriceDto.getRoomFid());
				thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
				if(!Check.NuNObj(housePriceDto.getThirtyDiscountRate())&&housePriceDto.getThirtyDiscountRate().intValue()!=0){
					thirtyEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getThirtyDiscountRate().toString(), "10").intValue()+"");
				}else {
					thirtyEntity.setDicVal("-1");
				}
				thirtyEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
				houseConfMsgDao.insertHouseConfMsg(thirtyEntity);
			} else {//设置满30天折扣
				HouseConfMsgEntity thirtyEntity=thirtyList.get(0);
				if(!Check.NuNObj(housePriceDto.getThirtyDiscountRate())&&housePriceDto.getThirtyDiscountRate().intValue()!=0){
					thirtyEntity.setDicVal(DecimalCalculate.mul(housePriceDto.getThirtyDiscountRate().toString(), "10").intValue()+"");
				}else {
					thirtyEntity.setDicVal("-1");
				}
				thirtyEntity.setIsDel(YesOrNoOrFrozenEnum.FROZEN.getCode());
				houseConfMsgDao.updateHouseConfMsg(thirtyEntity);
			}
		}
	}

	/**
	 * 修改房源：保存基础价格，清洁费，周末价格，长租折扣（整租/分租）
	 * @author wangwt
	 * @created 2017年06月29日 17:28:33
	 * @param HousePriceDto housePriceDto
	 * @return
	 */
	public void saveHouseOrRoomPriceForModify(HousePriceDto housePriceDto) throws Exception{
	    //1.基础价格, 清洁费保存
        if (!Check.NuNStr(housePriceDto.getRoomFid())) {
            //分租
            HouseRoomMsgEntity houseRoomMsgEntity = houseRoomMsgDao.getHouseRoomByFid(housePriceDto.getRoomFid());
			houseRoomMsgEntity.setRoomPrice(DecimalCalculate.mul(housePriceDto.getLeasePrice().toString(), "100").intValue());
			houseRoomMsgEntity.setRoomCleaningFees(DecimalCalculate.mul(housePriceDto.getCleaningFees().toString(), "100").intValue());

			/****审核未通过的房源修改审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
			if(!Check.NuNObj(houseRoomMsgEntity)&&houseRoomMsgEntity.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
				houseRoomMsgEntity = HouseUtils.FilterAuditField(houseRoomMsgEntity,houseUpdateFieldAuditManagerEntities);
			}
			/****审核未通过的房源修改审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/

			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsgEntity);
        } else {
            //整租
            HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(housePriceDto.getHouseBaseFid());
			houseBaseMsgEntity.setLeasePrice(DecimalCalculate.mul(housePriceDto.getLeasePrice().toString(), "100").intValue());
			houseBaseMsgEntity.setHouseCleaningFees(DecimalCalculate.mul(housePriceDto.getCleaningFees().toString(), "100").intValue());

			/****审核未通过的房源修改审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
			if(!Check.NuNObj(houseBaseMsgEntity)&&houseBaseMsgEntity.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
				houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
			}
			/****审核未通过的房源修改审核字段时不入库，审核过后再入库，@Author:lusp @Date:2017/8/7****/

			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
        }

        //2.周末价格保存
        weekendPriceHandle(housePriceDto, null);
	    //3.长租折扣保存
        longLeaseDiscountHandle(housePriceDto);
    }


	/**
	 *
	 * 保存房源描述及基础信息（整租）
	 *
	 * 1.先保存房源名称、房源描述、房源周边信息
	 * 2.保存房源面积、户型信息
	 * 3.保存入住人数限制到ext表
	 * 4.保存便利设施
	 * 5.保存房间数据：如果参数中的房间fid为空表示要新增的房间，fid不为空表示更新当前fid房间信息
	 * 6.删除房间：根据前端传过来的delRoomFid集合进行删除
	 *
	 * @author lusp
	 * @created 2017年6月29日 上午11:34:17
	 *
	 * @param housePriceDto
	 */
	public void saveHouseDescAndBaseInfoEntire(HouseDescAndBaseInfoDto houseDescAndBaseInfoDto)throws Exception{

		/****************************查询房间基础信息****************************/
		HouseBaseMsgEntity baseMsg=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseDescAndBaseInfoDto.getHouseBaseFid());
		Integer houseStatus = baseMsg.getHouseStatus();
		/****************************查询房间基础信息****************************/

		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(houseDescAndBaseInfoDto.getHouseBaseFid());
		houseBaseMsgEntity.setHouseName(houseDescAndBaseInfoDto.getHouseName());
		houseBaseMsgEntity.setHouseArea(houseDescAndBaseInfoDto.getHouseArea());
		//处理户型 卧室,客厅,卫生间,厨房,阳台
		if(!Check.NuNStr(houseDescAndBaseInfoDto.getHouseModel())){
			List<String> modelList=Arrays.asList(houseDescAndBaseInfoDto.getHouseModel().split(","));
			houseBaseMsgEntity.setRoomNum(Integer.valueOf(modelList.get(0)));
			houseBaseMsgEntity.setHallNum(Integer.valueOf(modelList.get(1)));
			houseBaseMsgEntity.setToiletNum(Integer.valueOf(modelList.get(2)));
			houseBaseMsgEntity.setKitchenNum(Integer.valueOf(modelList.get(3)));
			houseBaseMsgEntity.setBalconyNum(Integer.valueOf(modelList.get(4)));
		}

		HouseDescEntity desc = houseDescDao.findHouseDescByHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
		desc.setHouseDesc(houseDescAndBaseInfoDto.getHouseDesc());
		desc.setHouseAroundDesc(houseDescAndBaseInfoDto.getHouseAroundDesc());
		List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = null;

		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
		if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
			houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
			desc = HouseUtils.FilterAuditField(desc,houseUpdateFieldAuditManagerEntities);
			houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
		}
		if(houseStatus==HouseStatusEnum.SJ.getCode()){
			houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
			desc = HouseUtils.FilterAuditField(desc,houseUpdateFieldAuditManagerEntities);
			houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
		}
		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

		houseDescDao.updateHouseDesc(desc);
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);

		/****************************保存入住人数限制到ext表****************************/
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
		houseBaseExtEntity.setCheckInLimit(houseDescAndBaseInfoDto.getCheckInLimit());
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
		/****************************保存入住人数限制到ext表****************************/

		/****************************便利设施保存****************************/
		if(!Check.NuNStr(houseDescAndBaseInfoDto.getHouseFacility())){
			List<String> facilityList=Arrays.asList(houseDescAndBaseInfoDto.getHouseFacility().split(","));
			//查询配套设施
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("houseBaseFid", houseDescAndBaseInfoDto.getHouseBaseFid());
			paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum002.getValue());
			List<HouseConfVo> faConfList=houseConfMsgDao.findHouseConfVoList(paramMap);
			paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum0015.getValue());
			List<HouseConfVo> seConfList=houseConfMsgDao.findHouseConfVoList(paramMap);
			faConfList.addAll(seConfList);
			//查询要删除的
			for( HouseConfVo vo:faConfList){
				boolean isDel=true;
				for(String facility:facilityList){
					if(vo.getDicCode().equals(facility.split("_")[0])&&vo.getDicValue().equals(facility.split("_")[1])){
						isDel=false;
						break;
					}
				}
				if(isDel){
					houseConfMsgDao.delHouseConfByFid(vo.getFid());
				}
			}
			//查询需要插入的
			for(String facility:facilityList ){
				boolean isInsert=true;
				for( HouseConfVo vo:faConfList){
					if(vo.getDicCode().equals(facility.split("_")[0])&&vo.getDicValue().equals(facility.split("_")[1])){
						isInsert=false;
						break;
					}
				}
				if(isInsert){
					HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
					houseConfMsgEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
					houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
					houseConfMsgEntity.setDicCode(facility.split("_")[0]);
					houseConfMsgEntity.setDicVal(facility.split("_")[1]);
					houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
				}
			}
		}
		/****************************便利设施保存****************************/

		/****************************房间数据保存****************************/
		if(!Check.NuNCollection(houseDescAndBaseInfoDto.getHouseRoomList())){
			for(HouseRoomVo vo:houseDescAndBaseInfoDto.getHouseRoomList()){
				//如果是新建房间
				if(Check.NuNStr(vo.getFid())){
					HouseRoomMsgEntity room=new HouseRoomMsgEntity();
					room.setFid(UUIDGenerator.hexUUID());
					room.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
					//生成roomSn
					HousePhyMsgEntity housePhyMsgEntity=housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
					String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
					if(!Check.NuNStr(roomSn)){
						int i = 0;
						while (i<3) {
							Long count = houseRoomMsgDao.countByRoomSn(roomSn);
							if(count>0){
								i++;
								roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);
								continue;
							}
							break;
						}
					}
					room.setRoomSn(roomSn);
					room.setRoomPrice(0);
					room.setRoomStatus(baseMsg.getHouseStatus());
					houseRoomMsgDao.insertHouseRoomMsg(room);
					//插入床信息
					if(!Check.NuNStr(vo.getBedMsg())){
						Integer bedSnInteger=100;
						for(String bed:vo.getBedMsg().split(",")){
							Integer bedNum=Integer.valueOf(bed.split("_")[1]);
							for(int i=0;i<bedNum;i++){
								HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
								bedMsgEntity.setFid(UUIDGenerator.hexUUID());
								bedMsgEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
								bedMsgEntity.setBedSn(bedSnInteger++);
								bedMsgEntity.setRoomFid(room.getFid());
								bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
								bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
								houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
							}
						}
					}
				} else {
					//修改床信息，判断是否修改，设置可以发布标志
					String flagKey=houseDescAndBaseInfoDto.getHouseBaseFid()+"issue";
					boolean isFlag=false;
					//要保存的床类型列表
					List<Integer> bedTypeList=new ArrayList<>();
					if(!Check.NuNStr(vo.getBedMsg())){
						Integer bedSnInteger=100;
						for(String bed:vo.getBedMsg().split(",")){
							Integer bedNum=Integer.valueOf(bed.split("_")[1]);
							Integer bedType=Integer.valueOf(bed.split("_")[0]);
							bedTypeList.add(bedType);
							Map<String,Object> paramMap=new HashMap<String,Object>();
							paramMap.put("houseBaseFid", houseDescAndBaseInfoDto.getHouseBaseFid());
							paramMap.put("roomFid", vo.getFid());
							paramMap.put("bedType", bedType);
							List<HouseBedMsgEntity> bedList=houseBedMsgDao.getBedNumByType(paramMap);
							//判断下数量是增是减
							if(Check.NuNCollection(bedList)){
								for(int i=0;i<bedNum;i++){
									HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
									bedMsgEntity.setFid(UUIDGenerator.hexUUID());
									bedMsgEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
									bedMsgEntity.setBedSn(bedSnInteger++);
									bedMsgEntity.setRoomFid(vo.getFid());
									bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
									bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
									houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
								}
								//有新增床
								isFlag=true;
							} else if(bedNum>bedList.size()){
								for(int i=0;i<bedNum-bedList.size();i++){
									HouseBedMsgEntity bedMsgEntity=new HouseBedMsgEntity();
									bedMsgEntity.setFid(UUIDGenerator.hexUUID());
									bedMsgEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
									bedMsgEntity.setBedSn(bedSnInteger++);
									bedMsgEntity.setRoomFid(vo.getFid());
									bedMsgEntity.setBedType(Integer.valueOf(bed.split("_")[0]));
									bedMsgEntity.setBedStatus(baseMsg.getHouseStatus());
									houseBedMsgDao.insertHouseBedMsg(bedMsgEntity);
								}
								//有新增床
								isFlag=true;
							} else if(bedNum<bedList.size()) {
								for(int i=0;i<bedList.size()-bedNum;i++){
									houseBedMsgDao.deleteHouseBedMsgByFid(bedList.get(i).getFid());
								}
								//有减少床
								isFlag=true;
							}
						}
					}
					//查询房间下床列表
					List<HouseBedMsgEntity>bedMsgList=houseBedMsgDao.findBedListByRoomFid(vo.getFid());
					if(!Check.NuNCollection(bedMsgList)){
						for(HouseBedMsgEntity bed:bedMsgList){
							if(!bedTypeList.contains(bed.getBedType())){
								houseBedMsgDao.deleteHouseBedMsgByFid(bed.getFid());
								isFlag=true;
							}
						}
					}
					if(isFlag&&houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
						try {
							redisOperations.setex(flagKey.toString(), 24*60*60, "1");
						} catch (Exception e){
							LogUtil.error(logger, "redis保存整租修改床信息标志key{},{}",flagKey, e);
						}
					}
				}
			}
		}
		/****************************房间数据保存****************************/

		/***************************删除房间***************************/
		List<String> delRoomFidList = houseDescAndBaseInfoDto.getDelRoomFidList();
		deleteRoomMsg(delRoomFidList);
		/***************************删除房间***************************/
	}


	/**
	 * 删除房间信息
	 * @author jixd
	 * @created 2017年07月03日 16:55:07
	 * @param
	 * @return
	 */
	public int deleteRoomMsg(List<String> delFids){
		int count = 0;
		if (!Check.NuNCollection(delFids)){
			for (String delFid : delFids){
				count = houseRoomMsgDao.deleteHouseRoomMsgByFid(delFid);
				if (count > 0){
					count += houseBedMsgDao.deleteHouseBedMsgByRoomFid(delFid);
					count += housePicMsgDao.deleteHousePicMsgByRoomFid(delFid);
				}
			}
		}
		return count;
	}


	/**
	 * 更新房间信息 （更新分租可出租房间数 和 删除房源）
	 * @author jixd
	 * @created 2017年07月03日 19:11:30
	 * @param
	 * @return
	 */
	public int updateRoomNumAndRoomMsg(HouseRoomUpDto houseRoomUpDto){
		int count = 0;
		String houseBaseFid = houseRoomUpDto.getHouseBaseFid();
		HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseBaseFid(houseBaseFid);
		houseBaseExtEntity.setRentRoomNum(houseRoomUpDto.getRentRoomNum());
		count += houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
		count += deleteRoomMsg(houseRoomUpDto.getDelRoomFidList());
		
		HouseBaseMsgEntity  houseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
		if(!Check.NuNObj(houseRoomUpDto.getStep()) && houseRoomUpDto.getStep()>houseBaseMsgEntity.getOperateSeq()){
			houseBaseMsgEntity = new HouseBaseMsgEntity();
			houseBaseMsgEntity.setFid(houseBaseFid);
			houseBaseMsgEntity.setOperateSeq(houseRoomUpDto.getStep());
			houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.getValueByCode(houseRoomUpDto.getStep()));
			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
		}
		
		return count;
	}

}
