package com.ziroom.minsu.services.house.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.house.api.inner.HouseIssuePcService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HousePhyPcDto;
import com.ziroom.minsu.services.house.dto.HouseRoomListPcDto;
import com.ziroom.minsu.services.house.dto.HouseRoomsWithBedsPcDto;
import com.ziroom.minsu.services.house.dto.RoomHasBeds;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.pc.dto.HouseIssueDescDto;
import com.ziroom.minsu.services.house.pc.dto.HousePicDelDto;
import com.ziroom.minsu.services.house.pc.dto.HousePicTypeDto;
import com.ziroom.minsu.services.house.pc.dto.HouseRoomBaseMsg;
import com.ziroom.minsu.services.house.pc.dto.RoomDefaultPicDto;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseIssueServicePcImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>pc端发布房源相关接口</p>
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
@Component("house.houseIssueServicePcProxy")
public class HouseIssueServicePcProxy implements HouseIssuePcService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseIssueServicePcProxy.class);
	
	@Resource(name="house.HouseIssueServicePcImpl")
	private HouseIssueServicePcImpl houseIssueServicePcImpl;
	
	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name = "house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;
	
	@Resource(name="house.queueName")
	private QueueName queueName ;
	
	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;
	
	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;
	
	@Autowired
	private RedisOperations redisOperations;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#insertOrUpdateHouseLocation(java.lang.String)
	 */
	@Override
	public String insertOrUpdateHouseLocation(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			HousePhyPcDto housePhyPcDto=JsonEntityTransform.json2Object(paramJson, HousePhyPcDto.class);
			HouseBaseMsgEntity houseBaseMsg=houseIssueServicePcImpl.insertOrUpdateHouseLocation(housePhyPcDto);
			dto.putValue("houseBaseFid", houseBaseMsg.getFid());
			dto.putValue("housePhyFid", houseBaseMsg.getPhyFid());
			//mq推送
			if(!Check.NuNObj(houseBaseMsg)){
				updateHouseBaseMq(houseBaseMsg);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#findHouseConfigByPcode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findHouseConfigByPcode(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson); 
		List<HouseConfVo> confList=houseIssueServicePcImpl.findHouseConfVoList(paramMap);
		dto.putValue("confList", confList);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#findHouseRoomList(java.lang.String)
	 */
	@Override
	public String findHouseRoomList(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		HouseRoomListPcDto houseRoom=houseIssueServicePcImpl.findHouseRoomList(paramJson);
		dto.putValue("houseRoom", houseRoom);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 保存或更新房间信息
	 * 
	 * 如果是修改： 增加修改日志
	 *
	 * @author jixd
	 * @created 2016年8月15日 上午10:26:57
	 *
	 * @param param
	 * @return
	 */
	@Override
	public String saveOrUpHouseFRooms(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			JSONObject houseObj = JSONObject.parseObject(param);
			JSONArray roomsArray = houseObj.getJSONArray("rooms");
			String houseFid = houseObj.getString("houseFid");
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源Fid为空");
				return dto.toJsonString();
			}
			HouseBaseMsgEntity houseBaseEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseFid);
			if(Check.NuNObj(houseBaseEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}
			if(Check.NuNObj(roomsArray)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间信息为空");
				return dto.toJsonString();
			}
			if(houseObj.getInteger("roomNum") < roomsArray.size()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("户型数小于房间数量");
				return dto.toJsonString();
			}
			houseBaseEntity.setFid(houseFid);
			houseBaseEntity.setRoomNum(houseObj.getInteger("roomNum"));
			houseBaseEntity.setHallNum(houseObj.getInteger("hallNum"));
			houseBaseEntity.setToiletNum(houseObj.getInteger("toiletNum"));
			houseBaseEntity.setKitchenNum(houseObj.getInteger("kitchenNum"));
			houseBaseEntity.setBalconyNum(houseObj.getInteger("balconyNum"));

			List<RoomHasBeds> roomsList = new ArrayList<>();
			for(int i = 0;i<roomsArray.size();i++){
				RoomHasBeds roomHasBeds = new RoomHasBeds();
				JSONObject room = (JSONObject) roomsArray.get(i);
				HouseRoomMsgEntity roomEntity = new HouseRoomMsgEntity();
				String roomFid = room.getString("roomFid");
				roomEntity.setFid(room.getString("roomFid"));
				roomEntity.setHouseBaseFid(houseFid);
				roomEntity.setIsToilet(room.getInteger("isToilet"));
				roomEntity.setRoomName(room.getString("roomName"));
				roomEntity.setRoomArea(room.getDouble("roomArea"));
				roomEntity.setBedNum(room.getInteger("bedNum"));
				roomEntity.setRoomPrice(room.getInteger("roomPrice") * 100);
				roomEntity.setCheckInLimit(room.getInteger("checkInLimit"));
				roomEntity.setRoomCleaningFees(room.getInteger("cleanFee") * 100);
				roomEntity.setCreateUid(houseBaseEntity.getLandlordUid());

				JSONArray bedsArray = room.getJSONArray("beds");
				List<HouseBedMsgEntity> bedsList = new ArrayList<>();
				for(int j = 0;j<bedsArray.size();j++){
					JSONObject bed = (JSONObject) bedsArray.get(j);
					HouseBedMsgEntity bedEntity = new HouseBedMsgEntity();
					bedEntity.setFid(bed.getString("bedFid"));
				/*bedEntity.setBedSize(bed.getInteger("bedSize"));*/
					bedEntity.setBedType(bed.getInteger("bedType"));
					bedEntity.setRoomFid(roomFid);
					bedEntity.setHouseBaseFid(houseFid);
					bedsList.add(bedEntity);
				}
				roomHasBeds.setRoomMsg(roomEntity);
				roomHasBeds.setBeds(bedsList);

				roomsList.add(roomHasBeds);

			}
			int upCount = houseIssueServicePcImpl.saveOrUpdateRoomsMsg(houseBaseEntity, roomsList,null);
			dto.putValue("count", upCount);
			//推送mq
			updateHouseBaseMq(houseBaseEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#delFRoom(java.lang.String)
	 */
	@Override
	public String delFRoomByFid(String roomFid) {
		DataTransferObject dto = new DataTransferObject();
		HouseRoomMsgEntity roomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(roomFid);
		if(Check.NuNObj(roomMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间不存在");
			return dto.toJsonString();
		}
		if(roomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("上架状态不能删除");
		}
		
		int count = houseIssueServicePcImpl.delFRoomByFid(roomFid);
		dto.putValue("count", count);
		//推送mq
		HouseBaseMsgEntity houseBaseMsg=houseIssueServiceImpl.findHouseBaseMsgByFid(roomMsgEntity.getHouseBaseFid());
		updateHouseBaseMq(houseBaseMsg);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#delBed(java.lang.String)
	 */
	@Override
	public String delBedByFid(String bedFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(bedFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间FId为空");
			return dto.toJsonString();
		}
		HouseBedMsgEntity houseBedMsgEntity=houseIssueServiceImpl.findHouseBedMsgByFid(bedFid);
		if(Check.NuNObj(houseBedMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间不存在");
			return dto.toJsonString();
		}
		int count = houseIssueServicePcImpl.delBedByFid(bedFid);
		dto.putValue("count", count);
		//推送mq
		HouseBaseMsgEntity houseBaseMsg=houseIssueServiceImpl.findHouseBaseMsgByFid(houseBedMsgEntity.getHouseBaseFid());
		if(!Check.NuNObj(houseBaseMsg)){
			updateHouseBaseMq(houseBaseMsg);
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#findHouseRoomWithBedsList(java.lang.String)
	 */
	@Override
	public String findHouseRoomWithBedsList(String houseFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		HouseRoomsWithBedsPcDto roomList = houseIssueServicePcImpl.findHouseRoomWithBedsList(houseFid,dto);
		dto.putValue("roomList", roomList);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#saveHouseDesc(java.lang.String)
	 */
	@Override
	public String saveOrUpdateHouseDesc(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(param)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			HouseIssueDescDto descParam = JsonEntityTransform.json2Object(param, HouseIssueDescDto.class);
			if(Check.NuNStr(descParam.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源Fid为空");
				return dto.toJsonString();
			}
			if(Check.NuNStr(descParam.getHouseName())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源名称为空");
				return dto.toJsonString();
			}
			if(Check.NuNStr(descParam.getHouseDesc())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源描述为空");
				return dto.toJsonString();
			}
			if(Check.NuNStr(descParam.getHouseAround())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周边情况为空");
				return dto.toJsonString();
			}
			String houseFid = descParam.getHouseFid();
			HouseBaseMsgEntity msgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseFid);
			if(Check.NuNObj(msgEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}
			int count = houseIssueServicePcImpl.saveOrUpdateHouseDesc(descParam);
			dto.putValue("count", count);
			//推送mq
			updateHouseBaseMq(msgEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#delZRoomByFid(java.lang.String)
	 */
	@Override
	public String delZRoomByFid(String roomFid) {
		DataTransferObject dto = new DataTransferObject();
		HouseRoomMsgEntity roomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(roomFid);
		if(Check.NuNObj(roomMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间不存在");
			return dto.toJsonString();
		}
		int count = houseIssueServicePcImpl.delFRoomByFid(roomFid);
		dto.putValue("count", count);
		HouseBaseMsgEntity houseBaseMsg=houseIssueServiceImpl.findHouseBaseMsgByFid(roomMsgEntity.getHouseBaseFid());
		//mq推送
		if(!Check.NuNObj(houseBaseMsg)){
			updateHouseBaseMq(houseBaseMsg);
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#findHouseBaseAndDesc(java.lang.String)
	 */
	@Override
	public String findHouseBaseAndDesc(String houseFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		HouseBaseMsgEntity houseBaseMsgEntity = houseIssueServiceImpl.findHouseBaseMsgByFid(houseFid);
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		HouseIssueDescDto descDto = new HouseIssueDescDto();
		HouseDescEntity houseDescEntity = houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseFid);
		if(!Check.NuNObj(houseDescEntity)){
			descDto.setHouseDesc(houseDescEntity.getHouseDesc());
			descDto.setHouseAround(houseDescEntity.getHouseAroundDesc());
		}
		descDto.setHouseName(houseBaseMsgEntity.getHouseName());
		descDto.setHouseFid(houseFid);
		dto.putValue("desc", descDto);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#findHousePicByTypeAndFid(java.lang.String)
	 */
	@Override
	public String findHousePicByTypeAndFid(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		HousePicTypeDto housePicTypeDto = JSONObject.parseObject(param, HousePicTypeDto.class);
		List<HousePicMsgEntity> housePicList = houseIssueServicePcImpl.findHousePicByTypeAndFid(housePicTypeDto);
		dto.putValue("picList", housePicList);
		return dto.toJsonString();
	}
	
	
	/**
	 * 
	 */
	@Override
	public String saveOrUpdateZroom(String param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			JSONObject houseObj = JSONObject.parseObject(param);
			JSONArray roomsArray = houseObj.getJSONArray("rooms");
			String houseFid = houseObj.getString("houseFid");
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源Fid为空");
				return dto.toJsonString();
			}
			HouseBaseMsgEntity houseBaseEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseFid);
			if(Check.NuNObj(houseBaseEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}

			if(Check.NuNObj(roomsArray)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间信息为空");
				return dto.toJsonString();
			}
			if(houseObj.getInteger("roomNum") != roomsArray.size()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("户型与房间数量不一致");
				return dto.toJsonString();
			}
			HouseBaseMsgEntity newHouseBaseMsgEntity=new HouseBaseMsgEntity();
			newHouseBaseMsgEntity.setFid(houseFid);
			newHouseBaseMsgEntity.setRoomNum(houseObj.getInteger("roomNum"));
			newHouseBaseMsgEntity.setHallNum(houseObj.getInteger("hallNum"));
			newHouseBaseMsgEntity.setToiletNum(houseObj.getInteger("toiletNum"));
			newHouseBaseMsgEntity.setKitchenNum(houseObj.getInteger("kitchenNum"));
			newHouseBaseMsgEntity.setBalconyNum(houseObj.getInteger("balconyNum"));
			newHouseBaseMsgEntity.setOperateSeq(houseBaseEntity.getOperateSeq());

			List<RoomHasBeds> roomsList = new ArrayList<>();
			for(int i = 0;i<roomsArray.size();i++){
				RoomHasBeds roomHasBeds = new RoomHasBeds();
				JSONObject room = (JSONObject) roomsArray.get(i);
				HouseRoomMsgEntity roomEntity = new HouseRoomMsgEntity();
				String roomFid = room.getString("roomFid");
				roomEntity.setFid(room.getString("roomFid"));
				roomEntity.setHouseBaseFid(houseFid);
				roomEntity.setBedNum(room.getInteger("bedNum"));
				roomEntity.setRoomPrice(0);
				roomEntity.setCreateUid(houseBaseEntity.getLandlordUid());

				JSONArray bedsArray = room.getJSONArray("beds");
				List<HouseBedMsgEntity> bedsList = new ArrayList<>();
				for(int j = 0;j<bedsArray.size();j++){
					JSONObject bed = (JSONObject) bedsArray.get(j);
					HouseBedMsgEntity bedEntity = new HouseBedMsgEntity();
					bedEntity.setFid(bed.getString("bedFid"));
				/*bedEntity.setBedSize(bed.getInteger("bedSize"));*/
					bedEntity.setBedType(bed.getInteger("bedType"));
					bedEntity.setRoomFid(roomFid);
					bedEntity.setHouseBaseFid(houseFid);
					bedsList.add(bedEntity);
				}
				roomHasBeds.setRoomMsg(roomEntity);
				roomHasBeds.setBeds(bedsList);
				roomsList.add(roomHasBeds);
			}
			int upCount = houseIssueServicePcImpl.saveOrUpdateRoomsMsg(newHouseBaseMsgEntity, roomsList,houseBaseEntity.getHouseStatus());
			dto.putValue("count", upCount);
			//mq推送
			updateHouseBaseMq(houseBaseEntity);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		

		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#getHouseDefaultPicList(java.lang.String)
	 */
	@Override
	public String getHouseDefaultPicList(String houseFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		
		HouseBaseExtDto houseBaseExtDto = houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(houseFid);
		if(Check.NuNObj(houseBaseExtDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源不存在");
			return dto.toJsonString();
		}
		List<String> list = new ArrayList<>();
		
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog=new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlog.setHouseFid(houseFid);
		houseUpdateFieldAuditNewlog.setRentWay(houseBaseExtDto.getRentWay());
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(0);
		
		if(houseBaseExtDto.getRentWay() == RentWayEnum.HOUSE.getCode()){
			List<HouseFieldAuditLogVo> auditFiledList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlog);
			Map<String , HouseFieldAuditLogVo> resultMap=new HashMap<String,HouseFieldAuditLogVo>();
			for(HouseFieldAuditLogVo vo:auditFiledList){
				resultMap.put(vo.getFieldPath(), vo);
			}
			HouseBaseExtEntity houseBaseExt = houseBaseExtDto.getHouseBaseExt();
			//如果有未审核默认图片，替换
			if(resultMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())){
				list.add(resultMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
			} else {
				if(!Check.NuNObj(houseBaseExt)){
					list.add(houseBaseExt.getDefaultPicFid());
				}
			}
		}
		
		if(houseBaseExtDto.getRentWay() == RentWayEnum.ROOM.getCode()){
			List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseFid);
			if(!Check.NuNCollection(roomList)){
				for(HouseRoomMsgEntity roomMsg : roomList){
					houseUpdateFieldAuditNewlog.setRoomFid(roomMsg.getFid());
					List<HouseFieldAuditLogVo> auditFiledList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlog);
					Map<String , HouseFieldAuditLogVo> resultMap=new HashMap<String,HouseFieldAuditLogVo>();
					for(HouseFieldAuditLogVo vo:auditFiledList){
						resultMap.put(vo.getFieldPath(), vo);
					}
					//如果有未审核默认图片，替换
					if(resultMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())){
						list.add(resultMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
					}else {
						list.add(roomMsg.getDefaultPicFid());
					}
				}
			}
		}
		
		dto.putValue("list", list);
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#getHouseDefaultPicListNew(java.lang.String)
	 */
	@Override
	public String getHouseDefaultPicListNew(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#saveHousePic(java.lang.String)
	 */
	@Override
	public String saveHousePicByType(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		Map<String, Object> resultMap = houseIssueServicePcImpl.saveHousePicByType(param);
		dto.putValue("count", resultMap.get("count"));
		dto.putValue("defaultPicFid", resultMap.get("defaultPicFid"));
		//mq推送
		JSONObject object = JSONObject.parseObject(param);
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(object.getString("housefid"));
		updateHouseBaseMq(houseBaseMsgEntity);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#issueHouse(java.lang.String)
	 */
	@Override
	public String issueHouse(String param) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		
		JSONObject object = JSONObject.parseObject(param);
		String houseFid = object.getString("houseFid");
		String lanUid = object.getString("lanUid");
		
		
		
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(lanUid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东FID为空");
			
		}
		
		HouseBaseMsgEntity houseBaseMsg = houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseFid);
		if(Check.NuNObj(houseBaseMsg)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源未查询到");
			return dto.toJsonString();
		}
		dto.putValue("operateSeq", houseBaseMsg.getOperateSeq());
		
		String cameramanName = houseBaseMsg.getCameramanName();
		String cameramanMobile = houseBaseMsg.getCameramanMobile();
		if (Check.NuNStr(cameramanName) || Check.NuNStr(cameramanMobile)) {
			houseBaseMsg.setCameramanName(HouseConstant.HOUSE_CAMERAMAN_NAME);
			houseBaseMsg.setCameramanMobile(HouseConstant.HOUSE_CAMERAMAN_MOBILE);
		}
		//判断是否修改过押金和床位信息key
		StringBuilder flagKey=new StringBuilder();
		String issue=null;
		//发布房源(整租)
		if(houseBaseMsg.getRentWay()==RentWayEnum.HOUSE.getCode()){
			flagKey.append(houseFid).append("issue");
			try {
				issue=redisOperations.get(flagKey.toString());
			} catch (Exception e){
				LogUtil.error(LOGGER, "发布时获取是否修改过押金和床位标志key{},{}",flagKey, e.getMessage());
			}
			/****************校验是否是审核驳回没有修改就进行发布房源 @Author:busj @Date:2017/9/22**************/
			//查询审核不通过原因
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("houseFid", houseFid);
			HouseOperateLogEntity operateLogEntity=houseIssueServiceImpl.findLastHouseLog(paramMap);
			if(houseBaseMsg.getHouseStatus() == HouseStatusEnum.ZPSHWTG.getCode()
					&&!(HouseAuditCauseEnum.REJECT.LANDLORD_INFO.getCode()+"").equals(operateLogEntity.getAuditCause())
					&&!(HouseAuditCauseEnum.REJECT.OTHER.getCode()+"").equals(operateLogEntity.getAuditCause())
					&&Check.NuNStr(issue)){
				HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.HOUSE.getCode());
				houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
				List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
				List<HousePicMsgEntity> noAuditPicList = troyHouseMgtServiceImpl.findNoAuditAddHousePicList(houseFid, null);
				if(Check.NuNCollection(houseFieldAuditLogVoList)&&Check.NuNCollection(noAuditPicList)){
					dto.setErrCode(1);
					dto.setMsg("请按照审核未通过原因修改后再次提交发布房源");
					return dto.toJsonString();
				}
			}
			/****************校验是否是审核驳回没有修改就进行发布房源**************/
			houseIssueServiceImpl.issueHouseForPc(houseBaseMsg, lanUid);
		}
		
		if(houseBaseMsg.getRentWay()==RentWayEnum.ROOM.getCode()){
			List<HouseRoomMsgEntity> roomList=houseIssueServiceImpl.findRoomListByHouseBaseFid(houseFid);
			if(!Check.NuNCollection(roomList)){
				for(HouseRoomMsgEntity room:roomList){
					houseIssueServiceImpl.issueRoomForPc(room, lanUid,houseBaseMsg);
				}
			}
		}
		try {
			redisOperations.del(flagKey.toString());
		} catch (Exception e){
			LogUtil.error(LOGGER, "删除修改过押金和床位的标志key{},{}",flagKey, e.getMessage());
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#getHouseRoomMsg(java.lang.String)
	 */
	@Override
	public String getHouseRoomBaseMsgList(String houseFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		List<HouseRoomBaseMsg> list = houseIssueServicePcImpl.getHouseRoomBaseMsgList(houseFid);
		dto.putValue("list", list);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#setRoomDefaultPic(java.lang.String)
	 */
	@Override
	public String setRoomDefaultPic(String param) {
		DataTransferObject dto = new DataTransferObject();
		RoomDefaultPicDto defaultPicParam = JsonEntityTransform.json2Object(param, RoomDefaultPicDto.class);
		if(Check.NuNObj(defaultPicParam)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if(Check.NuNStr(defaultPicParam.getHouseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		if(Check.NuNStr(defaultPicParam.getRoomFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间FID为空");
			return dto.toJsonString();
		}
		if(Check.NuNStr(defaultPicParam.getPicFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("图片FID为空");
			return dto.toJsonString();
		}
		
		HouseBaseMsgEntity houseBaseMsgEntity = houseManageServiceImpl.getHouseBaseMsgEntityByFid(defaultPicParam.getHouseFid());
		if(houseBaseMsgEntity.getRentWay() == RentWayEnum.HOUSE.getCode()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源类型错误");
			return dto.toJsonString();
		}
		int count = houseIssueServicePcImpl.setRoomDefaultPic(defaultPicParam);
		dto.putValue("count", count);
		//mq推送
		updateHouseBaseMq(houseBaseMsgEntity);
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 更新房源推送mq
	 *
	 * @author bushujie
	 * @created 2016年8月25日 下午2:32:33
	 *
	 * @param houseBaseMsgEntity
	 */
	private void updateHouseBaseMq(HouseBaseMsgEntity houseBaseMsgEntity){
		//房源发布以后推送mq-
		if(HouseIssueStepEnum.SIX.getCode()<=houseBaseMsgEntity.getOperateSeq()){
			if(!Check.NuNStr(houseBaseMsgEntity.getFid())){
				//通知mq 更新房源索引信息 不区别当前房源的状态
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(houseBaseMsgEntity.getFid(), null,
						houseBaseMsgEntity.getRentWay(),null,null));
				// 推送队列消息
				try {
					rabbitMqSendClient.sendQueue(queueName, pushContent);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "mq推送异常:{}", pushContent);
					e.printStackTrace();
				}
				LogUtil.info(LOGGER, "发布房源成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
		}
	}

	/**
	 * PC端 分租发布房间
	 * add by jixd
	 */
	@Override
	public String issueRooms(String param) {
		DataTransferObject dto = new DataTransferObject();
		JSONObject jsonObj = JSONObject.parseObject(param);
		
		String houseFid = jsonObj.getString("houseFid");
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto.toJsonString();
		}
		JSONArray jsonArray = jsonObj.getJSONArray("roomList");
		if(jsonArray == null || jsonArray.size() == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间FID为空");
			return dto.toJsonString();
		}
		List<String> list = new ArrayList<>();
		for(int i = 0;i<jsonArray.size();i++){
			list.add(jsonArray.getString(i));
			HouseRoomMsgEntity houseRoomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(jsonArray.getString(i));
			/****************校验是否是审核驳回没有修改就进行发布房源 @Author:lusp @Date:2017/9/6**************/
			//查询审核不通过原因
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("houseFid", houseFid);
			paramMap.put("roomFid", jsonArray.getString(i));
			String issue=null;
			try {
				issue=redisOperations.get(jsonArray.getString(i)+"issue");
			} catch (Exception e){
				LogUtil.error(LOGGER, "发布时获取是否修改过押金和床位标志key{},{}",jsonArray.getString(i)+"issue", e.getMessage());
			}
			HouseOperateLogEntity operateLogEntity=houseIssueServiceImpl.findLastHouseLog(paramMap);
			if(houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.ZPSHWTG.getCode()
					&&!(HouseAuditCauseEnum.REJECT.LANDLORD_INFO.getCode()+"").equals(operateLogEntity.getAuditCause())
					&&!(HouseAuditCauseEnum.REJECT.OTHER.getCode()+"").equals(operateLogEntity.getAuditCause())
					&&Check.NuNStr(issue)){
				HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRoomFid(jsonArray.getString(i));
				houseUpdateFieldAuditNewlogEntity.setRentWay(RentWayEnum.ROOM.getCode());
				houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
				List<HouseFieldAuditLogVo> houseFieldAuditLogVoList = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlogEntity);
				List<HousePicMsgEntity> noAuditPicList = troyHouseMgtServiceImpl.findNoAuditAddHousePicList(houseFid, jsonArray.getString(i));
				if(Check.NuNCollection(houseFieldAuditLogVoList)&&Check.NuNCollection(noAuditPicList)){
					dto.setErrCode(1);
					dto.setMsg("请按照审核未通过原因修改后再次提交发布房源");
					return dto.toJsonString();
				}
			}
			try {
				redisOperations.del(jsonArray.getString(i)+"issue");
			} catch (Exception e){
				LogUtil.error(LOGGER, "删除是否修改过押金和床位标志key{},{}",jsonArray.getString(i)+"issue", e.getMessage());
			}
			/****************校验是否是审核驳回没有修改就进行发布房源**************/
		}
		int count = houseIssueServicePcImpl.updateRoomsStatusF(houseFid, list, HouseStatusEnum.YFB.getCode());
		dto.putValue("count", count);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.house.api.inner.HouseIssuePcService#updateHouseConf(java.lang.String)
	 */
	@Override
	public String updateHouseConf(String param) {
		DataTransferObject dto=new DataTransferObject();
		List<HouseConfMsgEntity> list=JsonEntityTransform.json2ObjectList(param, HouseConfMsgEntity.class);
		if(Check.NuNCollection(list)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("配置信息为空！");
			return dto.toJsonString();
		}
		//更新房源基本信息
		houseIssueServicePcImpl.updateHouseConf(list);
		return dto.toJsonString();
	}

	/**
	 * 删除房源图片
	 */
	@Override
	public String deleteHousePicMsgByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			HousePicDelDto housePicDto= JsonEntityTransform.json2Object(paramJson, HousePicDelDto.class);
			
			String houseFid = housePicDto.getHouseBaseFid();
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto.toJsonString();
			}
			String picFid = housePicDto.getHousePicFid();
			if(Check.NuNStr(picFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片fid为空");
				return dto.toJsonString();
			}
			Integer picType = housePicDto.getPicType();
			if(Check.NuNObj(picType)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型为空");
				return dto.toJsonString();
			}
			
			HouseBaseExtDto houseBaseExtDto=houseIssueServiceImpl.findHouseBaseExtDtoByHouseBaseFid(housePicDto.getHouseBaseFid());
			Integer rentWay = houseBaseExtDto.getRentWay();
			
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				
				//先判断是否为封面照片
				String defaultPic = houseBaseExtDto.getHouseBaseExt().getDefaultPicFid();
				if(!Check.NuNStr(defaultPic) && picFid.equals(defaultPic)){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("封面照片不允许删除");
					return dto.toJsonString();
				}
				if(houseBaseExtDto.getHouseStatus() == HouseStatusEnum.SJ.getCode()){
					
					int count=houseIssueServiceImpl.getHousePicCount(housePicDto.getHouseBaseFid(),null, housePicDto.getPicType());
					if(picType == HousePicTypeEnum.WS.getCode() || picType == HousePicTypeEnum.SW.getCode()){
						if(count <= 2){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg("照片数量不能少于2张");
							return dto.toJsonString();
						}
					}else{
						if(count <= 1){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg("照片数量不能少于1张");
							return dto.toJsonString();
						}
					}
					
				}
				
			}
			
			//如果出租类型为分租
			if(rentWay == RentWayEnum.ROOM.getCode()){
				List<HouseRoomMsgEntity> roomList = houseIssueServiceImpl.findRoomListByHouseBaseFid(houseBaseExtDto.getFid());
				//先判断图片是否是默认图片
				for(HouseRoomMsgEntity roomMsg : roomList){
					//if(roomMsg.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
						String defaultPic = roomMsg.getDefaultPicFid();
						if(!Check.NuNStr(defaultPic) && picFid.equals(defaultPic)){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg("封面照片不允许删除");
							return dto.toJsonString();
						}
					//}
				}
				if(picType == HousePicTypeEnum.WS.getCode()){
					HousePicMsgEntity housePicMsgEntity = houseIssueServiceImpl.findHousePicByFid(housePicDto.getHousePicFid());
					if(Check.NuNObj(housePicMsgEntity)){
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("图片不存在");
						return dto.toJsonString();
					}
					
					String roomfid = housePicMsgEntity.getRoomFid();
					if(!Check.NuNStr(roomfid)){
						HouseRoomMsgEntity roomMsgEntity = houseIssueServiceImpl.findHouseRoomMsgByFid(roomfid);
						//判断是否删除的是别的房间的照片
							if(roomMsgEntity.getRoomStatus()==HouseStatusEnum.YFB.getCode()){
								dto.setErrCode(DataTransferObject.ERROR);
								dto.setMsg("不能删除已发布房间的照片");
								return dto.toJsonString();
							}
						if(roomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
							int count = houseIssueServiceImpl.getHousePicCount(housePicDto.getHouseBaseFid(),null, housePicDto.getPicType());
							if(count <= 2){
								dto.setErrCode(DataTransferObject.ERROR);
								dto.setMsg("照片数量不能少于2张");
								return dto.toJsonString();
							}
						}
					}
				}
			}
			
			houseIssueServiceImpl.deleteHousePicMsgByFid(housePicDto.getHousePicFid());
			
			if(!Check.NuNStr(housePicDto.getHouseBaseFid())){
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息开始...");
				String pushContent = JsonEntityTransform.Object2Json(new HouseMq(housePicDto.getHouseBaseFid(),
						housePicDto.getHouseRoomFid(), RentWayEnum.HOUSE.getCode(), null, null));
				// 推送队列消息
				rabbitMqSendClient.sendQueue(queueName, pushContent);
				LogUtil.info(LOGGER, "删除图片成功,推送队列消息结束,推送内容:{}", pushContent);
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
