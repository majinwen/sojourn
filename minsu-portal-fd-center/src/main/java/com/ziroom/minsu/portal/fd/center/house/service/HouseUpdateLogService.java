/**
 * @FileName: HouseUpdateLogService.java
 * @Package com.ziroom.minsu.api.house.service
 * 
 * @author yd
 * @created 2017年7月11日 上午10:07:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.house.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDescDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HousePhyPcDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.WaitUpdateHouseInfoDto;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.pc.dto.HouseIssueDescDto;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源 修改记录 保存日志</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("fd.houseUpdateLogService")
public class HouseUpdateLogService{


	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseUpdateLogService.class);


	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;


	/**
	 * 
	 * 查询 待修改的房源历史信息
	 *
	 * @author yd
	 * @created 2017年7月7日 下午2:42:37
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public HouseUpdateHistoryLogDto findWaitUpdateHouseInfo(String houseFid,String roomFid,Integer rentWay){

		if(Check.NuNStr(houseFid)){
			LogUtil.info(LOGGER, "【房源修改记录保存-PC原房源数据查询】参数错误houseFid={},rentWay={}", houseFid,rentWay);
			return null;
		}

		WaitUpdateHouseInfoDto waitUpdateHouseInfo = new WaitUpdateHouseInfoDto();
		waitUpdateHouseInfo.setHouseFid(houseFid);
		waitUpdateHouseInfo.setRentWay(rentWay);
		waitUpdateHouseInfo.setRoomFid(roomFid);
		DataTransferObject   dto = JsonEntityTransform.json2DataTransferObject(this.houseUpdateHistoryLogService.findWaitUpdateHouseInfo(JsonEntityTransform.Object2Json(waitUpdateHouseInfo)));

		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "【房源修改记录保存-PC原房源数据查询失败】msg={},houseFid={},roomFid={},rentWay={}", dto.getMsg(),houseFid,roomFid,rentWay);
			return null;
		}
		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = dto.parseData("houseUpdateHistoryLogDto", new TypeReference<HouseUpdateHistoryLogDto>() {
		});

		return houseUpdateHistoryLogDto;
	}

	/**
	 * 
	 * 保存物理信息 修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(HousePhyPcDto housePhyPcDto,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(housePhyPcDto,houseUpdateHistoryLogDto)&&!Check.NuNStr(housePhyPcDto.getHouseBaseFid())){

				HousePhyMsgEntity housePhyMsgEntity = housePhyPcDto;

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

				//更新房源基础表
				HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
				houseBaseMsgEntity.setFid(housePhyPcDto.getHouseBaseFid());
				houseBaseMsgEntity.setHouseAddr(sb.toString());
				houseBaseMsgEntity.setHouseType(housePhyPcDto.getHouseType());
				houseBaseMsgEntity.setRentWay(housePhyPcDto.getRentWay());


				//更新房源扩展信息表
				HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseBaseFid(housePhyPcDto.getHouseBaseFid());
				houseBaseExtEntity.setHouseStreet(housePhyPcDto.getHouseStreet());
				houseBaseExtEntity.setDetailAddress(housePhyPcDto.getDetailAddress());

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsgEntity);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
				houseUpdateHistoryLogDto.setHousePhyMsg(housePhyMsgEntity);
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【PC物理信息-保存修改记录】返回结果dtoJson={},houseBaseFid={}", dtoJson,housePhyPcDto.getHouseBaseFid());
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【PC物理信息-保存修改记录异常】e={}",e);
		}
	}

	/**
	 * 
	 *更新房源基本信息和扩展信息 修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(HouseBaseExtDto houseBaseExtDto,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(houseBaseExtDto,houseUpdateHistoryLogDto)&&!Check.NuNStr(houseBaseExtDto.getFid())
					&&!Check.NuNObj(houseUpdateHistoryLogDto.getRentWay())){

				//更新房源基础表
				HouseBaseMsgEntity houseBaseMsgEntity=houseBaseExtDto;

				//更新房源扩展信息表
				HouseBaseExtEntity houseBaseExtEntity=houseBaseExtDto.getHouseBaseExt();

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsgEntity);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【PC更新房源基本信息和扩展信息-保存修改记录】返回结果dtoJson={},houseBaseFid={}", dtoJson,houseBaseExtDto.getFid());
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【PC更新房源基本信息和扩展信息-保存修改记录异常】e={}",e);
		}
	}



	/**
	 * 
	 *更新房源描述信息 修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(HouseIssueDescDto houseDesc,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(houseDesc,houseUpdateHistoryLogDto)&&!Check.NuNStr(houseDesc.getHouseFid())){

				//房源基础表
				HouseBaseMsgEntity houseBaseMsgEntity= new HouseBaseMsgEntity();

				houseBaseMsgEntity.setHouseName(houseDesc.getHouseName());

				//更新房源扩展信息表
				HouseDescEntity desc = new HouseDescEntity();
				desc.setHouseDesc(houseDesc.getHouseDesc());
				desc.setHouseAroundDesc(houseDesc.getHouseAround());

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsgEntity);
				houseUpdateHistoryLogDto.setHouseDesc(desc);
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【PC更新房源描述信息-保存修改记录】返回结果dtoJson={},houseBaseFid={}", dtoJson,houseDesc.getHouseFid());
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【PC更新房源描述信息-保存修改记录异常】e={}",e);
		}
	}


	/**
	 * 
	 *更新房源描述信息 修改记录（整租）
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(String houseInfo,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(houseUpdateHistoryLogDto)&&!Check.NuNStr(houseInfo)){

				JSONObject houseObj = JSONObject.parseObject(houseInfo);
				JSONArray roomsArray = houseObj.getJSONArray("rooms");
				String houseFid = houseObj.getString("houseFid");
				if(Check.NuNStr(houseFid)||Check.NuNObj(roomsArray)){
					LogUtil.error(LOGGER, "【pc整租保存房源修改记录-房间信息】houseInfo={}", houseInfo);
					return ;
				}

				//房源基础表
				HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
				houseBaseMsgEntity.setFid(houseFid);
				houseBaseMsgEntity.setRoomNum(houseObj.getInteger("roomNum"));
				houseBaseMsgEntity.setHallNum(houseObj.getInteger("hallNum"));
				houseBaseMsgEntity.setToiletNum(houseObj.getInteger("toiletNum"));
				houseBaseMsgEntity.setKitchenNum(houseObj.getInteger("kitchenNum"));
				houseBaseMsgEntity.setBalconyNum(houseObj.getInteger("balconyNum"));

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsgEntity);
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
				//如果调用houseUpdateHistoryLogService.saveHistoryLog()方法，houseUpdateHistoryLogDto中的houseBaseFid和rentWay必传，分租roomFid必传   @lusp
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【pc整租保存房源修改记录-房间信息】返回结果dtoJson={},houseBaseFid={}", dtoJson,houseFid);
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【pc整租保存房源修改记录-房间信息】e={}",e);
		}
	}


	/**
	 * 
	 *更新房源可选信息 修改记录（整租）
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(HouseBaseExtDescDto paramDto,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(houseUpdateHistoryLogDto,paramDto)&&!Check.NuNStr(paramDto.getHouseBaseFid())){
				//房源基础表
				HouseBaseExtEntity houseBaseExtEntity=paramDto;

				int rentWay = houseUpdateHistoryLogDto.getRentWay();

				if(rentWay == RentWayEnum.ROOM.getCode()&&!Check.NuNObj(paramDto.getHouseDescEntity())){
                   HouseRoomExtEntity  houseRoomExtEntity = new HouseRoomExtEntity();
                   houseRoomExtEntity.setRoomRules(paramDto.getHouseDescEntity().getHouseRules());
                   houseUpdateHistoryLogDto.setHouseRoomExt(houseRoomExtEntity);
				}

				if(rentWay == RentWayEnum.HOUSE.getCode()){
					HouseDescEntity houseDescEntity = paramDto.getHouseDescEntity();
					houseUpdateHistoryLogDto.setHouseDesc(houseDescEntity);
				}
			
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【pc整租保存房源修改记录-可选信息】返回结果dtoJson={},houseBaseFid={},roomFid", dtoJson,paramDto.getHouseBaseFid(),houseUpdateHistoryLogDto.getRoomFid());
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【pc整租保存房源修改记录-可选信息】e={}",e);
		}
	}
	
	/**
	 * 
	 * 待审核字段值map
	 *
	 * @author bushujie
	 * @created 2017年8月2日 上午9:59:15
	 *
	 * @param list
	 * @return
	 * @throws SOAParseException 
	 */
	public Map<String , HouseFieldAuditLogVo> houseFieldAuditLogVoConvertMap(String houseFid,String roomFid,Integer rentWay,Integer fieldAuditStatu) throws SOAParseException{
		Map<String , HouseFieldAuditLogVo> resultMap=new HashMap<String,HouseFieldAuditLogVo>();
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog=new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlog.setHouseFid(houseFid);
		houseUpdateFieldAuditNewlog.setRentWay(rentWay);
		houseUpdateFieldAuditNewlog.setRoomFid(roomFid);
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(fieldAuditStatu);
		String resultJson=troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlog));
		List<HouseFieldAuditLogVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseFieldAuditLogVo.class);
		for(HouseFieldAuditLogVo vo:list){
			resultMap.put(vo.getFieldPath(), vo);
		}
		return resultMap;
	}

}
