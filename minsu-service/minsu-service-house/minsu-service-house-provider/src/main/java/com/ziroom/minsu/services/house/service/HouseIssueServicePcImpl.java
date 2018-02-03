package com.ziroom.minsu.services.house.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.RandomUtil;
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
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryExtLogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBedMsgDao;
import com.ziroom.minsu.services.house.dao.HouseCommonLogicDao;
import com.ziroom.minsu.services.house.dao.HouseConfMsgDao;
import com.ziroom.minsu.services.house.dao.HouseDescDao;
import com.ziroom.minsu.services.house.dao.HouseGuardRelDao;
import com.ziroom.minsu.services.house.dao.HouseOperateLogDao;
import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;
import com.ziroom.minsu.services.house.dao.HousePicMsgDao;
import com.ziroom.minsu.services.house.dao.HousePriceConfDao;
import com.ziroom.minsu.services.house.dao.HouseRoomExtDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryLogDao;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HousePhyPcDto;
import com.ziroom.minsu.services.house.dto.HouseRoomListPcDto;
import com.ziroom.minsu.services.house.dto.HouseRoomsWithBedsPcDto;
import com.ziroom.minsu.services.house.dto.RoomHasBeds;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseRoomListVo;
import com.ziroom.minsu.services.house.pc.dto.HouseIssueDescDto;
import com.ziroom.minsu.services.house.pc.dto.HousePicTypeDto;
import com.ziroom.minsu.services.house.pc.dto.HouseRoomBaseMsg;
import com.ziroom.minsu.services.house.pc.dto.RoomDefaultPicDto;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0012Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>pc端发布房源相关接口实现</p>
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
@Service("house.HouseIssueServicePcImpl")
public class HouseIssueServicePcImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueServicePcImpl.class);

	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	@Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

	@Resource(name = "house.houseDescDao")
	private HouseDescDao houseDescDao;

	@Resource(name = "house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;

	@Resource(name = "house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;

	@Resource(name = "house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;

	@Resource(name = "house.housePriceConfDao")
	private HousePriceConfDao housePriceConfDao;

	@Resource(name = "house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;

	@Resource(name = "house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;

	@Resource(name = "house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;

	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "house.houseGuardRelDao")
	private HouseGuardRelDao houseGuardRelDao;

	@Resource(name = "house.houseCommonLogicDao")
	private HouseCommonLogicDao houseCommonLogicDao;

	@Resource(name = "house.houseUpdateHistoryLogDao")
	private HouseUpdateHistoryLogDao houseUpdateHistoryLogDao;

	@Resource(name = "house.houseUpdateHistoryExtLogDao")
	private HouseUpdateHistoryExtLogDao houseUpdateHistoryExtLogDao;

	@Resource(name = "house.houseUpdateFieldAuditNewlogDao")
	private HouseUpdateFieldAuditNewlogDao houseUpdateFieldAuditNewlogDao;

	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;
	
	@Resource(name="house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;


	/**
	 *
	 * 插入或者更新房源位置信息
	 *
	 * @author bushujie
	 * @created 2016年8月6日 下午3:51:53
	 *
	 * @param housePhyPcDto
	 * @return
	 */
	public HouseBaseMsgEntity insertOrUpdateHouseLocation (HousePhyPcDto housePhyPcDto) throws Exception{
		//房源fid为空则保存
		String houseBaseFid=null;
		//房源地址拼接
		StringBuilder sb=new StringBuilder();
		if(!Check.NuNStr(housePhyPcDto.getCityName())){
			sb.append(housePhyPcDto.getCityName());
		}
		if(!Check.NuNStr(housePhyPcDto.getAreaName())){
			sb.append(housePhyPcDto.getAreaName());
		}
		if(!Check.NuNStr(housePhyPcDto.getHouseStreet())){
			sb.append(housePhyPcDto.getHouseStreet().replaceAll(" ", ""));
		}
		if(!Check.NuNStr(housePhyPcDto.getCommunityName())){
			if(!housePhyPcDto.getCommunityName().equals(housePhyPcDto.getHouseStreet())){
				sb.append(housePhyPcDto.getCommunityName().replaceAll(" ", ""));
			}
		}
		if(!Check.NuNStr(housePhyPcDto.getDetailAddress())){
			sb.append(" "+housePhyPcDto.getDetailAddress());
		}

		if(Check.NuNStr(housePhyPcDto.getHouseBaseFid())){
			//保存房源物理信息表
			HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
			BeanUtils.copyProperties(housePhyPcDto, housePhyMsgEntity);
			housePhyMsgEntity.setFid(UUIDGenerator.hexUUID());
			housePhyMsgEntity.setBuildingCode(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS"));
			housePhyMsgEntity.setCreateUid(housePhyPcDto.getUid());
			housePhyMsgDao.insertHousePhyMsg(housePhyMsgEntity);
			//保存房源基础表
			HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
			houseBaseMsgEntity.setFid(UUIDGenerator.hexUUID());
			houseBaseFid=houseBaseMsgEntity.getFid();
			houseBaseMsgEntity.setPhyFid(housePhyMsgEntity.getFid());
			houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.DFB.getCode());
			houseBaseMsgEntity.setLandlordUid(housePhyPcDto.getUid());
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
			//步骤和完整率
			houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.ONE.getCode());
			houseBaseMsgEntity.setIntactRate(HouseIssueStepEnum.ONE.getValue());
			//来源
			houseBaseMsgEntity.setHouseSource(HouseSourceEnum.PC.getCode());
			houseBaseMsgEntity.setHouseAddr(sb.toString());
			houseBaseMsgEntity.setHouseType(housePhyPcDto.getHouseType());
			houseBaseMsgEntity.setRentWay(housePhyPcDto.getRentWay());
			houseBaseMsgEntity.setHouseChannel(housePhyPcDto.getHouseChannel());
			houseBaseMsgDao.insertHouseBaseMsg(houseBaseMsgEntity);
			//保存房源扩展信息
			HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
			houseBaseExtEntity.setFid(UUIDGenerator.hexUUID());
			houseBaseExtEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
			houseBaseExtEntity.setHouseStreet(housePhyPcDto.getHouseStreet());
			houseBaseExtEntity.setDetailAddress(housePhyPcDto.getDetailAddress());
			houseBaseExtDao.insertHouseBaseExt(houseBaseExtEntity);
			//保存房源管家信息
			if(!Check.NuNObj(housePhyPcDto.getHouseGuardRel())){
				housePhyPcDto.getHouseGuardRel().setHouseFid(houseBaseFid);
				houseGuardRelDao.insertHouseGuardRel(housePhyPcDto.getHouseGuardRel());
			}
		} else {
			houseBaseFid=housePhyPcDto.getHouseBaseFid();
			//兼容老房源，首先判断物理地址是否多个房源使用
			int phyHouseCount=houseBaseMsgDao.findHouseCountByPhyFid(housePhyPcDto.getFid(),housePhyPcDto.getHouseBaseFid());
			if(phyHouseCount>0){
				//保存房源物理信息表
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				BeanUtils.copyProperties(housePhyPcDto, housePhyMsgEntity);
				housePhyMsgEntity.setFid(UUIDGenerator.hexUUID());
				housePhyMsgEntity.setBuildingCode(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmssSSS"));
				housePhyMsgDao.insertHousePhyMsg(housePhyMsgEntity);
			} else {
				//更新房源物理信息表
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				BeanUtils.copyProperties(housePhyPcDto, housePhyMsgEntity);
				housePhyMsgDao.updateHousePhyMsg(housePhyMsgEntity);
			}
			HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
			//更新房源基础表
			houseBaseMsgEntity.setHouseAddr(sb.toString());
			houseBaseMsgEntity.setRentWay(housePhyPcDto.getRentWay());

			//更新房源扩展信息表
			HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
			houseBaseExtEntity.setHouseBaseFid(houseBaseFid);
			houseBaseExtEntity.setHouseStreet(housePhyPcDto.getHouseStreet());
			houseBaseExtEntity.setDetailAddress(housePhyPcDto.getDetailAddress());

			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
			if(!Check.NuNObj(houseBaseMsgEntity)){
				if(houseBaseMsgEntity.getHouseStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
					houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
					houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
					houseBaseExtEntity = HouseUtils.FilterAuditField(houseBaseExtEntity,houseUpdateFieldAuditManagerEntities);
				}
				if(houseBaseMsgEntity.getHouseStatus()==HouseStatusEnum.SJ.getCode()){
					houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
					houseBaseMsgEntity = HouseUtils.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
					houseBaseExtEntity = HouseUtils.FilterAuditField(houseBaseExtEntity,houseUpdateFieldAuditManagerEntities);
				}
			}
			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

			houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
			houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExtEntity);
			//更新房源管家信息
			HouseGuardRelEntity houseGuardRelEntity=houseGuardRelDao.findHouseGuardRelByHouseBaseFid(houseBaseFid);
			LOGGER.info("管家信息是否存在："+JsonEntityTransform.Object2Json(houseGuardRelEntity));
			if(!Check.NuNObj(housePhyPcDto.getHouseGuardRel())&&!Check.NuNObj(houseGuardRelEntity)){
				LOGGER.info("更新管家信息："+JsonEntityTransform.Object2Json(housePhyPcDto.getHouseGuardRel()));
				houseGuardRelEntity.setEmpGuardCode(housePhyPcDto.getHouseGuardRel().getEmpGuardCode());
				houseGuardRelEntity.setEmpGuardName(housePhyPcDto.getHouseGuardRel().getEmpGuardName());
				houseGuardRelEntity.setEmpPushCode(housePhyPcDto.getHouseGuardRel().getEmpPushCode());
				houseGuardRelEntity.setEmpPushName(housePhyPcDto.getHouseGuardRel().getEmpPushName());
				houseGuardRelDao.updateHouseGuardRelByFid(houseGuardRelEntity);
			}else if(!Check.NuNObj(housePhyPcDto.getHouseGuardRel())&&Check.NuNObj(houseGuardRelEntity)){
				LOGGER.info("插入管家信息："+JsonEntityTransform.Object2Json(housePhyPcDto.getHouseGuardRel()));
				housePhyPcDto.getHouseGuardRel().setHouseFid(houseBaseFid);
				houseGuardRelDao.insertHouseGuardRel(housePhyPcDto.getHouseGuardRel());
			}
		}
		HouseBaseMsgEntity resultBase=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
		return resultBase;
	}

	/**
	 *
	 * 模糊查询房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月9日 下午4:22:18
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseConfVo> findHouseConfVoList(Map<String, Object> paramMap){
		return houseConfMsgDao.findHouseConfVoList(paramMap);
	}

	/**
	 *
	 * 查询房源房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月11日 下午6:43:17
	 *
	 * @param fid
	 * @return
	 */
	public HouseRoomListPcDto findHouseRoomList(String fid){
		return houseBaseMsgDao.findHouseRoomList(fid);
	}

	/**
	 *
	 * 根据房源Fid查询房源下所有房间包括床位信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午2:51:00
	 *
	 * @param houseFid
	 * @return
	 */
	public HouseRoomsWithBedsPcDto findHouseRoomWithBedsList(String houseFid,DataTransferObject dto){
		HouseRoomsWithBedsPcDto pcDto = new HouseRoomsWithBedsPcDto();
		HouseRoomListPcDto houseRoomList = houseBaseMsgDao.findHouseRoomList(houseFid);
		List<RoomHasBeds> rooms = new ArrayList<>();
		if(Check.NuNObj(houseRoomList)){
			LogUtil.info(LOGGER, "调用findHouseRoomWithBedsList方法时，此房源已经被删除，查出houseRoomList为空");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源不存在");
			return pcDto;
		}
		
		List<HouseRoomMsgEntity> roomList = houseRoomList.getRoomList();
		for(HouseRoomMsgEntity roomMsg : roomList){
			RoomHasBeds room = new RoomHasBeds();
			List<HouseBedMsgEntity> beds = houseBedMsgDao.findBedListByRoomFid(roomMsg.getFid());
			room.setBeds(beds);
			room.setRoomMsg(roomMsg);
			rooms.add(room);
		}
		HouseBaseMsgEntity baseMsgEntity = new HouseBaseMsgEntity();
		BeanUtils.copyProperties(houseRoomList, baseMsgEntity);
		
		pcDto.setHouseBaseMsgEntity(baseMsgEntity);
		pcDto.setRoomList(rooms);
		return pcDto;
	}

	/**
	 * 分租流程
	 * PC发布房源步奏，更新房源户型信息，增加或者更新房间信息,增加或者更新床铺信息
	 *
	 * 这块只有pc调用  故来源于为pc  操作人 为房东
	 *
	 * @author jixd
	 * @created 2016年8月15日 上午10:50:35
	 *
	 * @return
	 */
	public int saveOrUpdateRoomsMsg(HouseBaseMsgEntity houseBaseMsg,List<RoomHasBeds> roomsList,Integer houseStatus)throws Exception{
		/*Integer operateSeq = houseBaseMsg.getOperateSeq();

    	if(HouseIssueStepEnum.FIVE.getCode() - operateSeq == 1){
    		houseBaseMsg.setOperateSeq(HouseIssueStepEnum.FIVE.getCode());
    		houseBaseMsg.setIntactRate(HouseIssueStepEnum.FIVE.getValue());
    	}*/
		//将要出租房间的实际数量，更新到ext表中
		HouseBaseExtEntity houseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseMsg.getFid());
		//保存修改日志
		saveHouseBaseMsgUpdateLog(houseBaseMsg, houseBaseExt,roomsList);

		houseBaseExt.setRentRoomNum(roomsList.size());

		if(houseBaseMsg.getOperateSeq().intValue() < HouseIssueStepEnum.SEVEN.getCode()){
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.FIVE.getValue());
		}
	
		if(!Check.NuNObj(houseStatus)){
			List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
			List<HouseUpdateFieldAuditManagerEntity> finalHouseUpdateFieldAuditManagerEntities=new ArrayList<HouseUpdateFieldAuditManagerEntity>();;
			if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
				
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
				//审核未通过，房东修改的客厅数量，厨房数量，阳台数量      2018-1-10修改
				for (HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManager : houseUpdateFieldAuditManagerEntities) {
					if(!houseUpdateFieldAuditManager.getFieldPath().equals(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())
							&& !houseUpdateFieldAuditManager.getFieldPath().equals(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath())
							&& !houseUpdateFieldAuditManager.getFieldPath().equals(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath())){
						finalHouseUpdateFieldAuditManagerEntities.add(houseUpdateFieldAuditManager);
					}
				}
				houseBaseMsg = HouseUtils.FilterAuditField(houseBaseMsg,finalHouseUpdateFieldAuditManagerEntities);
			}
			if(houseStatus==HouseStatusEnum.SJ.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
				houseBaseMsg = HouseUtils.FilterAuditField(houseBaseMsg,houseUpdateFieldAuditManagerEntities);
			}
		}
	

		/*分租 ：审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/ 
		if(!Check.NuNCollection(roomsList)){
			if(Check.NuNObj(houseStatus)){
				//如果是分租   必传roomFid 
				List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
				for (RoomHasBeds roomBed : roomsList) {
					HouseRoomMsgEntity room = roomBed.getRoomMsg();
					if(!Check.NuNObj(room)&&!Check.NuNStr(room.getFid())){
						HouseRoomMsgEntity oldRoom = this.houseRoomMsgDao.findHouseRoomMsgByFid(room.getFid());
						if(Check.NuNObj(oldRoom)){
							throw new BusinessException("【分租发布房源--房间不存在】oldRoom={"+JsonEntityTransform.Object2Json(oldRoom)+"}");
						}
						int roomStatu = oldRoom.getRoomStatus();
						room.setRoomStatus(roomStatu);
						if(Check.NuNCollection(houseUpdateFieldAuditManagerEntities)){
							if(roomStatu==HouseStatusEnum.ZPSHWTG.getCode()){
								houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
								houseBaseMsg = HouseUtils.FilterAuditField(houseBaseMsg,houseUpdateFieldAuditManagerEntities);
							}
							if(roomStatu==HouseStatusEnum.SJ.getCode()){
								houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
								houseBaseMsg = HouseUtils.FilterAuditField(houseBaseMsg,houseUpdateFieldAuditManagerEntities);
							}
						}
					}
				}
			}
		}
		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

		int houseCount = houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);

		HousePhyMsgEntity housePhyMsgEntity = housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBaseMsg.getFid());
		//是否修改过床信息标志
		boolean isFlag=false;
		if(houseCount > 0){
			for(RoomHasBeds rhb : roomsList){
				HouseRoomMsgEntity roomMsg = rhb.getRoomMsg();
				List<HouseBedMsgEntity> beds = rhb.getBeds();

				if(Check.NuNObj(roomMsg.getFid())){
					roomMsg.setFid(UUIDGenerator.hexUUID());
					roomMsg.setRoomStatus(HouseStatusEnum.DFB.getCode());
					String roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(),housePhyMsgEntity.getCityCode(), RentWayEnum.ROOM.getCode(), null);

					if(!Check.NuNStr(roomSn)){
						int i = 0;
						while (i<3) {
							Long count = 	this.houseRoomMsgDao.countByRoomSn(roomSn);
							if(count>0){
								i++;
								roomSn = HouseUtils.getHouseOrRoomSn(housePhyMsgEntity.getNationCode(), housePhyMsgEntity.getCityCode(),RentWayEnum.ROOM.getCode(), null);
								continue;
							}
							break;
						}
					}
					roomMsg.setRoomSn(roomSn);
					houseRoomMsgDao.insertHouseRoomMsg(roomMsg);
				}else{
					//房间属性审核  分租
					List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
					Integer roomStatu = roomMsg.getRoomStatus();
					if(Check.NuNObj(houseStatus)){
						HouseRoomMsgEntity oldRoom = this.houseRoomMsgDao.findHouseRoomMsgByFid(roomMsg.getFid());
						if(Check.NuNObj(oldRoom)){
							throw new BusinessException("【分租发布房源--房间不存在】oldRoom={"+JsonEntityTransform.Object2Json(oldRoom)+"}");
						}
						roomStatu = oldRoom.getRoomStatus();
						if(roomStatu==HouseStatusEnum.ZPSHWTG.getCode()){
							houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
							roomMsg = HouseUtils.FilterAuditField(roomMsg,houseUpdateFieldAuditManagerEntities);
						}
						if(roomStatu==HouseStatusEnum.SJ.getCode()){
							houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
							roomMsg = HouseUtils.FilterAuditField(roomMsg,houseUpdateFieldAuditManagerEntities);
						}
					}else{
						if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
							houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
							roomMsg = HouseUtils.FilterAuditField(roomMsg,houseUpdateFieldAuditManagerEntities);
						}
						if(houseStatus==HouseStatusEnum.SJ.getCode()){
							houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
							roomMsg = HouseUtils.FilterAuditField(roomMsg,houseUpdateFieldAuditManagerEntities);
						}
					}
					houseRoomMsgDao.updateHouseRoomMsg(roomMsg);
				}

				for(HouseBedMsgEntity bed : beds){
					bed.setRoomFid(roomMsg.getFid());
					if(Check.NuNObj(bed.getFid())){
						bed.setFid(UUIDGenerator.hexUUID());
						bed.setRoomFid(roomMsg.getFid());
						bed.setBedStatus(houseStatus);
						houseBedMsgDao.insertHouseBedMsg(bed);
					}else{
						houseBedMsgDao.updateHouseBedMsg(bed);
					}
					isFlag=true;
				}
			}
		}else{
			return 0;
		}
		//redis添加修改过床信息标志 整租
		HouseBaseMsgEntity housebase=houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseMsg.getFid());
		if(housebase.getRentWay()==RentWayEnum.HOUSE.getCode()&&houseStatus==HouseStatusEnum.ZPSHWTG.getCode()&&isFlag){
			try {
				redisOperations.setex(housebase.getFid()+"issue", 24*60*60, "1");
			} catch (Exception e){
				LogUtil.error(LOGGER, "redis合租保存床修改标志key{},{}",housebase.getFid()+"issue", e.getMessage());
			}
		}
		//redis添加修改过床信息标志 分组
		if(housebase.getRentWay()==RentWayEnum.ROOM.getCode()){
			for(RoomHasBeds rhb : roomsList){
				HouseRoomMsgEntity roomMsg = rhb.getRoomMsg();
				List<HouseBedMsgEntity> beds = rhb.getBeds();
				if(!Check.NuNObj(roomMsg.getFid())&&roomMsg.getRoomStatus()==HouseStatusEnum.ZPSHWTG.getCode()){
					if(!Check.NuNCollection(beds)){
						try {
							redisOperations.setex(roomMsg.getFid()+"issue", 24*60*60, "1");
						} catch (Exception e){
							LogUtil.error(LOGGER, "redis合租保存床修改标志key{},{}",roomMsg.getFid()+"issue", e.getMessage());
						}
					}
				}
			}
		}
		//添加修改日志
		return roomsList.size();
	}

	/**
	 *
	 * 分租保存房源修改日志  房源uid 必须存在
	 *
	 * 有修改 即保存
	 *
	 * @author yd
	 * @created 2017年7月12日 下午2:06:55
	 *
	 * @param houseBaseMsg
	 * @param roomsList
	 */
	private  void saveHouseBaseMsgUpdateLog(HouseBaseMsgEntity newHouseBaseMsg,HouseBaseExtEntity oldHouseBaseExt,List<RoomHasBeds> newRoomsList){


		if(Check.NuNObj(newHouseBaseMsg)||Check.NuNStr(newHouseBaseMsg.getFid())){
			LogUtil.info(LOGGER, "【保存修改日志-房源fid不存在】houseBaseFid={}", newHouseBaseMsg==null?"":newHouseBaseMsg.getFid());
			return ;
		}

		List<HouseUpdateHistoryLogEntity> list = new ArrayList<HouseUpdateHistoryLogEntity>();
		//房源修改日志
		//添加修改日志
		HouseBaseMsgEntity oldHouseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(newHouseBaseMsg.getFid());
		if(Check.NuNObj(oldHouseBaseMsgEntity)){
			LogUtil.error(LOGGER, "【保存修改日志-房源不存在】houseBaseFid={}", newHouseBaseMsg.getFid());
			throw new BusinessException("【保存修改日志-房源不存在】houseBaseFid="+newHouseBaseMsg.getFid()+"");
		}
		HouseUtils.contrastHouseBaseMsgObj(newHouseBaseMsg, oldHouseBaseMsgEntity, list);
		if(!Check.NuNCollection(newRoomsList)){

			if(!Check.NuNObj(oldHouseBaseExt)){
				HouseBaseExtEntity newHouseBaseExt = new HouseBaseExtEntity();
				newHouseBaseExt.setRentRoomNum(newRoomsList.size());
				HouseUtils.contrastHouseBaseExtObj(newHouseBaseExt, oldHouseBaseExt, list);
			}

			for (RoomHasBeds rhb : newRoomsList) {
				HouseRoomMsgEntity roomMsg = rhb.getRoomMsg();
				if(!Check.NuNObj(roomMsg)&&!Check.NuNStr(roomMsg.getFid())){
					HouseRoomMsgEntity oldHouseRoom = houseRoomMsgDao.findHouseRoomMsgByFid(roomMsg.getFid());
					if(Check.NuNObj(oldHouseRoom)){
						throw new BusinessException("【保存修改日志-房间不存在】roomFid="+roomMsg.getFid()+"");
					}
					HouseUtils.contrastHouseRoomMsgObj(roomMsg, oldHouseRoom, list,oldHouseBaseMsgEntity.getRentWay());
				}
			}
		}

		if(!Check.NuNCollection(list)){
			for (HouseUpdateHistoryLogEntity houseUpdateHistoryLog : list) {
				houseUpdateHistoryLog.setCreaterFid(oldHouseBaseMsgEntity.getLandlordUid());
				houseUpdateHistoryLog.setCreaterType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLog.setHouseFid(oldHouseBaseMsgEntity.getFid());
				houseUpdateHistoryLog.setRentWay(oldHouseBaseMsgEntity.getRentWay());
				String fieldPathKey = MD5Util.MD5Encode(houseUpdateHistoryLog.getHouseFid()+houseUpdateHistoryLog.getRoomFid()+houseUpdateHistoryLog.getRentWay()
						+houseUpdateHistoryLog.getFieldPath(), "UTF-8");
				houseUpdateHistoryLog.setFieldPathKey(fieldPathKey);
				houseUpdateHistoryLog.setSourceType(HouseSourceEnum.PC.getCode());
				saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
			}
		}


	}


	/**
	 *
	 * 保存
	 * 1. 保存 修改历史 HouseUpdateHistoryLog 如果 是大字段 则存入大字段表
	 * 2. 更新 最新记录表
	 *
	 * @author yd
	 * @created 2017年6月30日 下午4:34:42
	 *
	 * @param houseUpdateHistoryLog
	 */
	public int saveHouseUpdateHistoryLog(HouseUpdateHistoryLogEntity houseUpdateHistoryLog){

		if(!Check.NuNObj(houseUpdateHistoryLog)){

			if(Check.NuNStr(houseUpdateHistoryLog.getFid())) houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			//此字段 必传 在上层做校验
			int isTest = houseUpdateHistoryLog.getIsText();
			int i =  0;
			if(isTest == IsTextEnum.IS_TEST.getCode()){

				LogUtil.info(LOGGER, "【保存房源修改大字段】isTest={},houseFid={},roomFid={},rentWay={},fieldPathKey={},", isTest,houseUpdateHistoryLog.getHouseFid(),houseUpdateHistoryLog.getRoomFid()
						,houseUpdateHistoryLog.getRentWay(),houseUpdateHistoryLog.getFieldPathKey());
				HouseUpdateHistoryExtLogEntity houseUpdateHistoryExtLog = new HouseUpdateHistoryExtLogEntity();
				houseUpdateHistoryExtLog.setFid(houseUpdateHistoryLog.getFid());
				houseUpdateHistoryExtLog.setNewValue(houseUpdateHistoryLog.getNewValue());
				houseUpdateHistoryExtLog.setOldValue(houseUpdateHistoryLog.getOldValue());
				houseUpdateHistoryLog.setNewValue("");
				houseUpdateHistoryLog.setOldValue("");

				this.houseUpdateHistoryExtLogDao.saveHouseUpdateHistoryExtLog(houseUpdateHistoryExtLog);
			}
			i = this.houseUpdateHistoryLogDao.saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
			//t_house_update_field_audit_newlog 审核字段的最新记录 只做第一次插入  这里状态更改在 审核时候 才会状态更改
			if(i>0){

				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/
				//根据houseFid、rentWay、roomFid 来统一判断房源状态
				String houseFid = houseUpdateHistoryLog.getHouseFid();
				String roomFid = houseUpdateHistoryLog.getRoomFid();
				Integer rentWay = houseUpdateHistoryLog.getRentWay();
				HouseBaseMsgEntity houseBaseMsgEntity = null;
				if(Check.NuNStrStrict(houseFid)){
					LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),保存到房源审核记录表时出错houseFid为空，houseFid:{}",houseFid);
					return i;
				}
				if(Check.NuNObj(rentWay)){
					houseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
					if(Check.NuNObj(houseBaseMsgEntity)){
						LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据houseFid获取房源基础信息为空，houseFid:{}",houseFid);
						return i;
					}
					rentWay = houseBaseMsgEntity.getRentWay();
				}
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					Integer houseStatus = null;
					if(Check.NuNObj(houseBaseMsgEntity)){
						houseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
						if(Check.NuNObj(houseBaseMsgEntity)){
							LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据houseFid获取房源基础信息为空，houseFid:{}",houseFid);
							return i;
						}
						houseStatus = houseBaseMsgEntity.getHouseStatus();
					}else {
						houseStatus = houseBaseMsgEntity.getHouseStatus();
					}
					i = this.saveHouseAuditField(houseUpdateHistoryLog,houseStatus,i);
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					if(!Check.NuNStrStrict(roomFid)){
						HouseRoomMsgEntity houseRoomMsgEntity = houseRoomMsgDao.getHouseRoomByFid(roomFid);
						if(Check.NuNObj(houseRoomMsgEntity)){
							LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据roomFid获取房间基础信息为空，roomFid:{}",roomFid);
							return i;
						}
						Integer houseStatus = houseRoomMsgEntity.getRoomStatus();
						i = this.saveHouseAuditField(houseUpdateHistoryLog,houseStatus,i);
					}else{
						boolean hasSJ = false;
						boolean hasZPSHWTG = false;
						List<HouseRoomListVo> roomListVos = houseRoomMsgDao.getRoomListByHouseFid(houseFid);
						if(!Check.NuNCollection(roomListVos)){
							for (HouseRoomListVo houseRoomListVo:roomListVos){
								if(houseRoomListVo.getRoomStatus()== HouseStatusEnum.SJ.getCode()){
									hasSJ = true;
									break;
								}
							}
							for (HouseRoomListVo houseRoomListVo:roomListVos){
								if(houseRoomListVo.getRoomStatus()== HouseStatusEnum.ZPSHWTG.getCode()){
									hasZPSHWTG = true;
									break;
								}
							}
						}
						if(hasZPSHWTG){
							i = this.saveHouseAuditField(houseUpdateHistoryLog,HouseStatusEnum.ZPSHWTG.getCode(),i);
						}
						if(hasSJ){
							i = this.saveHouseAuditField(houseUpdateHistoryLog,HouseStatusEnum.SJ.getCode(),i);
						}
					}
				}
				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/

			}

			return i;
		}


		return 0;
	}

	/**
	 * @description: 根据房源状态以及房源基础信息保存审核记录表
	 * @author: lusp
	 * @date: 2017/8/9 17:10
	 * @params: houseUpdateHistoryLog,houseStatus,i
	 * @return:
	 */
	private int saveHouseAuditField(HouseUpdateHistoryLogEntity houseUpdateHistoryLog,Integer houseStatus,int i){
		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity = new HouseUpdateFieldAuditManagerEntity();
		houseUpdateFieldAuditManagerEntity.setFid(MD5Util.MD5Encode(houseUpdateHistoryLog.getFieldPath(), "UTF-8"));
		if(houseStatus == HouseStatusEnum.ZPSHWTG.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(0);
		}else if(houseStatus == HouseStatusEnum.SJ.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(1);
		}
		houseUpdateFieldAuditManagerEntity = this.houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByFidAndType(houseUpdateFieldAuditManagerEntity);
		if(Check.NuNObj(houseUpdateFieldAuditManagerEntity)){
			LogUtil.info(LOGGER, "【保存房源修改审核字段】当前字段非审核字段：fieldPath={}", houseUpdateHistoryLog.getFieldPath());
			return i;
		}
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = this.houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByFid(houseUpdateHistoryLog.getFieldPathKey());
		if(Check.NuNObj(houseUpdateFieldAuditNewlog)){
			houseUpdateFieldAuditNewlog = new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlog.setFieldPath(houseUpdateHistoryLog.getFieldPath());
			houseUpdateFieldAuditNewlog.setFieldDesc(houseUpdateHistoryLog.getFieldDesc());
			houseUpdateFieldAuditNewlog.setFid(houseUpdateHistoryLog.getFieldPathKey());
			houseUpdateFieldAuditNewlog.setCreaterFid(houseUpdateHistoryLog.getCreaterFid());
			houseUpdateFieldAuditNewlog.setCreaterType(houseUpdateHistoryLog.getCreaterType());
			houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
			houseUpdateFieldAuditNewlog.setHouseFid(houseUpdateHistoryLog.getHouseFid());
			houseUpdateFieldAuditNewlog.setRoomFid(houseUpdateHistoryLog.getRoomFid());
			houseUpdateFieldAuditNewlog.setRentWay(houseUpdateHistoryLog.getRentWay());
			i = this.houseUpdateFieldAuditNewlogDao.saveHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlog);
		}else{
			houseUpdateFieldAuditNewlog.setFieldAuditStatu(0);
			i = this.houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByFid(houseUpdateFieldAuditNewlog);
		}

		return i;

	}


	/**
	 *
	 * 根据床铺fid删除床铺信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午12:16:51
	 *
	 * @return
	 */
	public int delBedByFid(String bedFid){
		return houseBedMsgDao.deleteHouseBedMsgByFid(bedFid);
	}

	/**
	 *
	 * 根据房间Fid删除房间
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午12:22:19
	 *
	 * @param roomFid
	 * @return
	 */
	public int delFRoomByFid(String roomFid){
		HouseBaseMsgEntity houseBaseMsg= houseBaseMsgDao.getHouseBaseMsgEntityByRoomFid(roomFid);
		int count = houseRoomMsgDao.deleteHouseRoomMsgByFid(roomFid);
		if(count > 0){
			houseBedMsgDao.deleteHouseBedMsgByRoomFid(roomFid);
			housePicMsgDao.deleteHousePicMsgByRoomFid(roomFid);
			houseConfMsgDao.delHouseRoomConf(houseBaseMsg.getFid(), roomFid);
			HouseBaseExtEntity houseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseMsg.getFid());
			if(!Check.NuNObj(houseBaseExt) && !Check.NuNObj(houseBaseExt.getRentRoomNum())){
				houseBaseExt.setRentRoomNum(houseBaseExt.getRentRoomNum().intValue()-count);
				houseBaseExtDao.updateHouseBaseExt(houseBaseExt);
			}
		}
		return count;
	}

	/**
	 *
	 * 保存或者更新房源描述和房屋名称
	 *
	 * @author jixd
	 * @created 2016年8月16日 上午8:53:13
	 *
	 * @return
	 */
	public int saveOrUpdateHouseDesc(HouseIssueDescDto houseDescDto) throws Exception{
		String houseFid = houseDescDto.getHouseFid();
		HouseBaseMsgEntity msgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
		if( msgEntity.getOperateSeq().intValue() < HouseIssueStepEnum.SEVEN.getCode()){
			msgEntity.setIntactRate(HouseIssueStepEnum.FOUR.getValue());
		}

		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
		List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities=null;
		Integer houseStatus = msgEntity.getHouseStatus();
		if(!Check.NuNObj(msgEntity)){
			if(RentWayEnum.ROOM.getCode()==msgEntity.getRentWay() && !Check.NuNStr(houseDescDto.getRoomFid())){
				HouseRoomMsgEntity houseRoom = houseRoomMsgDao.getHouseRoomByFid(houseDescDto.getRoomFid());
				if(!Check.NuNObj(houseRoom) && !Check.NuNObj(houseRoom.getRoomStatus())){
					houseStatus = houseRoom.getRoomStatus();
				}
			}
			if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
				msgEntity = HouseUtils.FilterAuditField(msgEntity,houseUpdateFieldAuditManagerEntities);
			}
			if(houseStatus==HouseStatusEnum.SJ.getCode()){
				houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
				msgEntity = HouseUtils.FilterAuditField(msgEntity,houseUpdateFieldAuditManagerEntities);
			}
		}
		if(houseStatus!=HouseStatusEnum.SJ.getCode()&&houseStatus!=HouseStatusEnum.ZPSHWTG.getCode()){
			msgEntity.setHouseName(houseDescDto.getHouseName());
		}
		/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

		int count = houseBaseMsgDao.updateHouseBaseMsg(msgEntity);

		HouseDescEntity houseDesc= houseDescDao.findHouseDescByHouseBaseFid(houseFid);
		if(Check.NuNObj(houseDesc)){
			HouseDescEntity houseDescEntity = new HouseDescEntity();
			houseDescEntity.setFid(UUIDGenerator.hexUUID());
			houseDescEntity.setHouseBaseFid(houseFid);
			houseDescEntity.setHouseDesc(houseDescDto.getHouseDesc());
			houseDescEntity.setHouseAroundDesc(houseDescDto.getHouseAround());
			houseDescDao.insertHouseDesc(houseDescEntity);
		}else{
			houseDesc.setHouseAroundDesc(houseDescDto.getHouseAround());
			houseDesc.setHouseDesc(houseDescDto.getHouseDesc());

			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/
			if(!Check.NuNObj(msgEntity)){
				if(houseStatus==HouseStatusEnum.ZPSHWTG.getCode()){
					houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
					houseDesc = HouseUtils.FilterAuditField(houseDesc,houseUpdateFieldAuditManagerEntities);
				}
				if(houseStatus==HouseStatusEnum.SJ.getCode()){
					houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(1);
					houseDesc = HouseUtils.FilterAuditField(houseDesc,houseUpdateFieldAuditManagerEntities);
				}
			}
			/*审核未通过和上架房源的需要审核字段的修改暂不入库，后台人员审核后统一入库，@Author:lusp  @Date:2017/8/7*/

			houseDescDao.updateHouseDesc(houseDesc);
		}
		return count;
	}

	/**
	 *
	 * 根据房源fid 和图片类型 查询房源图片
	 *
	 * @author jixd
	 * @created 2016年8月17日 下午12:13:17
	 *
	 * @return
	 */
	public List<HousePicMsgEntity> findHousePicByTypeAndFid(HousePicTypeDto housePicType){
		return housePicMsgDao.findHousePicByTypeAndFid(housePicType);
	}

	/**
	 *
	 * 保存房源图片 根据图片类型， 或者房间
	 *
	 * @author jixd
	 * @created 2016年8月18日 上午11:03:36
	 *
	 * @return
	 */
	public Map<String, Object> saveHousePicByType(String json){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		JSONObject object = JSONObject.parseObject(json);
		JSONArray imgList = object.getJSONArray("list");
		String houseFid = object.getString("housefid");
		String roomFid = object.getString("roomfid");
		Integer picType = object.getInteger("picType");

		HouseBaseExtDto houseBaseExtDto = houseBaseMsgDao.findHouseBaseExtDtoByHouseBaseFid(houseFid);

		if(Check.NuNObj(houseBaseExtDto)){
			LogUtil.error(LOGGER, "saveHousePicByType(),该房源数据异常，houseFid = {}", houseFid);
			resultMap.put("count", 0);
			return resultMap;
		}

		//统计修改数量
		int count = 0;
		//是否本次保存有默认图片标示
		List<String> picFidList = new ArrayList<>();
		//遍历图片集合 某一类型图片
		for(int i = 0;i<imgList.size();i++){
			JSONObject obj = imgList.getJSONObject(i);
			String baseUrl = obj.getString("picbaseurl");
			String picFid = obj.getString("picfid");
			String picUUID = obj.getString("picserveruuid");
			Integer degree = obj.getInteger("degrees");
			Integer isdefault = obj.getInteger("isdefault");
			String picsuffix = obj.getString("picsuffix");
			Integer auditStatus=obj.getInteger("auditStatus");
			//先处理 图片保存逻辑 ，然后处理设置默认图片逻辑
			if(!Check.NuNStr(picFid)){
				//如果旋转角度为0,则不需要修改
				HousePicMsgEntity msgEntity = new HousePicMsgEntity();
				msgEntity.setFid(picFid);
				msgEntity.setPicSort(i);
				if(degree > 0){
					msgEntity.setPicServerUuid(picUUID);
					msgEntity.setPicBaseUrl(baseUrl);
				}
				count += housePicMsgDao.updateHousePicMsg(msgEntity);
			}else{
				picFid = UUIDGenerator.hexUUID();
				HousePicMsgEntity msgEntity = new HousePicMsgEntity();
				msgEntity.setFid(picFid);
				msgEntity.setPicBaseUrl(baseUrl);
				msgEntity.setPicServerUuid(picUUID);
				if(!Check.NuNStr(roomFid)){
					msgEntity.setRoomFid(roomFid);
				}
				msgEntity.setPicName(RandomUtil.genRandomNum(3)+picsuffix);
				msgEntity.setHouseBaseFid(houseFid);
				msgEntity.setPicType(picType);
				msgEntity.setOperateType(0);
				msgEntity.setPicSuffix(picsuffix);
				//图片排序
				msgEntity.setPicSort(i);
				msgEntity.setAuditStatus(auditStatus);
				count += housePicMsgDao.insertHousePicMsg(msgEntity);
			}

			//存储本次图片fid,用于设置默认图片使用
			picFidList.add(picFid);

			//前端设置默认图片 并且 图片类型不是厨房 卫生间 则进行设置默认图片操作
			if(isdefault == 1 && (picType != HousePicTypeEnum.CF.getCode() || picType != HousePicTypeEnum.WSJ.getCode())){
				resultMap.put("defaultPicFid", picFid);
				//整租设置默认图片
				if(houseBaseExtDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
					HouseBaseExtEntity houseBaseExt = houseBaseExtDto.getHouseBaseExt();
					if(!picFid.equals(houseBaseExt.getDefaultPicFid())){
						//如果房源状态不等于上架更新默认照片入库
						if(houseBaseExtDto.getHouseStatus() != HouseStatusEnum.SJ.getCode()){
							houseBaseExt.setDefaultPicFid(picFid);
							count += houseBaseExtDao.updateHouseBaseExt(houseBaseExt);
						}
					}
				}
				//分租设置默认图片
				if(houseBaseExtDto.getRentWay() == RentWayEnum.ROOM.getCode()){
					//如果是其他类型保存，没有roomFid直接跳过
					if(Check.NuNStr(roomFid)){
						continue;
					}
					HouseRoomMsgEntity roomMsgEntity = houseRoomMsgDao.findHouseRoomMsgByFid(roomFid);
					String defaultPicFid = roomMsgEntity.getDefaultPicFid();
					if(!picFid.equals(defaultPicFid)){
						if(picType == HousePicTypeEnum.WS.getCode()){
							//如果房源状态不等于上架更新默认照片入库
							if(roomMsgEntity.getRoomStatus() != HouseStatusEnum.SJ.getCode()){
								roomMsgEntity.setDefaultPicFid(picFid);
								count += houseRoomMsgDao.updateHouseRoomMsg(roomMsgEntity);
							}
						}
					}
				}
			}

		}

		/**
		 * 1.判断是否本次保存 已设置默认图片
		 * 2.本地保存图片类型是否是厨房 卫生间（如果是则不做默认图片设置）
		 * 3.如果整租 或者 合租房源没有默认图片 则取本次保存图片的第一张设置默认图片
		 */
		/*if(hasDefault == 0 && (picType != HousePicTypeEnum.CF.getCode() || picType != HousePicTypeEnum.WSJ.getCode())){
			if(houseBaseExtDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
				HouseBaseExtEntity houseBaseExt = houseBaseExtDto.getHouseBaseExt();
				if(houseBaseExt.getDefaultPicFid() == null){
					if(!Check.NuNCollection(picFidList)){
						houseBaseExt.setDefaultPicFid(picFidList.get(0));
						count += houseBaseExtDao.updateHouseBaseExt(houseBaseExt);
					}
				}

			}
			if(houseBaseExtDto.getRentWay() == RentWayEnum.ROOM.getCode()){
				//如果是其他类型保存，没有roomFid直接跳过
				if(!Check.NuNStr(roomFid)){
					HouseRoomMsgEntity roomMsgEntity = houseRoomMsgDao.findHouseRoomMsgByFid(roomFid);
					if(roomMsgEntity.getDefaultPicFid() == null){
						if(!Check.NuNCollection(picFidList)){
							roomMsgEntity.setDefaultPicFid(picFidList.get(0));
							count += houseRoomMsgDao.updateHouseRoomMsg(roomMsgEntity);
						}
					}
				}
			}

		}*/
		resultMap.put("count", count);
		return resultMap;
	}


	/**
	 *
	 * 获取房源 或者房间基本信息
	 *
	 * @author jixd
	 * @created 2016年8月18日 下午10:15:20
	 *
	 * @return
	 */
	public List<HouseRoomBaseMsg> getHouseRoomBaseMsgList(String houseFid){
		HouseBaseExtDto houseBaseExtDto = houseBaseMsgDao.findHouseBaseExtDtoByHouseBaseFid(houseFid);
		List<HouseRoomBaseMsg> list = new ArrayList<>();
		if(houseBaseExtDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
			HouseRoomBaseMsg baseMsg = new HouseRoomBaseMsg();
			baseMsg.setHouseFid(houseBaseExtDto.getFid());
			baseMsg.setName(houseBaseExtDto.getHouseName());
			baseMsg.setRentWay(RentWayEnum.HOUSE.getCode());
			baseMsg.setCheckInLimit(houseBaseExtDto.getHouseBaseExt().getCheckInLimit());
			baseMsg.setStatus(houseBaseExtDto.getHouseStatus());
			String defaultFid = houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
			if(!Check.NuNStr(defaultFid)){
				HousePicMsgEntity picEntity = housePicMsgDao.findHousePicMsgByFid(defaultFid);
				baseMsg.setPicMsgEntity(picEntity);
			}
			list.add(baseMsg);
		}
		if(houseBaseExtDto.getRentWay() == RentWayEnum.ROOM.getCode()){
			List<HouseRoomMsgEntity> rooms = houseRoomMsgDao.findRoomListByHouseBaseFid(houseFid);
			for(HouseRoomMsgEntity room : rooms){
				HouseRoomBaseMsg baseMsg = new HouseRoomBaseMsg();
				baseMsg.setCheckInLimit(room.getCheckInLimit());
				baseMsg.setHouseFid(houseBaseExtDto.getFid());
				baseMsg.setRoomFid(room.getFid());
				baseMsg.setRentWay(RentWayEnum.ROOM.getCode());
				baseMsg.setName(room.getRoomName());
				baseMsg.setStatus(room.getRoomStatus());
				String defaultFid = room.getDefaultPicFid();
				if(!Check.NuNStr(defaultFid)){
					HousePicMsgEntity picEntity = housePicMsgDao.findHousePicMsgByFid(defaultFid);
					baseMsg.setPicMsgEntity(picEntity);
				}

				list.add(baseMsg);
			}

		}

		return list;
	}

	/**
	 *
	 * 设置房间默认图片
	 *
	 * @author jixd
	 * @created 2016年8月24日 下午8:21:45
	 *
	 * @return
	 */
	public int setRoomDefaultPic(RoomDefaultPicDto picParam){
		HouseRoomMsgEntity roomEntity = houseRoomMsgDao.findHouseRoomMsgByFid(picParam.getRoomFid());
		String defaultFid = roomEntity.getDefaultPicFid();
		String picFid = picParam.getPicFid();
		//如果需要设置的默认图片与当前默认图片相同 直接忽略
		if(!picFid.equals(defaultFid)){
			roomEntity.setDefaultPicFid(picFid);
			if(roomEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
				//如果上架状态需要判断图片的审核状态
				HousePicMsgEntity picEntity = housePicMsgDao.findHousePicByFid(picFid);
				if(picEntity.getAuditStatus() == 0){
					roomEntity.setOldDefaultPicFid(defaultFid);
				}
			}
			return houseRoomMsgDao.updateHouseRoomMsg(roomEntity);
		}
		return 0;
	}

	/**
	 *
	 * 1.更新分租房间状态，级联更新床铺状态
	 * 2.如果是发布房间的话判断 房源状态是否未已发布，如果是未发布，则更新房源状态，并设置房源补充信息默认值
	 * 3.记录日志
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:56:04
	 *
	 * @return
	 */
	public int updateRoomsStatusF(String houseFid,List<String> roomfids,Integer status){
		int count = 0;
		HouseBaseMsgEntity houseBaseMsg = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
		houseBaseMsg.setOperateSeq(HouseIssueStepEnum.SEVEN.getCode());
		houseBaseMsg.setIntactRate(HouseIssueStepEnum.SEVEN.getValue());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
		if(houseBaseMsg.getHouseStatus() == HouseStatusEnum.DFB.getCode() && status == HouseStatusEnum.YFB.getCode()){
			//如果房源为待发布状态，并且需要更新为已发布状态需要更新房源
			houseBaseMsg.setHouseStatus(status);
			//房源发布成功 设置房源默认值
			if(houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg) > 0){
				count ++;
				//房源默认值设置
				saveDefaultHouseBaseExt(houseFid);
				//保存默认优惠规则
				saveDefaultDiscountRules(houseFid);
				//保存默认押金规则
				saveDefaultcheckOutRule(houseFid);
			}

		}
		//如果房间fid不为空 则开始更新房间状态 及床铺状态
		if(!Check.NuNCollection(roomfids)){
			for (String roomFid : roomfids) {
				HouseRoomMsgEntity houseRoomMsg = houseRoomMsgDao.findHouseRoomMsgByFid(roomFid);
				if(Check.NuNObj(houseRoomMsg)){
					continue;
				}
				//房间默认配置
				saveDefaultRoomExt(roomFid);
				//先保存日志
				houseCommonLogicDao.saveRoomOperateLogByLandlord(houseRoomMsg, status, houseBaseMsg.getLandlordUid());
				//级联床位状态
				count += houseCommonLogicDao.cascadingRoomStatus(houseRoomMsg, status);
				//更新房间状态
				houseRoomMsg.setRoomStatus(status);
				count += houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
				//更新新增图片状态为未审核
				Map<String, Object> paramMap=new HashMap<String,Object>();
				paramMap.put("houseBaseFid", houseRoomMsg.getHouseBaseFid());
				paramMap.put("roomFid", houseRoomMsg.getFid());
				housePicMsgDao.updatePicAuditStatusToNo(paramMap);
			}
		}

		return count;
	}

	/**
	 *
	 * 保存默认房源基础信息扩展
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:55:42
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultHouseBaseExt(String houseBaseFid) {

		HouseBaseExtEntity houseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(houseBaseFid);
		//HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
		houseBaseExt.setHouseBaseFid(houseBaseFid);

		/** 交易信息 **/
		// 下单类型-申请预订
		if(Check.NuNObj(houseBaseExt.getOrderType())){
			houseBaseExt.setOrderType(HouseConstant.DEFAULT_ORDER_TYPE);
		}
		if(Check.NuNObj(houseBaseExt.getHomestayType())){
			// 民宿类型
			houseBaseExt.setHomestayType(HouseConstant.DEFAULT_HOMESTAY_TYPE);
		}
		if(Check.NuNStr(houseBaseExt.getDiscountRulesCode())){
			// 优惠规则
			houseBaseExt.setDiscountRulesCode(ProductRulesEnum.ProductRulesEnum0012.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getDepositRulesCode())){
			// 押金规则
			houseBaseExt.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getCheckOutRulesCode())){
			// 退订政策
			houseBaseExt.setCheckOutRulesCode(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
		}
		/** 交易信息 **/

		/** 入住信息 **/
		if(Check.NuNObj(houseBaseExt.getMinDay())){
			// 最小入住天数
			houseBaseExt.setMinDay(HouseConstant.DEFAULT_MIN_DAY);
		}
		if(Check.NuNStr(houseBaseExt.getCheckInTime()) || houseBaseExt.getCheckInTime().equals("0")){
			// 入住时间
			houseBaseExt.setCheckInTime(HouseConstant.DEFAULT_CHECKIN_TIME);
		}
		if(Check.NuNStr(houseBaseExt.getCheckOutTime()) || houseBaseExt.getCheckOutTime().equals("0")){
			// 退房时间
			houseBaseExt.setCheckOutTime(HouseConstant.DEFAULT_CHECKOUT_TIME);
		}
		/** 入住信息 **/

		/** 配套设施与服务项 **/
		if(Check.NuNStr(houseBaseExt.getFacilityCode())){
			// 配套设施
			houseBaseExt.setFacilityCode(ProductRulesEnum.ProductRulesEnum002.getValue());
		}
		if(Check.NuNStr(houseBaseExt.getServiceCode())){
			// 服务
			houseBaseExt.setServiceCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
		}
		/** 配套设施与服务项 **/
		houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
	}
	
	/**
	 * 
	 * 
	 *
	 * @author bushujie
	 * @created 2017年9月11日 下午3:49:10
	 *
	 * @param roomFid
	 */
	private void saveDefaultRoomExt(String roomFid){
		HouseRoomExtEntity houseRoomExtEntity=houseRoomExtDao.getByRoomfid(roomFid);
		if(Check.NuNObj(houseRoomExtEntity)){
			houseRoomExtEntity=new HouseRoomExtEntity();
			houseRoomExtEntity.setFid(UUIDGenerator.hexUUID());
			houseRoomExtEntity.setRoomFid(roomFid);
			// 下单类型-申请预订
			if(Check.NuNObj(houseRoomExtEntity.getOrderType())){
				houseRoomExtEntity.setOrderType(HouseConstant.DEFAULT_ORDER_TYPE);
			}
			if(Check.NuNStr(houseRoomExtEntity.getCheckOutRulesCode())){
				// 退订政策
				houseRoomExtEntity.setCheckOutRulesCode(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
			}
			if(Check.NuNStr(houseRoomExtEntity.getDepositRulesCode())){
				// 押金规则
				houseRoomExtEntity.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
			}
			if(Check.NuNObj(houseRoomExtEntity.getMinDay())){
				// 最小入住天数
				houseRoomExtEntity.setMinDay(HouseConstant.DEFAULT_MIN_DAY);
			}
			if(Check.NuNStr(houseRoomExtEntity.getCheckInTime()) || houseRoomExtEntity.getCheckInTime().equals("0")){
				// 入住时间
				houseRoomExtEntity.setCheckInTime(HouseConstant.DEFAULT_CHECKIN_TIME);
			}
			if(Check.NuNStr(houseRoomExtEntity.getCheckOutTime()) || houseRoomExtEntity.getCheckOutTime().equals("0")){
				// 退房时间
				houseRoomExtEntity.setCheckOutTime(HouseConstant.DEFAULT_CHECKOUT_TIME);
			}
			houseRoomExtDao.insertHouseRoomExtSelective(houseRoomExtEntity);
		}
	}


	/**
	 *
	 * 保存默认优惠规则配置
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:55:20
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultDiscountRules(String houseBaseFid) {
		List<HouseConfMsgEntity> houseConfList = houseConfMsgDao.findHouseConfList(houseBaseFid);
		boolean threeflag = false;
		boolean sevenflag = false;
		boolean thirtyflag = false;
		for(HouseConfMsgEntity houseConfMsg : houseConfList){
			String dicCode = houseConfMsg.getDicCode();
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue().equals(dicCode)){
				threeflag = true;
			}
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue().equals(dicCode)){
				sevenflag = true;
			}
			if(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue().equals(dicCode)){
				thirtyflag = true;
			}
		}
		if(!threeflag){
			// 3天折扣率
			HouseConfMsgEntity threeDaysDiscount = new HouseConfMsgEntity();
			threeDaysDiscount.setFid(UUIDGenerator.hexUUID());
			threeDaysDiscount.setHouseBaseFid(houseBaseFid);
			threeDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue());
			threeDaysDiscount.setDicVal(HouseConstant.DEFAULT_THREE_DAYS_DISCOUNT);
			threeDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(threeDaysDiscount);
		}
		if(!sevenflag){
			// 7天优折扣率
			HouseConfMsgEntity sevenDaysDiscount = new HouseConfMsgEntity();
			sevenDaysDiscount.setFid(UUIDGenerator.hexUUID());
			sevenDaysDiscount.setHouseBaseFid(houseBaseFid);
			sevenDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue());
			sevenDaysDiscount.setDicVal(HouseConstant.DEFAULT_SEVEN_DAYS_DISCOUNT);
			sevenDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(sevenDaysDiscount);
		}
		if(!thirtyflag){
			// 30天优折扣率
			HouseConfMsgEntity thirtyDaysDiscount = new HouseConfMsgEntity();
			thirtyDaysDiscount.setFid(UUIDGenerator.hexUUID());
			thirtyDaysDiscount.setHouseBaseFid(houseBaseFid);
			thirtyDaysDiscount.setDicCode(ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue());
			thirtyDaysDiscount.setDicVal(HouseConstant.DEFAULT_THIRTY_DAYS_DISCOUNT);
			thirtyDaysDiscount.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(thirtyDaysDiscount);
		}

	}

	/**
	 *
	 * 保存默认押金规则
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:54:37
	 *
	 * @param houseBaseFid
	 */
	private void saveDefaultcheckOutRule(String houseBaseFid) {
		List<HouseConfMsgEntity> houseConfMsgList = houseConfMsgDao.findHouseConfList(houseBaseFid);
		boolean checkOutFlag = false;
		for(HouseConfMsgEntity houseConfMsg : houseConfMsgList){
			if(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue().equals(houseConfMsg.getDicCode())){
				checkOutFlag = true;
			}
		}
		//如果没有插入默认呢押金规则
		if(!checkOutFlag){
			HouseConfMsgEntity checkOutRules = new HouseConfMsgEntity();
			checkOutRules.setFid(UUIDGenerator.hexUUID());
			checkOutRules.setHouseBaseFid(houseBaseFid);
			checkOutRules.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008001.getValue());
			checkOutRules.setDicVal(HouseConstant.DEFAULT_DEPOSIT_BY_RENT);
			checkOutRules.setCreateDate(new Date());
			houseConfMsgDao.insertHouseConfMsg(checkOutRules);
		}
	}

	/**
	 *
	 * 更新房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月30日 上午10:17:35
	 *
	 * @param list
	 */
	public void updateHouseConf(List<HouseConfMsgEntity> list) {
		//删除配套设施
		houseConfMsgDao.delHouseConfByCode(list.get(0).getHouseBaseFid(), ProductRulesEnum.ProductRulesEnum002.getValue());
		//删除服务
		houseConfMsgDao.delHouseConfByCode(list.get(0).getHouseBaseFid(), ProductRulesEnum.ProductRulesEnum0015.getValue());
		for(HouseConfMsgEntity houseConfMsgEntity:list){
			houseConfMsgEntity.setFid(UUIDGenerator.hexUUID());
			houseConfMsgDao.insertHouseConfMsg(houseConfMsgEntity);
		}
		HouseBaseMsgEntity houseBaseMsg=houseBaseMsgDao.getHouseBaseMsgEntityByFid(list.get(0).getHouseBaseFid());
		/*if(HouseIssueStepEnum.THREE.getCode() - houseBaseMsg.getOperateSeq() == 1){
    		houseBaseMsg.setOperateSeq(HouseIssueStepEnum.THREE.getCode());
    		houseBaseMsg.setIntactRate(HouseIssueStepEnum.THREE.getValue());
    		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
    	}*/
		if(houseBaseMsg.getOperateSeq() < HouseIssueStepEnum.SEVEN.getCode()){
			houseBaseMsg.setIntactRate(HouseIssueStepEnum.THREE.getValue());
		}

		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsg);
	}

	/**
	 *
	 * 获取房源图片信息
	 *
	 * @author jixd
	 * @created 2016年9月8日 下午6:22:07
	 *
	 * @param picFid
	 * @return
	 */
	public HousePicMsgEntity findHousePicMsgEntityByFid(String picFid){
		return housePicMsgDao.findHousePicByFid(picFid);
	}
}
